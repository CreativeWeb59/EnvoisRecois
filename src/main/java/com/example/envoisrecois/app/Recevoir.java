package com.example.envoisrecois.app;
import com.example.envoisrecois.outils.RecupConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Permet de recuperer les emails
 */
public class Recevoir {
    public static void recevoirMessage() {
        final String username = RecupConfig.getUserEmail();
        final String password = RecupConfig.getPassword();

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", "imap.free.fr");
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.free.fr", username, password);

            // Récupérer tous les dossiers (boîtes aux lettres)
            Folder[] folders = store.getDefaultFolder().list("*");

            for (Folder folder : folders) {
                // Ouvrir chaque dossier en mode lecture
                folder.open(Folder.READ_ONLY);

                // Récupérer les messages du dossier
                Message[] messages = folder.getMessages();

                // Afficher les informations sur chaque message
                for (Message message : messages) {
                    System.out.println("Dossier: " + folder.getFullName());
                    System.out.println("Sujet: " + message.getSubject());
                    System.out.println("De: " + InternetAddress.toString(message.getFrom()));
                    System.out.println("Date: " + message.getSentDate());
                    System.out.println("Contenu: " + message.getContent());
                    System.out.println("----------------------------------------");
                }

                // Fermer le dossier
                folder.close(false);
            }

            // Fermer la connexion
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Messages> recoisMessages(){
        List<Messages>listeMessages = new ArrayList<>();

        // necessaire a la connexion à la messagerie
        final String username = RecupConfig.getUserEmail();
        final String password = RecupConfig.getPassword();

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", "imap.free.fr");
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.ssl.enable", "true");

        // recuperation de chaque message
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.free.fr", username, password);

            // Récupérer tous les dossiers (boîtes aux lettres)
            Folder[] folders = store.getDefaultFolder().list("*");

            for (Folder folder : folders) {
                // Ouvrir chaque dossier en mode lecture
                folder.open(Folder.READ_ONLY);

                // Récupérer les messages du dossier
                Message[] messages = folder.getMessages();


                // Afficher les informations sur chaque message
                for (Message message : messages) {
                    // transformation de la date
                    Date date = message.getSentDate();
                    long timestampMillisDateMessage = date.getTime();
                    LocalDateTime dateMessage = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillisDateMessage), ZoneId.systemDefault());
                    Messages messageRecu = new Messages(1, InternetAddress.toString(message.getFrom()), "delcri@free.fr", dateMessage, message.getSubject(), "test", 1);
                    listeMessages.add(messageRecu);
                }

                // Fermer le dossier
                folder.close(false);
            }

            // Fermer la connexion
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeMessages;
    }
}
