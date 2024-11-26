package utils;

import models.Appointment;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
/**
 * Helper class for dealing with  appointments
 */
public class AppointmentHelper {
    /**
     * Filters a list of appointments to only include those that fall within a given date window.
     *
     * @param appointments    The list of appointments to filter
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


    /**
     * Returns the time start and end in local system time for an appointment
     * The default time is in UTC and it must be converted to the local time zone
     */
    public static String[] getLocalDateTimeStartAndEndStrings(Appointment appointment) {
        Timestamp timeStart = appointment.getTimeStart();
        Timestamp timeEnd = appointment.getTimeEnd();

        // Convert the UTC Timestamp to a LocalDateTime
        LocalDateTime localDateTimeStart = timeStart.toLocalDateTime();
        LocalDateTime localDateTimeEnd = timeEnd.toLocalDateTime();

        // Convert to the system's default time zone
        LocalDateTime localDateTimeStartSystem = localDateTimeStart.atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTimeEndSystem = localDateTimeEnd.atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();

        return new String[]{localDateTimeStartSystem.toString(), localDateTimeEndSystem.toString()};
    }

    public static LocalDateTime[] getLocalDateTimeStartAndEnd(Appointment appointment) {
        Timestamp timeStart = appointment.getTimeStart();
        Timestamp timeEnd = appointment.getTimeEnd();

        // Convert the UTC Timestamp to a LocalDateTime
        LocalDateTime localDateTimeStart = timeStart.toLocalDateTime();
        LocalDateTime localDateTimeEnd = timeEnd.toLocalDateTime();

        // Convert to the system's default time zone
        LocalDateTime localDateTimeStartSystem = localDateTimeStart.atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTimeEndSystem = localDateTimeEnd.atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();

        LocalDateTime[] start_and_end = new LocalDateTime[2];

        start_and_end[0] = localDateTimeStartSystem;
        start_and_end[1] = localDateTimeEndSystem;

        return start_and_end;
    }

    public static LocalDateTime[] getUTCLocalDateTimeStartAndEnd(Appointment appointment){
        Timestamp timeStart = appointment.getTimeStart();
        Timestamp timeEnd = appointment.getTimeEnd();

        // Convert the UTC Timestamp to a LocalDateTime
        LocalDateTime localDateTimeStart = timeStart.toLocalDateTime();
        LocalDateTime localDateTimeEnd = timeEnd.toLocalDateTime();

        LocalDateTime[] start_and_end = new LocalDateTime[2];

        start_and_end[0] = localDateTimeStart;
        start_and_end[1] = localDateTimeEnd;

        return start_and_end;
    }
}
