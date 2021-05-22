package cmpt213.assignment1.tasktracker;

import java.util.GregorianCalendar;

/**
 * Class for storing Task information
 * @author Mikhail Alexeev
 */

public class Task {
    private String name;
    private String notes;
    private GregorianCalendar dueDate;
    private boolean isCompleted;

    /**
     * Main Constructor to create Task
     * @param name of note
     * @param notes to be recorded
     * @param dueDate date that it is due
     */
    public Task(String name, String notes, GregorianCalendar dueDate){
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }


    public void markCompleted(boolean completed){
        this.isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Task: " + name + '\'' +
                "Notes: " + notes + '\'' +
                "Due Date: " + dueDate + '\'' +
                "Completed? " + isCompleted;
    }
}
