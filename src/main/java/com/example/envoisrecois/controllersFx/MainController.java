package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.app.Envoyer;
import com.example.envoisrecois.outils.Fenetres;
import com.example.envoisrecois.outils.Positionnement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Pane paneMenu, paneNouveauMessage,
            paneBoiteReception, paneEnvoyes, paneCorbeille, paneSpam, paneCommercial, paneReseauxSociaux, paneBoite1, paneBoite2;
    @FXML
    private SplitPane paneCentralSplit;
    @FXML
    private ScrollPane paneDossiers, paneListeMessages, paneDetailsMessages;
    @FXML
    private AnchorPane anchorDossiers, anchorListeMessages, anchorDetailsMessages;
    @FXML
    private VBox vBoxDossiers;


    /**
     * Methode au chargement du controlleur
     */
    public void onLoad(){
        positionnementScrollPane(paneDossiers, anchorDossiers, 1);
        positionnementPaneDossiers(paneMenu, 2);
        positionnementSplitCentral(paneCentralSplit);
        positionnementPaneDossiers(paneNouveauMessage, 3);
        positionnementScrollPane(paneListeMessages, anchorListeMessages, 2);
        positionnementScrollPane(paneDetailsMessages, anchorDetailsMessages, 3);
//        anchorDossiers.setStyle("-fx-background-color: red;");
        createDossiers();
    }

    public void positionnementPaneDossiers(Pane pane, int position){
        double caracPaneDossiers[] = {200, 200, 20, 60};
        switch (position){
            case 1: caracPaneDossiers = Positionnement.paneDossiers(); break;
            case 2: caracPaneDossiers = Positionnement.paneMenu(); break;
            case 3: caracPaneDossiers = Positionnement.paneCentral();
        }

        pane.setPrefWidth(caracPaneDossiers[0]);
        pane.setPrefHeight(caracPaneDossiers[1]);
        pane.setLayoutX(caracPaneDossiers[2]);
        pane.setLayoutY(caracPaneDossiers[3]);
    }
    public void positionnementSplitCentral(SplitPane splitPane){
        double caracPaneDossiers[] = {200, 200, 20, 60};
        caracPaneDossiers = Positionnement.paneCentral();

        splitPane.setPrefWidth(caracPaneDossiers[0]);
        splitPane.setPrefHeight(caracPaneDossiers[1]);
        splitPane.setLayoutX(caracPaneDossiers[2]);
        splitPane.setLayoutY(caracPaneDossiers[3]);
    }
    public void positionnementScrollPane(ScrollPane scrollPane, AnchorPane anchorPane, int position){
        double caracPaneDossiers[] = {200, 200, 20, 60};
        if(position == 1){
            caracPaneDossiers = Positionnement.paneDossiers();
        } else if(position == 2){
            caracPaneDossiers = Positionnement.paneListeMessages();
        } else {
            caracPaneDossiers = Positionnement.paneDetailMessages();
        }

        scrollPane.setPrefWidth(caracPaneDossiers[0]); anchorPane.setPrefWidth(caracPaneDossiers[0] - 15);
        scrollPane.setPrefHeight(caracPaneDossiers[1]); anchorPane.setPrefHeight(caracPaneDossiers[1] - 5);
        scrollPane.setLayoutX(caracPaneDossiers[2]); anchorPane.setLayoutX(0);
        scrollPane.setLayoutY(caracPaneDossiers[3]);anchorPane.setLayoutY(0);
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
    public void createDossiers(){
        // ajustement du vbox
//        vBoxDossiers.setPrefWidth(anchorDossiers.getPrefWidth());
//        vBoxDossiers.setPrefHeight(anchorDossiers.getPrefHeight());

        double layoutY = 50;
        createDossier(paneBoiteReception, "Boîte de réception", layoutY);
        createDossier(paneEnvoyes,"Messages envoyés", layoutY);
        createDossier(paneCorbeille, "Corbeille", layoutY);
        createDossier(paneCommercial, "Commercial", layoutY);
        createDossier(paneReseauxSociaux, "Réseaux sociaux", layoutY);
        createDossier(paneSpam, "Spams", layoutY);

        // ajustement du vbox
        Fenetres.ajustementHauteurConteneur(anchorDossiers, vBoxDossiers, 6, 190);
        creationBoutonsDossier();
    }
    public void createDossier(Pane pane, String texte, double layoutY){
        // mise en forme du pane
        Fenetres.miseEnformeDossiers(pane, anchorDossiers.getPrefWidth(), 190);

//        // mise en place du titre
//        Text titreDossiers = Fenetres.createText(texte, "Arial", FontWeight.BOLD, 30, Color.rgb(255, 127, 80));
//        pane.getChildren().add(titreDossiers);
//
//        // centrage du titre
//        // Obtenez la taille du Text après qu'il a été affiché
//        Bounds bounds = titreDossiers.getBoundsInParent();
//
//        // Récupérez la largeur et la hauteur
//        double widthText = bounds.getWidth();
//        double heightText = bounds.getHeight();
//        titreDossiers.setLayoutX(Positionnement.centrerX(widthText, anchorDossiers.getPrefWidth()));
//        titreDossiers.setLayoutY(layoutY);
//
//        // mise en place de l'ombre du titre
//        pane.getChildren().add(Fenetres.shadowTextV2(titreDossiers));
    }
    public void creationBoutonsDossier(){
        Fenetres.creationButton(paneBoiteReception, "Boite de réception", 200, 50,  this::handleButtonClick);
        Fenetres.creationButton(paneEnvoyes, "Messages envoyés", 200, 50,  this::handleButtonClick);
        Fenetres.creationButton(paneCorbeille, "Corbeille", 200, 50,  this::handleButtonClick);
        Fenetres.creationButton(paneCommercial, "Commercial", 200, 50,  this::handleButtonClick);
        Fenetres.creationButton(paneReseauxSociaux, "Réseaux sociaux", 200, 50,  this::handleButtonClick);
        Fenetres.creationButton(paneSpam, "Spam", 200, 50,  this::handleButtonClick);
    }

    /**
     * Methode a executer au clic du bouton de la boite de reception
     * @param event
     */
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Le bouton a été cliqué !");
        // Ajoutez ici le code que vous souhaitez exécuter lorsque le bouton est cliqué.
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

    /**
     * Nouveau message
     * cache les panes liste et detail message
     * affiche/cree le pane : nouveau message
     */
    public void onNouveauMessage(){
        paneCentralSplit.setVisible(false);
        paneNouveauMessage.setVisible(true);
        paneNouveauMessage.setStyle("-fx-background-color: red;");
    }
    public void onBoiteReception(){
        paneNouveauMessage.setVisible(false);
        paneCentralSplit.setVisible(true);
    }
}