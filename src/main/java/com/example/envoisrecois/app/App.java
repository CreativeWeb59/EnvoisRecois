package com.example.envoisrecois.app;

import java.util.ArrayList;
import java.util.List;

public class App {
    private Utilisateurs utilisateur;
    private List<Messages>listeMessages = new ArrayList<>();
    private List<Contacts> listeContacts = new ArrayList<>();
    private List<Dossiers> listeDossiers = new ArrayList<>();
    private String passwordMessagrie;

    public App() {
    }

    public App(Utilisateurs utilisateur, List<Messages> listeMessages, List<Contacts> listeContacts, List<Dossiers> listeDossiers, String passwordMessagrie) {
        this.utilisateur = utilisateur;
        this.listeMessages = listeMessages;
        this.listeContacts = listeContacts;
        this.listeDossiers = listeDossiers;
        this.passwordMessagrie = passwordMessagrie;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Messages> getListeMessages() {
        return listeMessages;
    }

    public void setListeMessages(List<Messages> listeMessages) {
        this.listeMessages = listeMessages;
    }

    public List<Contacts> getListeContacts() {
        return listeContacts;
    }

    public void setListeContacts(List<Contacts> listeContacts) {
        this.listeContacts = listeContacts;
    }

    public List<Dossiers> getListeDossiers() {
        return listeDossiers;
    }

    public void setListeDossiers(List<Dossiers> listeDossiers) {
        this.listeDossiers = listeDossiers;
    }
    public void ajouterDossier(Dossiers dossier) {
        listeDossiers.add(dossier);
    }

    public String getPasswordMessagrie() {
        return passwordMessagrie;
    }

    public void setPasswordMessagrie(String passwordMessagrie) {
        this.passwordMessagrie = passwordMessagrie;
    }

    /**
     * Creation des dossiers par defaut
     */
    public void setupDossiers(){
        Dossiers dossier0 = new Dossiers(1, "Boîte de réception", "INBOX", "Boite de reception");
        Dossiers dossier1 = new Dossiers(2, "Envoyés", "Sent", "Messages envoyés");
        Dossiers dossier2 = new Dossiers(3, "Spam", "Spams", "Spams");
        Dossiers dossier3 = new Dossiers(4, "Corbeille", "Trash", "Corbeille");
        Dossiers dossier4 = new Dossiers(5, "Commercial", "Commercial", "Messages commerciaux");
        Dossiers dossier5 = new Dossiers(6, "RéseauxSociaux", "ReseauxSociaux", "Réseaux sociaux");
    }
    @Override
    public String toString() {
        return "App{" +
                "utilisateur=" + utilisateur +
                ", listeMessages=" + listeMessages +
                ", listeContacts=" + listeContacts +
                ", listeDossiers=" + listeDossiers +
                '}';
    }
}
