package com.bookstore.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book_rack")
public class BookRack {

    @Id
    @SequenceGenerator(name = "book_rack_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_rack_seq")
    @Column(name = "book_rack_id")
    private Integer bookRackId;

    @Column(name = "rack_name", nullable = false,unique = true)
    private String rackName; //genre

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY)
    private List<Book> books;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public BookRack(String rackName) {
        this.rackName = rackName;
    }

    public BookRack() {
    }

    public Integer getBookRackId() {
        return bookRackId;
    }

    public void setBookRackId(Integer bookRackId) {
        this.bookRackId = bookRackId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookRack{");
        sb.append("rackName='").append(rackName).append('\'');
        sb.append(", books=").append(books);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRack bookRack = (BookRack) o;
        return rackName.equals(bookRack.rackName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rackName);
    }
}
