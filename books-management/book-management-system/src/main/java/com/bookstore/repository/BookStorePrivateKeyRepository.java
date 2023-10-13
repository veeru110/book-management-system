package com.bookstore.repository;

import com.bookstore.model.BookStorePrivateKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStorePrivateKeyRepository extends JpaRepository<BookStorePrivateKeys, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM book_store_private_keys_config")
    Optional<BookStorePrivateKeys> getServerPrivateKey();
}
