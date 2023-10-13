package com.bookstore.dao;

import com.bookstore.model.BookRack;

import java.util.List;

public interface IBookRackManager {

    BookRack findByRackName(String rackName);

    void save(BookRack bookRack);

    List<String> getAllGenres();
}
