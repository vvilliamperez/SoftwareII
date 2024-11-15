package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


/**
 * Class for Appointments
 */
public class Appointment {
    private Timestamp timeStart, timeEnd;

    private String title, description, location, type;
    private int apptID, custID, userID, contactID;

    public Appointment(int apptID, String title, String desc, String location, String type, Timestamp timeStart, Timestamp timeEnd, int custID, int userID, int contactID) {
        this.apptID = apptID;
        this.title = title;
        this.description = desc;
        this.location = location;
        this.type = type;
        this.userID = userID;
        this.custID = custID;
        this.contactID = contactID;
        // Time is stored in UTC
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public Integer getCustID() {
        return custID;
    }

    public Integer getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public ZonedDateTime getZonedTimeStart() {
        return timeStart.toLocalDateTime().atZone(java.time.ZoneId.systemDefault());
    }

    public ZonedDateTime getZonedTimeEnd() {
        return timeEnd.toLocalDateTime().atZone(java.time.ZoneId.systemDefault());
    }



}