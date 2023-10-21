package com.bookstore.service;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.dao.IBookManager;
import com.bookstore.model.BookRack;
import com.bookstore.model.Book;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.utils.UserUtils;
import com.bookstore.vo.ErrorVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookManagementServiceImpl implements BookManagementService {

    private static final Logger logger = LogManager.getLogger(BookManagementServiceImpl.class);

    private final IBookManager bookManager;
    private final UserUtils userUtils;

    private static final Mapper mapper = new DozerBeanMapper();

    private static final Map<String, ReentrantLock> rackExplicitLocks = new ConcurrentHashMap<>();

    private static synchronized ReentrantLock getExplicitLockForRack(String bookCategory) {
        if (!rackExplicitLocks.containsKey(bookCategory)) {
            rackExplicitLocks.put(bookCategory, new ReentrantLock());
        }
        return rackExplicitLocks.get(bookCategory);
    }

    public BookManagementServiceImpl(IBookManager bookManager, UserUtils userUtils) {
        this.bookManager = bookManager;
        this.userUtils = userUtils;
    }

    enum BookMovementType {
        STOCK_INWARD, SALE
    }

    private void doInwardBookStockOne(BooksCommand booksCommand, List<ErrorVo> errorVos) {
        String rack = booksCommand.getGenre();
        ReentrantLock rackExplicitLock = getExplicitLockForRack(rack);
        logger.info("Got Explicit Lock Object for rack {}, Is Locked {}, Waiting threads {}", rack, rackExplicitLock.isLocked(), rackExplicitLock.getQueueLength());
        try {
            if (rackExplicitLock.tryLock(30, TimeUnit.SECONDS)) {
                Book book = bookManager.getBookByNameAndEdition(booksCommand.getBookName(), booksCommand.getEdition());
                if (Objects.isNull(book)) {
                    BookRack bookRack = new BookRack();
                    bookRack.setRackName(rack);

                    book = mapper.map(booksCommand, Book.class);
                    book.setRack(bookRack);
                    book.setAvailableStock(0l);
                }
                book.setAvailableStock(book.getAvailableStock() + booksCommand.getStockInward());
                bookManager.stockInward(book, userUtils.getUser(), booksCommand.getTotalPrice(), booksCommand.getStockInward());
            }
        } catch (Exception e) {
            logger.error("Error while acquiring lock for rack " + rack);
            errorVos.add(new ErrorVo("Book Inward for " + booksCommand.getBookName(), e.getMessage()));
        } finally {
            logger.info("Releasing lock for rack {}", rack);
            rackExplicitLock.unlock();
        }
    }

    @Override
    public List<ErrorVo> addBooksStock(List<BooksCommand> booksCommands) throws RuntimeException {
        List<ErrorVo> errorVos = new ArrayList<>();
        for (BooksCommand booksCommand : booksCommands) {
            doInwardBookStockOne(booksCommand, errorVos);
        }
        return errorVos;
    }

    private void doSaleBookOne(BookSaleCommand bookSaleCommand, List<ErrorVo> errorVos, Optional<BuyerMembershipHistory> buyerMembershipHistory) {
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

                Double actualPrice = bookSaleCommand.getCountRequired() * book.getBookPrice();
                Double discountAmount = 0d;
                BuyerMembershipHistory buyerMembership = buyerMembershipHistory.orElse(null);
                if (!Objects.isNull(buyerMembership)) {
                    discountAmount = (0.01d * buyerMembership.getMembershipType().getDiscountPercentage()) * actualPrice;
                }

                bookManager.updateStockAfterSale(book, buyerMembership, actualPrice, discountAmount);
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
        Optional<BuyerMembershipHistory> activeBuyerMembership = userUtils.getActiveBuyerMembershipHistory();
        List<ErrorVo> errorVos = new ArrayList<>();
        for (BookSaleCommand bookSaleCommand : bookSaleCommands) {
            doSaleBookOne(bookSaleCommand, errorVos, activeBuyerMembership);
        }
        return errorVos;
    }
}
