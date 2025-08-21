import java.time.LocalDateTime;

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

    @Override
    public void markAsCompleted() {
        this.completed = true;
    }

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

        return "[E] [" +  completedString + "] " + this.name + " from: " + startDate + " to: " + endDate;
    }

    @Override
    public String toDBRepresentation() {
        return "E" + "|" +  this.completed + "|" + this.name + "|" + startDate + "|" + endDate;
    }
}
