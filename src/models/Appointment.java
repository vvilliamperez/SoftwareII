package models;

import javax.sound.sampled.FloatControl;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class Appointment {
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    private String title, description, location, type;
    private int apptID, custID, userID, contactID;
    private Time start, end;

    public Appointment(int apptID, String title, String desc, String location, String type, int userID, int custID, int contactID) {
        this.apptID = apptID;
        this.title = title;
        this.description = desc;
        this.location = location;
        this.type = type;
        this.userID = userID;
        this.custID = custID;
        this.contactID = contactID;
    }


    public int getID() {
        return apptID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public Integer getCustID() {
        return custID;
    }

    public Integer getUserID() {
        return userID;
    }

    public int getContact() {
        return contactID;
    }
}
