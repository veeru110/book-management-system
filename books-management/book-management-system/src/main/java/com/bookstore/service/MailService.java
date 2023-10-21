package com.bookstore.service;

import com.bookstore.constants.EmailEvents;
import com.bookstore.model.User;
import com.bookstore.vo.EmailTableVo;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailService {
    void sendEmailSimple(String subject, String toEmail, String cc, String bcc, String emailText) throws MessagingException, TemplateException, IOException;

    void sendEmailSimple(EmailEvents emailEvents, User user) throws MessagingException, TemplateException, IOException;

    void sendEmailToAllBuyers(EmailEvents emailEvents, EmailTableVo emailTableVo) throws MessagingException, TemplateException, IOException;

    void sendEmailWithTable(EmailEvents emailEvents, User user, EmailTableVo emailTableVo) throws MessagingException, TemplateException, IOException;
}
