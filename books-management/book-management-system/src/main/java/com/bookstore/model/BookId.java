package com.bookstore.model;

import java.io.Serializable;
import java.util.Objects;

public class BookId implements Serializable {

    private final String bookName;

    private final Integer edition;

    public BookId(String bookName, Integer edition) {
        this.bookName = bookName;
        this.edition = edition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return bookName.equals(bookId.bookName) && edition.equals(bookId.edition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName, edition);
    }
}
