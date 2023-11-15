package com.example.envoisrecois.outils;

/**
 * permet de positionner les fenetres javafx
 */
public class Positionnement {
    /**
     * renvoi la largeur, hauteur
     * le layoutx et layoutY du pane Dossier
     */
    public static double[] paneDossiers(){
        // valeurs par defaut
        double width = (Environment.getWidth() / 4) - 10;
        double height = Environment.getHeight() * 0.85;
        double layoutX = 10;
        double layoutY = Environment.getHeight() * 0.14;
        double resultat[] = {width, height, layoutX, layoutY};

        return resultat;
    }

    /**
     * renvoi la largeur, hauteur
     * le layoutx et layoutY du pane Menu
     */
    public static double[] paneMenu(){
        // valeurs par defaut
        double width = (Environment.getWidth()) - 20;
        double height = (Environment.getHeight() * 0.14) - 10;
        double layoutX = 10;
        double layoutY = Environment.getHeight() * 0;
        double resultat[] = {width, height, layoutX, layoutY};

        return resultat;
    }

    /**
     * renvoi la largeur, hauteur
     * le layoutx et layoutY du pane Liste messages
     */
    public static double[] paneListeMessages(){
        // valeurs par defaut
        double width = (Environment.getWidth() / 4 * 3) - 20;
        double height = (Environment.getHeight() * 0.85 / 2) - 5;
        double layoutX = (Environment.getWidth() / 4) + 10;
        double layoutY = Environment.getHeight() * 0.14;
        double resultat[] = {width, height, layoutX, layoutY};

        return resultat;
    }

    /**
     * renvoi la largeur, hauteur
     * le layoutx et layoutY du pane Liste messages
     */
    public static double[] paneDetailMessages(){
        // valeurs par defaut
        double width = (Environment.getWidth() / 4 * 3) - 20;
        double height = (Environment.getHeight() * 0.85 / 2);
        double layoutX = (Environment.getWidth() / 4) + 10;
        double layoutY = (Environment.getHeight() * 0.14) + (Environment.getHeight() * 0.85 / 2);
        double resultat[] = {width, height, layoutX, layoutY};

        return resultat;
    }
}
