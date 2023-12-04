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
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private ScrollPane scrollListeDossiers, centreContacts;
    @FXML
    private SplitPane centreMessages;
    @FXML
    private GridPane gridListeDossiers;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

//            FXMLLoader fxmlLoader = new FXMLLoader();
        //            fxmlLoader.setLocation(getClass().getResource("ListeDossiers.fmlx"));
//
//        listeDossiersController.ajoutDossiers();


    }
    public void  afficheCentrePage(Node fenetreCentrale){
        centreContacts.setVisible(false);
        centreMessages.setVisible(false);

        fenetreCentrale.setVisible(true);
        System.out.println("methode complete");
    }

    public void onAfficheContacts(){
        System.out.println("methode on affiche Contacts");
        afficheCentrePage(centreContacts);
    }
    public void onAfficheMessages(){
        System.out.println("methode on affiche Message");
        afficheCentrePage(centreMessages);
    }
}
