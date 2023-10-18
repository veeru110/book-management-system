package com.bookstore.command;

import jakarta.validation.constraints.NotEmpty;

public class LoginCommand {

    @NotEmpty(message = "Username can't be empty")
    private String userName;

    @NotEmpty(message = "password can't be empty")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
