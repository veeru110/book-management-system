package com.bookstore.dao;

import com.bookstore.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserManager {

    Optional<User> findByUserName(String userName);

    Integer countAllUsers();

    List<User> allBuyers();
    void save(User user);
}
