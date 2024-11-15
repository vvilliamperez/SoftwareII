package models;
/**
 * Class for Contacts
 */
public class Contact {

    private int contactID;
    private String name, email;

    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getID() {
        return contactID;
    }
}
