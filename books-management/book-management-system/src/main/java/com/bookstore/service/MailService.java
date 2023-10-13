package com.bookstore.service;

import java.util.Map;

public interface MailService {
    void sendEmail(String subject, String toEmail, String cc, String bcc, Map<String, String> freemarkerMergeData);
}
