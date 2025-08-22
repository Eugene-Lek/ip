
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void TestTodoWithoutDeadlineToDBRepresentation() {
        Todo todo = new Todo("Item &*@^$@% HJSKF HIU*(#@34", null);
        String actual = todo.toDBRepresentation();
        String expected = "T|false|Item &*@^$@% HJSKF HIU*(#@34";
        assertEquals(actual, expected);
    }

    @Test
    public void TestTodoWithDeadlineToDBRepresentation() {
        Todo todo = new Todo("fkjaewhr erkj2n 3 '3498p ad  c uwthfrsd", LocalDate.of(2001, Month.MARCH, 12).atStartOfDay());
        todo.markAsCompleted();
        String actual = todo.toDBRepresentation();
        String expected = "D|true|fkjaewhr erkj2n 3 '3498p ad  c uwthfrsd|2001-03-12T00:00";
        assertEquals(actual, expected);
    }
}
