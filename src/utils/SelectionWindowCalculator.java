package utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for calculating selection windows
 */
public class SelectionWindowCalculator {
    /**
     * Calculates a selection window based on the current time and a selection type.
     * @param now The current time
     * @param selection The selection type (e.g., "Week" or "Month")
     * @param offset The offset from the current time
     * @return An array containing the start and end of the selection window
     */
    public static Timestamp[] calculateSelectionWindow(Timestamp now, String selection, int offset) {
        // Convert the SQL Timestamp to LocalDateTime
        LocalDateTime localDateTime = now.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();

        // Calculate the window start and end in the same direction
        LocalDateTime windowStart, windowEnd;
        if ("Week".equalsIgnoreCase(selection)) {
            // Offset both start and end in the same direction (e.g., future weeks)
            windowStart = localDateTime.plus(offset, ChronoUnit.WEEKS);
            windowEnd = localDateTime.plus(offset + 1, ChronoUnit.WEEKS);
        } else if ("Month".equalsIgnoreCase(selection)) {
            // Offset both start and end in the same direction (e.g., future months)
            windowStart = localDateTime.plus(offset, ChronoUnit.MONTHS);
            windowEnd = localDateTime.plus(offset + 1, ChronoUnit.MONTHS);
        } else {
            throw new IllegalArgumentException("Invalid selection: " + selection);
        }

        // Convert back to SQL Timestamps and return as an array
        return new Timestamp[] {
                Timestamp.valueOf(windowStart),
                Timestamp.valueOf(windowEnd)
        };
    }
}
