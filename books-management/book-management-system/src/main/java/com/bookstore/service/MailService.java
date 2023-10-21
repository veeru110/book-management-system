package com.bookstore.service;

import com.bookstore.constants.EmailEvents;
import com.bookstore.constants.UserRole;
import com.bookstore.model.User;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface MailService {
    void sendEmail(String subject, String toEmail, String cc, String bcc, String greeting, String name, String body) throws MessagingException, TemplateException, IOException;

    void sendEmail(EmailEvents emailEvents,  User user) throws MessagingException, TemplateException, IOException;
}
