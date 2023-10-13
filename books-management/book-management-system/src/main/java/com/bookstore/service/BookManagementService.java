package com.bookstore.service;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.vo.ErrorVo;

import java.util.List;

public interface BookManagementService {
    List<ErrorVo> addBooksStock(List<BooksCommand> booksCommands) throws RuntimeException;

    List<ErrorVo> saleBooks(List<BookSaleCommand> bookSaleCommands);
}
