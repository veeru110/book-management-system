package com.bookstore.service;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.constants.EmailEvents;
import com.bookstore.dao.IBookManager;
import com.bookstore.dao.IBookRackManager;
import com.bookstore.model.BookRack;
import com.bookstore.model.Book;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import com.bookstore.utils.UserUtils;
import com.bookstore.vo.EmailTableRow;
import com.bookstore.vo.EmailTableVo;
import com.bookstore.vo.ErrorVo;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookManagementServiceImpl implements BookManagementService {

    private static final Logger logger = LogManager.getLogger(BookManagementServiceImpl.class);

    private final IBookManager bookManager;
    private final UserUtils userUtils;
    private final MailService mailService;

    private final IBookRackManager bookRackManager;

    private static final Mapper mapper = new DozerBeanMapper();

    private static final Map<String, ReentrantLock> rackExplicitLocks = new ConcurrentHashMap<>();

    private static synchronized ReentrantLock getExplicitLockForRack(String bookCategory) {
        if (!rackExplicitLocks.containsKey(bookCategory)) {
            rackExplicitLocks.put(bookCategory, new ReentrantLock());
        }
        return rackExplicitLocks.get(bookCategory);
    }

    public BookManagementServiceImpl(IBookManager bookManager, UserUtils userUtils, MailService mailService, IBookRackManager bookRackManager) {
        this.bookManager = bookManager;
        this.userUtils = userUtils;
        this.mailService = mailService;
        this.bookRackManager = bookRackManager;
    }

    private void doInwardBookStockOne(BooksCommand booksCommand, List<ErrorVo> errorVos) {
        String rack = booksCommand.getGenre();
        ReentrantLock rackExplicitLock = getExplicitLockForRack(rack);
        logger.info("Got Explicit Lock Object for rack {}, Is Locked {}, Waiting threads {}", rack, rackExplicitLock.isLocked(), rackExplicitLock.getQueueLength());
        try {
            if (rackExplicitLock.tryLock(30, TimeUnit.SECONDS)) {
                Book book = bookManager.getBookByNameAndEdition(booksCommand.getBookName(), booksCommand.getEdition());
                if (Objects.isNull(book)) {
                    BookRack bookRack = bookRackManager.findByRackName(rack);
                    if (Objects.isNull(bookRack)) {
                        bookRack = new BookRack();
                        bookRack.setRackName(rack);
                        bookRack = bookRackManager.save(bookRack);
                    }
                    book = mapper.map(booksCommand, Book.class);
                    book.setRack(bookRack);
                    book.setAvailableStock(0L);
                    book.setBookPrice(booksCommand.getTotalPrice() / booksCommand.getStockInward());
                }
                book.setAvailableStock(book.getAvailableStock() + booksCommand.getStockInward());
                bookManager.stockInward(book, userUtils.getUser(), booksCommand.getTotalPrice(), booksCommand.getStockInward());
            }
        } catch (Exception e) {
            logger.error("Error while acquiring lock for rack " + rack, e);
            errorVos.add(new ErrorVo("Book Inward for " + booksCommand.getBookName(), e.getMessage()));
        } finally {
            logger.info("Releasing lock for rack {}", rack);
            rackExplicitLock.unlock();
        }
    }

    @Override
    public List<ErrorVo> addBooksStock(List<BooksCommand> booksCommands) throws RuntimeException, MessagingException, TemplateException, IOException {
        List<ErrorVo> errorVos = new ArrayList<>();
        EmailTableVo emailTableVo = new EmailTableVo("BookGenre", "BookName");
        for (BooksCommand booksCommand : booksCommands) {
            doInwardBookStockOne(booksCommand, errorVos);
            emailTableVo.addTableRow(new EmailTableRow(booksCommand.getGenre(), booksCommand.getBookName()));
        }
        mailService.sendEmailToAllBuyers(EmailEvents.STOCK_INWARD, emailTableVo);
        return errorVos;
    }

    private void doSaleBookOne(BookSaleCommand bookSaleCommand, List<ErrorVo> errorVos, Optional<BuyerMembershipHistory> buyerMembershipHistory, User buyer) {
        Book book = bookManager.getBookByNameAndEdition(bookSaleCommand.getBookName(), bookSaleCommand.getEdition());
        if (Objects.isNull(book)) {
            errorVos.add(new ErrorVo("No Books found with " + bookSaleCommand.getBookName() + " and edition " + bookSaleCommand.getEdition(), "Not found"));
            return;
        }
        String rack = book.getRack().getRackName();
        ReentrantLock rackExplicitLock = getExplicitLockForRack(rack);
        logger.info("Got Explicit Lock Object for rack {}, Is Locked {}, Waiting threads {}", rack, rackExplicitLock.isLocked(), rackExplicitLock.getQueueLength());
        try {
            if (rackExplicitLock.tryLock(30, TimeUnit.SECONDS)) {
                if (book.getAvailableStock() < bookSaleCommand.getCountRequired()) {
                    errorVos.add(new ErrorVo("Less stock while Book sale for " + bookSaleCommand.getBookName() + " avaialble " + book.getAvailableStock() + " required " + bookSaleCommand.getCountRequired(), ""));
                    return;
                }
                book.setAvailableStock(book.getAvailableStock() - bookSaleCommand.getCountRequired());

                double actualPrice = bookSaleCommand.getCountRequired() * book.getBookPrice();
                double discountAmount = 0d;
                BuyerMembershipHistory buyerMembership = buyerMembershipHistory.orElse(null);
                if (!Objects.isNull(buyerMembership)) {
                    discountAmount = (0.01d * buyerMembership.getMembershipType().getDiscountPercentage()) * actualPrice;
                }

                bookManager.updateStockAfterSale(book, buyerMembership, actualPrice, discountAmount, buyer);
            }
        } catch (InterruptedException e) {
            logger.error("Error while acquiring lock for rack " + rack);
            errorVos.add(new ErrorVo("Book sale for " + bookSaleCommand.getBookName(), e.getMessage()));
        } finally {
            logger.info("Releasing lock for rack {}", rack);
            rackExplicitLock.unlock();
        }
    }


    @Override
    public List<ErrorVo> saleBooks(List<BookSaleCommand> bookSaleCommands) {
        try {
            User buyer = userUtils.getUser();
            Optional<BuyerMembershipHistory> activeBuyerMembership = userUtils.getActiveBuyerMembershipHistory(buyer);
            List<ErrorVo> errorVos = new ArrayList<>();
            for (BookSaleCommand bookSaleCommand : bookSaleCommands) {
                doSaleBookOne(bookSaleCommand, errorVos, activeBuyerMembership, buyer);
            }
            return errorVos;
        } catch (Exception e) {
            logger.error("Error while books sale", e);
            throw e;
        }
    }
}
