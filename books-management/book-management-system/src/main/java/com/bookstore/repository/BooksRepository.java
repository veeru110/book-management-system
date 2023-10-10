package com.bookstore.repository;

import com.bookstore.model.BookRack;
import com.bookstore.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    @Query("select * from books b where b.rack.rackName=?1")
    List<Books> findAllByRack(String rack);

    Books findByBookNameAndEdition(String bookName,Integer edition);
}
