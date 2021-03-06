package cmpt213.assignment1.tasktracker;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Main controller class for handling application
 *
 * @author Mikhail Alexeev
 */

public class Controller {

    private enum OPTION {EMPTY, LIST_ALL, ADD, REMOVE, MARK_COMPLETE, LIST_OVERDUE, LIST_UPCOM, EXIT}

    private static final boolean UPCOMING = true;
    private static final String JSON_PATH = "tasks.json";
    private static List<Task> taskList = new ArrayList<>();

    /**
     * Entrypoint for the program
     */
    public static void main(String[] args) {
        loadFromJson();

        TextMenu menu = new TextMenu();
        menu.displayMenu();
        while (true) {
            OPTION userInput = OPTION.values()[menu.handleRangeInput(1, 7, "Selection",
                    "Choose an option by entering 1 - 7: ")];
            // Handle different inputs
            switch (userInput) {
                case LIST_ALL -> menu.listAllTasks(taskList);
                case ADD -> menu.addNewTask(taskList);
                case REMOVE -> menu.removeTask(taskList);
                case MARK_COMPLETE -> menu.markTaskCompleted(taskList);
                case LIST_OVERDUE -> menu.incompleteTasks(taskList, !UPCOMING);
                case LIST_UPCOM -> menu.incompleteTasks(taskList, UPCOMING);
            }
            // Break out of loop
            if (userInput == OPTION.EXIT) {
                saveToJson();
                break;
            }


            menu.displayMenu();
        }
        System.out.println("Thank you for using the system");
    }


    private static void loadFromJson() {
        File file = new File(JSON_PATH);
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));

            JsonArray jsonArray = fileElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject taskObj = jsonArray.get(i).getAsJsonObject();
                JsonObject taskDate = taskObj.get("dueDate").getAsJsonObject();
                taskList.add(new Task(
                        taskObj.get("name").getAsString(),
                        taskObj.get("notes").getAsString(),
                        new GregorianCalendar(
                                taskDate.get("year").getAsInt(),
                                taskDate.get("month").getAsInt(),
                                taskDate.get("dayOfMonth").getAsInt(),
                                taskDate.get("hourOfDay").getAsInt(),
                                taskDate.get("minute").getAsInt()
                        ),
                        taskObj.get("isCompleted").getAsBoolean()
                ));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Tasks list does not yet exist, nothing to load");
        }
    }


    /**
     * Saves the taskList to a json file
     */
    private static void saveToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter(JSON_PATH);
            gson.toJson(taskList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

