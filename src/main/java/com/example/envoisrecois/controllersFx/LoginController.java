package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.Main;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.Utilisateurs;
import com.example.envoisrecois.bdd.UtilisateursService;
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
    private TextField fieldUserName, fieldNom, fieldEMail, fieldprenom;
    @FXML
    private PasswordField fielPassword, fielPasswordConfirm;

    // utilise pour le switch de fenetres
    private Stage stage;
    private Scene scene;
    private Parent root;
    // bdd
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private Utilisateurs utilisateurs;
    private UtilisateursService utilisateursService;
    // verification du formulaire
    private boolean fieldVerifUsername = false;
    private boolean fieldVerifNom = false;
    private boolean fieldVerifPrenom = false;
    private boolean fieldVerifEmail = false;
    private boolean fieldVerifPassword1 = false;
    private boolean fieldVerifPassword2 = false;

    /**
     * Methode au chargement du controlleur
     */
    public void onLoad() {
        // creation tables si necessaire
        try {
            createDonnees();
        } catch (Exception e) {
            System.out.println(e);
        }
        // verifie si un utilisateur existe dans la base
        // si oui affiche le formulaire de login
        // sinon affiche le formulaire de creation utilisateur
        if (nbUtilisateurs() > 0) {
            paneLogin.setVisible(true);
            paneInscription.setVisible(false);
        } else {
            paneLogin.setVisible(false);
            paneInscription.setVisible(true);
        }

        // creation de l'instance utilisateur  vide
        this.utilisateurs = new Utilisateurs("", "", "", "", "", "", 1);
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
     *
     * @return
     */
    public int nbUtilisateurs() {
        connectionBdd.connect();
        utilisateursService = new UtilisateursService(connectionBdd);
        int resultat = 0;
        resultat = utilisateursService.nombreUtilisateurs();
        connectionBdd.close();
        return resultat;
    }

    /**
     * Recupere la valeur des champs du formulaire
     * stocke dans la classe Utilisateurs en mettant en forme les attributs
     */
    public void creationUtilisateur(){
        String chaine = "";
        String chaineATraiter = Securite.miseEnFormePseudo(chaine);
    }
    /**
     * Teste si le username existe en dbb
     * si non le cree
     */
    public void verifUserNameBdd() {
        connectionBdd.connect();
        if (!utilisateursService.existUserName(fieldUserName.getText())) {
            // cree le joueur en bdd
            this.creerUtilisateur();
        } else {
            // Le joueur existe on bloque le lancement du jeu
            this.labelErreur.setText("L'utilisateur existe déja");
            afficherMessageTemporaire(this.labelErreur, "L'utilisateur existe déja !", 3000);
        }
        connectionBdd.close();
    }

    /**
     * Renseigne les boolean fieldVerifUsername...
     * true / false suivant verification de chaque champ
     */
    public void verifFormulaire() {
        verifTextField(fieldUserName, 3, 20, false, labelErreur, "Probleme de username");
        verifTextField(fieldNom, 3, 20, false, labelErreur, "Probleme de nom");
        verifTextField(fieldprenom, 3, 20, true, labelErreur, "Probleme de prénom");
        verifTextFieldEmail(fieldEMail, labelErreur, "Email non valide");
        verifPaswords(fielPassword, fielPasswordConfirm,3, 20, false, labelErreur, "Probleme de mot de passe");
        verifPaswords(fielPasswordConfirm, fielPassword, 3, 20, false, labelErreur, "Probleme de mot de passe");
    }

    /**
     * Creation de l'observable qui va ecouter les champs du formulaire
     * et renvoyer une erreur si c'est le cas
     *
     * @param fieldAVerifier nom du TextField à verifier
     * @param min            nb de caracteres minimum autorisés
     * @param max            nb de caracteres maximum autorisés
     * @param accents        prise en charge des accents ou non
     * @param labelErreur    nom du label à afficher
     * @param MessageErreur  message qui s'affichera dans le label
     */
    public void verifTextField(TextField fieldAVerifier, int min, int max, boolean accents, Label labelErreur, String MessageErreur) {
        String textFieldName = fieldAVerifier.getId();
        System.out.println("Champ a verifier : " + textFieldName);
        fieldAVerifier.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // Le TextField a perdu le focus
                System.out.println("Le TextField a perdu le focus.");
                if (Securite.verifChaineVide(fieldAVerifier.getText(), min, max, accents, 1)) {
                    valeurChampFormulaireVerif(textFieldName, true);
                    System.out.println("chaine ok");
                } else {
                    Securite.afficherMessageTemporaire(labelErreur, MessageErreur, 3000);
                    System.out.println("probleme dans la chaine");
                    valeurChampFormulaireVerif(textFieldName, false);
                }
            }
        });
    }

    /**
     * Creation de l'observable pour verifier l'email
     *
     * @param fieldEmailAVerifier
     */
    public void verifTextFieldEmail(TextField fieldEmailAVerifier, Label labelErreur, String MessageErreur) {
        String textFieldName = fieldEmailAVerifier.getId();
        System.out.println("Champ a verifier : " + textFieldName);
        fieldEmailAVerifier.focusedProperty().addListener((observable, oldValue, newValueMail) -> {
            if (!newValueMail) {
                if (Securite.validerEmail(fieldEmailAVerifier)) {
                    valeurChampFormulaireVerif(textFieldName, true);
                    System.out.println("email ok");
                } else {
                    Securite.afficherMessageTemporaire(labelErreur, MessageErreur, 3000);
                    System.out.println("probleme d'email");
                    valeurChampFormulaireVerif(textFieldName, false);
                }
            }
        });
    }

    /**
     * Verifie la validite du premier champ password
     * et verifie que les deux champs passwords sont égaux
     */
    public void verifPaswords(PasswordField fieldAVerifier1, PasswordField fieldAVerifier2, int min, int max, boolean accents, Label labelErreur, String MessageErreur){
        String textFieldName = fieldAVerifier1.getId();
        System.out.println("Champ a verifier : " + textFieldName);
        fieldAVerifier1.focusedProperty().addListener((observable, oldValue, newValuePassword) -> {
            if (!newValuePassword) {
                // Le TextField a perdu le focus
                if (Securite.verifChaineVide(fieldAVerifier1.getText(), min, max, accents, 2)) {
                    if(Securite.checkChaine(fieldAVerifier1.getText(), fieldAVerifier2.getText())){
                        valeurChampFormulaireVerif(textFieldName, true);
                        System.out.println("password ok");
                    } else {
                        Securite.afficherMessageTemporaire(labelErreur, MessageErreur, 3000);
                        System.out.println("Les champs ne sont pas identiques");
                        valeurChampFormulaireVerif(textFieldName, false);
                    }

                } else {
                    Securite.afficherMessageTemporaire(labelErreur, MessageErreur, 3000);
                    System.out.println("probleme dans la chaine");
                    valeurChampFormulaireVerif(textFieldName, false);
                }
            }
        });
    }

    /**
     * Permet de renseigner le bon boolean pour la verification des champs
     *
     * @param idChamp
     */
    public void valeurChampFormulaireVerif(String idChamp, boolean valeur) {
        switch (idChamp) {
            case "username":
                fieldVerifUsername = valeur;
                break;
            case "nom":
                fieldVerifNom = valeur;
                break;
            case "prenom":
                fieldVerifPrenom = valeur;
                break;
            case "email":
                fieldVerifEmail = valeur;
                break;
            case "password1":
                fieldVerifPassword1 = valeur;
                break;
            case "password2":
                fieldVerifPassword2 = valeur;
                break;
        }
    }
    /**
     * Creation de l'utilisateur en bdd
     */
    public void creerUtilisateur() {
//        String passwordHashed = BCrypt.hashpw(fielPassword.getText(), BCrypt.gensalt());
        String passwordHashed = Securite.hashPassword(fielPassword.getText());

        // recuperation des donnees
        connectionBdd.connect();
        this.utilisateurs = new Utilisateurs(fieldUserName.getText(), fieldNom.getText(), fieldprenom.getText(), passwordHashed, fieldEMail.getText(), "1234", 1);
        try {
            utilisateursService.addJoueur(utilisateurs);
        } catch (Exception e) {
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
    protected void onInscription() {
        if (fieldVerifUsername && fieldVerifNom && fieldVerifPrenom && fieldVerifEmail &&
        fieldVerifPassword1 && fieldVerifPassword2) {
            System.out.println("inscription ok");
            verifUserNameBdd();
        } else {
            System.out.println("probleme");
        }
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
    protected void onSinscrire() {
        paneLogin.setVisible(false);
        paneInscription.setVisible(true);
    }

    /**
     * Affiche le pane de connexion
     */
    @FXML
    protected void onSeConnecter() {
        paneLogin.setVisible(true);
        paneInscription.setVisible(false);
    }

    /**
     * Quitte l'application
     */
    @FXML
    protected void onQuit() {
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
