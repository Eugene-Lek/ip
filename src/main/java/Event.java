import java.time.LocalDateTime;

/**
 * An event
 */
public class Event implements TrackerItem {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private boolean completed;

    Event(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = false;
    }

    /**
     * Marks the event as completed
     */
    @Override
    public void markAsCompleted() {
        this.completed = true;
    }

    /**
     * Unmarks the event as completed
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

        return "[E] [" + completedString + "] " + this.name + " from: " + startDate + " to: " + endDate;
    }

    @Override
    public String toDbRepresentation() {
        return "E" + "|" + this.completed + "|" + this.name + "|" + startDate + "|" + endDate;
    }
}
