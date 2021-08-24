package models;

import javafx.collections.ObservableList;

public class User {

    private ObservableList<Appointment> appointments;
    private boolean queuedUpdate = false;

    private int UID;

    public User(int UID){
        this.UID = UID;
        //syncAppointments();
    }




    public void addAppointment(Appointment a){
        appointments.add(a);
    }

    public boolean removeAppointment(Appointment a){
        try {
            appointments.remove(a);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateAppointment(Appointment old, Appointment updated){
        removeAppointment(old);
        addAppointment(updated);
        return true;
    }

}
