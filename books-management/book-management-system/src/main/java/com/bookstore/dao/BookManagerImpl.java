package com.bookstore.dao;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookManagerImpl implements IBookManager{

    private final BookRepository bookRepository;

    public BookManagerImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooksByCategory(String category) {
        return bookRepository.findAllByRack(category);
    }

    @Override
    public Book getBookByNameAndEdition(String bookName, Integer edition) {
        return bookRepository.findByBookNameAndEdition(bookName,edition);
    }
}
