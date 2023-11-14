package com.example.envoisrecois.app;

import com.example.envoisrecois.outils.RecupConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class Envoyer {
    public static void main(String[] args) {
        final String username = RecupConfig.getUsername();
        final String password = RecupConfig.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.free.fr");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("delphine.laiglesia@free.fr"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("delcri@free.fr"));
            message.setSubject("Message 2");
            message.setText("Coucou à moi du message 2");

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
