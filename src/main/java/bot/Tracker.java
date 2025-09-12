package bot;

import java.util.ArrayList;
import java.util.List;
import java.lang.IndexOutOfBoundsException;

import storage.Storage;

/**
 * Tracks a list of items
 */
public class Tracker {
    private final List<TrackerItem> items;
    private final Storage<TrackerItem> storage;

    Tracker() {
        this.items = new ArrayList<>();

        String dataFileName = "tracker-items.txt";
        this.storage = new Storage<>(dataFileName, this.items, TrackerItem::fromDbRepresentation);
        this.storage.loadFromDB();
    }

    /**
     * Returns the number of items in the tracker
     *
     * @return number of items in the tracker
     */
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * Adds an item to the tracker
     *
     * @param item an item which can be tracked by the tracker
     */
    public void addItem(TrackerItem item) {
        this.items.add(item);
        this.storage.saveToDB();
    }

    /**
     * Returns an item in the tracker based on its number
     *
     * @param itemNumber the number of the item
     * @return the corresponding item
     */
    public TrackerItem getItemByNumber(int itemNumber) throws Exception {
        try {
            return this.items.get(itemNumber - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("The item number does not exist in the tracker");
        }
    }

    /**
     * Marks an item as completed
     *
     * @param itemNumber the number of the item
     */
    public void markItemAsCompleted(int itemNumber) throws Exception {
        try {
            this.items.get(itemNumber - 1).markAsCompleted();
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("The item number does not exist in the tracker");
        }

        this.storage.saveToDB();
    }

    /**
     * Unmarks an item as completed
     *
     * @param itemNumber the number of the item
     */
    public void unmarkItemAsCompleted(int itemNumber) throws Exception {
        try {
            this.items.get(itemNumber - 1).undoMarkAsCompleted();
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("The item number does not exist in the tracker");
        }
        this.storage.saveToDB();
    }

    /**
     * Deletes an item from the tracker
     *
     * @param itemNumber the number of the item
     */
    public TrackerItem deleteItem(int itemNumber) throws Exception {
        TrackerItem removedItem;
        try {
            removedItem = this.items.remove(itemNumber - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("The item number does not exist in the tracker");
        }

        this.storage.saveToDB();
        return removedItem;
    }

    /**
     * Returns a list of tracker items whose names contain the search query
     *
     * @param query a search query
     * @return a list of tracker items whose names contain the search query
     */
    public List<TrackerItem> find(String query) {
        return this.items
                .stream()
                .filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "There are currently no items in the tracker";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            TrackerItem item = this.items.get(i);
            String itemString = (i + 1) + ". " + item.toString() + "\n";
            display.append(itemString);
        }

        return display.toString();
    }
}
