package com.bookstore.service;

import com.bookstore.constants.EmailEvents;
import com.bookstore.constants.UserRole;
import com.bookstore.model.User;
import com.bookstore.utils.TemplateMerger;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.bookstore.constants.Constants.BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME;

@Service
public class MailServiceImpl implements MailService {


    private final JavaMailSender javaMailSender;
    private final TemplateMerger templateMerger;

    @Value("${email.username}")
    private String username;

    private static final String EMAIL_TEMPLATE = "email_template";

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, TemplateMerger templateMerger) {
        this.javaMailSender = javaMailSender;
        this.templateMerger = templateMerger;
    }


    @Override
    public void sendEmail(String subject, String toEmail, String cc, String bcc, String greeting, String name, String body) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject(subject);
        if (!StringUtils.isEmpty(bcc)) {
            mimeMessageHelper.setBcc(bcc.split(","));
        }
        if (!StringUtils.isEmpty(cc)) {
            mimeMessageHelper.setCc(cc.split(","));
        }
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setReplyTo(username);

        Map<String, String> emailDataModel = new HashMap<>();
        emailDataModel.put("greeting", greeting);
        if (StringUtils.isEmpty(name)) {
            name = "Customer";
        } else {
            name = StringUtils.capitalize(name);
        }
        emailDataModel.put("name", name);
        emailDataModel.put("body", body);
        String emailText = templateMerger.getContentUsingTemplate(emailDataModel, EMAIL_TEMPLATE);
        mimeMessageHelper.setText(emailText, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    @Async(BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME)
    public void sendEmail(EmailEvents emailEvents, User user) throws MessagingException, TemplateException, IOException {
        sendEmail(emailEvents.getEmailSubject(), user.getEmail(), null, null, emailEvents.getGreeting().formatted(user.getRole().toString()), user.getFirstName(), emailEvents.getBody().formatted(user.getRole().toString()));
    }
}
