package cmpt213.assignment1.tasktracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller class for handling application
 * @author Mikhail Alexeev
 */

public class Controller {

    private enum OPTION {EMPTY, LIST_ALL,  ADD, REMOVE, MARK_COMPLETE, LIST_OVERDUE, LIST_UPCOM, EXIT }
    private static List<Task> taskList  = new ArrayList<>();
    /**
     * Entrypoint for the program
     */
    public static void main(String[] args){
        //TODO: Load JSON task file
        TextMenu menu = new TextMenu("My to-do List");
        menu.displayMenu();
        while (true){
            OPTION userInput = OPTION.values()[menu.handleUserInput()];
            // Handle different inputs
            switch (userInput){
                case LIST_ALL:
                    menu.listAllTasks(taskList);
                    break;
                case ADD:
                    break;
                case REMOVE:
                    break;
                case MARK_COMPLETE:
                    break;
                case LIST_OVERDUE:
                    break;
                case LIST_UPCOM:
                    break;
            }
            // Break out of loop
            if (userInput == OPTION.EXIT) {
                break;
            }


            menu.displayMenu();
        }
        System.out.println("Thank you for using the system");
    }


}

