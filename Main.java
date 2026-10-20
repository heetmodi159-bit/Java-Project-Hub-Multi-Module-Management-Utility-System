package OS_System;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("┌───────────────────────────────────────────────┐");
            System.out.println("│                 O S   S Y S T E M             │");
            System.out.println("├───────────────────────────────────────────────┤");
            System.out.println("│                                               │");
            System.out.println("│          MULTI-MODULE MANAGEMENT OS           │");
            System.out.println("│                                               │");

            System.out.println("│  ┌─────────────┐       ┌─────────────┐        │");
            System.out.println("│  │     [01]    │       │     [02]    │        │");
            System.out.println("│  │   PARKING   │       │    KIDS     │        │");
            System.out.println("│  │ MANAGEMENT  │       │    GAMES    │        │");
            System.out.println("│  └─────────────┘       └─────────────┘        │");
            System.out.println("│                                               │");

            System.out.println("│  ┌─────────────┐       ┌─────────────┐        │");
            System.out.println("│  │     [03]    │       │     [04]    │        │");
            System.out.println("│  │   TO-DO     │       │  SHOPPING   │        │");
            System.out.println("│  │    LIST     │       │ MANAGEMENT  │        │");
            System.out.println("│  └─────────────┘       └─────────────┘        │");
            System.out.println("│                                               │");

            System.out.println("│  ┌─────────────┐       ┌─────────────┐        │");
            System.out.println("│  │     [05]    │       │     [06]    │        │");
            System.out.println("│  │   TYPING    │       │    TOOLS    │        │");
            System.out.println("│  │    SPEED    │       │ & UTILITIES │        │");
            System.out.println("│  └─────────────┘       └─────────────┘        │");
            System.out.println("│                                               │");

            System.out.println("│  ┌─────────────┐       ┌─────────────┐        │");
            System.out.println("│  │     [07]    │       │     [08]    │        │");
            System.out.println("│  │   CONTACT   │       │    EXIT     │        │");
            System.out.println("│  │ MANAGEMENT  │       │ APPLICATION │        │");
            System.out.println("│  └─────────────┘       └─────────────┘        │");
            System.out.println("│                                               │");
            System.out.println("├───────────────────────────────────────────────┤");
            System.out.println("│             OS:// READY  •  ONLINE            │");
            System.out.println("└───────────────────────────────────────────────┘");

            System.out.print("\nOS://INPUT > ");

            String input = sc.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n┌───────────────────────────────────────────────┐");
                System.out.println("│              INVALID INPUT                   │");
                System.out.println("│       Please enter a valid number.           │");
                System.out.println("└───────────────────────────────────────────────┘");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n[ OS:// OPENING PARKING MANAGEMENT... ]");
                    Parking_Management_System.startParkingSystem(sc);
                    break;

                case 2:
                    System.out.println("\n[ OS:// OPENING KIDS GAMES... ]");
                    kidsMenu KidsMenu = null;
                    KidsMenu.kidsMenu(sc);
                    break;

                case 3:
                    System.out.println("\n[ OS:// OPENING TO-DO LIST... ]");
                    TodoApp.todoMenu(sc);
                    break;

                case 4:
                    System.out.println("\n[ OS:// OPENING SHOPPING MANAGEMENT... ]");
                    Shopping_App.Main_Shopping.shoppingMenu(sc);
                    break;

                case 5:
                    System.out.println("\n[ OS:// OPENING TYPING SPEED ANALYZER... ]");
                    TypingSpeed.typingMenu(sc);
                    break;

                case 6:
                    System.out.println("\n[ OS:// OPENING TOOLS & UTILITIES... ]");
                    ToolsAndUtilities.toolsMenu(sc);
                    break;

                case 7:
                    System.out.println("\n[ OS:// OPENING CONTACT MANAGEMENT... ]");
                    ContactManagement.contactMenu(sc);
                    break;

                case 8:
                    System.out.println("\n┌───────────────────────────────────────────────┐");
                    System.out.println("  │              SYSTEM SHUTDOWN                  │");
                    System.out.println("  ├───────────────────────────────────────────────┤");
                    System.out.println("  │                                               │");
                    System.out.println("  │       Thank you for using the OS!             │");
                    System.out.println("  │                                               │");
                    System.out.println("  └───────────────────────────────────────────────┘");
                    running = false;
                    break;

                default:
                    System.out.println("\n┌───────────────────────────────────────────────┐");
                    System.out.println("  │              INVALID CHOICE                   │");
                    System.out.println("  │          Select 1 to 8 only.                  │");
                    System.out.println("  └───────────────────────────────────────────────┘");
            }
        }

        sc.close();
    }
}
