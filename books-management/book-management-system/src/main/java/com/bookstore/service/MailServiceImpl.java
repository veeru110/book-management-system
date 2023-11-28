package com.bookstore.service;

import com.bookstore.constants.EmailEvents;
import com.bookstore.dao.IUserManager;
import com.bookstore.model.User;
import com.bookstore.utils.TemplateMerger;
import com.bookstore.vo.EmailTableVo;
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
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bookstore.constants.Constants.BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME;

@Service
public class MailServiceImpl implements MailService {


    private final JavaMailSender javaMailSender;
    private final TemplateMerger templateMerger;
    private final IUserManager userManager;

    @Value("${email.username}")
    private String username;

    private static final String EMAIL_TEMPLATE = "email_template";

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, TemplateMerger templateMerger, IUserManager userManager) {
        this.javaMailSender = javaMailSender;
        this.templateMerger = templateMerger;
        this.userManager = userManager;
    }


    @Override
    public void sendEmailSimple(String subject, String toEmail, String cc, String bcc, String emailText) throws MessagingException, TemplateException, IOException {
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
        mimeMessageHelper.setText(emailText, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    @Async(BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME)
    public void sendEmailSimple(EmailEvents emailEvents, User user) throws MessagingException, TemplateException, IOException {
        String greeting = emailEvents.getGreeting().formatted(user.getRole().toString());
        String name = user.getFirstName();
        String body = emailEvents.getBody().formatted(user.getRole().toString());

        Map<String, Object> emailDataModel = new HashMap<>();
        emailDataModel.put("greeting", greeting);
        if (StringUtils.isEmpty(name)) {
            name = "Customer";
        } else {
            name = StringUtils.capitalize(name);
        }
        emailDataModel.put("name", name);
        emailDataModel.put("body", body);
        String emailText = templateMerger.getContentUsingTemplate(emailDataModel, EMAIL_TEMPLATE);
        sendEmailSimple(emailEvents.getEmailSubject(), user.getEmail(), null, null, emailText);
    }

    @Override
    @Async(BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME)
    public void sendEmailToAllBuyers(EmailEvents emailEvents, EmailTableVo emailTableVo) throws MessagingException, TemplateException, IOException {
        List<User> buyers = userManager.allBuyers();
        for (User buyer : buyers) {
            sendEmailWithTable(emailEvents, buyer, emailTableVo);
        }
    }

    @Override
    @Async(BOOK_STORE_ASYNC_TASK_EXECUTOR_BEAN_NAME)
    public void sendEmailWithTable(EmailEvents emailEvents, User user, EmailTableVo emailTableVo) throws MessagingException, TemplateException, IOException {
        if (CollectionUtils.isEmpty(emailTableVo.getDataItr())) {
            return;
        }
        String greeting = emailEvents.getGreeting().formatted(user.getRole().toString());
        String name = user.getFirstName();
        String body = emailEvents.getBody().formatted(user.getRole().toString());

        Map<String, Object> emailDataModel = new HashMap<>();
        emailDataModel.put("greeting", greeting);
        if (StringUtils.isEmpty(name)) {
            name = "Customer";
        } else {
            name = StringUtils.capitalize(name);
        }
        emailDataModel.put("name", name);
        emailDataModel.put("body", body);
        emailDataModel.put("tableExists", true);
        emailDataModel.put("column1", emailTableVo.getColumn1Header());
        emailDataModel.put("column2", emailTableVo.getColumn2Header());
        emailDataModel.put("dataItr", emailTableVo.getDataItr());
        String emailText = templateMerger.getContentUsingTemplate(emailDataModel, EMAIL_TEMPLATE);
        sendEmailSimple(emailEvents.getEmailSubject(), user.getEmail(), null, null, emailText);
    }

}
