package com.bookstore.dao;

import com.bookstore.model.Books;

import java.util.List;

public interface IBookManager {
    void save(Books book);
    List<Books> getAllBooksByCategory(String category);

    Books getBookByNameAndEdition(String bookName, Integer edition);
}
