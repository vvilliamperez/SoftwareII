package utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


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
}

