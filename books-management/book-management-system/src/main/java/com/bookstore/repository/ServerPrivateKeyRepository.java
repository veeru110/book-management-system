package com.bookstore.repository;

import com.bookstore.model.ServerPrivateKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerPrivateKeyRepository extends JpaRepository<ServerPrivateKeys, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM server_private_keys_config")
    Optional<ServerPrivateKeys> getServerPrivateKey();
}
