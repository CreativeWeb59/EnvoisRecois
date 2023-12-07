package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.Main;
import com.example.envoisrecois.app.App;
import com.example.envoisrecois.app.Contacts;
import com.example.envoisrecois.app.Utilisateurs;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.ContactsService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.transform.Rotate;

public class MainViewController {
    @FXML
    private ScrollPane scrollListeDossiers;
    @FXML
    private SplitPane centreMessages;
    //    @FXML
//    private Pane centreNouveauContact;
    @FXML
    private GridPane gridListeDossiers, gridListeContacts;
    @FXML
    private HBox centreNouveauContact, listeMessages;
    @FXML
    private VBox centreContacts;
    // formulaire ajout contact
    @FXML
    private TextField ajoutNom, ajoutPrenom, ajoutEmail, ajoutTelephone;
    @FXML
    private TextArea ajoutNote;
    // bdd
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private Utilisateurs utilisateur;
    private List<Contacts> listContacts = new ArrayList<>();
    private UtilisateursService utilisateursService;
    private ContactsService contactsService;
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

        // initialisation de la liste des dossiers
        initialiseListeDossiers();

        // initialisation de la liste des contacts
//        initialiseListeContacts();
        creationListeContacts();

        onAfficheMessages();

        Rotate rotate = new Rotate(30, Rotate.Y_AXIS);
        centreNouveauContact.getTransforms().add(rotate);

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
     * @param fenetreCentrale
     */
    public void afficheCentrePage(Node fenetreCentrale) {
        listeMessages.setVisible(false);
        centreContacts.setVisible(false);
        centreMessages.setVisible(false);
        centreNouveauContact.setVisible(false);
        listeMessages.setVisible(false);

        fenetreCentrale.setVisible(true);

    }

    public void onAfficheContacts() {
        afficheCentrePage(centreContacts);
    }

    public void onAfficheMessages() {
        afficheCentrePage(listeMessages);
    }

    /**
     * Ajoute la liste de contacts à l'application
     */
    public void ajoutContacts() {
        listContacts.addAll(listeDesContacts());
        app.setListeContacts(listContacts);
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
        return listeDesContacts;
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
        gridListeContacts.add(hBox, 0, positionY);
        gridListeContacts.getRowConstraints().add(row);
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

    public void resetFormNouveauContact() {
        ajoutNom.setText("");
        ajoutPrenom.setText("");
        ajoutEmail.setText("");
        ajoutTelephone.setText("");
        ajoutNote.setText("");
    }
}
