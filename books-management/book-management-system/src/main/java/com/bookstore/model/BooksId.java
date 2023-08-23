package com.bookstore.model;

import java.io.Serializable;
import java.util.Objects;

public class BooksId implements Serializable {

    private String bookName;

    private Integer edition;

    public BooksId(String bookName, Integer edition) {
        this.bookName = bookName;
        this.edition = edition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksId booksId = (BooksId) o;
        return bookName.equals(booksId.bookName) && edition.equals(booksId.edition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName, edition);
    }
}
