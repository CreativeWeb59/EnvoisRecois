package com.example.envoisrecois.outils;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
public class Environment {
    /**
     * Permet de renvoyer la largeur de l'écran
     * @return
     */
    public static double getWidth(){
        // Récupérer l'écran principal
        Screen screen = Screen.getPrimary();

        // Récupérer la taille de l'écran principal
        Rectangle2D bounds = screen.getBounds();

        // largeur de la bande
        double decalageWidth = 0;

        return bounds.getWidth() - decalageWidth;
    }

    /**
     * Permet de renvoyer la hauteur de l'écran
     * @return
     */
    public static double getHeight(){
        // Récupérer l'écran principal
        Screen screen = Screen.getPrimary();

        // Récupérer la taille de l'écran principal
        Rectangle2D bounds = screen.getBounds();

        // hauteur de la bande
        double decalageHeight = 80;
        return bounds.getHeight() - decalageHeight;
    }
}
