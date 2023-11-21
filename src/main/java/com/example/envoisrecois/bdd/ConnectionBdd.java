package com.example.envoisrecois.bdd;

import com.example.envoisrecois.app.Dossiers;

import java.sql.*;
import java.util.Date;

public class ConnectionBdd {
    private String DBPath = "Chemin aux base de donnée SQLite";
    private Connection connection = null;
    private Statement statement = null;

    public ConnectionBdd() {
        this.DBPath = "courrierPlus.db";
    }
    /**
     * cree la connection à la Bdd
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            System.out.println("Connexion a " + DBPath + " avec succès");
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Erreur de connection");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Erreur de connection");
        }
    }
    /**
     * Ferme la connection à la Bdd
     */
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * permet d'effectuer des requettes
     *
     * @param request
     * @return
     */
    public ResultSet query(String request) {
        ResultSet resultat = null;
        try {
            resultat = statement.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur dans la requete : " + request);
        }
        return resultat;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    /**
     * Renvoi true si la table existe
     *
     * @return
     */
    public Boolean isModel(String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet resultSet = metadata.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }
    /**
     * cree la table utilisateurs
     *
     * @throws SQLException
     */
    public void createModelUtilisateurs() throws SQLException {
        String sql = "CREATE TABLE utilisateurs (ID INTEGER PRIMARY KEY, USERNAME TEXT, NOM TEXT, PRENOM TEXT, PASSWORD TEXT, EMAIL TEXT," +
                "MESSAGERIE INTEGER, PASSWORDM TEXT)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        System.out.println("La table 'utilisateurs' a été créée avec succès.");
    }
    /**
     * cree la table contacts
     *
     * @throws SQLException
     */
    public void createModelContact() throws SQLException {
        String sql = "CREATE TABLE contacts (ID INTEGER PRIMARY KEY, NOM TEXT, PRENOM TEXT, EMAIL TEXT, PHOTO TEXT, TELEPHONE TEXT," +
                "NOTE TEXT, IDUTILISATEUR INTEGER)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        System.out.println("La table 'contacts' a été créée avec succès.");
    }
    /**
     * cree la table messages
     *
     * @throws SQLException
     */
    public void createModelMessages() throws SQLException {
        String sql = "CREATE TABLE messages (ID INTEGER PRIMARY KEY, IDUTILISATEUR INTEGER, EXPEDITEUR TEXT, RECEVEUR TEXT, DATEMESSAGE TEXT," +
                "OBJET TEXT , SUJET TEXT, CONTENUMESSAGE TEXT, IDDOSSIER INTEGER)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        System.out.println("La table 'messages' a été créée avec succès.");
    }
     /**
     * Cree la table dossiers
     */
    public void createModelDossiers() throws SQLException {
        String sql = "CREATE TABLE dossiers (ID INTEGER PRIMARY KEY, NOM TEXT, REFERENCE TEXT, NOTE TEXT)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        System.out.println("La table 'dossiers' a été créée avec succès.");
    }
}