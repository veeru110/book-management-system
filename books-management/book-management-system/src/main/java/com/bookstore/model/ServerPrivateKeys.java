package com.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "server_private_keys_config")
public class ServerPrivateKeys {

    @Id
    @Column(name = "private_key_id")
    private Integer privateKeyId;

    @Column(name = "private_key", nullable = false)
    private String privateKey;

    public Integer getPrivateKeyId() {
        return privateKeyId;
    }

    public void setPrivateKeyId(Integer privateKeyId) {
        this.privateKeyId = privateKeyId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
