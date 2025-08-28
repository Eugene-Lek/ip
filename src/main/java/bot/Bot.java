package bot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datetime.DateTime;

/**
 * Main class
 */
public class Bot {
    private static final Tracker tracker = new Tracker();

    // Just use the same tracker instance for all bot instances
    // This is okay since we will only instantiate bot once
    // Anyway, there can only be 1 tracker instance since all tracker instances
    // are tied to the same data file
    public Bot() {
    }

    /**
     * Handles the user input
     *
     * @param userInput the user's input
     * @return the bot's response
     */
    public String getResponse(String userInput) {
        String command = userInput.toLowerCase().split(" ")[0];

        try {
            if (command.equalsIgnoreCase("todo")) {
                return handleAddTodo(userInput);

            } else if (command.equalsIgnoreCase("deadline")) {
                return handleAddTodoWithDeadline(userInput);

            } else if (command.equalsIgnoreCase("event")) {
                return handleAddEvent(userInput);

            } else if (userInput.equalsIgnoreCase("list")) {
                return tracker.toString();

            } else if (command.equalsIgnoreCase("mark")) {
                return handleMarkItemAsCompleted(userInput);

            } else if (command.equalsIgnoreCase("unmark")) {
                return handleUnmarkItemAsCompleted(userInput);

            } else if (command.equalsIgnoreCase("delete")) {
                return handleDeleteItem(userInput);

            } else if (command.equalsIgnoreCase("find")) {
                return handleFindItems(userInput);

            } else {
                return "Unknown command";
            }
        } catch (java.time.format.DateTimeParseException e) {
            return "Error parsing date: " + e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String handleAddTodo(String userInput) throws Exception {
        String regex = "todo (.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the name of the todo to add");
        }

        String todoName = matcher.group(1);
        Todo todo = new Todo(todoName, null);
        tracker.addItem(todo);

        return "Got it. I've added this task:" + "\n"
                + todo + "\n"
                + "Now you have " + tracker.getItemCount() + " item(s) in the tracker";
    }

    private String handleAddTodoWithDeadline(String userInput) throws Exception {
        String regex = "deadline (.*) /by (.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the name and/or deadline of the todo to add");
        }

        String todoName = matcher.group(1);
        LocalDateTime dueDate = DateTime.parseStringToDate(matcher.group(2));
        Todo todo = new Todo(todoName, dueDate);
        tracker.addItem(todo);

        return "Got it. I've added this task:" + "\n"
                + todo + "\n"
                + "Now you have " + tracker.getItemCount() + " item(s) in the tracker";
    }

    private String handleAddEvent(String userInput) throws Exception {
        String regex = "event (.*) /from (.*) /to (.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the name, start date, and/or end date of the event to add");
        }

        String todoName = matcher.group(1);
        LocalDateTime startDate = DateTime.parseStringToDate(matcher.group(2));
        LocalDateTime endDate = DateTime.parseStringToDate(matcher.group(3));
        Event event = new Event(todoName, startDate, endDate);
        tracker.addItem(event);

        return "Got it. I've added this task:" + "\n"
                + event + "\n"
                + "Now you have " + tracker.getItemCount() + " item(s) in the tracker";
    }

    private String handleMarkItemAsCompleted(String userInput) throws Exception {
        String regex = "mark ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to mark as completed");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        tracker.markItemAsCompleted(itemNumber);

        TrackerItem markedItem = tracker.getItemByNumber(itemNumber);
        return "OK, I've marked this item as done:" + "\n"
                + markedItem;
    }

    private String handleUnmarkItemAsCompleted(String userInput) throws Exception {
        String regex = "unmark ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to unmark as completed");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        tracker.unmarkItemAsCompleted(itemNumber);

        TrackerItem unmarkedItem = tracker.getItemByNumber(itemNumber);
        return "OK, I've marked this item as not done yet:" + "\n"
                + unmarkedItem;
    }

    private String handleDeleteItem(String userInput) throws Exception {
        String regex = "delete ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to delete");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        TrackerItem deletedItem = tracker.deleteItem(itemNumber);

        return "Noted. I've removed this task:" + "\n"
                + deletedItem + "\n"
                + "Now you have " + tracker.getItemCount() + " item(s) in the tracker";
    }

    private String handleFindItems(String userInput) throws Exception {
        String regex = "find (.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to delete");
        }

        List<TrackerItem> matchedItems = tracker.find(matcher.group(1));
        if (matchedItems.isEmpty()) {
            System.out.println("No items match the given query");
        }

        StringBuilder display = new StringBuilder();
        for (int i = 0; i < matchedItems.size(); i++) {
            TrackerItem item = matchedItems.get(i);
            String itemString = (i + 1) + ". " + item.toString() + "\n";
            display.append(itemString);
        }

        return "Here are the matching items in your tracker" + "\n"
                + display;
    }
}
