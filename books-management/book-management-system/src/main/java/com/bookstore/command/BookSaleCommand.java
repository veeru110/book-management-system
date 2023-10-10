package com.bookstore.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookSaleCommand {

    @NotEmpty
    @NotNull
    private String bookName;

    @NotEmpty
    @NotNull
    private Integer edition;

    @NotNull
    private Integer countRequired;

    public Integer getCountRequired() {
        return countRequired;
    }

    public void setCountRequired(Integer countRequired) {
        this.countRequired = countRequired;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}
