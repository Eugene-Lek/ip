public class Event implements Completable {
    private final String name;
    private final String startDate;
    private final String endDate;
    private boolean completed;

    Event(String name, String startDate, String endDate) {
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
}
