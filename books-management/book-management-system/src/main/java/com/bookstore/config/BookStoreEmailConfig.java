package com.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class BookStoreEmailConfig {

    @Value("${self.email.server.host}")
    private String emailServer;

    @Value("${self.email.username}")
    private String username;

    @Value("${self.email.password}")
    private String password;

    @Value("${self.email.server.port}")
    private Integer port;

    private Properties getEmailProperties() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.debug", "true");
        return mailProperties;
    }


    @Bean
    @ConditionalOnProperty(value = "bookstore.email.service", havingValue = "self")
    public JavaMailSender bookStoreSelfMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailServer);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getEmailProperties());
        return javaMailSender;
    }
}
