package models;

import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.util.Date;

public class Appointment {
    private LocalDateTime time;
    private String location;
    private String title;
    private String notes;


    public Appointment(LocalDateTime time, String title) {
        this.time = time;
        this.title = title;
    }

    public Appointment(LocalDateTime time, String location, String title, String notes) {
        this.time = time;
        this.location = location;
        this.title = title;
        this.notes = notes;
    }



}
