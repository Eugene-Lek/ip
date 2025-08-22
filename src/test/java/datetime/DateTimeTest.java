package datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeTest {
    @Test
    public void TestDayMonthYearWithoutTime(){
        String dateString = "12/3/2001";
        LocalDateTime actual = DateTime.parseStringToDate(dateString);
        LocalDateTime expected = LocalDate.of(2001, Month.MARCH, 12).atStartOfDay();
        assertEquals(actual, expected);
    }

    @Test
    public void TestDayMonthYearWithTime(){
        String dateString = "12/3/2001 1420";
        LocalDateTime actual = DateTime.parseStringToDate(dateString);
        LocalDateTime expected = LocalDate.of(2001, Month.MARCH, 12).atTime(14, 20);
        assertEquals(actual, expected);
    }

    @Test
    public void TestISODateWithoutTime(){
        String dateString = "2024-2-9";
        LocalDateTime actual = DateTime.parseStringToDate(dateString);
        LocalDateTime expected = LocalDate.of(2024, Month.FEBRUARY, 9).atStartOfDay();
        assertEquals(actual, expected);
    }

    @Test
    public void TestISOWithTime(){
        String dateString = "2023-7-12 8:16";
        LocalDateTime actual = DateTime.parseStringToDate(dateString);
        LocalDateTime expected = LocalDate.of(2023, Month.JULY, 12).atTime(8, 16);
        assertEquals(actual, expected);
    }
}