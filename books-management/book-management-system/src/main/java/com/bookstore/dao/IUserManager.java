package com.bookstore.dao;

import com.bookstore.model.User;

import java.util.Optional;

public interface IUserManager {

    Optional<User> findByUserName(String userName);
}
