package utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class TimeHelper {
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

