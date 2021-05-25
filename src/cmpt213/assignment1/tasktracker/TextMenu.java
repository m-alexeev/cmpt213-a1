package cmpt213.assignment1.tasktracker;

import java.time.LocalDate;
import java.util.*;

/**
 * Class for storing displaying the and interacting with the text Menu
 * @author Mikhail Alexeev
 */

public class TextMenu {

    private String menuTitle;
    final int NUM_OPTIONS = 7;
    final int HASHTAG_OFFSET = 4;
    private final String[] MENU_OPTIONS = new String[]{
            "List all tasks",
            "Add a new task",
            "Remove a task",
            "Mark a task as completed",
            "List overdue incomplete tasks",
            "List upcoming incomplete tasks",
            "Exit"
    };

    /**
     * Default constructor to instantiate the class
     */
    public TextMenu(String menuTitle){
        this.menuTitle = menuTitle;
    }


    /**
     * Print out the text menu
     */
    public void displayMenu(){
        //Print out title
        int numHashtags = menuTitle.length() + HASHTAG_OFFSET;
        drawHashtagLine(numHashtags);
        System.out.println("# " + menuTitle + " #" );
        drawHashtagLine(numHashtags);

        // Print menu options starting @ 1
        for(int i = 0; i < NUM_OPTIONS; i++){
            System.out.println((i+1) + ": " + MENU_OPTIONS[i]);
        }
        System.out.print("Choose an option by entering 1 - " + NUM_OPTIONS + ": ");

    }

    /**
     * Print a line of # to the out stream
     * @param numHashtags number of hashtags to print
     */
    private void drawHashtagLine(int numHashtags){
        for (int i = 0; i < numHashtags; i ++){
            if (i < numHashtags - 1){
                System.out.print("#");
            }else{
                System.out.println("#");
            }
        }
    }

    /**
     * Get input from user and display the selected output
     * @return return the menu value specified by user input
     */
    public int handleUserInput(){
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        String input = scanner.nextLine();

        // Validate Input
        int parsedInput = Integer.parseInt(input);
        while(parsedInput < 1 || parsedInput > NUM_OPTIONS){
            System.out.println("Invalid selection. Enter a number between 1 and " + NUM_OPTIONS);
            System.out.print("Choose an option by entering 1 - " + NUM_OPTIONS + ": ");
            parsedInput = Integer.parseInt(scanner.nextLine());
        }
        return parsedInput;
    }

    /**
     * Lists all tasks in order that the were created
     * @param tasks Tasks that will be listed
     */
    public void listAllTasks(List<Task> tasks){
        if (tasks.size() == 0){
            System.out.println("\nNo tasks to show.\n");
        }else{
            for (int i = 0; i < tasks.size(); i++){
                System.out.println("\nTask #" + (i + 1) );
                System.out.println(tasks.get(i).toString() + "\n");
            }
        }
    }

    /**
     * Create a new task based on user input
     * @return returns a newly created task
     */
    public Task addNewTask(){
        Scanner scanner = new Scanner(System.in);

        // Handle user input step by step
        System.out.print("Enter the name of the new task: ");
        String name = scanner.nextLine();
        // Handle empty names
        while(name.length() == 0){
            System.out.println("Cannot have an empty task name");
            System.out.print("Enter the name of the new task: ");
            name = scanner.nextLine();
        }
        //Notes
        System.out.print("Enter the notes of the new task: ");
        String notes = scanner.nextLine();

        //Due date
        int year = handleRangeInput(Calendar.getInstance().get(Calendar.YEAR),      //Cannot be smaller than current year
                Integer.MAX_VALUE, "year",  "Enter the year of the due date: " );

        int month = handleRangeInput(1, 12, "month",
                "Enter the month of the due date (1-12): ");

        int day = 0;
        boolean validDate = false;
        while (!validDate) {
            day = handleRangeInput(1, 31, "day",
                    "Enter the day of the due date (1-28/29/30/31): ");
            //Check if date exists
            try {
                LocalDate.of(year, month, day);
                validDate = true;
            } catch (Exception e) {
                System.out.println("Error: this date does not exist");
            }
        }

        int hour = handleRangeInput(0, 23, "hour",
                    "Enter the hour of the due date (0-23): ");

        int minute = handleRangeInput(0,59, "minute",
                    "Enter the minute of the due data (0-59): ");

        System.out.println("Task " + name + " has been added to the list of tasks\n");
        return new Task(name, notes, new GregorianCalendar(year,month - 1, day, hour, minute), false);

    }

    private int handleRangeInput(int min, int max, String item, String query){
        System.out.print(query);
        Scanner scanner = new Scanner(System.in);

        int input = Integer.parseInt(scanner.nextLine());
        while (input < min || input > max){
            System.out.printf("Error: %s must be between (%d, %d)\n", item , min, max);
            System.out.print(query);
            input = Integer.parseInt(scanner.nextLine());
        }
        return input;
    }

    /**
     * Remove a task selected by the user
     */
    public void removeTask(List<Task> tasks){
        if (tasks.size() == 0){
            System.out.println("No tasks to show\n");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        Collections.sort(tasks);
        listAllTasks(tasks);
        int input = handleRangeInput(0, tasks.size(), "Index",
                "Enter the task number you want to remove (0 to cancel):");
        if (input > 0){
            Task removeTask = tasks.get(input - 1);
            System.out.println("Task " + removeTask.getName() + " is now removed");
            tasks.remove(input - 1);
        }
    }

}
