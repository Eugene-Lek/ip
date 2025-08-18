import java.util.Scanner;

public class Laundry {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Laundry");
        System.out.println("What can I do for you?");

        while (true) {
            Scanner userInputScanner = new Scanner(System.in);
            String userInput = userInputScanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(userInput);
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
