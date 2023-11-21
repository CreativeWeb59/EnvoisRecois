package com.example.envoisrecois.app;

import java.util.ArrayList;
import java.util.List;

public class App {
    private Utilisateurs utilisateur;
    private List<Messages>listeMessages = new ArrayList<>();
    private List<Contacts> listeContacts = new ArrayList<>();
    private List<Dossiers> listeDossiers = new ArrayList<>();

    public App() {
    }

    public App(Utilisateurs utilisateur, List<Messages> listeMessages, List<Contacts> listeContacts, List<Dossiers> listeDossiers) {
        this.utilisateur = utilisateur;
        this.listeMessages = listeMessages;
        this.listeContacts = listeContacts;
        this.listeDossiers = listeDossiers;
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
