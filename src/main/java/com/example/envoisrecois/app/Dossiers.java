package com.example.envoisrecois.app;

public class Dossiers {
    private int id;
    private String nom;
    private String reference; // correspond au repertoire de reference sur le provider
    private String note;

    public Dossiers(int id, String nom, String reference, String note) {
        this.id = id;
        this.nom = nom;
        this.reference = reference;
        this.note = note;
    }

    public Dossiers(String nom, String reference, String note) {
        this.nom = nom;
        this.reference = reference;
        this.note = note;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Dossiers{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", reference='" + reference + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
