package com.example.envoisrecois.app;

import java.util.ArrayList;
import java.util.List;

public class AssetSetter {
    /**
     * Creation des dossiers par defaut
     */
    public static Dossiers boiteReception(){
        Dossiers boiteReception = new Dossiers(1, "Boîte de réception", "Boîte de réception", "inbox");
        return boiteReception;
    }
    public static Dossiers envoyes(){
        Dossiers envoyes = new Dossiers(2, "Envoyés", "Messages envoyés", "send");
        return envoyes;
    }
    public static Dossiers corbeille(){
        Dossiers corbeille = new Dossiers(3, "Corbeille", "Eléments à supprimer", "bin");
        return corbeille;
    }
    public static Dossiers spams(){
        Dossiers spams = new Dossiers(4, "Spams", "Spams", "spams");
        return spams;
    }
    public static Dossiers commercial(){
        Dossiers commercial = new Dossiers(5, "Commercial", "Messages commerciaux", "commercial");
        return commercial;
    }
    public static Dossiers reseauxSociaux(){
        Dossiers reseauxSociaux = new Dossiers(6, "ReseauxSociaux", "Messages des réseaux sociaux", "reseauxsociaux");
        return reseauxSociaux;
    }
    public static Dossiers brouillons(){
        Dossiers brouillons = new Dossiers(7, "Brouillons", "Messages brouillons, en attente", "brouillons");
        return brouillons;
    }
}
