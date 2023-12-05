package com.example.envoisrecois.outils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Cursor;

/**
 * cree les labels, textes et cadres necessaires à l'application
 */
public class Fenetres {
    /**
     * Methode pour creer un Text
     */
    public static Text createText(String content, String fontName, FontWeight fontWeight, int fontSize, Color fontColor){
        Text objetText = new Text(content);
        objetText.setFont(Font.font(fontName, fontWeight, fontSize));
        objetText.setFill(fontColor);
        // affichage du titre 1
        objetText.setLayoutX(50);
        objetText.setLayoutY(400);

        return objetText;
    }
    /**
     * Methode pour creer une bordure sur un texte
     */
    public static Text bordureText(Text objetText){
        // Ajouter une bordure rouge d'épaisseur 2 pixels
        objetText.setStroke(Color.RED);
        objetText.setStrokeWidth(1);
        return objetText;
    }
    /**
     * Methode pour creer une ombre sur un texte
     * utilisation de dropShadow
     */
    public static Text shadowText(Text objetText){
        // Ajouter un ombrage noir avec un décalage de 3 pixels en X et Y
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        objetText.setEffect(dropShadow);
        return objetText;
    }
    /**
     * Methode pour creer une ombre sur un texte
     * doublage du Text avec decalage
     */
    public static Text shadowTextV2(Text objetText){
        Text textOmbre = new Text(objetText.getText());
        // texte ombre
//        textOmbre.setFill(Color.rgb(51, 102, 255, 1));

        // Récupérer la couleur RGB
        Color originalColor = (Color) objetText.getFill();
        Color darker = darkerColor(originalColor, 0.8); // Crée une couleur plus foncée (factor = 0.8)

        textOmbre.setFill(darker);
        textOmbre.setFont(objetText.getFont());
        // affichage de l'ombre
        textOmbre.setLayoutX(objetText.getLayoutX()-2);
        textOmbre.setLayoutY(objetText.getLayoutY()-2);
        return textOmbre;
    }

    /**
     * Renvoi une couleur plus foncée que celle donnée en parametres
     * @param color
     * @param factor
     * @return
     */
    public static Color darkerColor(Color color, double factor) {
        return color.deriveColor(0, 1, factor, 1);
    }

    /**
     * mise en forme des panes coté gauche
     * boite de reception, répertoires ...
     */
    public static void miseEnformeDossiers(Pane pane, double width, double height){
        pane.setPrefWidth(width - 50);
        pane.setPrefHeight(height);
        pane.setStyle("-fx-background-color: lightred;");
        pane.setStyle("-fx-border-color: green; -fx-border-width: 2;");
    }
    /**
     * Permet d'ajuster la bonne taille pour le scroll pane et vbox
     * pour avoir un bon defilement
     */
    public static void ajustementHauteurConteneur(AnchorPane anchorPane, VBox vBox, int nbPanes, int heightPane){
        double totalHeight = (nbPanes * heightPane);
        anchorPane.setPrefHeight(totalHeight);
        vBox.setPrefWidth(anchorPane.getPrefWidth() - 10);
        vBox.setPrefHeight(totalHeight);
    }

    /**
     * Permet de créer des boutons
     */
    public static void creationButton(Pane paneParent, String texteBouton, double width, double height, EventHandler<ActionEvent> eventHandler){
        Button newButton = new Button(texteBouton);
        newButton.setPrefWidth(width);
        newButton.setPrefHeight(height);
        newButton.setOnAction(eventHandler);
        newButton.setTextFill(Color.WHITE);
        newButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // ombre
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.DARKBLUE);
        newButton.setEffect(dropShadow);

        // Définir les bords arrondis
        CornerRadii cornerRadii = new CornerRadii(14); // Ajustez le rayon selon vos préférences
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLUE, cornerRadii, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        newButton.setBackground(background);

//        newButton.setStyle("-fx-background-color: red;");

        // Définir le pointeur de la souris sur hover
        newButton.setOnMouseEntered(e -> newButton.setCursor(Cursor.HAND));

        // Remettre le pointeur par défaut lorsque la souris quitte le bouton
        newButton.setOnMouseExited(e -> newButton.setCursor(Cursor.DEFAULT));


        // centrage du bouton
        newButton.setLayoutX(Positionnement.centrerX(width-50, paneParent.getPrefWidth()));
//        newButton.setLayoutY(Positionnement.centrerY(height, paneParent.getPrefHeight()));
        newButton.setLayoutY(46);
        paneParent.getChildren().add(newButton);
    }
    public static void cacherPane(Pane pane){

    }

    /**
     * Permet de mettre en forme le label erreur
     * position x / y + couleur et fonte
     * @param label
     * @param valeurX
     * @param valeurY
     */
    public static void labelErreur(Label label, double valeurX, double valeurY){
        // position y
        label.setLayoutX(valeurX);
        label.setLayoutY(valeurY);
        label.setAlignment(Pos.CENTER);
        // fonte
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        // couleur
        label.setStyle("-fx-background-color: red;");
        label.setTextFill(Color.WHITE);
    }


    /**
     * Creation par defaut d'un label
     * @param description
     * @param width
     * @param height
     * @param layoutX
     * @param layoutY
     * @return
     */
    public static Label createLabel(String description, double width, double height, double layoutX, double layoutY){
        Label label = new Label(description);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPadding(new Insets(5, 10, 5, 10));
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setStyle("-fx-background-color: white;");
        label.setMouseTransparent(false);
        return label;
    }

    /**
     * Creation automatique d'un Text
     * @param description
     * @param width
     * @param height
     * @param layoutX
     * @param layoutY
     * @return
     */
    public static Text createText(String description, double width, double height, double layoutX, double layoutY){
        Text text = new Text("description");
        text.setWrappingWidth(width);
//        text.setprefHeight(height);
        text.setLayoutX(layoutX);
        text.setLayoutY(layoutY);
//        text.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.web("#FF0000")); // Rouge
        text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        text.setStyle("-fx-background-color: white;");
        text.setMouseTransparent(true);
        return text;
    }

    /**
     * Creation automatique d'un textfield
     * il affichera juste des donnees
     * le boolean editable permet de rendre non modifable le champs
     * l'objectif est de rendre sélectionnable le champ
     * @param leTexte
     * @param width
     * @param height
     * @param layoutX
     * @param layoutY
     * @param editable
     * @return
     */
    public static TextField createTextField(String leTexte, double width, double height, double layoutX, double layoutY, boolean editable){
        TextField textField = new TextField();
        textField.setText(leTexte);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPadding(new Insets(5, 10, 5, 10));
        textField.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        textField.setStyle("-fx-background-color: white;");
        textField.setEditable(editable);
        return textField;
    }

    /**
     * Creation d'un textField dans un HBox (sans layout X, Y qui sont inutiles
     * @param leTexte
     * @param width
     * @param height
     * @param editable
     * @return
     */
    public static TextField createTextField(String leTexte, double width, double height, boolean editable){
        TextField textField = new TextField();
        textField.setText(leTexte);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        textField.setPadding(new Insets(5, 10, 5, 10));
        textField.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        textField.setStyle("-fx-background-color: white;");
        textField.setEditable(editable);
        return textField;
    }
    /**
     * creation par defaut d'un bouton
     * @param description
     * @param width
     * @param height
     * @param layoutX
     * @param layoutY
     * @return
     */
    public static Button createButton(String description, double width, double height, double layoutX, double layoutY){
        Button button = new Button(description);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        return button;
    }
    public static Separator createSeparator(double width, double height, double layoutX, double layoutY){
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setVisible(true);
        separator.setStyle("-fx-background-color: black;");
        separator.setPrefWidth(width);
        separator.setPrefHeight(height);
        separator.setLayoutX(layoutX);
        separator.setLayoutY(layoutY);
        return separator;
    }
}
