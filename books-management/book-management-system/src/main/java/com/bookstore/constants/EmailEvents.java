package com.bookstore.constants;

public enum EmailEvents {
    REGISTRATION("User Registration Successful!", "%s Registration", "Your registration as %s in BookStore is successful"),
    MEMBERSHIP("Membership Subscription Succesful!", "Membership Subscription Registration", "Your %s %s Subscription purchase is successful");

    private String emailSubject;
    private String greeting;
    private String body;

    EmailEvents(String emailSubject, String greeting, String body) {
        this.emailSubject = emailSubject;
        this.greeting = greeting;
        this.body = body;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getBody() {
        return body;
    }
}
