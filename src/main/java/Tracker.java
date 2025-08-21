import java.util.ArrayList;

public class Tracker {
    private final ArrayList<Completable> items;

    Tracker() {
        this.items = new ArrayList<>();
    }

    public int getItemCount () {
        return this.items.size();
    }

    public void addItem(Completable item) {
        this.items.add(item);
    }

    public Completable getItemByNumber(int itemNumber) {
        return this.items.get(itemNumber - 1);
    }

    public void markItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).markAsCompleted();
    }

    public void unmarkItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).undoMarkAsCompleted();
    }

    public Completable deleteItem(int itemNumber) {
        return this.items.remove(itemNumber - 1);
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "There are currently no items in the tracker";
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Completable item = this.items.get(i);
            String itemString = (i+1) + ". " + item.toString() + "\n";
            display.append(itemString);
        }

        return display.toString();
    }
}
