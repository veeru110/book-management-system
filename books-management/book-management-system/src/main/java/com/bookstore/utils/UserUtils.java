package com.bookstore.utils;

import com.bookstore.config.jwt.JWTService;
import com.bookstore.dao.IBuyerMembershipManager;
import com.bookstore.dao.IUserManager;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUtils {

    private final ThreadLocal<String> threadLocalAuthBearer = new ThreadLocal<>();
    private final JWTService jwtService;
    private final IUserManager userManager;

    private final IBuyerMembershipManager buyerMembershipManager;

    @Autowired
    public UserUtils(JWTService jwtService, IUserManager userManager, IBuyerMembershipManager buyerMembershipManager) {
        this.jwtService = jwtService;
        this.userManager = userManager;
        this.buyerMembershipManager = buyerMembershipManager;
    }

    public User getUser() {
        String authBearer = threadLocalAuthBearer.get();
        String jwtToken = authBearer.substring(7);
        String username = jwtService.getUserNameFromJWTToken(jwtToken);

        Optional<User> userOptional = userManager.findByUserName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return userOptional.get();
    }

    public Optional<BuyerMembershipHistory> getActiveBuyerMembershipHistory() {
        User user = getUser();
        return buyerMembershipManager.getActiveMembershipForTheUser(user.getEmail());
    }

    public void setUser(String user) {
        threadLocalAuthBearer.set(user);
    }
}
