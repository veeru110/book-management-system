package com.bookstore.service;

import com.bookstore.dao.IUserManager;
import com.bookstore.model.User;
import com.bookstore.utils.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class BookStoreUserDetailsService implements UserDetailsService {

    IUserManager userManager;

    @Autowired
    public BookStoreUserDetailsService(IUserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userManager.findByUserName(username);
        return userOptional.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not found for " + username));
    }
}
