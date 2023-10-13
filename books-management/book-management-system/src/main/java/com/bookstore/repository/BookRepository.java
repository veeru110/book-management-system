package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(nativeQuery = true, value = "select * from book b inner join book_rack br on b.fk_rack_id=br.book_rack and br.rack_name=:rackName")
    List<Book> findAllByRack(@Param("rackName") String rackName);

    Book findByBookNameAndEdition(String bookName, Integer edition);
}
