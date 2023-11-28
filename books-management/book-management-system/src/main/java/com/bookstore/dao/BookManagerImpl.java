package com.bookstore.dao;

import com.bookstore.constants.BookStoreTransactionType;
import com.bookstore.model.Book;
import com.bookstore.model.BookStoreTransactionHistory;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.BookStoreTransactionHistoryRepository;
import com.bookstore.vo.BookVo;
import com.bookstore.vo.BooksSaleInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BookManagerImpl implements IBookManager {

    private static final Logger logger = LogManager.getLogger(BookManagerImpl.class);

    private final BookRepository bookRepository;
    private final BookStoreTransactionHistoryRepository bookStoreTransactionHistoryRepository;

    @Autowired
    public BookManagerImpl(BookRepository bookRepository, BookStoreTransactionHistoryRepository bookStoreTransactionHistoryRepository) {
        this.bookRepository = bookRepository;
        this.bookStoreTransactionHistoryRepository = bookStoreTransactionHistoryRepository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void updateStockAfterSale(Book book, BuyerMembershipHistory buyerMembershipHistory, Double actualPrice, Double discountAmount, User buyer) {
        bookRepository.save(book);
        BookStoreTransactionHistory bookStoreTransactionHistory = new BookStoreTransactionHistory();
        bookStoreTransactionHistory.setBookBought(book);
        if (Objects.isNull(buyerMembershipHistory)) {
            bookStoreTransactionHistory.setUser(buyer);
        } else {
            bookStoreTransactionHistory.setBuyerMembershipHistory(buyerMembershipHistory);
            bookStoreTransactionHistory.setUser(buyerMembershipHistory.getUser());
        }
        bookStoreTransactionHistory.setActualPrice(actualPrice);
        bookStoreTransactionHistory.setDiscountAmount(discountAmount);
        bookStoreTransactionHistory.setTransactionAmount(actualPrice - discountAmount);
        bookStoreTransactionHistory.setTransactionType(BookStoreTransactionType.SALE);
        bookStoreTransactionHistoryRepository.save(bookStoreTransactionHistory);
    }

    @Override
    public void stockInward(Book book, User stockInwardUser, Double transactionAmount, Integer quantity) {
        bookRepository.save(book);
        BookStoreTransactionHistory bookStoreTransactionHistory = new BookStoreTransactionHistory();
        bookStoreTransactionHistory.setUser(stockInwardUser);
        bookStoreTransactionHistory.setQuantity(quantity);
        bookStoreTransactionHistory.setBookBought(book);
        bookStoreTransactionHistory.setActualPrice(transactionAmount);
        bookStoreTransactionHistory.setTransactionAmount(transactionAmount);
        bookStoreTransactionHistory.setTransactionType(BookStoreTransactionType.STOCK_INWARD);
        bookStoreTransactionHistoryRepository.save(bookStoreTransactionHistory);
    }

    @Override
    public List<Book> getAllBooksByCategory(String category) {
        return bookRepository.findAllByRack(category);
    }

    @Override
    public Book getBookByNameAndEdition(String bookName, Integer edition) {
        return bookRepository.findByBookNameAndEdition(bookName, edition);
    }

    private BookVo getStockDataOne(Object[] data) {
        try {
            String bookName = (String) data[0];
            Integer edition = (Integer) data[1];
            String authorNames = (String) data[2];
            Long availableStock = (Long) data[3];
            String genre = (String) data[4];
            Double bookPrice = (Double) data[5];

            BookVo bookVo = new BookVo();
            bookVo.setBookName(bookName);
            bookVo.setBookPrice(bookPrice);
            bookVo.setAvailableStock(availableStock);
            bookVo.setGenre(genre);
            bookVo.setAuthorNames(authorNames);
            bookVo.setEdition(edition);
            return bookVo;
        } catch (Exception e) {
            logger.error("Error while preparing stock info", e);
            return null;
        }
    }

    @Override
    public List<BookVo> getAvailableBooksStockInfo() {
        List<Object[]> stockData = bookRepository.getAvailableBookStock();
        return stockData.stream().map(this::getStockDataOne).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private BooksSaleInfo doGetBooksSaleInfoOne(Object[] saleData) {
        try {
            String genre = (String) saleData[0];
            String bookName = (String) saleData[1];
            Integer edition = (Integer) saleData[2];
            Long totalSales = (Long) saleData[3];
            Double salePrice = (Double) saleData[4];

            BooksSaleInfo booksSaleInfo = new BooksSaleInfo();
            booksSaleInfo.setBookName(bookName);
            booksSaleInfo.setTotalSales(totalSales);
            booksSaleInfo.setEdition(edition);
            booksSaleInfo.setGenre(genre);
            booksSaleInfo.setTotalAmount(salePrice);
            return booksSaleInfo;
        } catch (Exception e) {
            logger.error("Error parsing sales info", e);
            return null;
        }
    }

    @Override
    public List<BooksSaleInfo> getBookSalesInfo() {
        List<Object[]> salesData = bookStoreTransactionHistoryRepository.getBooksSalesInfo();
        return salesData.stream().map(this::doGetBooksSaleInfoOne).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
