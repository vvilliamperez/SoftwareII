package utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
/**
 * Helper class for dealing with time
 */
public class TimeHelper {
    /**
     * Converts a UTC Timestamp to a LocalDateTime in the system's default time zone
     * Not currently used
     * @param date LocalDateTime
     * @param hour int The hour of the day (0-23)
     * @param minute int The minute of the hour (0-59)
     * @return The LocalDateTime in the system's default time zone
     */
    public static Timestamp localDateHourAndMinToUtcTimestamp(LocalDate date, int hour, int minute) {
        // Create a LocalDateTime from the provided date, hour, and minute
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        // Convert to a ZonedDateTime in the system's default time zone
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        // Convert to UTC ZonedDateTime
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        // Convert to Timestamp
        return Timestamp.valueOf(utcZonedDateTime.toLocalDateTime());
    }


    public static Timestamp utcTimestampToLocalTimestamp(Timestamp utcTimestamp) {
        // Convert the UTC Timestamp to a LocalDateTime
        LocalDateTime localDateTime = utcTimestamp.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        // Convert to a ZonedDateTime in the system's default time zone
        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        // Convert to Timestamp
        return Timestamp.valueOf(localZonedDateTime.toLocalDateTime());
    }

    /**
     * Adds or subtracts a number of periods to a given time based on a selection type
     * @param now The current time
     * @param selection The selection type (e.g., "Week" or "Month")
     * @param numberOfPeriods The number of periods to add or subtract
     * @return The LocalDateTime in the system's default time zone
     */
    public static Timestamp calculateResultTimestamp(Timestamp now, String selection, int numberOfPeriods) {
        // Convert the SQL Timestamp to LocalDateTime
        LocalDateTime localDateTime = now.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();

        // Add or subtract based on the selection
        LocalDateTime resultDateTime;
        if ("Week".equalsIgnoreCase(selection)) {
            resultDateTime = localDateTime.plusWeeks(numberOfPeriods);
        } else if ("Month".equalsIgnoreCase(selection)) {
            resultDateTime = localDateTime.plusMonths(numberOfPeriods);
        } else {
            throw new IllegalArgumentException("Invalid selection: " + selection);
        }

        // Convert back to SQL Timestamp
        return Timestamp.valueOf(resultDateTime);
    }

}

