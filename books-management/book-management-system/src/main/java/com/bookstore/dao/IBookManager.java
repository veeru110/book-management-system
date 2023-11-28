package com.bookstore.dao;

import com.bookstore.model.Book;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import com.bookstore.vo.BookVo;
import com.bookstore.vo.BooksSaleInfo;

import java.util.List;
import java.util.Objects;

public interface IBookManager {
    void updateStockAfterSale(Book book, BuyerMembershipHistory buyerMembershipHistory, Double actualPrice, Double discountAmount,User buyer);

    void stockInward(Book book, User stockInwardUser, Double transactionAmount, Integer quantity);

    List<Book> getAllBooksByCategory(String category);

    Book getBookByNameAndEdition(String bookName, Integer edition);

    List<BookVo> getAvailableBooksStockInfo();

    List<BooksSaleInfo> getBookSalesInfo();
}
