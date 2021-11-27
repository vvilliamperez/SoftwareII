package models;

import javafx.collections.ObservableList;

public class User {

    private ObservableList<Appointment> appointments;

    private int UID;
    private String username, password;

    public User(int UID){
        this.UID = UID;
    }

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

    public void updateAppointment(Appointment old, Appointment updated){
        removeAppointment(old);
        addAppointment(updated);
    }

    public boolean passwordVerified(String input){
        return input.equals(password);
    }

}
