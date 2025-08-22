package datetime;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class DateTime {
    public static LocalDateTime parseStringToDate(String dateTimeString) {
        DateTimeFormatter ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
                .optionalStart().appendValue(ChronoField.YEAR, 4).optionalEnd()
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.DAY_OF_MONTH,  1, 2, SignStyle.NOT_NEGATIVE)
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        DateTimeFormatter DAY_MONTH_YEAR = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH,  1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .appendValue(ChronoField.MONTH_OF_YEAR,  1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral('/').optionalEnd()
                .optionalStart().appendLiteral('-').optionalEnd()
                .optionalStart().appendValue(ChronoField.YEAR, 4).optionalEnd()
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter();

        DateTimeFormatter ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart().appendLiteral(':').optionalEnd()
                .optionalStart().appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalEnd()
                .optionalStart().appendLiteral(':').optionalEnd()
                .optionalStart().appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalEnd()
                .optionalStart().appendZoneId()
                .toFormatter();

        DateTimeFormatter isoDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(ISO_LOCAL_DATE)
                .optionalStart().appendLiteral(' ').optionalEnd()
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendOptional(ISO_LOCAL_TIME).optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        DateTimeFormatter dayMonthYearDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(DAY_MONTH_YEAR)
                .optionalStart().appendLiteral(' ').optionalEnd()
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendOptional(ISO_LOCAL_TIME).optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        try {
            return LocalDateTime.parse(dateTimeString, isoDateTimeFormatter);
        } catch (Exception e) {
            return LocalDateTime.parse(dateTimeString, dayMonthYearDateTimeFormatter);
        }
    }
}
