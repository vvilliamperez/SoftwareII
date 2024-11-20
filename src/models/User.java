package models;

import javafx.collections.ObservableList;
/**
 * Class for User objects
 */
public class User {

    private ObservableList<Appointment> appointments;

    private int UID;
    private String username, password;

    /**
     * Constructor for User objects
     * @param UID The user ID
     */
    public User(int UID){
        this.UID = UID;
    }

    /**
     * Constructor for User objects
     * @param UID The user ID
     * @param username The username
     * @param password The password
     */
    public User(int UID, String username, String password){
        this.UID = UID;
        this.username = username;
        this.password = password;
    }


    public void addAppointment(Appointment a){
        appointments.add(a);
    }

    public void removeAppointment(Appointment a){
        try {
            appointments.remove(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName(){
        return username;
    }



    public void updateAppointment(Appointment old, Appointment updated){
        removeAppointment(old);
        addAppointment(updated);
    }

    public boolean passwordVerified(String input){
        return input.equals(password);
    }

    public int getUID() {
        return UID;
    }
}
