import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    String name;
    String phone;
    String email;

    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    void displayContactDetails() {
        System.out.println("Name   : " + name);
        System.out.println("Phone  : " + phone);
        System.out.println("Email  : " + email);
    }

    void call() {
        System.out.println("Calling " + name + " ...");
        System.out.println("Phone Number : " + phone);
    }
}

class ContactBook {
    ArrayList<Contact> contacts = new ArrayList<>();
    Scanner sc;

    ContactBook(Scanner sc) {
        this.sc = sc;
    }

    void showMenu() {
        System.out.println("\n========= CONTACT MANAGEMENT =========");
        System.out.println(" 1. Add Contact");
        System.out.println(" 2. Remove Contact");
        System.out.println(" 3. Update Contact");
        System.out.println(" 4. Search Contact");
        System.out.println(" 5. Call Person");
        System.out.println(" 6. Exit");
        System.out.print("Enter your choice: ");
    }

    // Returns the position of the contact in the list, or -1 if not found
    int findContact(String key) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.name.equalsIgnoreCase(key) || c.phone.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    void addContact() {
        System.out.println("--- Add Contact ---");
        System.out.print("Name   : ");
        String name = sc.nextLine();
        System.out.print("Phone  : ");
        String phone = sc.nextLine();
        System.out.print("Email  : ");
        String email = sc.nextLine();

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully!");
    }

    void removeContact() {
        System.out.println("--- Remove Contact ---");
        System.out.print("Enter name / phone: ");
        int index = findContact(sc.nextLine());

        if (index == -1) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.print("Confirm delete? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            contacts.remove(index);
            System.out.println("Contact removed successfully!");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    void updateContact() {
        System.out.println("--- Update Contact ---");
        System.out.print("Enter name / phone: ");
        int index = findContact(sc.nextLine());

        if (index == -1) {
            System.out.println("Contact not found.");
            return;
        }

        Contact c = contacts.get(index);
        System.out.print("New Name   : ");
        c.name = sc.nextLine();
        System.out.print("New Phone  : ");
        c.phone = sc.nextLine();
        System.out.print("New Email  : ");
        c.email = sc.nextLine();
        System.out.println("Contact updated successfully!");
    }

    void searchContact() {
        System.out.println("--- Search Contact ---");
        System.out.print("Enter name / phone: ");
        int index = findContact(sc.nextLine());

        if (index == -1) {
            System.out.println("Contact not found.");
        } else {
            contacts.get(index).displayContactDetails();
        }
    }

    void callPerson() {
        System.out.println("--- Call Person ---");
        System.out.print("Enter name / phone: ");
        int index = findContact(sc.nextLine());

        if (index == -1) {
            System.out.println("Contact not found.");
        } else {
            contacts.get(index).call();
        }
    }
}

public class ContactManagement {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ContactBook book = new ContactBook(sc);
        boolean running = true;

        while (running) {
            book.showMenu();
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    book.addContact();
                    break;
                case "2":
                    book.removeContact();
                    break;
                case "3":
                    book.updateContact();
                    break;
                case "4":
                    book.searchContact();
                    break;
                case "5":
                    book.callPerson();
                    break;
                case "6":
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number from 1 to 6.");
            }
        }
    }
}