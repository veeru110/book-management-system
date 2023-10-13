package com.bookstore.service;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.dao.IBookManager;
import com.bookstore.model.BookRack;
import com.bookstore.model.Book;
import com.bookstore.vo.ErrorVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookManagementServiceImpl implements BookManagementService {

    private static final Logger logger = LogManager.getLogger(BookManagementServiceImpl.class);

    private final IBookManager bookManager;

    private static final Mapper mapper = new DozerBeanMapper();

    private static final Map<String, ReentrantLock> rackExplicitLocks = new ConcurrentHashMap<>();

    private static synchronized ReentrantLock getExplicitLockForRack(String bookCategory) {
        if (!rackExplicitLocks.containsKey(bookCategory)) {
            rackExplicitLocks.put(bookCategory, new ReentrantLock());
        }
        return rackExplicitLocks.get(bookCategory);
    }

    public BookManagementServiceImpl(IBookManager bookManager) {
        this.bookManager = bookManager;
    }

    enum BookMovementType {
        STOCK_INWARD, SALE
    }

    @Override
    public List<ErrorVo> addBooksStock(List<BooksCommand> booksCommands) throws RuntimeException {
        List<ErrorVo> errorVos = new ArrayList<>();
        for (BooksCommand booksCommand : booksCommands) {
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
                    bookManager.save(book);
                }
            } catch (Exception e) {
                logger.error("Error while acquiring lock for rack " + rack);
                errorVos.add(new ErrorVo("Book Inward for " + booksCommand.getBookName(), e.getMessage()));
            } finally {
                logger.info("Releasing lock for rack {}", rack);
                rackExplicitLock.unlock();
            }
        }
        return errorVos;
    }

    @Override
    public List<ErrorVo> saleBooks(List<BookSaleCommand> bookSaleCommands) {
        List<ErrorVo> errorVos = new ArrayList<>();
        for (BookSaleCommand bookSaleCommand : bookSaleCommands) {
            Book book = bookManager.getBookByNameAndEdition(bookSaleCommand.getBookName(), bookSaleCommand.getEdition());
            if (Objects.isNull(book)) {
                errorVos.add(new ErrorVo("No Books found with " + bookSaleCommand.getBookName() + " and edition " + bookSaleCommand.getEdition(), "Not found"));
                continue;
            }
            String rack = book.getRack().getRackName();
            ReentrantLock rackExplicitLock = getExplicitLockForRack(rack);
            logger.info("Got Explicit Lock Object for rack {}, Is Locked {}, Waiting threads {}", rack, rackExplicitLock.isLocked(), rackExplicitLock.getQueueLength());
            try {
                if (rackExplicitLock.tryLock(30, TimeUnit.SECONDS)) {
                    if (book.getAvailableStock() < bookSaleCommand.getCountRequired()) {
                        errorVos.add(new ErrorVo("Less stock while Book sale for " + bookSaleCommand.getBookName() + " avaialble " + book.getAvailableStock() + " required " + bookSaleCommand.getCountRequired(), ""));
                        continue;
                    }
                    book.setAvailableStock(book.getAvailableStock() - bookSaleCommand.getCountRequired());
                    bookManager.save(book);
                }
            } catch (InterruptedException e) {
                logger.error("Error while acquiring lock for rack " + rack);
                errorVos.add(new ErrorVo("Book sale for " + bookSaleCommand.getBookName(), e.getMessage()));
            } finally {
                logger.info("Releasing lock for rack {}", rack);
                rackExplicitLock.unlock();
            }
        }
        return errorVos;
    }
}
