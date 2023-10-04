package com.bookstore.dao;

import com.bookstore.model.ServerPrivateKeys;
import com.bookstore.repository.ServerPrivateKeyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CredentialManager implements ICredentialManager {

    private final ServerPrivateKeyRepository serverPrivateKeyRepository;

    public CredentialManager(ServerPrivateKeyRepository serverPrivateKeyRepository) {
        this.serverPrivateKeyRepository = serverPrivateKeyRepository;
    }

    @Override
    public Optional<ServerPrivateKeys> getLatestPrivateKey() {
        return serverPrivateKeyRepository.getServerPrivateKey();
    }
}
