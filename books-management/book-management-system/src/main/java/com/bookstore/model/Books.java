package com.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
@IdClass(BooksId.class)
public class Books {

    @Id
    @Column(name = "book_name")
    private String bookName;

    @Id
    @Column(name = "edition")
    private Integer edition;

    @Column(name = "author_names")
    private String authorNames;

    @Column(name = "available_stock")
    private Long availableStock;

    @Column(name = "book_category")
    private String bookCategory;


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

    public String getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Books{");
        sb.append("bookName='").append(bookName).append('\'');
        sb.append(", edition=").append(edition);
        sb.append(", authorNames='").append(authorNames).append('\'');
        sb.append(", availableStock=").append(availableStock);
        sb.append(", bookCategory='").append(bookCategory).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
