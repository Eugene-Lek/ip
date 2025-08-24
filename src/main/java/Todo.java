import java.time.LocalDateTime;

/**
 * A todo
 */
public class Todo implements TrackerItem {
    private final String name;
    private final LocalDateTime dueDate;
    private boolean completed;

    Todo(String name, LocalDateTime dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.completed = false;
    }

    /**
     * Marks the todo as completed
     */
    @Override
    public void markAsCompleted() {
        this.completed = true;
    }

    /**
     * Unmarks the todo as completed
     */
    @Override
    public void undoMarkAsCompleted() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String completedString = " ";
        if (this.completed) {
            completedString = "X";
        }

        if (this.dueDate != null) {
            return "[D] [" + completedString + "] " + this.name + " (by: " + this.dueDate + ")";
        }
        return "[T] [" + completedString + "] " + this.name;
    }

    @Override
    public String toDbRepresentation() {
        if (this.dueDate != null) {
            return "D" + "|" + this.completed + "|" + this.name + "|" + this.dueDate;
        }
        return "T" + "|" + this.completed + "|" + this.name;
    }
}
