package com.my.kramarenko.taxService.web.mail;

//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHelper {
    private static final Logger LOG = Logger.getLogger(MailHelper.class);
    private static final String USERNAME = "tax.service.task@gmail.com";
    private static final String PASSWORD = "aypjihhgnqdsgbcq";

    public static void sendMail(String mail, String subject, String message)
            throws MessagingException {
        Message msg = new MimeMessage(getSession());
        msg.setFrom(new InternetAddress(USERNAME));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        msg.setSubject(subject);
        msg.setContent(message, "text/html;charset=utf-8");
        Transport.send(msg);
        LOG.trace("Mail successfully sent");
    }

    private static Session getSession() {
        return Session.getInstance(getProperties(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();


        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");

//        properties.put("mail.smtp.socketFactory.port", "465");
//        properties.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.socketFactory.class",
//                "jakarta.net.ssl.SSLSocketFactory");
        return properties;
    }
}