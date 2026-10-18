import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    private ArrayList<Task> tasks = new ArrayList<>();
    private int nextId = 1;
    private Scanner sc;

    public TaskManager(Scanner sc) {
        this.sc = sc;
    }

    public void addTask() {
        System.out.println("\n--- Add Task ---");

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        LocalDate dueDate = readDate();
        String priority = readPriority();

        Task task = new Task(nextId, title, description, dueDate, priority);
        tasks.add(task);
        nextId++;

        System.out.println("Task added with ID " + task.getId() + ".");
    }

    public void viewAllTasks() {
        System.out.println("\n--- All Tasks ---");

        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).display();
        }
    }

    public void updateTask() {
        System.out.println("\n--- Update Task ---");

        System.out.print("Enter task ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Task task = findById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.print("New title: ");
        task.setTitle(sc.nextLine());

        System.out.print("New description: ");
        task.setDescription(sc.nextLine());

        task.setDueDate(readDate());
        task.setPriority(readPriority());

        System.out.println("Task updated.");
    }

    public void deleteTask() {
        System.out.println("\n--- Delete Task ---");

        System.out.print("Enter task ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        Task task = findById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        tasks.remove(task);
        System.out.println("Task deleted.");
    }

    public void markAsDone() {
        System.out.println("\n--- Mark as Done ---");

        System.out.print("Enter task ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Task task = findById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        task.setDone(true);
        System.out.println("Task marked as done.");
    }

    public void searchTask() {
        System.out.println("\n--- Search Task ---");

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTitle().toLowerCase().contains(keyword)
                    || task.getDescription().toLowerCase().contains(keyword)) {
                task.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching tasks found.");
        }
    }

    private Task findById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                return tasks.get(i);
            }
        }
        return null;
    }

    private LocalDate readDate() {
        System.out.print("Due year (e.g. 2026): ");
        int year = sc.nextInt();
        while (year < 2000 || year > 2100) {
            System.out.print("Please enter a year from 2000 to 2100: ");
            year = sc.nextInt();
        }

        System.out.print("Due month (1-12): ");
        int month = sc.nextInt();
        while (month < 1 || month > 12) {
            System.out.print("Please enter a month from 1 to 12: ");
            month = sc.nextInt();
        }

        int lastDay = LocalDate.of(year, month, 1).lengthOfMonth();
        System.out.print("Due day (1-" + lastDay + "): ");
        int day = sc.nextInt();
        while (day < 1 || day > lastDay) {
            System.out.print("Please enter a day from 1 to " + lastDay + ": ");
            day = sc.nextInt();
        }
        sc.nextLine();

        return LocalDate.of(year, month, day);
    }

    private String readPriority() {
        System.out.print("Priority (1 = High, 2 = Medium, 3 = Low): ");
        int p = sc.nextInt();
        while (p < 1 || p > 3) {
            System.out.print("Please enter 1, 2 or 3: ");
            p = sc.nextInt();
        }
        sc.nextLine();

        if (p == 1) {
            return "High";
        } else if (p == 2) {
            return "Medium";
        } else {
            return "Low";
        }
    }
}