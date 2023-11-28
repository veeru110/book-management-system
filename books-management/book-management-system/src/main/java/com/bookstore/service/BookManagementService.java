package com.bookstore.service;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.vo.ErrorVo;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface BookManagementService {
    List<ErrorVo> addBooksStock(List<BooksCommand> booksCommands) throws RuntimeException, MessagingException, TemplateException, IOException;

    List<ErrorVo> saleBooks(List<BookSaleCommand> bookSaleCommands) throws Exception;
}
