package OS_System;

import java.time.LocalDate;

public class Task {

    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private boolean isDone;

    public Task(int id, String title, String description, LocalDate dueDate, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isDone = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void display() {
        String status;
        if (isDone) {
            status = "Done";
        } else {
            status = "Pending";
        }

        System.out.println("ID: " + id + " | " + title + " | Due: " + dueDate
                + " | Priority: " + priority + " | " + status);
        System.out.println("    " + description);
    }
}
