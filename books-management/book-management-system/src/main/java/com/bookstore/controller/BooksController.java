package com.bookstore.controller;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.service.BooksService;
import com.bookstore.vo.ErrorVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("books")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PreAuthorize("hasRole('ADMIN','SELLER')")
    @PostMapping("/addBooksStock")
    public ResponseEntity<Object> addBooksStock(@RequestBody List<BooksCommand> booksCommands) {
        List<ErrorVo> errorVos = booksService.addBooksStock(booksCommands);
        if (CollectionUtils.isEmpty(errorVos)) {
            return new ResponseEntity<>(errorVos, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/buyBook")
    public ResponseEntity<Object> buyBook(@Valid @RequestBody List<BookSaleCommand> bookSaleCommands) {
        List<ErrorVo> errorVos = booksService.saleBooks(bookSaleCommands);
        if (CollectionUtils.isEmpty(errorVos)) {
            return new ResponseEntity<>(errorVos, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
