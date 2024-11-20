package utils;

import models.Appointment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * Helper class for dealing with  appointments
 */
public class AppointmentHelper {
    /**
     * Filters a list of appointments to only include those that fall within a given date window.
     * @param appointments The list of appointments to filter
     * @param selectionWindow The date window to filter by
     * @return A list of appointments that fall within the date window
     */
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
