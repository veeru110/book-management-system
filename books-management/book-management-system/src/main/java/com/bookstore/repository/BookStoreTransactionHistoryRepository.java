package com.bookstore.repository;

import com.bookstore.model.BookStoreTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreTransactionHistoryRepository extends JpaRepository<BookStoreTransactionHistory, Integer> {
}
