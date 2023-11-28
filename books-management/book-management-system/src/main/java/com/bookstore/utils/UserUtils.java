package com.bookstore.utils;

import com.bookstore.dao.IBuyerMembershipManager;
import com.bookstore.dao.IUserManager;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUtils {

    private final InheritableThreadLocal<String> threadLocalUser = new InheritableThreadLocal<>();
    private final IUserManager userManager;

    private final IBuyerMembershipManager buyerMembershipManager;

    @Autowired
    public UserUtils(IUserManager userManager, IBuyerMembershipManager buyerMembershipManager) {
        this.userManager = userManager;
        this.buyerMembershipManager = buyerMembershipManager;
    }

    public User getUser() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();
        Optional<User> userOptional = userManager.findByUserName(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username " + userDetails.getUsername() + " not found");
        }
        return userOptional.get();
    }

    public Optional<BuyerMembershipHistory> getActiveBuyerMembershipHistory() {
        User user = getUser();
        return getActiveBuyerMembershipHistory(user);
    }

    public Optional<BuyerMembershipHistory> getActiveBuyerMembershipHistory(User user) {
        return buyerMembershipManager.getActiveMembershipForTheUser(user.getEmail());
    }

    public void setUser(String user) {
        threadLocalUser.set(user);
    }
}
