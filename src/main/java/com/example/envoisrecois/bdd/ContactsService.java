package com.example.envoisrecois.bdd;

import com.example.envoisrecois.app.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsService {
    private ConnectionBdd connectionBdd;

    public ContactsService(ConnectionBdd connectionBdd) {
        this.connectionBdd = connectionBdd;
    }
    public void suprContact(String nom) {
        String sql = "DELETE FROM contacts WHERE idUtilisateur LIKE ?";
        try {
            PreparedStatement stmt = connectionBdd.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.executeUpdate();
            System.out.println("Contact " + nom + " supprimé");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * renvoie une liste des contacts appartenant a un utilisateur
     * @param idUtilisateur
     * @return
     */
    public List<Contacts> listeContactsUtilisateur(int idUtilisateur){
        List<Contacts>listeContacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE idUtilisateur LIKE ?";
        try {
            PreparedStatement statement = connectionBdd.prepareStatement(query);
            statement.setInt(1, idUtilisateur);
            ResultSet resultSet = statement.executeQuery();
            // construit la liste des contacts
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String photo = resultSet.getString("photo");
                String telephone = resultSet.getString("telephone");
                String note = resultSet.getString("note");

                Contacts contact = new Contacts(id, nom, prenom, email, photo, telephone, note, idUtilisateur);
                listeContacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeContacts;
    }
}
