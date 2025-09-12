package datetime;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeTest {

    @Test
    void testParseStringToDateEmptyInput() throws Exception {
        assertThrows(Exception.class, () -> {
            DateTime.parseStringToDate("");
        });
    }

    @Test
    void testParseStringToDateWhitespaceInput() throws Exception {
        assertThrows(Exception.class, () -> {
            DateTime.parseStringToDate("   ");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-date",
            "2023-13-45", // Invalid month and day
            "32-12-2023", // Invalid day
            "2023/13/01", // Invalid month
            "abc-def-ghi" // Complete nonsense
    })
    void testParseStringToDateInvalidFormats(String invalidDate) throws Exception {
        assertThrows(Exception.class, () -> {
            DateTime.parseStringToDate(invalidDate);
        });
    }

    @ParameterizedTest
    @MethodSource("validIsoDateProvider")
    void testParseStringToDateIsoFormats(String dateString, LocalDateTime expected) throws Exception {
        LocalDateTime result = DateTime.parseStringToDate(dateString);
        assertEquals(expected, result);
    }

    private static Stream<Object[]> validIsoDateProvider() {
        return Stream.of(
                // Format: yyyy-MM-dd variations
                new Object[]{"2023-12-25", LocalDateTime.of(2023, 12, 25, 0, 0, 0)},
                new Object[]{"2023/12/25", LocalDateTime.of(2023, 12, 25, 0, 0, 0)},
                new Object[]{"2023-12-25 14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"2023/12/25 14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"2023-12-25T14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"2023/12/25T14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},

                // Time variations
                new Object[]{"2023-12-25 14:30", LocalDateTime.of(2023, 12, 25, 14, 30, 0)},
                new Object[]{"2023-12-25 14", LocalDateTime.of(2023, 12, 25, 14, 0, 0)},
                new Object[]{"2023-12-25T14", LocalDateTime.of(2023, 12, 25, 14, 0, 0)}
        );
    }

    private static Stream<Object[]> validDayMonthYearProvider() {
        return Stream.of(
                // Format: dd-MM-yyyy variations
                new Object[]{"25-12-2023", LocalDateTime.of(2023, 12, 25, 0, 0, 0)},
                new Object[]{"25/12/2023", LocalDateTime.of(2023, 12, 25, 0, 0, 0)},
                new Object[]{"25.12.2023", LocalDateTime.of(2023, 12, 25, 0, 0, 0)},
                new Object[]{"25-12-2023 14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"25/12/2023 14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"25.12.2023 14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},
                new Object[]{"25-12-2023T14:30:45", LocalDateTime.of(2023, 12, 25, 14, 30, 45)},

                // Single digit day/month
                new Object[]{"5-8-2023", LocalDateTime.of(2023, 8, 5, 0, 0, 0)},
                new Object[]{"05-08-2023", LocalDateTime.of(2023, 8, 5, 0, 0, 0)}
        );
    }

    @Test
    void testParseStringToDateMonthDayOnly() throws Exception {
        // This test assumes current year
        int currentYear = LocalDateTime.now().getYear();
        LocalDateTime result = DateTime.parseStringToDate("12-25");
        LocalDateTime expected = LocalDateTime.of(currentYear, 12, 25, 0, 0, 0);
        assertEquals(expected, result);
    }

    @Test
    void testParseStringToDateWithDifferentSeparatorsMixed() throws Exception {
        LocalDateTime result1 = DateTime.parseStringToDate("2023-12/25 14:30:45");
        LocalDateTime expected1 = LocalDateTime.of(2023, 12, 25, 14, 30, 45);
        assertEquals(expected1, result1);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2023-12-25 00:00:00",
            "2023-12-25 23:59:59",
            "2023-12-25 12:30:45"
    })
    void testParseStringToDateTimeBoundaries(String dateTimeString) {
        assertDoesNotThrow(() -> {
            LocalDateTime result = DateTime.parseStringToDate(dateTimeString);
            assertNotNull(result);
        });
    }

    @Test
    void testParseStringToDateLeapYear() throws Exception {
        LocalDateTime result = DateTime.parseStringToDate("2024-02-29"); // Leap year
        LocalDateTime expected = LocalDateTime.of(2024, 2, 29, 0, 0, 0);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("edgeCaseDateProvider")
    void testParseStringToDateEdgeCases(String dateString, LocalDateTime expected) throws Exception {
        LocalDateTime result = DateTime.parseStringToDate(dateString);
        assertEquals(expected, result);
    }

    private static Stream<Object[]> edgeCaseDateProvider() {
        return Stream.of(
                new Object[]{"0001-01-01", LocalDateTime.of(1, 1, 1, 0, 0, 0)}, // Minimum valid date
                new Object[]{"9999-12-31", LocalDateTime.of(9999, 12, 31, 0, 0, 0)}, // Maximum valid date
                new Object[]{"2023-01-01", LocalDateTime.of(2023, 1, 1, 0, 0, 0)}, // First day of year
                new Object[]{"2023-12-31", LocalDateTime.of(2023, 12, 31, 0, 0, 0)}  // Last day of year
        );
    }

    @ParameterizedTest
    @MethodSource("timeOnlyProvider")
    void testParseStringToDateTimeOnlyShouldFail(String timeOnlyString) throws Exception {
        assertThrows(Exception.class, () -> {
            DateTime.parseStringToDate(timeOnlyString);
        });
    }

    private static Stream<String> timeOnlyProvider() {
        return Stream.of(
                "14:30:45",
                "14:30",
                "14",
                "T14:30:45"
        );
    }
}