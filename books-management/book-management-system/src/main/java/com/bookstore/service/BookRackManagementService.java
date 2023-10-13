package com.bookstore.service;

import java.util.List;

public interface BookRackManagementService {
    void saveNewRacks(String racks);
    void saveNewRacks(List<String> racks);

    List<String> getAllGenres();
}
