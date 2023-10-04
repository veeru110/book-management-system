package com.bookstore.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DashboardVo {

    private Integer registeredUsers;

    private Integer noOfBooksInStore;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String failureMessage;

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public Integer getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Integer registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public Integer getNoOfBooksInStore() {
        return noOfBooksInStore;
    }

    public void setNoOfBooksInStore(Integer noOfBooksInStore) {
        this.noOfBooksInStore = noOfBooksInStore;
    }
}
