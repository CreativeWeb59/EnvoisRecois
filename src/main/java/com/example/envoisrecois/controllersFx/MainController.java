package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.app.Envoyer;
import com.example.envoisrecois.outils.Positionnement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Pane paneDossiers, paneMenu, paneListeMessages, paneDetailsMessages;

    /**
     * Methode au chargement du controlleur
     */
    public void onLoad(){
        System.out.println("Mise en place des panes de base");
        positionnementPaneDossiers(paneDossiers, 1);
        positionnementPaneDossiers(paneMenu, 2);
        positionnementPaneDossiers(paneListeMessages, 3);
        positionnementPaneDossiers(paneDetailsMessages, 4);
    }

    public void positionnementPaneDossiers(Pane pane, int position){
        double caracPaneDossiers[] = {200, 200, 20, 60};
        switch (position){
            case 1: caracPaneDossiers = Positionnement.paneDossiers(); break;
            case 2: caracPaneDossiers = Positionnement.paneMenu(); break;
            case 3: caracPaneDossiers = Positionnement.paneListeMessages(); break;
            case 4: caracPaneDossiers = Positionnement.paneDetailMessages(); break;
        }

        pane.setPrefWidth(caracPaneDossiers[0]);
        pane.setPrefHeight(caracPaneDossiers[1]);
        pane.setLayoutX(caracPaneDossiers[2]);
        pane.setLayoutY(caracPaneDossiers[3]);

//        paneListeMessages.setVisible(false);

    }
    @FXML
    protected void onHelloButtonClick() {
        String receiver = "delphine.laiglesia@free.fr";
        String subject = "Message de moi";
        String contenuMessage = "Salut c'est pas moi\nPour le deuxième message !!!";

        // Corps de l'e-mail au format HTML
        String corpsHtml = "<p>Ceci est un <strong>test</strong> d'e-mail au format HTML.</p>"
                + "<p>Vous pouvez mettre du texte <em>en italique</em>, changer les <span style='color:blue;'>couleurs</span>, etc.</p>";


        Envoyer.envoyerMessage(receiver, subject, corpsHtml);
        welcomeText.setText("Message envoyé avec succés");

    }

    /**
     * Quitte l'application
     */
    @FXML
    protected void onQuit(){
//        public void exitJeu(ActionEvent event) {
            // Code pour quitter l'application
            Platform.exit();
    }
}