package com.bookstore.dao;

import com.bookstore.model.ServerPrivateKeys;

import java.util.Optional;

public interface ICredentialManager {

    Optional<ServerPrivateKeys> getLatestPrivateKey();
}
