package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Appointment {
    private Timestamp timeStart, timeEnd;
    private LocalDateTime ldtStart, ldtEnd;


    private String title, description, location, type;
    private int apptID, custID, userID, contactID;

    public Appointment(int apptID, String title, String desc, String location, String type, Timestamp timeStart, Timestamp timeEnd, int custID, int userID,  int contactID) {
        this.apptID = apptID;
        this.title = title;
        this.description = desc;
        this.location = location;
        this.type = type;
        this.userID = userID;
        this.custID = custID;
        this.contactID = contactID;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.ldtStart = this.timeStart.toLocalDateTime();
        this.ldtEnd = this.timeEnd.toLocalDateTime();
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

    public LocalDateTime getLdtStart() {
        return ldtStart;
    }

    public LocalDateTime getLdtEnd() {
        return ldtEnd;
    }
}
