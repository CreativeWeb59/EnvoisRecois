package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.Main;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.Utilisateurs;
import com.example.envoisrecois.bdd.UtilisateursService;
import com.example.envoisrecois.outils.Resultat;
import com.example.envoisrecois.outils.Securite;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.sql.SQLException;

public class LoginController {
    // elements FXML
    @FXML
    private Label labelErreur;
    @FXML
    private Pane paneLogin, paneInscription;
    @FXML
    private TextField fieldUserName, fieldNom, fieldMail, fieldprenom;
    @FXML
    private PasswordField fielPassword, fielPasswordConfirm;

    // utilise pour le switch de fenetres
    private Stage stage;
    private Scene scene;
    private Parent root;
    // variables supplementaires
    private String username;
    // bdd
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private Utilisateurs utilisateurs;
    private UtilisateursService utilisateursService;

    /**
     * Methode au chargement du controlleur
     */
    public void onLoad(){
        // creation tables si necessaire
        try {
            createDonnees();
        } catch (Exception e){
            System.out.println(e);
        }
        // verifie si un utilisateur existe dans la base
        // si oui affiche le formulaire de login
        // sinon affiche le formulaire de creation utilisateur
        if(nbUtilisateurs() > 0){
            paneLogin.setVisible(true);
            paneInscription.setVisible(false);
        } else {
            paneLogin.setVisible(false);
            paneInscription.setVisible(true);
        }

        // verification des champs du formulaire d'inscription
        // avec observable pour vérifier toute modification
        verifFormulaire();
    }
    /**
     * Teste si les tables sauvegarde et parametres existent
     * sinon les cree
     * Insertion des donnees dans les paramètres
     */
    public void createDonnees() throws SQLException {
        connectionBdd.connect();
        if (!connectionBdd.isModel("utilisateurs")) {
            connectionBdd.createModelUtilisateurs();
        }
        connectionBdd.close();
    }

    /**
     * donne le nombre d'utilisateurs dans la table utilisateurs
     * @return
     */
    public int nbUtilisateurs(){
        connectionBdd.connect();
        utilisateursService = new UtilisateursService(connectionBdd);
        int resultat = 0;
        resultat = utilisateursService.nombreUtilisateurs();
        connectionBdd.close();
        return resultat;
    }

    /**
     * Verifie si le userName existe dans la base
     */
    public void verifUserName(Event event){
        String username = fieldUserName.getText();

        // Serie de tests sur la validité du pseudo : taille, caractères...
        // renvoi le pseudo modifité (sans espaces...)
        // permet de recuperer l'erreur

        Resultat resultat = Securite.testTailleChaine(username, 3, 20, false);
        this.username = resultat.getChaine();
        // si chaine ok on teste l'existence du pseudo en bdd
        if (resultat.getValeurBool()) {
            // teste si le joueur existe en bdd
            connectionBdd.connect();
            if (!utilisateursService.existUserName(this.username)) {
                // cree le joueur en bdd
                // et cree l'instance joueur
                this.creerUtilisateur();
                // ouvre la page de gestion du jeu
                this.switchApplication(event);
            } else {
                // Le joueur existe on bloque le lancement du jeu
                this.labelErreur.setText("L'utilisateur existe déja");
                afficherMessageTemporaire(this.labelErreur, "L'utilisateur existe déja !", 3000);
            }
            connectionBdd.close();
        } else {
            afficherMessageTemporaire(this.labelErreur, resultat.getChaine(), 3000);
        }
    }
    public void verifFormulaire(){
        Securite.verifTextField(fieldUserName, 3, 10, false, labelErreur, "Probleme de username");
        Securite.verifTextField(fieldNom, 3, 20, false, labelErreur, "Probleme de nom");
        Securite.verifTextField(fieldprenom, 3, 20, true, labelErreur, "Probleme de prénom");
    }

    public boolean verifUserName222(){
        String username = fieldUserName.getText();

        // Serie de tests sur la validité du pseudo : taille, caractères...
        // renvoi le pseudo modifité (sans espaces...)
        // permet de recuperer l'erreur

        Resultat resultat = Securite.testTailleChaine(username, 3, 20, true);
        this.username = resultat.getChaine();
        // si chaine ok on teste l'existence du pseudo en bdd
        if (resultat.getValeurBool()) {
            // teste si le joueur existe en bdd
            connectionBdd.connect();
            if (!utilisateursService.existUserName(this.username)) {
                    // on peut creer le joueur
                return true;
            } else {
                // Le joueur existe on bloque le lancement du jeu
                this.labelErreur.setText("L'utilisateur existe déja");
                afficherMessageTemporaire(this.labelErreur, "L'utilisateur existe déja !", 3000);
            }
            connectionBdd.close();
        } else {
            afficherMessageTemporaire(this.labelErreur, resultat.getChaine(), 3000);
        }
        return true;
    }


    public void creerUtilisateur(){
        // recuperation des donnees
        connectionBdd.connect();
        this.utilisateurs = new Utilisateurs(username, "Maurer", "Delphine", "123", "delcri@free.fr", "1234", 1);
        try {
            utilisateursService.addJoueur(utilisateurs);
        } catch (Exception e){
            System.out.println(e);
        }
        connectionBdd.close();
    }
    /**
     * Affichage du message d'erreur pendant un certain laps de temps donnée
     *
     * @param label
     * @param message
     * @param delai
     */
    private void afficherMessageTemporaire(Label label, String message, int delai) {
        // affiche le label pour la gestion des erreurs
        this.labelErreur.setVisible(true);

        label.setText(message); // Afficher le message dans le label

        // Créer une PauseTransition pour la durée spécifiée
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(delai));
        pauseTransition.setOnFinished(event -> {
            label.setText(""); // Effacer le contenu du label après le délai
            this.labelErreur.setVisible(false); // cache a nouveau le label d'erreur
        });

        pauseTransition.play(); // Démarrer la temporisation
    }
    // gestion des boutons

    /**
     * Verifie les champs TextField
     * Verifie que l'utilisateur n'existe pas
     * Cree l'utilisateur
     * switch vers l'application => page de paramétrage
     */
    @FXML
    protected void onInscription(){

    }

    /**
     * Ouvre la page gestion
     *
     * @param event
     */
    public void switchApplication(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
            root = loader.load();
            MainController mainController = loader.getController();
            mainController.onLoad();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Affiche le pane d'inscription
     */
    @FXML
    protected void onSinscrire(){
        paneLogin.setVisible(false);
        paneInscription.setVisible(true);
    }
    /**
     * Affiche le pane de connexion
     */
    @FXML
    protected void onSeConnecter(){
        paneLogin.setVisible(true);
        paneInscription.setVisible(false);
    }
    /**
     * Quitte l'application
     */
    @FXML
    protected void onQuit(){
        // Code pour quitter l'application
        Platform.exit();
    }

    // evenement après clic bouton
    /**
     * Action a executer lors de la fermeture de la fentre avec la croix : sauvegarde
     *
     * @param event
     */

    public void onWindowClose(WindowEvent event) {
        // fermeture des barres, enregistrement + stop et sauvegarde date deco
//        fermetureProgress();
//        // sauvegarde bdd
//        sauveBdd();
//
//        // Sauvegarde de la base de donnees
//        System.out.println("fermeture fenetre : Sauvegarde");
//        try {
//            this.jeu.sauvegardejeu();
//            this.jeu.sauvegardeCredit();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
