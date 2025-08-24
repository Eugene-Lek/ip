package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage<T extends SaveableToDb> {
    private static final String storageDirectoryName = "data";

    private final String filePath;
    private final ArrayList<T> dataEntries;
    private final DBRepresentationParser<T> parser;

    public Storage(String fileName, ArrayList<T> dataEntries, DBRepresentationParser<T> parser) {
        String currentDirectory = System.getProperty("user.dir");
        String dataDirectoryPath = currentDirectory + File.separator + storageDirectoryName;
        filePath = dataDirectoryPath + File.separator + fileName;

        // Create the /data folder and tracker-items.txt file if it doesn't already exist
        try {
            File dataDirectory = new File(dataDirectoryPath);
            boolean directoryCreated = dataDirectory.mkdir();

            File myObj = new File(filePath);
            boolean createdNewFile = myObj.createNewFile();

        } catch (IOException e) {
            System.out.println("An error occurred with creating the data file: " + e.getMessage());
        }

        this.dataEntries = dataEntries;
        this.parser = parser;
    }

    /**
     * Loads the data stored in the DB into dataEntries
     */
    public void loadFromDB() {
        try {
            File myObj = new File(filePath);
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String dbRepresentation = reader.nextLine().trim();
                if (dbRepresentation.isEmpty()) {
                    continue;
                }

                T item = parser.parse(dbRepresentation);
                if (item == null) {
                    continue;
                }

                this.dataEntries.add(item);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred in loading data from the file.");
        }
    }

    /**
     * Saves the data present in dataEntries into the DB
     * The latest data in dataEntries overrides the data in the DB
     */
    public void saveToDB() {
        StringBuilder latestDataEntries = new StringBuilder();
        for (T dataEntry : dataEntries) {
            String dataEntryString = dataEntry.toDbRepresentation();
            latestDataEntries.append(dataEntryString);
            latestDataEntries.append("\n");
        }

        try {
            FileWriter myWriter = new FileWriter(filePath, false);
            myWriter.write(latestDataEntries.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred with saving the latest items to the data file.");
        }
    }
}
