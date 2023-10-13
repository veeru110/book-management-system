package com.bookstore.dao;

import com.bookstore.model.BookStorePrivateKeys;

import java.util.Optional;

public interface ICredentialManager {

    Optional<BookStorePrivateKeys> getLatestPrivateKey();
}
