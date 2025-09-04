package bot;

import java.time.LocalDateTime;

/**
 * An event
 */
public class Event implements TrackerItem {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private boolean isCompleted;

    Event(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = false;
    }

    /**
     * Gets the name of the event
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Marks the event as completed
     */
    @Override
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    /**
     * Unmarks the event as completed
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

        return "[E] [" + completedString + "] " + this.name + " from: " + startDate + " to: " + endDate;
    }

    @Override
    public String toDbRepresentation() {
        return "E" + "|" + this.isCompleted + "|" + this.name + "|" + startDate + "|" + endDate;
    }
}
