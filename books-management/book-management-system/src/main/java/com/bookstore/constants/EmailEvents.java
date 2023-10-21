package com.bookstore.constants;

public enum EmailEvents {
    REGISTRATION("User Registration Successful!", "%s Registration", "Your registration as %s in BookStore is successful"), MEMBERSHIP("Membership Subscription Successful!", "Membership Subscription Registration", "Your Membership purchase is successful"), STOCK_INWARD("New Stock Inward Notification", "New Stock Notification", "New Stock has be added to the bookstore, please find the details below:"), BULK_EMAIL_PROMOTIONAL_NOTIFICATION("Promotional Notification", "Greetings", "As per your interests, this is promotional email for the books available");

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
