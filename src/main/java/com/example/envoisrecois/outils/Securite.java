package com.example.envoisrecois.outils;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Securite {
    public boolean texteVide(TextField champTexte){
        return false;
    }
    public static boolean validerEmail(TextField emailTextField) {
        String email = emailTextField.getText().trim();

        if (isValidEmail(email)) {
            // L'adresse e-mail est valide
//            afficherAlerte("Adresse e-mail valide", "L'adresse e-mail est correcte.");
            return true;
        } else {
            // L'adresse e-mail n'est pas valide
            afficherAlerte("Adresse e-mail non valide", "Veuillez entrer une adresse e-mail valide.");
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        // Expression régulière pour valider une adresse e-mail simple
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        return email.matches(regex);
    }

    public static void afficherAlerte(String titre, String message) {
        Alert alerte = new Alert(Alert.AlertType.WARNING);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
    /**
     * Mise en forme du pseudo :
     * on enleve tous les espaces
     * nombre mini de caratères : 3
     * nombre maxi de caratères : 20
     * Caracteres autorisés : alphabet de a à z + chiffres de 0 à 9
     */
    public static Resultat testTailleChaine(String chaine, int min, int max, boolean accents) {
        if (chaine.length() > 0) {
            String chaineATraiter = miseEnFormePseudo(chaine);

            // Test taille de la chaine
            Boolean taille = isTailleChaine(chaine, min, max);

            // Test du contenu de la chaine
            Boolean contenu = isAlphabet(chaineATraiter, accents);

            if (taille && contenu) {
                return new Resultat(chaineATraiter, true);
            } else {
                return new Resultat("La chaine n'est pas valide", false);
            }
        } else {
            return new Resultat("Le champ est vide", false);
        }
    }

    /**
     * On passe la chaine tout en majuscule
     * on enlève tous les espaces
     *
     * @param chaine
     * @return
     */
    public static String miseEnFormePseudo(String chaine) {
        // tout minuscule
        chaine = chaine.toLowerCase();

        // enleve les caractères espace, les tabulations et les retours à la ligne
        String chaineTransformee = chaine.replaceAll("\\s", "");

        // enleve les espaces
        return chaineTransformee;
    }

    /**
     * la chaine doit contenir les caractères de l'alphabet
     * et doit commencer forcement par une lettre : cad les 26 premiers caractères de la liste
     *
     * @param chaine
     * @return
     */
    public static Boolean isAlphabet(String chaine, boolean accents) {
        ArrayList<String> alphabetList = new ArrayList<>(List.of(
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w", "x", "y", "z"
        ));
        // premier test sur le premier caractère qui doit être une lettre
        Boolean premierTest = alphabetList.contains(chaine.substring(0, 1));

        // ajoute les chiffres de 0 à 9
        for (int i = 0; i < 10; i++) {
            alphabetList.add(String.valueOf(i));
        }

        if(accents){
            alphabetList.addAll(List.of("à", "â", "ä", "é", "è", "ê", "ë", "î", "ï", "ô","ö", "ù"));
        }
        if (premierTest) {
            for (char c : chaine.toCharArray()) {
                String caractere = String.valueOf(c);
                if (!alphabetList.contains(caractere.toLowerCase())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * permet de verifer la taille d'une chaine de caractères
     * renvoi false si la taille n'est pas comprise entre les deux valeurs min et max
     *
     * @param chaine
     * @param max
     * @param min
     * @return
     */
    public static Boolean isTailleChaine(String chaine, int min, int max) {
        if (chaine.length() >= min && chaine.length() <= max) {
            return true;
        }
        return false;
    }
    /**
     * Affichage du message d'erreur pendant un certain laps de temps donnée
     *
     * @param labelErreur   le label qui doit être affiché temporairement
     * @param messageErreur le message à inscrire
     * @param delai         délai d'affichage du message
     */
    public static void afficherMessageTemporaire(Label labelErreur, String messageErreur, int delai) {
        // affiche le label pour la gestion des erreurs
        labelErreur.setVisible(true);

        labelErreur.setText(messageErreur); // Afficher le message dans le label

        // Créer une PauseTransition pour la durée spécifiée
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(delai));
        pauseTransition.setOnFinished(event -> {
            labelErreur.setText(""); // Effacer le contenu du label après le délai
            labelErreur.setVisible(false); // cache a nouveau le label d'erreur
        });

        pauseTransition.play(); // Démarrer la temporisation
    }

    public static void verifTextField(TextField fieldAVerifier, int min, int max, boolean accents, Label labelErreur, String MessageErreur){
        fieldAVerifier.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // Le TextField a perdu le focus
                System.out.println("Le TextField a perdu le focus.");
                if (verifChaineVide(fieldAVerifier.getText(), min, max, accents)) {
                    System.out.println("chaine ok");
                } else {
                    afficherMessageTemporaire(labelErreur, MessageErreur, 3000);
                    System.out.println("probleme dans la chaine");
                }
            }
        });
    }
    /**
     * Verifie si une chaine est valide :
     * nb de caractères min et max + lettres de l'alphabet et chiffres seulement autorisés
     * @param chaineATester
     * @param min
     * @param max
     * @return
     */
    public static boolean verifChaineVide(String chaineATester, int min, int max, boolean accents){
        Resultat resultatChaine = Securite.testTailleChaine(chaineATester, min, max, accents);
        return resultatChaine.getValeurBool();
    }
}
