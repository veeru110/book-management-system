package com.bookstore.dao;

import com.bookstore.model.Book;

import java.util.List;

public interface IBookManager {
    void save(Book book);
    List<Book> getAllBooksByCategory(String category);

    Book getBookByNameAndEdition(String bookName, Integer edition);
}
