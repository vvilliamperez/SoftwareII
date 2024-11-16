package utils;

import models.Appointment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentHelper {

    public static List<Appointment> filterAppointmentsByDateWindow(
            List<Appointment> appointments,
            Timestamp[] selectionWindow) {

        List<Appointment> filteredAppointments = new ArrayList<Appointment>();
        Timestamp windowStart = selectionWindow[0];
        Timestamp windowEnd = selectionWindow[1];

        for (Appointment appointment : appointments) {
            // Check if the appointment's time falls within the date window
            if ((appointment.getTimeStart().after(windowStart) || appointment.getTimeStart().equals(windowStart)) &&
                    (appointment.getTimeEnd().before(windowEnd) || appointment.getTimeEnd().equals(windowEnd))) {
                filteredAppointments.add(appointment);
            }
        }

        return filteredAppointments;
    }
}
