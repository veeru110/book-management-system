package com.bookstore.vo;

import java.util.List;

public class BookStoreStock {

    private List<BookVo> booksStock;
    private Long noOfBooksInStore;

    public List<BookVo> getBooksStock() {
        return booksStock;
    }

    public void setBooksStock(List<BookVo> booksStock) {
        this.booksStock = booksStock;
    }

    public Long getNoOfBooksInStore() {
        return noOfBooksInStore;
    }

    public void setNoOfBooksInStore(Long noOfBooksInStore) {
        this.noOfBooksInStore = noOfBooksInStore;
    }
}
