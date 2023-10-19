package com.bookstore.dao;

import com.bookstore.model.BookRack;
import com.bookstore.repository.BookRackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class IBookRackManagerImpl implements IBookRackManager{

    private final BookRackRepository bookRackRepository;

    @Autowired
    public IBookRackManagerImpl(BookRackRepository bookRackRepository) {
        this.bookRackRepository = bookRackRepository;
    }

    @Override
    public BookRack findByRackName(String rackName) {
        return bookRackRepository.findByRackName(rackName);
    }

    @Override
    public void save(BookRack bookRack) {
        bookRackRepository.save(bookRack);
    }

    @Override
    public Set<String> getAllGenres() {
        return bookRackRepository.getAllGenres();
    }

    @Override
    public Set<BookRack> getAllBookRacks() {
        return new HashSet<>(bookRackRepository.findAll());
    }
}
