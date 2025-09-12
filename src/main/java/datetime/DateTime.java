package datetime;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

/**
 * Provides DateTime utilities
 */
public class DateTime {
    /**
     * Parses a datetime string into a datetime object
     *
     * @param dateTimeString a string that represents a datetime
     * @return a dateTime object representing the date of the string
     */
    public static LocalDateTime parseStringToDate(String dateTimeString) throws Exception {
        DateTimeFormatter isoLocalDate = new DateTimeFormatterBuilder()
                .optionalStart().appendValue(ChronoField.YEAR, 4).optionalEnd()
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        DateTimeFormatter dayMonthYear = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .optionalStart().appendValue(ChronoField.YEAR, 4).optionalEnd()
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        DateTimeFormatter isoLocalTime = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral(':').optionalEnd()
                .optionalStart().appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalEnd()
                .optionalStart().appendLiteral(':').optionalEnd()
                .optionalStart().appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalEnd()
                .optionalStart().appendZoneId()
                .toFormatter();

        DateTimeFormatter isoDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(isoLocalDate)
                .optionalStart().appendLiteral(' ').optionalEnd()
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendOptional(isoLocalTime).optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        DateTimeFormatter dayMonthYearDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(dayMonthYear)
                .optionalStart().appendLiteral(' ').optionalEnd()
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendOptional(isoLocalTime).optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        try {
            return LocalDateTime.parse(dateTimeString, isoDateTimeFormatter);
        } catch (Exception e) {
            try {
                return LocalDateTime.parse(dateTimeString, dayMonthYearDateTimeFormatter);
            } catch (Exception err) {
                throw new Exception("The provided date is in an unsupported format. One of the supported formats is dd/mm/yy");
            }
        }
    }
}
