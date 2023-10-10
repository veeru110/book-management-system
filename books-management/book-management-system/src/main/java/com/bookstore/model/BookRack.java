package com.bookstore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book_rack")
public class BookRack {

    @Id
    @Column(name = "rack_name", nullable = false)
    private String rackName;

    @OneToMany(mappedBy = "rack")
    private List<Books> books;

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }


    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookRack{");
        sb.append("rackName='").append(rackName).append('\'');
        sb.append(", books=").append(books);
        sb.append('}');
        return sb.toString();
    }
}
