package OS_System;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("       MULTI-MODULE MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Advance Parking Management System");
            System.out.println("2. Kids Games Menu");
            System.out.println("3. To-Do List Application");
            System.out.println("4. Shopping Management App");
            System.out.println("5. Typing Speed Analyzer");
            System.out.println("6. Tools & Utilities");
            System.out.println("7. Contact Management");
            System.out.println("8. Exit Application");
            System.out.println("========================================");
            System.out.print("Enter Your Choice: ");

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
                    System.out.println("\nOpening Parking Management...");
                    Parking_Management_System.startParkingSystem(sc);
                    break;

                case 2:
                    System.out.println("\nOpening Kids Games...");
                    kidsMenu KidsMenu = null;
                    KidsMenu.kidsMenu(sc);
                    break;

                case 3:
                    System.out.println("\nOpening To-Do List...");
                    TodoApp.todoMenu(sc);
                    break;

                case 4:
                    System.out.println("\nOpening Shopping Management...");
                    Shopping_App.Main_Shopping.shoppingMenu(sc);
                    break;

                case 5:
                    System.out.println("\nOpening Typing Speed Analyzer...");
                    TypingSpeed.typingMenu(sc);
                    break;

                case 6:
                    System.out.println("\nOpening Tools & Utilities...");
                    ToolsAndUtilities.toolsMenu(sc);
                    break;

                case 7:
                    System.out.println("\nOpening Contact Management...");
                    ContactManagement.contactMenu(sc);
                    break;

                case 8:
                    System.out.println("\nThank you for using the application!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Select 1 to 8.");
            }
        }

        sc.close();
    }
}
