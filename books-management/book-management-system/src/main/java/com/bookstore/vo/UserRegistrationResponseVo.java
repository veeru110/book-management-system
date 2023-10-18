package com.bookstore.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class UserRegistrationResponseVo {

    private String userName;
    private LocalDateTime registeredOn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean isRegistrationSuccessful;

    private String failureMessage;

    private String messageToUser;

    public UserRegistrationResponseVo(String userName, LocalDateTime registeredOn, boolean isRegistrationSuccessful, String failureMessage,String messageToUser) {
        this.userName = userName;
        this.registeredOn = registeredOn;
        this.isRegistrationSuccessful = isRegistrationSuccessful;
        this.failureMessage = failureMessage;
        this.messageToUser = messageToUser;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public boolean isRegistrationSuccessful() {
        return isRegistrationSuccessful;
    }

    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        isRegistrationSuccessful = registrationSuccessful;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getMessageToUser() {
        return messageToUser;
    }

    public void setMessageToUser(String messageToUser) {
        this.messageToUser = messageToUser;
    }
}
