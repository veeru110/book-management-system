package com.bookstore.repository;

import com.bookstore.model.BookStoreTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookStoreTransactionHistoryRepository extends JpaRepository<BookStoreTransactionHistory, Integer> {

    @Query(nativeQuery = true, value = "select rack_name,fk_book_name,fk_edition_id,sum(quantity),sum(actual_price)-sum(discount_amount) from book_store_transaction_history bsth inner join book on fk_book_name=book_name and fk_edition_id=edition and transaction_type!='STOCK_INWARD' inner join book_rack br on fk_rack_id=book_rack_id group by fk_book_name,fk_edition_id")
    List<Object[]> getBooksSalesInfo();
}
