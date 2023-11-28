package com.bookstore.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class DashboardVo {

    private Integer registeredUsers;

    private BookStoreStock bookStoreStock;

    public BookStoreStock getBookStoreStock() {
        return bookStoreStock;
    }

    public void setBookStoreStock(BookStoreStock bookStoreStock) {
        this.bookStoreStock = bookStoreStock;
    }

    private BookStoreSalesInfo bookStoreSalesInfo;

    public BookStoreSalesInfo getBookStoreSalesInfo() {
        return bookStoreSalesInfo;
    }

    public void setBookStoreSalesInfo(BookStoreSalesInfo bookStoreSalesInfo) {
        this.bookStoreSalesInfo = bookStoreSalesInfo;
    }

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

}
