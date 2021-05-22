package cmpt213.assignment1.tasktracker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

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
        String input = scanner.next();

        // Validate Input
        int parsedInput = Integer.parseInt(input);
        while(parsedInput < 1 || parsedInput > NUM_OPTIONS){
            System.out.println("Invalid selection. Enter a number between 1 and " + NUM_OPTIONS);
            System.out.print("Choose an option by entering 1 - " + NUM_OPTIONS + ": ");
            parsedInput = Integer.parseInt(scanner.next());
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
                System.out.println("\nTask #" + i);
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
        String name = scanner.next();
        // Handle empty names
        while(name.length() == 0){
            System.out.println("Cannot have an empty task name");
            System.out.print("Enter the name of the new task:");
            name = scanner.next();
        }
        //Notes
        System.out.print("Enter the notes of the new task: ");
        String notes = scanner.next();

        //Due date
        System.out.print("Enter the year of the due date: ");
        int year = handleDateInput(Calendar.getInstance().get(Calendar.YEAR), Integer.MAX_VALUE , "Enter the year of the due date: " );
        //Year cannot be smaller than current year

        System.out.print("Enter the month of the due date (1-12): ");
        int month = handleDateInput(1, 12, "Enter the month of the due date (1-12): ");

        System.out.print("Enter the day of the due date (1-28/29/30/31): ");
        int day = handleDateInput(1,31 , "Enter the day of the due date (1-28/29/30/31): " );

        //Check if date exists
        try{
            LocalDate.of(year,month,day);
        }catch (Exception e){
            System.out.println(e);
        }

        return new Task(name, notes, new GregorianCalendar(year,Calendar.JANUARY,day));
    }

    private int handleDateInput(int min, int max, String query){
        Scanner scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.next());
        while (input < min || input > max){
            System.out.println("Error: This is an invalid input");
            System.out.print(query);
            input = Integer.parseInt(scanner.next());
        }
        return input;
    }


}
