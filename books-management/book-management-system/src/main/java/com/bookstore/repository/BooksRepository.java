package com.bookstore.repository;

import com.bookstore.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    @Query(nativeQuery = true, value = "select * from books b inner join book_rack br on b.fk_rack_id=br.book_rack and br.rack_name=:rackName")
    List<Books> findAllByRack(@Param("rackName") String rackName);

    Books findByBookNameAndEdition(String bookName, Integer edition);
}
