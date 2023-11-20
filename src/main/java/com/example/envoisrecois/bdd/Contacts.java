package com.example.envoisrecois.bdd;

public class Contacts {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String photo;
    private String telephone;
    private String note;
    private int idUtilisateur;

    public Contacts(int id, String nom, String prenom, String email, String photo, String telephone, String note, int idUtilisateur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.telephone = telephone;
        this.note = note;
        this.idUtilisateur = idUtilisateur;
    }

    public Contacts(String nom, String prenom, String email, String photo, String telephone, String note, int idUtilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.telephone = telephone;
        this.note = note;
        this.idUtilisateur = idUtilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
