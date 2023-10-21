package com.bookstore.dao;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserManagerImpl implements IUserManager {


    private final UserRepository userRepository;

    @Autowired
    public UserManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByEmailAndIsDeletedFalseAndIsActiveTrue(userName);
    }

    @Override
    public Integer countAllUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public List<User> allBuyers() {
        return userRepository.getAllBuyers();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
