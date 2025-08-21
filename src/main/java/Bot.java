import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot {
    private final static Tracker tracker = new Tracker();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Laundry");
        System.out.println("What can I do for you?");

        Scanner userInputScanner = new Scanner(System.in);
        while (true) {
            String userInput = userInputScanner.nextLine();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    handleAddTodo(userInput);

                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    handleAddTodoWithDeadline(userInput);

                } else if (userInput.toLowerCase().startsWith("event")) {
                    handleAddEvent(userInput);

                } else if (userInput.equalsIgnoreCase("list")) {
                    System.out.println(tracker);

                } else if (userInput.toLowerCase().startsWith("mark")) {
                    handleMarkItemAsCompleted(userInput);

                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    handleUnmarkItemAsCompleted(userInput);

                } else if (userInput.toLowerCase().startsWith("delete")){
                    handleDeleteItem(userInput);
                } else {
                    System.out.println("Unknown command");
                }
            } catch (java.time.format.DateTimeParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void handleAddTodo(String userInput) throws Exception{
        String regex = "todo (.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the name of the todo to add");
        }

        String todoName = matcher.group(1);
        Todo todo = new Todo(todoName, null);
        tracker.addItem(todo);

        System.out.println("Got it. I've added this task:");
        System.out.println(todo);
        System.out.println("Now you have " + tracker.getItemCount() + " item(s) in the tracker");
    }

    private static void handleAddTodoWithDeadline(String userInput) throws Exception{
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

        System.out.println("Got it. I've added this task:");
        System.out.println(todo);
        System.out.println("Now you have " + tracker.getItemCount() + " item(s) in the tracker");
    }

    private static void handleAddEvent(String userInput) throws Exception{
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

        System.out.println("Got it. I've added this task:");
        System.out.println(event);
        System.out.println("Now you have " + tracker.getItemCount() + " item(s) in the tracker");
    }

    private static void handleMarkItemAsCompleted(String userInput) throws Exception{
        String regex = "mark ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to mark as completed");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        tracker.markItemAsCompleted(itemNumber);

        TrackerItem markedItem = tracker.getItemByNumber(itemNumber);
        System.out.println("OK, I've marked this item as done:");
        System.out.println(markedItem);
    }

    private static void handleUnmarkItemAsCompleted(String userInput) throws Exception{
        String regex = "unmark ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to unmark as completed");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        tracker.unmarkItemAsCompleted(itemNumber);

        TrackerItem unmarkedItem = tracker.getItemByNumber(itemNumber);
        System.out.println("OK, I've marked this item as not done yet:");
        System.out.println(unmarkedItem);
    }

    private static void handleDeleteItem(String userInput) throws Exception{
        String regex = "delete ([0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);
        if (!matcher.find()) {
            throw new Exception("could not find the item number to delete");
        }

        int itemNumber = Integer.parseInt(matcher.group(1));
        TrackerItem deletedItem = tracker.deleteItem(itemNumber);

        System.out.println("Noted. I've removed this task:");
        System.out.println(deletedItem);
        System.out.println("Now you have " + tracker.getItemCount() + " item(s) in the tracker");
    }
}
