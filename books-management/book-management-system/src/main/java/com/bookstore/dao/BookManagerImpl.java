package com.bookstore.dao;

import com.bookstore.model.Books;
import com.bookstore.repository.BooksRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookManagerImpl implements IBookManager{

    private final BooksRepository booksRepository;

    public BookManagerImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void save(Books book) {
        booksRepository.save(book);
    }

    @Override
    public List<Books> getAllBooksByCategory(String category) {
        return booksRepository.findAllByRack(category);
    }

    @Override
    public Books getBookByNameAndEdition(String bookName, Integer edition) {
        return booksRepository.findByBookNameAndEdition(bookName,edition);
    }
}
