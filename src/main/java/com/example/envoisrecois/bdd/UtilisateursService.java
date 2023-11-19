package com.example.envoisrecois.bdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateursService {
    private ConnectionBdd connectionBdd;

    public UtilisateursService(ConnectionBdd connectionBdd) {
        this.connectionBdd = connectionBdd;
    }

    public void suprUtilisateur(String nom) {
        String sql = "DELETE FROM sauvegarde WHERE pseudo LIKE ?";
        try {
            PreparedStatement stmt = connectionBdd.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.executeUpdate();
            System.out.println("Utilisateur " + nom + " supprimé");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Renvoi le nombre d'utilisateurs dans la table utilisateurs
     * @return
     */
    public int nombreUtilisateurs(){
        String query = "SELECT COUNT(*) FROM utilisateurs";
        try {
            PreparedStatement statement = connectionBdd.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("il y a " + resultSet.getInt(1) + " utilisateurs dans la table");
                return resultSet.getInt(1);
            } else {
                System.out.println("Il n'y a aucun utilisateurs dans la table");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * Teste si le username donné est dans la Bdd
     *
     * @param username
     * @return
     */
    public Boolean existUserName(String username) {

        String query = "SELECT username FROM utilisateurs WHERE username LIKE ?";
        try {

            PreparedStatement statement = connectionBdd.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Le username existe dans la base");
                return true;
            } else {
                System.out.println("Le username n'existe pas");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Renvoi le mot de passe appartement au username
     *
     * @param username
     * @return
     */
    public String getPassword(String username) {

        String query = "SELECT password FROM utilisateurs WHERE username LIKE ?";
        try {

            PreparedStatement statement = connectionBdd.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            } else {
                System.out.println("Le username n'existe pas");
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * Ajoute une sauvegarde
     *
     * @param utilisateurs
     * @throws SQLException
     */
    public void addJoueur(Utilisateurs utilisateurs) throws SQLException {

        // Insertion des données
        String sql = "INSERT INTO utilisateurs (userName, nom, prenom, password, email, passwordM, messagerie" +
                ") VALUES (" +
                " ?, ?, ?, ?, ?, ?, ?" +
                ")";

        PreparedStatement stmt = connectionBdd.prepareStatement(sql);
        stmt.setString(1, utilisateurs.getUserName());
        stmt.setString(2, utilisateurs.getNom());
        stmt.setString(3, utilisateurs.getPrenom());
        stmt.setString(4, utilisateurs.getPassword());
        stmt.setString(5, utilisateurs.getEmail());
        stmt.setString(6, utilisateurs.getPasswordM());
        stmt.setInt(7, utilisateurs.getMessagerie());

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
            utilisateurs.setId(generatedId);
            System.out.println("L'utilisateur a été inséré avec succès dans la base de données avec l'ID : " + generatedId);
        }
    }
}
