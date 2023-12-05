package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.Main;
import com.example.envoisrecois.app.App;
import com.example.envoisrecois.app.Contacts;
import com.example.envoisrecois.app.Utilisateurs;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.ContactsService;
import com.example.envoisrecois.bdd.UtilisateursService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {
    @FXML
    private ScrollPane scrollListeDossiers, centreContacts;
    @FXML
    private SplitPane centreMessages;
    @FXML
    private GridPane gridListeDossiers, gridListeContacts;
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
        initialiseListeContacts();

        onAfficheMessages();
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
    public void initialiseListeDossiers(){
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
    public void initialiseListeContacts(){
        HBox contentHBox = null;
        int i = 0;
        try {
            for (Contacts contact: app.getListeContacts()) {
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
    public void  afficheCentrePage(Node fenetreCentrale){
        centreContacts.setVisible(false);
        centreMessages.setVisible(false);

        fenetreCentrale.setVisible(true);
        System.out.println("methode complete");
    }

    public void onAfficheContacts(){
        afficheCentrePage(centreContacts);
    }
    public void onAfficheMessages(){
        afficheCentrePage(centreMessages);
    }
    /**
     * Ajoute la liste de contacts à l'application
     */
    public void ajoutContacts(){
        listContacts.addAll(listeDesContacts());
        app.setListeContacts(listContacts);
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

}
