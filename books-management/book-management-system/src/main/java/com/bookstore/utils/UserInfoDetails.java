package com.bookstore.utils;

import com.bookstore.constants.UserRole;
import com.bookstore.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInfoDetails implements UserDetails {

    private final User user;

    public UserInfoDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRole.valueOf(user.getRole()).getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return user.getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActive() && !user.getDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getActive();
    }

    @Override
    public boolean isEnabled() {
        return user.getActive();
    }
}
