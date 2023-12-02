package com.example.envoisrecois.controllersFx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ListeDossiersController {
    @FXML
    private Button btnNomDossier;
    @FXML
    private HBox hBoxDossier;
    public void ajoutDossiers(int ligne, int color){
        String[] couleurs = {"blue", "lightblue", "green", "lightgreen", "red", "yellow", "Orange", "black", "purple", "grey"};
        hBoxDossier.setStyle("-fx-background-color: " + couleurs[color] + ";");

        btnNomDossier.setText("Bouton " + ligne);
    }
}
