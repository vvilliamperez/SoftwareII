package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl {
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM Contacts";
        Query.makeQuery(sqlStatement);
        ResultSet contResults = Query.getResult();
        while(contResults.next()){
            int contactID = contResults.getInt("Contact_ID");
            String name = contResults.getString("Contact_Name");
            String email = contResults.getString("Email");
            Contact contact = new Contact(contactID, name, email);
            contacts.add(contact);
        }
        return contacts;
    }
}
