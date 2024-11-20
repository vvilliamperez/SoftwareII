package DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Contacts
 * Handles all SQL queries for contacts
 * @see models.Contact
 */
public class ContactDaoImpl {
    public static List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<Contact>();
        DBConnection.makeConnection();
        String sqlStatement = "SELECT * FROM contacts";
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
