package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.app.*;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.ContactsService;
import com.example.envoisrecois.bdd.UtilisateursService;
import com.example.envoisrecois.outils.Fenetres;
import com.example.envoisrecois.outils.Positionnement;
import com.example.envoisrecois.outils.Securite;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Pane paneMenu, paneNouveauMessage,
            paneBoiteReception, paneEnvoyes, paneCorbeille, paneSpam, paneCommercial, paneReseauxSociaux,
            paneNouveauMessageBtnH, paneNouveauMessageEntete, paneNouveauMessageJoindre,
            paneContacts, paneDetailContact, paneContactBas, panceContactsBtn, paneListeContact;
    @FXML
    private SplitPane paneCentralSplit;
    @FXML
    private ScrollPane paneDossiers, paneListeMessages, paneDetailsMessages;
    @FXML
    private AnchorPane anchorDossiers, anchorListeMessages, anchorDetailsMessages;
    @FXML
    private VBox vBoxDossiers, vboxNouveauMessage, vboxContacts, vboxListeContacts;
    @FXML
    private HTMLEditor htmlNouveauMessage;
    @FXML
    TextField fieldSend, fieldSujet;

    // bdd
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private Utilisateurs utilisateur;
    private List<Contacts> listContacts = new ArrayList<>();
    private UtilisateursService utilisateursService;
    private ContactsService contactsService;
    private App app= new App();

    // utilise pour le switch de fenetres
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Methode au chargement du controlleur
     * recupere l'instance app de l'application
     */
    public void onLoad(App app){
        // recuperation de l'application
        this.app = app;
        // creation des dossiers par defaut
        creationDossiersDefaut();

        // ajout des contacts existants
        ajoutContacts();

        // ajout de la liste des messages

        // redimensionnement des fenetres de base
        redimFenetres();
        redimElements();
        createDossiers();
        onBoiteReception();

        // creation de la liste des contacts
        CreationElementsContact();

        // affichage de l'écran principal
        afficheUnPanePrincipal(paneCentralSplit);
        System.out.println(app);


    }
    /**
     * Redimensionne les fenetres de bases
     * par rapport à l'écran de l'utilisateur
     */
    public void redimFenetres(){
        positionnementScrollPane(paneDossiers, anchorDossiers, 1);
        positionnementPaneDossiers(paneMenu, 2);
        positionnementSplitCentral(paneCentralSplit);
        positionnementPaneDossiers(paneNouveauMessage, 3);
        positionnementPaneDossiers(paneContacts, 3);
        positionnementScrollPane(paneListeMessages, anchorListeMessages, 2);
        positionnementScrollPane(paneDetailsMessages, anchorDetailsMessages, 3);
        positionnementContacts();
    }

    /**
     * Redimensionne les éléments de l'application
     * par rapport à la taille de base de leur fenetre
     */
    public void redimElements(){
        redimNouveauMessage();
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

    /**
     * Dimensionne les éléments d'un nouveau message
     */
    public void redimNouveauMessage(){
        // vbox Nouveau message
        vboxNouveauMessage.setPrefWidth(paneNouveauMessage.getPrefWidth());
        vboxNouveauMessage.setPrefHeight(paneNouveauMessage.getPrefHeight());
        // pane des boutons haut
        paneNouveauMessageBtnH.setPrefWidth(vboxNouveauMessage.getPrefWidth());
        paneNouveauMessageBtnH.setPrefHeight(vboxNouveauMessage.getPrefHeight()*0.14);
        paneNouveauMessageBtnH.setStyle("-fx-background-color: lightblue;");
        // pane de l'entete du message
        paneNouveauMessageEntete.setPrefWidth(vboxNouveauMessage.getPrefWidth());
        paneNouveauMessageEntete.setPrefHeight(vboxNouveauMessage.getPrefHeight()*0.1);
        paneNouveauMessageEntete.setStyle("-fx-background-color: lavender;");
        // html Editor
        htmlNouveauMessage.setPrefWidth(vboxNouveauMessage.getPrefWidth());
        htmlNouveauMessage.setPrefHeight(vboxNouveauMessage.getPrefHeight() * 0.6);
        // pane des boutons bas
        paneNouveauMessageJoindre.setPrefWidth(paneNouveauMessageBtnH.getPrefWidth());
        paneNouveauMessageJoindre.setPrefHeight(vboxNouveauMessage.getPrefHeight()* 0.16);
        paneNouveauMessageJoindre.setStyle("-fx-background-color: lightgreen;");
    }
    public void positionnementContacts(){
        // recupere les dimensions du parent contacts
        vboxContacts.setPrefWidth(paneContacts.getPrefWidth());
        vboxContacts.setPrefHeight(paneContacts.getPrefHeight());
        // recupere les dimensions des details messages
        paneListeContact.setPrefWidth(paneDetailsMessages.getPrefWidth());
        paneListeContact.setPrefHeight(paneDetailsMessages.getPrefHeight());

        // recupere les dimensions la liste des contacts
        vboxListeContacts.setPrefWidth(paneListeContact.getPrefWidth());
        vboxListeContacts.setPrefHeight(paneListeContact.getPrefHeight());
    }

    @FXML
    protected void onHelloButtonClick() {
        String receiver = "delphine.laiglesia@free.fr";
        String subject = "Message de moi";
        String contenuMessage = "Salut c'est pas moi\nPour le deuxième message !!!";

        // Corps de l'e-mail au format HTML
        String corpsHtml = "<p>Ceci est un <strong>test</strong> d'e-mail au format HTML.</p>"
                + "<p>Vous pouvez mettre du texte <em>en italique</em>, changer les <span style='color:blue;'>couleurs</span>, etc.</p>";


        System.out.println("Envoi du message");
        Envoyer.envoyerMessage(receiver, subject, corpsHtml);

    }
    @FXML
    protected void onNouveauMessageClick(){
        String receiver = this.fieldSend.getText();
        String subject = this.fieldSujet.getText();
        String corpsHtml = this.htmlNouveauMessage.getHtmlText();

        if(Securite.validerEmail(fieldSend)){
        Envoyer.envoyerMessage(receiver, subject, corpsHtml);
        } else {
            System.out.println("Erreur de destinataire");
        }


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
        paneNouveauMessage.setStyle("-fx-background-color: red;");
        afficheUnPanePrincipal(paneNouveauMessage);
    }

    /**
     * cache tous les panes principaux sauf celui en parametre
     */
    public void afficheUnPanePrincipal(Pane paneAAfficher){
        paneCentralSplit.setVisible(false);
        paneNouveauMessage.setVisible(false);
        paneContacts.setVisible(false);
        paneAAfficher.setVisible(true);
        paneAAfficher.setStyle("-fx-background-color: lightblue;");
    }
    /**
     * cache tous les panes principaux sauf celui en parametre qui sera un splitPane
     */
    public void afficheUnPanePrincipal(SplitPane paneAAfficher){
        paneCentralSplit.setVisible(false);
        paneNouveauMessage.setVisible(false);
        paneContacts.setVisible(false);
        paneAAfficher.setVisible(true);
        paneAAfficher.setStyle("-fx-background-color: lightblue;");
    }
    public void onBoiteReception(){
        afficheUnPanePrincipal(paneCentralSplit);
    }
    public void onBtnContacts(){
        afficheUnPanePrincipal(paneContacts);
    }
    /**
     * Recupere la liste des contacts pour l'utilisateur depuis la bdd
     * @return
     */
    public List<Contacts> listeDesContacts(){
        List<Contacts> listeDesContacts;
        connectionBdd.connect();
        contactsService = new ContactsService(connectionBdd);
        listeDesContacts = contactsService.listeContactsUtilisateur(app.getUtilisateur().getId());
        return listeDesContacts;
    }

    /**
     * Ajoute la liste de contacts à l'application
     */
    public void  ajoutContacts(){
        listContacts.addAll(listeDesContacts());
        app.setListeContacts(listContacts);
    }
    /**
     * Crée les dossiers par défaut
     */
    public void creationDossiersDefaut(){
        app.ajouterDossier(AssetSetter.boiteReception());
        app.ajouterDossier(AssetSetter.envoyes());
        app.ajouterDossier(AssetSetter.corbeille());
        app.ajouterDossier(AssetSetter.spams());
        app.ajouterDossier(AssetSetter.commercial());
        app.ajouterDossier(AssetSetter.reseauxSociaux());
        app.ajouterDossier(AssetSetter.brouillons());
    }

    /**
     * permet de gérer la creation du pane de la liste des contacts
     */
    public void CreationElementsContact(){
        // parcours la liste des contacts et ajoute 1 pane par contact
        double layoutY = 0;
        if(app.getListeContacts().size() > 0){
            for (Contacts contact: app.getListeContacts()) {
                paneListeContacts(vboxListeContacts, contact.getId(), contact.getNom(), contact.getEmail(), layoutY);
                layoutY += 60;
            }
        } else {
            paneListeContactVide();
        }


    }

    /**
     * cree automatique les panes pour chaque contact
     * @param vBoxParent
     * @param idContact
     * @param nom
     * @param email
     * @param layoutYPaneprincipal
     */
    public void paneListeContacts(VBox vBoxParent, int idContact, String nom, String email, double layoutYPaneprincipal){
        // mise en fome pane
        Pane panePrincipal = new Pane();
        panePrincipal.setPrefWidth(vBoxParent.getPrefWidth());
        panePrincipal.setPrefHeight(50);
        panePrincipal.setLayoutY(layoutYPaneprincipal);

        double widthLabel = 250;
        double widthBouton = 80;
        double heightElement = 30;
        double ecart = 20;
        double layoutX = ecart;
        double layoutY = 10;

        // creation des labels
        Label labelNom = Fenetres.createLabel(nom, widthLabel, heightElement, layoutX, layoutY);
        layoutX += widthLabel + ecart;
        Label labelEmail = Fenetres.createLabel(email, widthLabel, heightElement, layoutX, layoutY);
        layoutX += widthLabel + ecart;

        // creation de boutons
        Button buttonMaj = Fenetres.createButton("Modifier", widthBouton, heightElement, layoutX, layoutY);
        buttonMaj.setOnAction(event -> {
            onMajContact(idContact);
        });
        layoutX += widthBouton + ecart;
        Button buttonEcrire = Fenetres.createButton("Ecrire", widthBouton, heightElement, layoutX, layoutY);
        buttonEcrire.setOnAction(event -> {
            onEnvoiMessageContact(idContact);
        });
        layoutX += widthBouton + ecart;
        Button buttonSuppr = Fenetres.createButton("Suppr", widthBouton, heightElement, layoutX, layoutY);
        buttonSuppr.setOnAction(event -> {
            onSupprContact(idContact);
        });

        Separator separator = Fenetres.createSeparator(panePrincipal.getPrefWidth()-(ecart*2), 2, ecart, panePrincipal.getPrefHeight()-4);

        // ajout des elements dans le pane
        panePrincipal.getChildren().addAll(labelNom, labelEmail, buttonMaj, buttonEcrire, buttonSuppr, separator);

        // ajout du pane dans le vbox
        vBoxParent.setStyle("-fx-background-color: white;");
        vBoxParent.getChildren().add(panePrincipal);
    }
    public void onMajContact(int id){
        System.out.println("maj le contact " + id);
    }
    public void onEnvoiMessageContact(int id){
        System.out.println("envouie un message au contact " + id);
    }
    public void onSupprContact(int id){
        System.out.println("supprime le contact " + id);
    }
    public void paneListeContactVide(){
        // mise en fome pane
        Pane panePrincipal = new Pane();
        panePrincipal.setPrefWidth(vboxListeContacts.getPrefWidth());
        panePrincipal.setPrefHeight(200);
        panePrincipal.setLayoutY(0);

        double widthLabel = panePrincipal.getPrefWidth();
        double widthBouton = 200;
        double heightElement = 30;
        double layoutY = 120;

        // creation des labels
        Label labelNom = Fenetres.createLabel("Vous n'avez aucun contact d'enregistré", widthLabel, heightElement, 0, layoutY);
        labelNom.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        labelNom.setAlignment(Pos.CENTER);

        // creation de boutons
        Button buttonMaj = Fenetres.createButton("Créer un contact", widthBouton, heightElement, Positionnement.centrerX(widthBouton, panePrincipal.getPrefWidth()), layoutY + heightElement + 40);
        buttonMaj.setOnAction(event -> {
            onMajContact(1);
        });

        // ajout des elements dans le pane
        panePrincipal.getChildren().addAll(labelNom, buttonMaj);

        // ajout du pane dans le vbox
        vboxListeContacts.setStyle("-fx-background-color: white;");
        vboxListeContacts.getChildren().add(panePrincipal);
    }
}