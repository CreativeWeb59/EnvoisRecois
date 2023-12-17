package com.example.envoisrecois.app;

import com.example.envoisrecois.outils.RecupConfig;
import javafx.scene.control.Label;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Permet d'envoyer les messages
 */
public class Envoyer {
    public static void envoyerMessage(String receiver, String subject, String contenuMessage, String userEmail, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.free.fr");
        props.put("mail.smtp.port", "587");

        // Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, password);
            }
        });

//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(RecupConfig.getUserEmail(), RecupConfig.getPassword());
//            }
//        });

        try {
            // recuperation des répertoires
            // recuperation des messages


            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(RecupConfig.getUserEmail()));
            message.setFrom(new InternetAddress(userEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
//            message.setText(contenuMessage);

            // Utilisation de setContent avec le type "text/html" pour spécifier un e-mail au format HTML
            message.setContent(contenuMessage, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès!");
        } catch (AuthenticationFailedException authentification){
            System.out.println("probleme d'authentification");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
