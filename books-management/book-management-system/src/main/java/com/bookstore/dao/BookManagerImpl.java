package com.bookstore.dao;

import com.bookstore.constants.BookStoreTransactionType;
import com.bookstore.model.Book;
import com.bookstore.model.BookStoreTransactionHistory;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.BookStoreTransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookManagerImpl implements IBookManager {

    private final BookRepository bookRepository;
    private final BookStoreTransactionHistoryRepository bookStoreTransactionHistoryRepository;

    @Autowired
    public BookManagerImpl(BookRepository bookRepository, BookStoreTransactionHistoryRepository bookStoreTransactionHistoryRepository) {
        this.bookRepository = bookRepository;
        this.bookStoreTransactionHistoryRepository = bookStoreTransactionHistoryRepository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void updateStockAfterSale(Book book, BuyerMembershipHistory buyerMembershipHistory, Double actualPrice, Double discountAmount) {
        bookRepository.save(book);
        BookStoreTransactionHistory bookStoreTransactionHistory = new BookStoreTransactionHistory();
        bookStoreTransactionHistory.setBookBought(book);
        bookStoreTransactionHistory.setBuyerMembershipHistory(buyerMembershipHistory);
        bookStoreTransactionHistory.setUser(buyerMembershipHistory.getUser());
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
}
