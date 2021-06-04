package cmpt213.assignment1.tasktracker;

import java.time.LocalDate;
import java.util.*;

/**
 * Class for storing displaying the and interacting with the text Menu
 *
 * @author Mikhail Alexeev
 */

public class TextMenu {

    private final String menuTitle = "My task list";
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
    public TextMenu() {
    }


    /**
     * Print out the text menu
     */
    public void displayMenu() {
        //Print out title
        int numHashtags = menuTitle.length() + HASHTAG_OFFSET;
        drawHashtagLine(numHashtags);
        System.out.println("# " + menuTitle + " #");
        drawHashtagLine(numHashtags);

        // Print menu options starting @ 1
        for (int i = 0; i < NUM_OPTIONS; i++) {
            System.out.println((i + 1) + ": " + MENU_OPTIONS[i]);
        }
    }

    /**
     * Print a line of # to the out stream
     *
     * @param numHashtags number of hashtags to print
     */
    private void drawHashtagLine(int numHashtags) {
        for (int i = 0; i < numHashtags; i++) {
            if (i < numHashtags - 1) {
                System.out.print("#");
            } else {
                System.out.println("#");
            }
        }
    }


    /**
     * Get user input based on a range of values
     *
     * @param min   Minimum value that user can input
     * @param max   Maximum value that user can input
     * @param item  The name of the object which the input is collected for
     * @param query The statement that is displayed to the user - specifying input
     * @return A valid input from user
     */
    public int handleRangeInput(int min, int max, String item, String query) {
        System.out.print(query);
        Scanner scanner = new Scanner(System.in);

        int input = Integer.parseInt(scanner.nextLine());
        while (input < min || input > max) {
            System.out.printf("Invalid Input: %s must be between (%d, %d)\n", item, min, max);
            System.out.print(query);
            input = Integer.parseInt(scanner.nextLine());
        }
        return input;
    }

    /**
     * Lists all tasks
     *
     * @param tasks Tasks that will be listed
     */
    public void listAllTasks(List<Task> tasks) {
        if (tasks.size() == 0) {
            System.out.println("\nNo tasks to show.\n");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("\nTask #" + (i + 1));
                System.out.println(tasks.get(i).toString() + "\n");
            }
        }
    }

    private void listIncompleteTasks(List<Task> incompleteTasks, String type) {
        if (incompleteTasks.size() == 0) {
            System.out.println("\nNo " + type + " incomplete tasks to show\n");
        } else {
            for (int i = 0; i < incompleteTasks.size(); i++) {
                System.out.println("\nTask #" + (i + 1));
                System.out.println(incompleteTasks.get(i).toStringNoStatus() + "\n");
            }
        }
    }


    /**
     * Create a new task based on user input
     *
     * @param taskList The task list that will be updated with new task
     */
    public void addNewTask(List<Task> taskList) {
        Scanner scanner = new Scanner(System.in);

        // Handle user input step by step
        System.out.print("Enter the name of the new task: ");
        String name = scanner.nextLine();
        name = name.trim(); // Trim trailing and leading whitespaces
        // Handle empty names
        while (name.length() == 0) {
            System.out.println("Cannot have an empty task name");
            System.out.print("Enter the name of the new task: ");
            name = scanner.nextLine();
        }
        //Notes
        System.out.print("Enter the notes of the new task: ");
        String notes = scanner.nextLine();

        //Due date
        int year = handleRangeInput(0, Integer.MAX_VALUE,
                "Year", "Enter the year of the due date: ");

        int month = handleRangeInput(1, 12, "Month",
                "Enter the month of the due date (1-12): ");

        int day = 0;
        boolean validDate = false;
        while (!validDate) {
            day = handleRangeInput(1, 31, "Day",
                    "Enter the day of the due date (1-28/29/30/31): ");
            //Check if date exists
            try {
                LocalDate.of(year, month, day);
                validDate = true;
            } catch (Exception e) {
                System.out.println("Error: this date does not exist");
            }
        }

        int hour = handleRangeInput(0, 23, "Hour",
                "Enter the hour of the due date (0-23): ");

        int minute = handleRangeInput(0, 59, "Minute",
                "Enter the minute of the due data (0-59): ");

        System.out.println("Task " + name + " has been added to the list of tasks\n");
        taskList.add(new Task(name, notes, new GregorianCalendar(year, month - 1, day, hour, minute), false));

    }

    /**
     * Remove a task selected by the user
     *
     * @param taskList The task list that will be updated
     */
    public void removeTask(List<Task> taskList) {
        if (taskList.size() == 0) {
            System.out.println("No tasks to show\n");
            return;
        }
        Collections.sort(taskList);    //Sort tasks by due date
        listAllTasks(taskList);
        int input = handleRangeInput(0, taskList.size(), "Index",
                "Enter the task number you want to remove (0 to cancel): ");
        if (input > 0) {
            Task removeTask = taskList.get(input - 1);
            System.out.println("Task " + removeTask.getName() + " is now removed");
            taskList.remove(input - 1);
        }
        System.out.println();
    }


    /**
     * Prompt user for task they want to mark as completed.
     *
     * @param taskList The list of task which can be manipulated.
     */
    public void markTaskCompleted(List<Task> taskList) {
        Collections.sort(taskList);
        List<Task> incompleteTasks = getIncompleteTasks(taskList);
        listIncompleteTasks(incompleteTasks, "");

        int input = handleRangeInput(0, incompleteTasks.size(), "Index",
                "Enter the task number you want to mark as completed (0 to cancel): ");
        if (input > 0) {
            Task markComplete = incompleteTasks.get(input - 1);
            taskList.get(taskList.indexOf(markComplete)).markCompleted(true);
            System.out.println("Task " + markComplete.getName() + " is now completed.");
        }
        System.out.println();
    }

    /**
     * Lists upcoming / overdue incomplete tasks
     *
     * @param taskList   Task list which will be used to retrieve incomplete tasks
     * @param isUpcoming If true => shows upcoming tasks, otherwise => shows overdue tasks
     */
    public void incompleteTasks(List<Task> taskList, boolean isUpcoming) {
        List<Task> incompleteTasks = getIncompleteTasks(taskList);
        List<Task> filteredTasks = new ArrayList<>();
        long today = new Date().getTime();
        for (Task task : incompleteTasks) {
            //Add only upcoming tasks
            if (isUpcoming && today <= task.getDueDate().getTimeInMillis()) {
                filteredTasks.add(task);
            }
            //Add on only overdue tasks
            else if (!isUpcoming && today > task.getDueDate().getTimeInMillis()) {
                filteredTasks.add(task);
            }
        }
        Collections.sort(filteredTasks);
        if (isUpcoming) {
            listIncompleteTasks(filteredTasks, "upcoming");
        } else {
            listIncompleteTasks(filteredTasks, "overdue");
        }

    }

    private List<Task> getIncompleteTasks(List<Task> taskList) {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (!task.isCompleted()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }


}
