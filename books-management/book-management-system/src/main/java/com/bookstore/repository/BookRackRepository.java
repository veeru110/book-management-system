package com.bookstore.repository;

import com.bookstore.model.BookRack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRackRepository extends JpaRepository<BookRack,Integer> {

    BookRack findByRackName(String rackName);

    @Query("select rackName from BookRack where isDeleted=false")
    Set<String> getAllGenres();
}
