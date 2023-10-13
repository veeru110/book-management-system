package com.bookstore.service;

import com.bookstore.dao.IBookRackManager;
import com.bookstore.model.BookRack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BookRackManagementServiceImpl implements BookRackManagementService {

    private final IBookRackManager bookRackManager;

    @Autowired
    public BookRackManagementServiceImpl(IBookRackManager bookRackManager) {
        this.bookRackManager = bookRackManager;
    }

    @Override
    public void saveNewRacks(String racks) {
        saveNewRacks(Arrays.asList(racks.split(",")));
    }

    @Override
    public void saveNewRacks(List<String> racks) {
        for (String rack : racks) {
            rack = StringUtils.trim(rack);
            BookRack bookRack = bookRackManager.findByRackName(rack);
            if (Objects.isNull(bookRack)) {
                bookRack = new BookRack();
                bookRack.setRackName(rack);
                bookRackManager.save(bookRack);
            }
        }
    }

    @Override
    public List<String> getAllGenres() {
        return bookRackManager.getAllGenres();
    }
}
