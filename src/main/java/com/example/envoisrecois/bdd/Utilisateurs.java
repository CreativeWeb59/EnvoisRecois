package com.example.envoisrecois.bdd;

public class Utilisateurs {
    private int id;
    private String userName;
    public String nom;
    private String prenom;
    private String password;
    private String email;
    private String passwordM;
    private int messagerie; // 1 pour free, 2 pour gmail...

    public Utilisateurs(int id, String userName, String nom, String prenom, String password, String email, String passwordM, int messagerie) {
        this.id = id;
        this.userName = userName;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.passwordM = passwordM;
        this.messagerie = messagerie;
    }

    public Utilisateurs(String userName, String nom, String prenom, String password, String email, String passwordM, int messagerie) {
        this.userName = userName;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.passwordM = passwordM;
        this.messagerie = messagerie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMessagerie() {
        return messagerie;
    }

    public void setMessagerie(int messagerie) {
        this.messagerie = messagerie;
    }

    public String getPasswordM() {
        return passwordM;
    }

    public void setPasswordM(String passwordM) {
        this.passwordM = passwordM;
    }



}
