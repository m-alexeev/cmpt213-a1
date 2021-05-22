package cmpt213.assignment1.tasktracker;

import java.util.Scanner;

/**
 * Class for storing displaying the and interacting with the text Menu
 * @author Mikhail Alexeev
 */

public class TextMenu {

    private String menuTitle = "My to-do list";

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
    public TextMenu(){}


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
            System.out.print("Choose an option by entering 1 - " + NUM_OPTIONS + ": ");
            parsedInput = Integer.parseInt(scanner.next());
        }
        return parsedInput;
    }

}
