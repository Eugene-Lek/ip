import datetime.DateTime;
import storage.SaveableToDb;

import java.time.LocalDateTime;

/**
 * An interface to define the functionality of a tracker item
 */
public interface TrackerItem extends SaveableToDb {
    void markAsCompleted();

    void undoMarkAsCompleted();

    static TrackerItem fromDbRepresentation(String dbRepresentation) {
        String[] entries = dbRepresentation.split("(\\s)*\\|(\\s)*");
        String itemType = entries[0];
        boolean isCompleted = Boolean.parseBoolean(entries[1].toLowerCase());
        String itemName = entries[2];
        TrackerItem item;
        switch (itemType) {
            case "T":
                item = new Todo(itemName, null);
                break;
            case "D":
                LocalDateTime dueDate = DateTime.parseStringToDate(entries[3]);
                item = new Todo(itemName, dueDate);
                break;
            case "E":
                LocalDateTime startDate = DateTime.parseStringToDate(entries[3]);
                LocalDateTime endDate = DateTime.parseStringToDate(entries[4]);
                item = new Event(itemName, startDate, endDate);
                break;
            default:
                System.out.println("Warning: The following file entry does not contain a valid Tracker Item type: " + dbRepresentation);
                return null;
        }

        if (isCompleted) {
            item.markAsCompleted();
        }

        return item;
    }
}
