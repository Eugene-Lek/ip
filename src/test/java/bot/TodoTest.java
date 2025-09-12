package bot;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void TestTodoWithoutDeadlineToDBRepresentation() {
        Todo todo = new Todo("Item &*@^$@% HJSKF HIU*(#@34", null);
        String actual = todo.toDbRepresentation();
        String expected = "T|false|Item &*@^$@% HJSKF HIU*(#@34";
        assertEquals(expected, actual);
    }

    @Test
    public void TestTodoWithDeadlineToDBRepresentation() {
        Todo todo = new Todo("fkjaewhr erkj2n 3 '3498p ad  c uwthfrsd", LocalDateTime.of(2001, Month.MARCH, 12, 14, 30));
        todo.markAsCompleted();
        String actual = todo.toDbRepresentation();
        String expected = "D|true|fkjaewhr erkj2n 3 '3498p ad  c uwthfrsd|2001-03-12T14:30";
        assertEquals(expected, actual);
    }

    /**
     * Tests the representation of a Todo with an empty name.
     * This is an edge case to ensure the method doesn't crash and handles empty strings correctly.
     */
    @Test
    public void TestTodoWithEmptyNameToDBRepresentation() {
        Todo todo = new Todo("", LocalDateTime.of(2023, Month.DECEMBER, 25, 23, 59));
        String actual = todo.toDbRepresentation();
        String expected = "D|false||2023-12-25T23:59";
        assertEquals(expected, actual);
    }

    /**
     * Tests the representation of a Todo with a name containing the delimiter ('|').
     * This is crucial to ensure the storage format won't be corrupted by the data itself.
     */
    @Test
    public void TestTodoWithNameContainingDelimiterToDBRepresentation() {
        Todo todo = new Todo("Buy pipe | connector", null);
        String actual = todo.toDbRepresentation();
        String expected = "T|false|Buy pipe | connector";
        assertEquals(expected, actual);
    }

    /**
     * Tests the representation of an uncompleted Todo *with* a deadline.
     * This ensures the initial state (isCompleted = false) is correctly serialized.
     */
    @Test
    public void TestUncompletedTodoWithDeadlineToDBRepresentation() {
        Todo todo = new Todo("Submit report", LocalDateTime.of(2024, Month.JULY, 1, 9, 0));
        // Intentionally not marking as completed
        String actual = todo.toDbRepresentation();
        String expected = "D|false|Submit report|2024-07-01T09:00";
        assertEquals(expected, actual);
    }

    /**
     * Tests the representation of a completed Todo *without* a deadline.
     * This covers the combination of T-type with the completed state.
     */
    @Test
    public void TestCompletedTodoWithoutDeadlineToDBRepresentation() {
        Todo todo = new Todo("Call mom", null);
        todo.markAsCompleted();
        String actual = todo.toDbRepresentation();
        String expected = "T|true|Call mom";
        assertEquals(expected, actual);
    }

    /**
     * Tests the state change from completed back to uncompleted.
     * This verifies the undoMarkAsCompleted() method works and is correctly reflected in the serialization.
     */
    @Test
    public void TestUndoneTodoToDBRepresentation() {
        Todo todo = new Todo("Task with state change", LocalDateTime.of(2024, Month.JANUARY, 10, 12, 0));
        todo.markAsCompleted();
        todo.undoMarkAsCompleted(); // Revert the completion
        String actual = todo.toDbRepresentation();
        String expected = "D|false|Task with state change|2024-01-10T12:00";
        assertEquals(expected, actual);
    }

    /**
     * Tests a deadline at a very specific time (including seconds, though not in the class, this tests robustness).
     * The LocalDateTime object can hold this precision even if not used elsewhere.
     */
    @Test
    public void TestTodoWithPreciseDeadlineToDBRepresentation() {
        Todo todo = new Todo("Midnight launch", LocalDateTime.of(2030, Month.JANUARY, 1, 0, 0, 0));
        String actual = todo.toDbRepresentation();
        String expected = "D|false|Midnight launch|2030-01-01T00:00";
        assertEquals(expected, actual);
    }
}