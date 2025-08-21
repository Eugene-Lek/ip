import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tracker {
    private final String dataDirectoryName = "data";
    private final String dataFileName = "tracker-items.txt";
    private String dataFilePath;
    private final ArrayList<TrackerItem> items = new ArrayList<>();

    Tracker() {
        // Create the /data folder and tracker-items.txt file if it doesn't already exist
        try {
            // Sprecify the Directory Name
            String currentDirectory = System.getProperty("user.dir");
            String dataDirectoryPath = currentDirectory + File.separator + dataDirectoryName;

            // Create a File object representing the directory
            File dataDirectory = new File(dataDirectoryPath);

            // Attempt to create the directory
            boolean directoryCreated = dataDirectory.mkdir();
            dataFilePath = dataDirectoryPath + File.separator + dataFileName;

            File myObj = new File(dataFilePath);
            boolean createdNewFile = myObj.createNewFile();
            if (!createdNewFile) {
                loadItemsFromFile();
            }

        } catch (IOException e) {
            System.out.println("An error occurred with creating the data file: " + e.getMessage());
        }
    }

    public int getItemCount () {
        return this.items.size();
    }

    public void addItem(TrackerItem item) {
        this.items.add(item);
        saveItemsToFile();
    }

    public TrackerItem getItemByNumber(int itemNumber) {
        return this.items.get(itemNumber - 1);
    }

    public void markItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).markAsCompleted();
        saveItemsToFile();
    }

    public void unmarkItemAsCompleted(int itemNumber) {
        this.items.get(itemNumber - 1).undoMarkAsCompleted();
        saveItemsToFile();
    }

    public TrackerItem deleteItem(int itemNumber) {
        TrackerItem removedItem = this.items.remove(itemNumber - 1);
        saveItemsToFile();
        return removedItem;
    }

    private void saveItemsToFile() {
        StringBuilder latestItems = new StringBuilder();
        for (TrackerItem item : items) {
            String itemString = item.toDBRepresentation();
            latestItems.append(itemString);
            latestItems.append("\n");
        }

        try {
            FileWriter myWriter = new FileWriter(dataFilePath, false);
            myWriter.write(latestItems.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred with saving the latest items to the data file.");
        }
    }

    private void loadItemsFromFile() {
        try {
            File myObj = new File(dataFilePath);
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String dbRepresentation = reader.nextLine().trim();
                if (dbRepresentation.isEmpty()) {
                    continue;
                }

                TrackerItem item = TrackerItem.fromDBRepresentation(dbRepresentation);
                if (item == null) {
                    continue;
                }

                this.items.add(item);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred in loading data from the file.");
        }
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
