package bot;

import java.time.LocalDateTime;

/**
 * A todo
 */
public class Todo implements TrackerItem {
    private final String name;
    private final LocalDateTime dueDate;
    private boolean isCompleted;

    Todo(String name, LocalDateTime dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    /**
     * Gets the name of the todo
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Marks the todo as completed
     */
    @Override
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    /**
     * Unmarks the todo as completed
     */
    @Override
    public void undoMarkAsCompleted() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        String completedString = " ";
        if (this.isCompleted) {
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
            return "D" + "|" + this.isCompleted + "|" + this.name + "|" + this.dueDate;
        }
        return "T" + "|" + this.isCompleted + "|" + this.name;
    }
}
