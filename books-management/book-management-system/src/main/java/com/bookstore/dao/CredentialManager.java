package com.bookstore.dao;

import com.bookstore.model.BookStorePrivateKeys;
import com.bookstore.repository.BookStorePrivateKeyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CredentialManager implements ICredentialManager {

    private final BookStorePrivateKeyRepository bookStorePrivateKeyRepository;

    public CredentialManager(BookStorePrivateKeyRepository bookStorePrivateKeyRepository) {
        this.bookStorePrivateKeyRepository = bookStorePrivateKeyRepository;
    }

    @Override
    public Optional<BookStorePrivateKeys> getLatestPrivateKey() {
        return bookStorePrivateKeyRepository.getServerPrivateKey();
    }
}
