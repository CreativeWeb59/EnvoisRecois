package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.Main;
import com.example.envoisrecois.app.*;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.ContactsService;
import com.example.envoisrecois.bdd.MessagesService;
import com.example.envoisrecois.bdd.UtilisateursService;
import com.example.envoisrecois.outils.Fenetres;
import com.example.envoisrecois.outils.Securite;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class MainViewController {
    @FXML
    private ScrollPane scrollListeDossiers;
    @FXML
    private SplitPane splitListeMessages;
    @FXML
    private GridPane gridListeDossiers, gridListeContacts, gridListeMessages;
    @FXML
    private HBox centreNouveauContact, listeMessages, nouveauMessage;
    @FXML
    private VBox centreContacts, vboxListeMessages;
    // formulaire ajout contact
    @FXML
    private TextField ajoutNom, ajoutPrenom, ajoutEmail, ajoutTelephone;
    // formulaire nouveau message
    @FXML
    private TextField nouvDestinataire, nouvObjet;
    @FXML
    private HTMLEditor nouvMessage;
    @FXML
    private Label nouveauExpediteur;

    @FXML
    private TextArea ajoutNote;
    // bdd
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private Utilisateurs utilisateur;
    private List<Contacts> listContacts = new ArrayList<>();
    private List<Messages> listMessages = new ArrayList<>();
    private List<Dossiers> listDossiers = new ArrayList<>();
    private UtilisateursService utilisateursService;
    private ContactsService contactsService;
    private MessagesService messagesService = new MessagesService(connectionBdd);
    private App app = new App();

    // utilise pour le switch de fenetres
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Methode au chargement du controlleur
     * recupere l'instance app de l'application
     */
    public void onLoad(App app) {
        // recuperation de l'application
        this.app = app;

        // récupération des contacts
        ajoutContacts();

        // récupération des messages depuis la bdd
//        recupMessages();

        // rempli la liste de messages depuis la messagerie
//        app.setListeMessages(Recevoir.recoisMessages());

        // initialisation de la liste des dossiers
        initialiseListeDossiers();

        // initialisation de la liste des contacts
//        initialiseListeContacts();
        creationListeContacts();

        // initialisation liste des messages
        creationNodeMessages();

        onAfficheMessages();

        miseEnPlace();

        System.out.println("Liste des messages : ");
        System.out.println(app.getListeMessages().size());

    }


    /**
     * Quitte l'application
     */
    @FXML
    protected void onQuit() {
//        public void exitJeu(ActionEvent event) {
        // Code pour quitter l'application
        Platform.exit();
    }

    public void initialiseListeDossiers() {
        HBox contentHBox = null;
        try {
            for (int i = 0; i < 10; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ListeDossiers.fxml"));
                contentHBox = fxmlLoader.load();
                ListeDossiersController listeDossiersController = fxmlLoader.getController();
                listeDossiersController.ajoutDossiers(i, i);
                gridListeDossiers.add(contentHBox, 0, i);
//                GridPane.setMargin(contentHBox, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * permet d'ajouter les derniers éléments récupérer de l'application
     */
    public void miseEnPlace(){
        // inscrit le nom de l'expediteur dans les nouveaux messages
        nouveauExpediteur.setText(app.getUtilisateur().getEmail() + "(" + app.getUtilisateur().getNom() + " " + app.getUtilisateur().getPrenom() + ")");
    }

    /**
     * Creation des dossiers de la boite de reception
     */
    public void createDossiers(){

    }
    public void initialiseListeContacts() {
        HBox contentHBox = null;
        int i = 0;
        try {
            for (Contacts contact : app.getListeContacts()) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ListeContacts.fxml"));
                contentHBox = fxmlLoader.load();
                ListeContactsController listeContactsController = fxmlLoader.getController();
                listeContactsController.ajoutContact(contact);
                gridListeContacts.add(contentHBox, 0, i);
                i++;
//                GridPane.setMargin(contentHBox, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cache toutes les fenetre principales et affiche uniquement celle demandée
     * @param fenetreAOuvrir
     */
    public void afficheCentrePage(Node fenetreAOuvrir) {
        listeMessages.setVisible(false);
        centreContacts.setVisible(false);
        centreNouveauContact.setVisible(false);
        nouveauMessage.setVisible(false);

        Fenetres.animationOuverture(fenetreAOuvrir);
    }

    public void onAfficheContacts() {
        afficheCentrePage(centreContacts);
    }

    public void onAfficheMessages() {
        afficheCentrePage(listeMessages);
    }

    public void onAfficheNouveauMessage() {
        afficheCentrePage(nouveauMessage);
    }

    /**
     * Ajoute la liste de contacts à l'application
     */
    public void ajoutContacts() {
        listContacts.addAll(listeDesContacts());
        app.setListeContacts(listContacts);
    }

    /**
     * Recupere les messages depuis la bdd
     */
    public void recupMessages(){
        listMessages.addAll(listeDesMessages());
        app.setListeMessages(listMessages);
    }
    /**
     * Recupere la liste des contacts pour l'utilisateur depuis la bdd
     *
     * @return
     */
    public List<Contacts> listeDesContacts() {
        List<Contacts> listeDesContacts;
        connectionBdd.connect();
        contactsService = new ContactsService(connectionBdd);
        listeDesContacts = contactsService.listeContactsUtilisateur(app.getUtilisateur().getId());
        connectionBdd.close();
        return listeDesContacts;
    }
    public List<Messages> listeDesMessages() {
        List<Messages> listeDesMessages;
        connectionBdd.connect();
        listeDesMessages = messagesService.listeMessagesUtilisateur(app.getUtilisateur().getId());
        connectionBdd.close();
        return listeDesMessages;
    }
    // tests liste des contacts interractive
    public void creationListeContacts() {
        // parcours la liste des contacts et ajoute 1 pane par contact
        if (app.getListeContacts().size() > 0) {
            int position = 0;
            for (Contacts contact : app.getListeContacts()) {
                dynamicListeContacts(gridListeContacts, contact, position);
                position++;
            }
        } else {
            System.out.println("Pas de contacts");
        }
        System.out.println("Creation de la liste des contacts");
    }
    public void creationNodeMessages(){
        // parcours la liste des contacts et ajoute 1 pane par contact
        if (app.getListeMessages().size() > 0) {
            int position = 0;
            for (Messages message : app.getListeMessages()) {
                dynamicListeMessages(gridListeMessages, message, position);
                position++;
            }
        } else {
            System.out.println("Pas de contacts");
        }
        System.out.println("Creation de la liste des contacts");
    }

    /**
     * cree automatique les panes pour chaque contact
     *
     * @param gridParent
     * @param contact
     * @param positionY  position dans la grille
     */
    public void dynamicListeContacts(GridPane gridParent, Contacts contact, int positionY) {
        // creation du hBox container
        HBox hBox = new HBox();
        hBox.setPrefWidth(gridParent.getPrefWidth());
        hBox.setPrefHeight(50);
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);

        // gestion de la hauteur des lignes
        RowConstraints row = new RowConstraints();
        row.setMinHeight(50);
        row.setPrefHeight(50);
        row.setMaxHeight(50);

        double widthLabel = 250;
        double widthBouton = 80;
        double heightElement = 30;

        // creation des textField
        javafx.scene.control.TextField textlNom = Fenetres.createTextField(contact.getNom(), widthLabel, heightElement, false);
        javafx.scene.control.TextField textPrenom = Fenetres.createTextField(contact.getPrenom(), widthLabel, heightElement, false);
        javafx.scene.control.TextField textEmail = Fenetres.createTextField(contact.getEmail(), widthLabel, heightElement, false);

        // creation de boutons
        javafx.scene.control.Button buttonMaj = Fenetres.createButton("Modifier", widthBouton, heightElement);
        buttonMaj.setOnAction(event -> {
//            onMajContact(vboxDetailContact, contact, 0);
            onMajContact();
        });
        javafx.scene.control.Button buttonEcrire = Fenetres.createButton("Ecrire", widthBouton, heightElement);
        buttonEcrire.setOnAction(event -> {
            onEnvoiMessageContact(contact.getEmail());
        });
        Button buttonSuppr = Fenetres.createButton("Suppr", widthBouton, heightElement);
        buttonSuppr.setOnAction(event -> {
            onBtnSupprContact(contact, positionY);
        });

        // ajout des elements dans le hbox
        hBox.getChildren().addAll(textlNom, textPrenom, textEmail, buttonMaj, buttonEcrire, buttonSuppr);

//        if(positionY %2 == 0){
//            hBox.setStyle("-fx-background-color: lightgreen;");
//        } else {
//            hBox.setStyle("-fx-background-color: lightblue;");
//        }

        // ajout du hbox dans le gridpane
        gridParent.add(hBox, 0, positionY);
        gridParent.getRowConstraints().add(row);
    }

    /**
     * Rempli automatiquement le grid pour la liste des messages
     * @param gridParent
     * @param message
     * @param positionY  position dans la grille
     */
    public static void dynamicListeMessages(GridPane gridParent, Messages message, int positionY){
        // creation du hBox container
        HBox hBox = new HBox();
        hBox.setPrefWidth(gridParent.getPrefWidth());
        hBox.setMinWidth(gridParent.getPrefWidth());
        hBox.setPrefHeight(50);
        hBox.setSpacing(0);
        hBox.setAlignment(Pos.CENTER);

        // gestion de la hauteur des lignes
        RowConstraints row = new RowConstraints();
        row.setMinHeight(40);
        row.setPrefHeight(40);
        row.setMaxHeight(40);

        double heightElement = 30;

        // taille : 820, 500, 200
        // creation des textField
        TextField textObjet = Fenetres.createTextField(message.getObjet(), 820, heightElement, false);
        TextField textExpediteur = Fenetres.createTextField(message.getReceveur(), 500, heightElement, false);
        TextField textDateMessage = Fenetres.createTextField(message.getDateMessage().toString(), 200, heightElement, false);

        // ajout des elements dans le hbox
        hBox.getChildren().addAll(textObjet, textExpediteur, textDateMessage);

//        if(positionY %2 == 0){
//            hBox.setStyle("-fx-background-color: lightgreen;");
//        } else {
//            hBox.setStyle("-fx-background-color: lightblue;");
//        }

        // ajout du hbox dans le gridpane
        gridParent.add(hBox, 0, positionY);
        gridParent.getRowConstraints().add(row);
    }
    public void onMajContact() {
        System.out.println("maj du contact");
    }

    public void onEnvoiMessageContact(String email) {
        System.out.println("Envoi message au contact");
    }

    /**
     * Suppression du contact
     * avec boite de confirmation
     */
    public void onBtnSupprContact(Contacts contact, int rowToDelete) {
        boolean question = Securite.afficherConfirm("Suppression contact", "Voulez vous vraiment supprimer le contact\n" + contact.getPrenom() + " " + contact.getNom() + " ?");
        if (question) {
            try {
                connectionBdd.connect();
                this.contactsService.suprContact(contact.getId());
                System.out.println("suppression du contact : " + contact.getNom());

                // supprime le contact de la liste
                listContacts.remove(contact);

                // supprime toutes les lignes
                gridListeContacts.getChildren().clear();

                // recree la liste
                creationListeContacts();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("annulation");
        }
    }

    public void onNouveauContact() {
        afficheCentrePage(centreNouveauContact);
    }

    /**
     * Ajoute un contact
     */
    public void onAjoutContact() {
        // recuperation des donnéées des textfields

        // verification des données du formulaire contact

        // ajout du contact dans l'app
        Contacts nouveauContact = new Contacts(ajoutNom.getText(), ajoutPrenom.getText(), ajoutEmail.getText(), "photo", ajoutTelephone.getText(), ajoutNote.getText(), app.getUtilisateur().getId());
        // ajout dans la liste
        listContacts.add(nouveauContact);
        // ajout du contact dans la bdd
        try {
            connectionBdd.connect();
            this.contactsService.addContact(nouveauContact);

            // supprime toutes les lignes de la liste des contacts
            gridListeContacts.getChildren().clear();

            // recree la liste des contacts
            creationListeContacts();

            // reset du formulaire
            resetFormNouveauContact();

            // affiche la liste des contacts
            afficheCentrePage(centreContacts);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Envoie le message
     */
    public void onEnvoyerMessage(){
        // recuperation des textfields
        String receiver = this.nouvDestinataire.getText();
        String objet = this.nouvObjet.getText();
        String corpsHtml = this.nouvMessage.getHtmlText();
        LocalDateTime dateEncours = LocalDateTime.now();

        // creation d'un nouveau message dans l'app
        Messages nouveauMessage = new Messages(app.getUtilisateur().getId(), app.getUtilisateur().getEmail(), receiver, dateEncours, objet, corpsHtml, 1);

        // ecriture du message dans la bdd
        try {
            connectionBdd.connect();
            this.messagesService.addMessage(nouveauMessage);
        } catch (Exception e) {
            System.out.println(e);
        }

        // envoi de l'email
        if(Securite.validerEmail(nouvDestinataire)){
            Envoyer.envoyerMessage(receiver, objet, corpsHtml, app.getUtilisateur().getEmail(), app.getPasswordMessagrie());
        } else {
            System.out.println("Erreur de destinataire");
        }

        // reset des champs
        resetFormNouveauMessage();
        // renvoie vers la boite de reception
        afficheCentrePage(listeMessages);
    }

    /**
     * remet à zéro les champs du formulaire nouveau contact
     */
    public void resetFormNouveauContact() {
        ajoutNom.setText("");
        ajoutPrenom.setText("");
        ajoutEmail.setText("");
        ajoutTelephone.setText("");
        ajoutNote.setText("");
    }
    /**
     * remet à zéro les champs du formulaire nouveau message
     */
    public void resetFormNouveauMessage() {
        nouvDestinataire.setText("");
        nouvObjet.setText("");
        nouvMessage.setHtmlText("");
    }

}
