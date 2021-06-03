package cmpt213.assignment1.tasktracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class for storing Task information
 * @author Mikhail Alexeev
 */

public class Task implements Comparable<Task>{
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
    public Task(String name, String notes, GregorianCalendar dueDate, boolean isCompleted){
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }


    public void markCompleted(boolean completed){
        this.isCompleted = completed;
    }

    public boolean isCompleted(){
        return this.isCompleted;
    }

    public GregorianCalendar getDueDate() {
        return dueDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Task o) {
        return this.dueDate.compareTo(o.getDueDate());
    }

    /**
     * ToString method for printing out class info
     * @return String containing class Info
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
        dateFormat.setCalendar(dueDate);
        String completedStr = isCompleted ? "Yes" : "No";
        return "Task: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Due Date: " + dateFormat.format(dueDate.getTime()) + "\n" +
                "Completed? " + completedStr;
    }

    /**
     * ToString method for print class info without status
     * @return String containing class info without completion status
     */
    public String toStringNoStatus(){
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
        dateFormat.setCalendar(dueDate);
        return "Task: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Due Date: " + dateFormat.format(dueDate.getTime());
    }

}
