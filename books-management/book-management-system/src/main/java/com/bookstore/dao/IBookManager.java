package com.bookstore.dao;

import com.bookstore.model.Book;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;

import java.util.List;

public interface IBookManager {
    void updateStockAfterSale(Book book, BuyerMembershipHistory buyerMembershipHistory, Double actualPrice, Double discountAmount);

    void stockInward(Book book, User stockInwardUser, Double transactionAmount, Integer quantity);

    List<Book> getAllBooksByCategory(String category);

    Book getBookByNameAndEdition(String bookName, Integer edition);
}
