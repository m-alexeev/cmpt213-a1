package cmpt213.assignment1.tasktracker;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main controller class for handling application
 * @author Mikhail Alexeev
 */

public class Controller {

    private static final int EXIT_OPTION = 7;
    private ArrayList<Task> taskList  = new ArrayList<>();
    /**
     * Entrypoint for the program
     */
    public static void main(String[] args){
        //TODO: Load JSON task file
        TextMenu menu = new TextMenu();
        menu.displayMenu();
        while (true){
            int userInput = menu.handleUserInput();
            if (userInput == EXIT_OPTION) {
                break;
            }

            //Do other work;

            //Finally show
            menu.displayMenu();
        }
        System.out.println("Thank you for using the system");
    }


}

