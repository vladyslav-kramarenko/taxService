package com.my.kramarenko.taxService.web.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHelper {
    private static final String USERNAME = "razorvlad1992@gmail.com";
    private static final String PASSWORD = "!!!zhfpjh1992";
     
    public static void sendMail(String mail, String subject, String message)
            throws AddressException, MessagingException {
        Message msg = new MimeMessage(getSession());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject(subject);
        msg.setText(message);
        Transport.send(msg);
    }
    private static Session getSession() {
        Session session = Session.getDefaultInstance(getProperties(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
        return session;
    }
    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        return properties;
    }
}