package com.bookstore.dao;

import com.bookstore.model.BookRack;

import java.util.List;
import java.util.Set;

public interface IBookRackManager {

    BookRack findByRackName(String rackName);

    void save(BookRack bookRack);

    Set<String> getAllGenres();

    Set<BookRack> getAllBookRacks();
}
