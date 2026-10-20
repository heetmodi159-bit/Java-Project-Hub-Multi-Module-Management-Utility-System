
package OS_System;

import java.util.Scanner;

public class TodoApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        todoMenu(sc);
        sc.close();
    }

    public static void todoMenu(Scanner sc) {

        TaskManager manager = new TaskManager(sc);
        boolean running = true;

        while (running) {
            showMenu();

            System.out.print("Enter your choice (1-7): ");

            String input = sc.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    manager.addTask();
                    break;

                case 2:
                    manager.viewAllTasks();
                    break;

                case 3:
                    manager.updateTask();
                    break;

                case 4:
                    manager.deleteTask();
                    break;

                case 5:
                    manager.markAsDone();
                    break;

                case 6:
                    manager.searchTask();
                    break;

                case 7:
                    System.out.println("Going back to Main Menu...");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Oops! Please choose a number from 1 to 7."
                    );
            }
        }
    }

    public static void showMenu() {
        System.out.println("\n===== TO-DO LIST =====");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Mark as Done");
        System.out.println("6. Search Task");
        System.out.println("7. Back to Main Menu");
    }
}
