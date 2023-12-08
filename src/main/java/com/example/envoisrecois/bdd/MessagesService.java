package com.example.envoisrecois.bdd;

import com.example.envoisrecois.app.Contacts;
import com.example.envoisrecois.app.Messages;
import com.example.envoisrecois.app.Utilisateurs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagesService {
    private ConnectionBdd connectionBdd;

    public MessagesService(ConnectionBdd connectionBdd) {
        this.connectionBdd = connectionBdd;
    }

    /**
     * Ajoute un message dans la table
     * @param message
     * @throws SQLException
     */
    public void addMessage(Messages message) throws SQLException {
        // recuperation date en cours au bon format
        LocalDateTime dateMessage = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateMessage);

        // Insertion des données
        String sql = "INSERT INTO messages (idutilisateur, expediteur, receveur, datemessage, objet, contenumessage, iddossier" +
                ") VALUES (" +
                " ?, ?, ?, ?, ?, ?, ?" +
                ")";

        PreparedStatement stmt = connectionBdd.prepareStatement(sql);
        stmt.setInt(1, message.getIdUtilisateur());
        stmt.setString(2, message.getExpediteur());
        stmt.setString(3, message.getReceveur());
        stmt.setTimestamp(4, timestamp);
        stmt.setString(5, message.getObjet());
        stmt.setString(6, message.getContenuMessage());
        stmt.setInt(7, message.getIdDossier());

        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Récupération de l'ID généré
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            message.setId(generatedId);
            System.out.println("Le message a été inséré avec succès dans la base de données avec l'ID : " + generatedId);
        }
    }

    /**
     * Supprime un message suivant son id
     * @param id
     */
    public void suprMessage(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id LIKE ?";
        try {
            PreparedStatement stmt = connectionBdd.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("message " + id + " supprimé");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Renvoi la liste des messages appartenants à un utilisateur
     * @param idUtilisateur
     * @return
     */
    public List<Messages> listeMessagesUtilisateur(int idUtilisateur){
        List<Messages>listeMessages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE idUtilisateur LIKE ?";
        try {
            PreparedStatement statement = connectionBdd.prepareStatement(query);
            statement.setInt(1, idUtilisateur);
            ResultSet resultSet = statement.executeQuery();
            // construit la liste des contacts
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String expediteur = resultSet.getString("expediteur");
                String receveur = resultSet.getString("receveur");
                String objet = resultSet.getString("objet");
                String contenuMessage = resultSet.getString("contenuMessage");
                int idDossier = resultSet.getInt("idDossier");

                // transformation de la date
                long timestampMillisDateMessage = resultSet.getLong("dateMessage");
                LocalDateTime dateMessage = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillisDateMessage), ZoneId.systemDefault());

                Messages message = new Messages(id, idUtilisateur, expediteur, receveur, dateMessage, objet, contenuMessage, idDossier);
                listeMessages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeMessages;
    }
}
