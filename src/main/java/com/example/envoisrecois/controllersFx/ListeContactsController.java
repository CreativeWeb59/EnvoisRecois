package com.example.envoisrecois.controllersFx;

import com.example.envoisrecois.app.Contacts;
import com.example.envoisrecois.app.Utilisateurs;
import com.example.envoisrecois.bdd.ConnectionBdd;
import com.example.envoisrecois.bdd.ContactsService;
import com.example.envoisrecois.bdd.UtilisateursService;
import com.example.envoisrecois.outils.Securite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class ListeContactsController {
    @FXML
    private HBox hBoxListeContacts;
    @FXML
    private TextField fieldNom, fieldPrenom, fieldEmail;
    @FXML
    private Button buttonSuppr;
    private ConnectionBdd connectionBdd = new ConnectionBdd();
    private ContactsService contactsService;
    public void ajoutContact(Contacts contact){
        fieldNom.setText(contact.getNom());
        miseEnForme(fieldNom);

        fieldPrenom.setText(contact.getPrenom());
        miseEnForme(fieldPrenom);

        fieldEmail.setText(contact.getEmail());
        miseEnForme(fieldEmail);

        // maj du bouton
        buttonSuppr.setOnAction(event -> {
            onBtnSupprContact(contact);
        });
    }
    public void miseEnForme(TextField textField){
        textField.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        textField.setStyle("-fx-background-color: white;");
        textField.setEditable(false);
    }

    /**
     * Suppression du contact
     * avec boite de confirmation
     */
    public void onBtnSupprContact(Contacts contact){
        boolean question = Securite.afficherConfirm("Suppression contact", "Voulez vous vraiment supprimer le contact\n" + contact.getPrenom() + " " + contact.getNom()  + " ?");
        if(question){
            System.out.println("suppression du contact : " + contact.getNom());
        } else {
            System.out.println("annulation");
        }
    }
    /**
     * Supprime la partie selectionnee en bdd
     * supprime le contact dans la liste des contacts
     * @param id => id du contact
     */
    public void suppressionContact(int id) {
        try {
            connectionBdd.connect();
//            this.contactsService.suprContact(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
