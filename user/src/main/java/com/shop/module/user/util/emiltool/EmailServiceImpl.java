package com.shop.module.user.util.emiltool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

   /* @Autowired
    private VelocityEngine velocityEngine;*/
    @Autowired
    Configuration configuration; //freeMarker configuration
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String sendTo, String titel, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getEmailFrom());
        message.setTo(sendTo);
        message.setSubject(titel);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendAttachmentsMail(String sendTo, String titel, String content, List<Pair<String, File>> attachments) {

    }

    @Override
    public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, List<Pair<String, File>> attachments) {

    }

    @Override
    public String sendHtmlMail(String to, String subject, Map<String, Object> map,String model) {
        MimeMessage message = mailSender.createMimeMessage();
        Template t; // freeMarker template
        try {
            t = configuration.getTemplate(model);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
            MimeMessageHelper helper = setInfoByHelper(to, subject, content, message);
            mailSender.send(message);
            System.out.print("html邮件已经发送。");
            return "T";
        } catch (Exception e) {
            System.out.print("发送html邮件时发生异常！");
            return "F";
        }
    }

    private MimeMessageHelper setInfoByHelper(String to, String subject, String content, MimeMessage message)
            throws MessagingException {
        //true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailConfig.getEmailFrom());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        return helper;
    }
}