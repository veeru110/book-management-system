package com.bookstore.controller;

import com.bookstore.command.BookSaleCommand;
import com.bookstore.command.BooksCommand;
import com.bookstore.service.BookManagementService;
import com.bookstore.service.BookRackManagementService;
import com.bookstore.vo.ErrorVo;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookManagementService bookManagementService;
    private final BookRackManagementService bookRackManagementService;

    @Value("${books.genre.types}")
    private String bookGenreTypes;

    @Autowired
    public BooksController(BookManagementService bookManagementService, BookRackManagementService bookRackManagementService) {
        this.bookManagementService = bookManagementService;
        this.bookRackManagementService = bookRackManagementService;
    }

    @PostConstruct
    public void booksContractInit() {
        bookRackManagementService.saveNewRacks(bookGenreTypes);
    }

    @PreAuthorize("hasRole('ADMIN','SELLER')")
    @PostMapping("/addBooksStock")
    public ResponseEntity<Object> addBooksStock(@RequestBody List<BooksCommand> booksCommands) throws MessagingException, TemplateException, IOException {
        List<ErrorVo> errorVos = bookManagementService.addBooksStock(booksCommands);
        if (CollectionUtils.isEmpty(errorVos)) {
            return new ResponseEntity<>(errorVos, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/buyBook")
    public ResponseEntity<Object> buyBook(@Valid @RequestBody List<BookSaleCommand> bookSaleCommands) {
        List<ErrorVo> errorVos = bookManagementService.saleBooks(bookSaleCommands);
        if (CollectionUtils.isEmpty(errorVos)) {
            return new ResponseEntity<>(errorVos, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BUYER','ADMIN','SELLER')")
    @PostMapping("/allAvailableGenres")
    public ResponseEntity<Set<String>> getAllGenres() {
        Set<String> allGenres = bookRackManagementService.getAllGenres();
        return new ResponseEntity<>(allGenres, HttpStatus.OK);
    }

}
