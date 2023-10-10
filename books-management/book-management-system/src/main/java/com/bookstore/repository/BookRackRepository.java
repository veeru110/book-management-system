package com.bookstore.repository;

import com.bookstore.model.BookRack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRackRepository extends JpaRepository<BookRack,Integer> {
}
