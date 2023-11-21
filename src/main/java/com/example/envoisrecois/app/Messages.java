package com.example.envoisrecois.app;

import java.util.Date;

public class Messages {
    private int id;
    private int idUtilisateur;
    private String expediteur;
    private String receveur;
    private Date dateMessage;
    private String objet;
    private String sujet;
    private String contenuMessage;
    private int idDossier; // id du dossier

    public Messages(int id, int idUtilisateur, String expediteur, String receveur, Date dateMessage, String objet, String sujet, String contenuMessage, int idDossier) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.expediteur = expediteur;
        this.receveur = receveur;
        this.dateMessage = dateMessage;
        this.objet = objet;
        this.sujet = sujet;
        this.contenuMessage = contenuMessage;
        this.idDossier = idDossier;
    }

    public Messages(int idUtilisateur, String expediteur, String receveur, Date dateMessage, String objet, String sujet, String contenuMessage, int idDossier) {
        this.idUtilisateur = idUtilisateur;
        this.expediteur = expediteur;
        this.receveur = receveur;
        this.dateMessage = dateMessage;
        this.objet = objet;
        this.sujet = sujet;
        this.contenuMessage = contenuMessage;
        this.idDossier = idDossier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getReceveur() {
        return receveur;
    }

    public void setReceveur(String receveur) {
        this.receveur = receveur;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenuMessage() {
        return contenuMessage;
    }

    public void setContenuMessage(String contenuMessage) {
        this.contenuMessage = contenuMessage;
    }
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(int idDossier) {
        this.idDossier = idDossier;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", idUtilisateur=" + idUtilisateur +
                ", expediteur='" + expediteur + '\'' +
                ", receveur='" + receveur + '\'' +
                ", dateMessage=" + dateMessage +
                ", objet='" + objet + '\'' +
                ", sujet='" + sujet + '\'' +
                ", contenuMessage='" + contenuMessage + '\'' +
                ", idDossier=" + idDossier +
                '}';
    }
}
