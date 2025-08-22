import storage.Storage;

import java.util.ArrayList;

public class Tracker {
    private final ArrayList<TrackerItem> items;
    private final Storage<TrackerItem> storage;

    Tracker() {
        this.items = new ArrayList<>();

        String dataFileName = "tracker-items.txt";
        this.storage = new Storage<>(dataFileName, this.items, TrackerItem::fromDBRepresentation);
        this.storage.loadFromDB();
    }

    public int getItemCount () {
        return this.items.size();
    }

    public void addItem(TrackerItem item) {
        this.items.add(item);
        this.storage.saveToDB();
    }

    public TrackerItem getItemByNumber(int itemNumber) {
        return this.items.get(itemNumber - 1);
    }

    public void markItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).markAsCompleted();
        this.storage.saveToDB();
    }

    public void unmarkItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).undoMarkAsCompleted();
        this.storage.saveToDB();
    }

    public TrackerItem deleteItem(int itemNumber) {
        TrackerItem removedItem = this.items.remove(itemNumber - 1);
        this.storage.saveToDB();
        return removedItem;
    }


    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "There are currently no items in the tracker";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            TrackerItem item = this.items.get(i);
            String itemString = (i+1) + ". " + item.toString() + "\n";
            display.append(itemString);
        }

        return display.toString();
    }
}
