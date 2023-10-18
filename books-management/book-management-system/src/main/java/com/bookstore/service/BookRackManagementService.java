package com.bookstore.service;

import com.bookstore.model.BookRack;

import java.util.List;
import java.util.Set;

public interface BookRackManagementService {
    void saveNewRacks(String racks);
    void saveNewRacks(List<String> racks);

    Set<String> getAllGenres();

    Set<BookRack> getAllBookRacks();
}
