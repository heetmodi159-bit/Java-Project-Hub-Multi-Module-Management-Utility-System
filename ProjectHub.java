// Phone Apps - Multi-Module Project Hub (single file)
// All modules written by the team are kept as they are; only the entry points were joined.

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProjectHub {

    // ONE Scanner shared by every module (closing it would close System.in for all modules)
    static Scanner sc = new Scanner(System.in);

    static String readChoice() {
        String choice = sc.nextLine().trim();
        while (choice.isEmpty()) {
            choice = sc.nextLine().trim();
        }
        return choice;
    }

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n==========================================");
            System.out.println("         PHONE APPS - PROJECT HUB");
            System.out.println("==========================================");
            System.out.println(" 1. To Do List");
            System.out.println(" 2. Contact");
            System.out.println(" 3. Parking App");
            System.out.println(" 4. For Kids");
            System.out.println(" 5. Tools");
            System.out.println(" 6. Shopping Cart");
            System.out.println(" 7. Typing Speed Enhancer");
            System.out.println(" 0. Exit");
            System.out.print("Enter your choice: ");
            String choice = readChoice();

            try {
                switch (choice) {
                    case "1":
                        TodoApp.run();
                        break;
                    case "2":
                        ContactManagement.run();
                        break;
                    case "3":
                        Parking_Management_System.startParkingSystem(sc);
                        break;
                    case "4":
                        KidsMenu.kidsMenu();
                        break;
                    case "5":
                        ToolsAndUtilities.run();
                        break;
                    case "6":
                        Main_Shopping.run();
                        break;
                    case "7":
                        TypingSpeed.run();
                        break;
                    case "0":
                        System.out.println("Thank you for using Phone Apps. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Enter a number from 0 to 7.");
                }
            } catch (Exception e) {
                System.out.println("\nSomething went wrong in this module (" + e + "). Returning to the Main Menu.");
            }
        }
    }
}

// ======================================================================
// MODULE: TO DO LIST
// ======================================================================

// ---- from Task.java ----
class Task {

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

// ---- from TaskManager.java ----
class TaskManager {

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

// ---- from TodoApp.java ----
class TodoApp {

    static void run() {
        Scanner sc = ProjectHub.sc;
        TaskManager manager = new TaskManager(sc);

        while (true) {
            showMenu();

            System.out.print("Enter your choice (1-7): ");
            int choice = sc.nextInt();
            sc.nextLine();

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
                    return;
                default:
                    System.out.println("Oops! Please choose a number from 1 to 7.");
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

// ======================================================================
// MODULE: CONTACT
// ======================================================================

// ---- from ContactManagement.java ----
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

class ContactManagement {
    static void run() {

        Scanner sc = ProjectHub.sc;
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
                    System.out.println("Going back to Main Menu...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number from 1 to 6.");
            }
        }
    }
}

// ======================================================================
// MODULE: PARKING APP
// ======================================================================

// ---- from Customer.java ----
class Customer {

    private final String cust_name;
    private final String password;

    public Customer(String cust_name, String password) {
        this.cust_name = cust_name.trim();
        this.password = password;
    }

    public String getCustName() {
        return cust_name;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void display_customer_details() {
        System.out.println("Customer Name : " + cust_name);
    }
}

// ---- from Vehicle.java ----
class Vehicle {

    private final String number_plate;
    private final String vehicle_type;

    public Vehicle(String number_plate, String vehicle_type) {

        if (number_plate == null || number_plate.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle number plate cannot be empty."
            );
        }

        if (vehicle_type == null
                || (!vehicle_type.equalsIgnoreCase("Car")
                && !vehicle_type.equalsIgnoreCase("Bike"))) {

            throw new IllegalArgumentException(
                    "Only Car and Bike are allowed."
            );
        }

        this.number_plate = number_plate.trim().toUpperCase();

        this.vehicle_type = vehicle_type.equalsIgnoreCase("Car")
                ? "Car" : "Bike";
    }

    public String getNumberPlate() {
        return number_plate;
    }

    public String getVehicleType() {
        return vehicle_type;
    }

    public void display_vehicle_details() {
        System.out.println("Vehicle Number Plate : " + number_plate);
        System.out.println("Vehicle Type         : " + vehicle_type);
    }
}

// ---- from ParkingSlot.java ----
class ParkingSlot {

    public final int total_car_slot = 25;
    public final int total_bike_slot = 25;

    // Validate the Selected Slot
    public boolean isValidSlot(String vehicleType, int slot) {

        if (vehicleType.equalsIgnoreCase("Car")) {
            return slot >= 1 && slot <= total_car_slot;
        }

        if (vehicleType.equalsIgnoreCase("Bike")) {
            return slot >= 1 && slot <= total_bike_slot;
        }

        return false;
    }

    // Check Whether the Slot Is Available for the Selected Time
    public boolean isSlotAvailable(
            String vehicleType,
            int selectedSlot,
            LocalDateTime entry,
            LocalDateTime exit,
            List<BookingRecord> bookings) {

        if (!isValidSlot(vehicleType, selectedSlot)
                || entry == null
                || exit == null
                || !exit.isAfter(entry)) {

            return false;
        }

        for (BookingRecord booking : bookings) {

            if (booking.status.equals("CANCELLED")) {
                continue;
            }

            if (!booking.vehicle.getVehicleType()
                    .equalsIgnoreCase(vehicleType)
                    || booking.selected_slot != selectedSlot) {

                continue;
            }

            LocalDateTime existingEntry = booking.scheduled_entry;
            LocalDateTime existingExit = booking.expected_exit;

            // An Active Vehicle Continues Occupying Its Slot
            if (booking.status.equals("ACTIVE")
                    && booking.actual_entry != null) {

                existingEntry = booking.actual_entry;
                existingExit = LocalDateTime.MAX;

            } else if (booking.status.equals("COMPLETED")
                    && booking.actual_entry != null
                    && booking.actual_exit != null) {

                existingEntry = booking.actual_entry;
                existingExit = booking.actual_exit;
            }

            boolean overlaps =
                    entry.isBefore(existingExit)
                            && exit.isAfter(existingEntry);

            if (overlaps) {
                return false;
            }
        }

        return true;
    }

    // Display Car Parking Slots
    public void car_parking_slot(
            LocalDateTime entry,
            LocalDateTime exit,
            List<BookingRecord> bookings) {

        System.out.println("\n=========== CAR PARKING SLOTS ===========");

        int available = 0;

        for (int i = 1; i <= total_car_slot; i++) {

            if (isSlotAvailable("Car", i, entry, exit, bookings)) {
                System.out.printf("[C%02d] ", i);
                available++;
            } else {
                System.out.printf("[XX%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("Available Car Slots : " + available);
    }

    // Display Bike Parking Slots
    public void bike_parking_slot(
            LocalDateTime entry,
            LocalDateTime exit,
            List<BookingRecord> bookings) {

        System.out.println("\n=========== BIKE PARKING SLOTS ===========");

        int available = 0;

        for (int i = 1; i <= total_bike_slot; i++) {

            if (isSlotAvailable("Bike", i, entry, exit, bookings)) {
                System.out.printf("[B%02d] ", i);
                available++;
            } else {
                System.out.printf("[XX%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("Available Bike Slots : " + available);
    }
}

// ---- from BookingRecord.java ----
class BookingRecord {

    final String booking_id;
    final String customer_name;
    final Vehicle vehicle;
    final int selected_slot;

    final LocalDate booking_date;
    final LocalDateTime scheduled_entry;
    final LocalDateTime expected_exit;

    LocalDateTime actual_entry;
    LocalDateTime actual_exit;

    String status;

    int parking_fare;
    int advance_fare;
    int extra_fare;
    int total_fare;

    // Constructor for a New Booking
    public BookingRecord(
            String booking_id,
            String customer_name,
            Vehicle vehicle,
            int selected_slot,
            LocalDateTime scheduled_entry,
            LocalDateTime expected_exit) {

        this(
                booking_id,
                customer_name,
                vehicle,
                selected_slot,
                LocalDate.now(),
                scheduled_entry,
                expected_exit,
                null,
                null,
                "BOOKED",
                0,
                50,
                0,
                0
        );
    }

    // Constructor for a Booking Loaded from MySQL
    public BookingRecord(
            String booking_id,
            String customer_name,
            Vehicle vehicle,
            int selected_slot,
            LocalDate booking_date,
            LocalDateTime scheduled_entry,
            LocalDateTime expected_exit,
            LocalDateTime actual_entry,
            LocalDateTime actual_exit,
            String status,
            int parking_fare,
            int advance_fare,
            int extra_fare,
            int total_fare) {

        this.booking_id = booking_id;
        this.customer_name = customer_name;
        this.vehicle = vehicle;
        this.selected_slot = selected_slot;

        this.booking_date = booking_date;
        this.scheduled_entry = scheduled_entry;
        this.expected_exit = expected_exit;

        this.actual_entry = actual_entry;
        this.actual_exit = actual_exit;

        this.status = status;

        this.parking_fare = parking_fare;
        this.advance_fare = advance_fare;
        this.extra_fare = extra_fare;
        this.total_fare = total_fare;
    }
}

// ---- from DatabaseConnection.java ----
class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/advance_parking_system"
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";

    // Replace this with your MySQL password.
    private static final String PASSWORD = "root";

    // Open a Connection to MySQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// ---- from BookingDatabase.java ----
class BookingDatabase {

    // Save a New Booking in MySQL
    public static void saveBooking(BookingRecord booking)
            throws SQLException {

        String sql = "INSERT INTO parking_bookings "
                + "(booking_id, customer_name, number_plate, vehicle_type, "
                + "slot_number, booking_date, scheduled_entry, expected_exit, "
                + "actual_entry, actual_exit, status, parking_fare, "
                + "advance_fare, extra_fare, total_fare) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, booking.booking_id);
            ps.setString(2, booking.customer_name);
            ps.setString(3, booking.vehicle.getNumberPlate());
            ps.setString(4, booking.vehicle.getVehicleType());
            ps.setInt(5, booking.selected_slot);
            ps.setDate(6, Date.valueOf(booking.booking_date));

            ps.setTimestamp(7,
                    Timestamp.valueOf(booking.scheduled_entry));

            ps.setTimestamp(8,
                    Timestamp.valueOf(booking.expected_exit));

            setNullableTimestamp(ps, 9, booking.actual_entry);
            setNullableTimestamp(ps, 10, booking.actual_exit);

            ps.setString(11, booking.status);
            ps.setInt(12, booking.parking_fare);
            ps.setInt(13, booking.advance_fare);
            ps.setInt(14, booking.extra_fare);
            ps.setInt(15, booking.total_fare);

            ps.executeUpdate();
        }
    }

    // Load Every Saved Booking into an ArrayList
    public static ArrayList<BookingRecord> loadBookings()
            throws SQLException {

        ArrayList<BookingRecord> bookings = new ArrayList<>();

        String sql = "SELECT * FROM parking_bookings "
                + "ORDER BY booking_date, booking_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Timestamp actualEntry =
                        rs.getTimestamp("actual_entry");

                Timestamp actualExit =
                        rs.getTimestamp("actual_exit");

                Vehicle vehicle = new Vehicle(
                        rs.getString("number_plate"),
                        rs.getString("vehicle_type")
                );

                BookingRecord booking = new BookingRecord(
                        rs.getString("booking_id"),
                        rs.getString("customer_name"),
                        vehicle,
                        rs.getInt("slot_number"),
                        rs.getDate("booking_date").toLocalDate(),
                        rs.getTimestamp("scheduled_entry").toLocalDateTime(),
                        rs.getTimestamp("expected_exit").toLocalDateTime(),
                        actualEntry == null
                                ? null : actualEntry.toLocalDateTime(),
                        actualExit == null
                                ? null : actualExit.toLocalDateTime(),
                        rs.getString("status"),
                        rs.getInt("parking_fare"),
                        rs.getInt("advance_fare"),
                        rs.getInt("extra_fare"),
                        rs.getInt("total_fare")
                );

                bookings.add(booking);
            }
        }

        return bookings;
    }

    // Save Booking Cancellation
    public static boolean cancelBooking(String bookingId)
            throws SQLException {

        String sql = "UPDATE parking_bookings "
                + "SET status = 'CANCELLED' "
                + "WHERE booking_id = ? AND status = 'BOOKED'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save Actual Vehicle Entry Time
    public static boolean saveEntryTime(
            String bookingId,
            LocalDateTime entry) throws SQLException {

        String sql = "UPDATE parking_bookings "
                + "SET actual_entry = ?, status = 'ACTIVE' "
                + "WHERE booking_id = ? AND status = 'BOOKED'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(entry));
            ps.setString(2, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save Exit Time and Final Fare
    public static boolean saveCheckout(
            String bookingId,
            LocalDateTime exit,
            Payment payment) throws SQLException {

        String sql = "UPDATE parking_bookings SET "
                + "actual_exit = ?, status = 'COMPLETED', "
                + "parking_fare = ?, advance_fare = ?, "
                + "extra_fare = ?, total_fare = ? "
                + "WHERE booking_id = ? AND status = 'ACTIVE'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(exit));
            ps.setInt(2, payment.getParkingFare());
            ps.setInt(3, payment.getAdvanceFare());
            ps.setInt(4, payment.getExtraFare());
            ps.setInt(5, payment.getTotalFare());
            ps.setString(6, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save a Nullable Timestamp
    private static void setNullableTimestamp(
            PreparedStatement ps,
            int position,
            LocalDateTime value) throws SQLException {

        if (value == null) {
            ps.setNull(position, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(position, Timestamp.valueOf(value));
        }
    }
}

// ---- from Booking.java ----
class Booking {

    private final ParkingSlot ps;
    private final ArrayList<BookingRecord> all_bookings;
    private int next_booking_number = 1001;

    // Create Booking Manager and Load Saved Bookings
    public Booking(ParkingSlot ps) throws SQLException {

        this.ps = ps;
        this.all_bookings = BookingDatabase.loadBookings();

        for (BookingRecord booking : all_bookings) {

            try {

                if (booking.booking_id.startsWith("B")) {

                    int number = Integer.parseInt(
                            booking.booking_id.substring(1)
                    );

                    if (number >= next_booking_number) {
                        next_booking_number = number + 1;
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Skipping invalid booking ID: " + booking.booking_id
                );
            }
        }

        System.out.println(
                "Saved bookings loaded: " + all_bookings.size()
        );
    }

    // Book a Selected Parking Slot
    public BookingRecord Advance_booking(
            Customer customer,
            Vehicle vehicle,
            int selected_slot,
            LocalDateTime entry,
            LocalDateTime exit) {

        if (customer == null || vehicle == null) {
            System.out.println(
                    "Customer and vehicle details are required."
            );
            return null;
        }

        if (entry == null || exit == null || !exit.isAfter(entry)) {
            System.out.println(
                    "Expected exit must be after entry."
            );
            return null;
        }

        if (entry.isBefore(LocalDateTime.now().minusMinutes(1))) {
            System.out.println(
                    "Entry date/time cannot be in the past."
            );
            return null;
        }

        if (!ps.isValidSlot(
                vehicle.getVehicleType(), selected_slot)) {

            System.out.println("Invalid slot number.");
            return null;
        }

        if (hasOverlappingBookingForVehicle(
                vehicle.getNumberPlate(), entry, exit)) {

            System.out.println(
                    "This vehicle already has an overlapping booking."
            );
            return null;
        }

        if (!ps.isSlotAvailable(
                vehicle.getVehicleType(),
                selected_slot,
                entry,
                exit,
                all_bookings)) {

            System.out.println(
                    "This slot is unavailable for the selected time."
            );
            return null;
        }

        String bookingId = "B" + next_booking_number;

        BookingRecord record = new BookingRecord(
                bookingId,
                customer.getCustName(),
                vehicle,
                selected_slot,
                entry,
                exit
        );

        try {

            // Save to MySQL Before Confirming the Booking
            BookingDatabase.saveBooking(record);

            all_bookings.add(record);
            next_booking_number++;

            System.out.println("Booking Confirmed Successfully.");
            System.out.println("Booking ID : " + record.booking_id);
            System.out.println("Selected Slot : " + slotLabel(record));

            return record;

        } catch (SQLException e) {

            System.out.println(
                    "Booking could not be saved: " + e.getMessage()
            );

            return null;
        }
    }

    // Prevent Overlapping Bookings for the Same Vehicle
    private boolean hasOverlappingBookingForVehicle(
            String numberPlate,
            LocalDateTime entry,
            LocalDateTime exit) {

        for (BookingRecord booking : all_bookings) {

            if (booking.status.equals("CANCELLED")
                    || !booking.vehicle.getNumberPlate()
                    .equalsIgnoreCase(numberPlate)) {

                continue;
            }

            LocalDateTime existingEntry = booking.scheduled_entry;
            LocalDateTime existingExit = booking.expected_exit;

            if (booking.status.equals("ACTIVE")
                    && booking.actual_entry != null) {

                existingEntry = booking.actual_entry;
                existingExit = LocalDateTime.MAX;

            } else if (booking.status.equals("COMPLETED")
                    && booking.actual_entry != null
                    && booking.actual_exit != null) {

                existingEntry = booking.actual_entry;
                existingExit = booking.actual_exit;
            }

            if (entry.isBefore(existingExit)
                    && exit.isAfter(existingEntry)) {

                return true;
            }
        }

        return false;
    }

    // Cancel Booking and Save the Status in MySQL
    public boolean Cancel_booking(
            Customer customer,
            String bookingId) {

        BookingRecord booking = findOwnedBooking(customer, bookingId);

        if (booking == null) {
            System.out.println(
                    "Booking not found for this account."
            );
            return false;
        }

        if (!booking.status.equals("BOOKED")) {
            System.out.println(
                    "Only BOOKED reservations can be cancelled."
            );
            return false;
        }

        try {

            if (!BookingDatabase.cancelBooking(booking.booking_id)) {
                System.out.println(
                        "Cancellation was not saved."
                );
                return false;
            }

            booking.status = "CANCELLED";

            System.out.println("Booking Cancelled Successfully.");
            System.out.println(
                    "Released Slot : " + slotLabel(booking)
            );

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Could not save cancellation: " + e.getMessage()
            );

            return false;
        }
    }
// Check In a Vehicle
public BookingRecord checkIn(
        Customer customer,
        String bookingId) {

    BookingRecord booking =
            findOwnedBooking(customer, bookingId);

    if (booking == null) {
        System.out.println("Booking not found.");
        return null;
    }

    if (!booking.status.equals("BOOKED")) {
        System.out.println(
                "Only BOOKED reservations can check in."
        );
        return null;
    }

    LocalDateTime now = LocalDateTime.now();

    if (now.isBefore(booking.scheduled_entry)) {
        System.out.println("It is too early to check in.");
        return null;
    }

    if (!now.isBefore(booking.expected_exit)) {
        System.out.println("The scheduled parking period has ended.");
        return null;
    }

    try {

        if (!BookingDatabase.saveEntryTime(
                booking.booking_id, now)) {

            System.out.println("Check-in was not saved.");
            return null;
        }

        booking.actual_entry = now;
        booking.status = "ACTIVE";

        System.out.println("Vehicle Checked In Successfully.");
        System.out.println("Actual Entry Time : " + now);

        return booking;

    } catch (SQLException e) {

        System.out.println(
                "Database error during check-in: " + e.getMessage()
        );

        return null;
    }
}


// Check Out a Vehicle and Calculate Final Fare
public BookingRecord checkOut(
        Customer customer,
        String bookingId) {

    BookingRecord booking =
            findOwnedBooking(customer, bookingId);

    if (booking == null) {
        System.out.println("Booking not found.");
        return null;
    }

    if (!booking.status.equals("ACTIVE")
            || booking.actual_entry == null) {

        System.out.println(
                "The vehicle must check in before checkout."
        );

        return null;
    }

    LocalDateTime actualExit = LocalDateTime.now();

    if (!actualExit.isAfter(booking.actual_entry)) {
        actualExit = booking.actual_entry.plusSeconds(1);
    }

    Duration duration = Duration.between(
            booking.actual_entry, actualExit
    );

    Payment payment = new Payment();

    payment.calculate_fare(booking.vehicle, duration);

    try {

        if (!BookingDatabase.saveCheckout(
                booking.booking_id, actualExit, payment)) {

            System.out.println("Checkout was not saved.");
            return null;
        }

        booking.actual_exit = actualExit;
        booking.status = "COMPLETED";

        booking.parking_fare = payment.getParkingFare();
        booking.advance_fare = payment.getAdvanceFare();
        booking.extra_fare = payment.getExtraFare();
        booking.total_fare = payment.getTotalFare();

        ParkingTicket.displayFinalTicket(
                booking, payment, duration
        );

        return booking;

    } catch (SQLException e) {

        System.out.println(
                "Database error during checkout: " + e.getMessage()
        );

        return null;
    }
}


// Display Booking History for the Logged-In Customer
public void displayHistory(Customer customer) {

    boolean found = false;

    System.out.println(
            "\n=========== MY BOOKING HISTORY ==========="
    );

    for (BookingRecord booking : all_bookings) {

        if (booking.customer_name.equalsIgnoreCase(
                customer.getCustName())) {

            found = true;

            System.out.println(
                    "Booking ID : " + booking.booking_id
                            + " | Vehicle : "
                            + booking.vehicle.getNumberPlate()
                            + " | Slot : " + slotLabel(booking)
                            + " | Entry : " + booking.scheduled_entry
                            + " | Exit : " + booking.expected_exit
                            + " | Status : " + booking.status
            );
        }
    }

    if (!found) {
        System.out.println("No bookings found.");
    }
}


// Display a Booking Ticket
public void displayTicket(
        Customer customer,
        String bookingId) {

    BookingRecord booking =
            findOwnedBooking(customer, bookingId);

    if (booking == null) {
        System.out.println(
                "Booking not found for this account."
        );
        return;
    }

    if (booking.status.equals("COMPLETED")
            && booking.actual_entry != null
            && booking.actual_exit != null) {

        Duration duration = Duration.between(
                booking.actual_entry,
                booking.actual_exit
        );

        Payment payment = new Payment();

        payment.calculate_fare(
                booking.vehicle, duration
        );

        ParkingTicket.displayFinalTicket(
                booking, payment, duration
        );

    } else {

        ParkingTicket.displayBookingTicket(booking);
    }
}


// Return Booking List for Slot Availability Checking
public List<BookingRecord> getAllBookings() {
    return all_bookings;
}


// Find a Booking Belonging to the Logged-In Customer
public BookingRecord findOwnedBooking(
        Customer customer,
        String bookingId) {

    if (customer == null || bookingId == null) {
        return null;
    }

    for (BookingRecord booking : all_bookings) {

        if (booking.booking_id.equalsIgnoreCase(
                bookingId.trim())
                && booking.customer_name.equalsIgnoreCase(
                customer.getCustName())) {

            return booking;
        }
    }

    return null;
}


// Generate the Car or Bike Slot Label
public String slotLabel(BookingRecord booking) {

    return booking.vehicle.getVehicleType().equalsIgnoreCase("Car")
            ? "C" + String.format("%02d", booking.selected_slot)
            : "B" + String.format("%02d", booking.selected_slot);
}
}

// ---- from Payment.java ----
class Payment {

    private int parking_fare;
    private final int advance_fare = 50;
    private int extra_fare;
    private int total_fare;

    // Calculate Parking Fare
    public void calculate_fare(
            Vehicle vehicle,
            Duration duration) {

        if (vehicle.getVehicleType().equalsIgnoreCase("Car")) {
            parking_fare = 50;
        } else if (vehicle.getVehicleType().equalsIgnoreCase("Bike")) {
            parking_fare = 30;
        } else {
            throw new IllegalArgumentException(
                    "Only Car and Bike are allowed."
            );
        }

        if (duration.compareTo(Duration.ofHours(4)) > 0) {
            extra_fare = parking_fare;
        } else {
            extra_fare = 0;
        }

        total_fare = parking_fare + advance_fare + extra_fare;
    }

    public int getParkingFare() {
        return parking_fare;
    }

    public int getAdvanceFare() {
        return advance_fare;
    }

    public int getExtraFare() {
        return extra_fare;
    }

    public int getTotalFare() {
        return total_fare;
    }

    // Display Fare Details
    public void display_fare(Duration duration) {

        long totalMinutes = Math.max(0, duration.toMinutes());

        System.out.println(
                "Parking Duration : "
                        + (totalMinutes / 60) + " hour(s) "
                        + (totalMinutes % 60) + " minute(s)"
        );

        System.out.println("Parking Fare     : Rs. " + parking_fare);
        System.out.println("Advance Fare     : Rs. " + advance_fare);
        System.out.println("Extra Fare       : Rs. " + extra_fare);
        System.out.println("-----------------------------------------");
        System.out.println("Total Fare       : Rs. " + total_fare);
    }
}

// ---- from ParkingTicket.java ----
class ParkingTicket {

    // Display the Advance Booking Ticket
    public static void displayBookingTicket(BookingRecord booking) {

        Duration estimatedDuration = Duration.between(
                booking.scheduled_entry,
                booking.expected_exit
        );

        Payment payment = new Payment();

        payment.calculate_fare(
                booking.vehicle,
                estimatedDuration
        );

        System.out.println("\n=============== BOOKING TICKET ===============");

        System.out.println("Booking ID       : " + booking.booking_id);
        System.out.println("Customer Name    : " + booking.customer_name);

        booking.vehicle.display_vehicle_details();

        System.out.println("Selected Slot    : " + slotLabel(booking));
        System.out.println("Booking Date     : " + booking.booking_date);
        System.out.println("Scheduled Entry  : " + booking.scheduled_entry);
        System.out.println("Expected Exit    : " + booking.expected_exit);
        System.out.println("Status           : " + booking.status);

        System.out.println("---------------- ESTIMATED FARE ----------------");

        payment.display_fare(estimatedDuration);

        System.out.println(
                "Note: Final fare is calculated at actual checkout."
        );

        System.out.println("===============================================");
    }

    // Display the Final Parking Ticket
    public static void displayFinalTicket(
            BookingRecord booking,
            Payment payment,
            Duration actualDuration) {

        System.out.println("\n=============== FINAL PARKING TICKET ===============");

        System.out.println("Ticket ID        : FT-" + booking.booking_id);
        System.out.println("Booking ID       : " + booking.booking_id);
        System.out.println("Customer Name    : " + booking.customer_name);

        booking.vehicle.display_vehicle_details();

        System.out.println("Parking Slot     : " + slotLabel(booking));
        System.out.println("Scheduled Entry  : " + booking.scheduled_entry);
        System.out.println("Expected Exit    : " + booking.expected_exit);
        System.out.println("Actual Entry     : " + booking.actual_entry);
        System.out.println("Actual Exit      : " + booking.actual_exit);
        System.out.println("Status           : " + booking.status);

        System.out.println("---------------- FINAL FARE ----------------");

        payment.display_fare(actualDuration);

        System.out.println("====================================================");
    }

    // Generate the Car or Bike Slot Label
    private static String slotLabel(BookingRecord booking) {

        if (booking.vehicle.getVehicleType().equalsIgnoreCase("Car")) {
            return "C" + String.format("%02d", booking.selected_slot);
        }

        return "B" + String.format("%02d", booking.selected_slot);
    }
}

// ---- from Parking_Management_System.java ----
class Parking_Management_System {

    public static void startParkingSystem(Scanner sc) {

        try {

            ArrayList<Customer> users = new ArrayList<>();

            System.out.println("==========================================");
            System.out.println("      ADVANCE PARKING BOOKING APP");
            System.out.println("==========================================");

            Customer customer = authenticate(sc, users);

            if (customer == null) {
                System.out.println("Application Closed.");
                return;
            }

            ParkingSlot parkingSlot = new ParkingSlot();

            // Load saved booking records from MySQL
            Booking bookingManager;

            try {

                bookingManager = new Booking(parkingSlot);

            } catch (SQLException e) {

                System.out.println(
                        "Could not load database bookings: "
                                + e.getMessage()
                );

                return;
            }

            customer.display_customer_details();

            int choice;

            do {

                System.out.println(
                        "\n================ MAIN MENU ================"
                );

                System.out.println("1. Advance Booking");
                System.out.println("2. Check In Vehicle");
                System.out.println("3. Check Out Vehicle / Final Ticket");
                System.out.println("4. Cancel Booking");
                System.out.println("5. My Bookings / History");
                System.out.println("6. View Booking Ticket");
                System.out.println("7. Check Available Slots");
                System.out.println("8. Exit");

                System.out.println(
                        "==========================================="
                );

                choice = readInt(sc, "Enter Your Choice : ");

                switch (choice) {

                    case 1:
                        advanceBooking(
                                sc, customer, parkingSlot, bookingManager
                        );
                        break;

                    case 2:
                        System.out.print("Enter Booking ID (e.g. B1001): ");
                        bookingManager.checkIn(
                                customer, sc.nextLine().trim()
                        );
                        break;

                    case 3:
                        System.out.print("Enter Booking ID (e.g. B1001): ");
                        bookingManager.checkOut(
                                customer, sc.nextLine().trim()
                        );
                        break;

                    case 4:
                        System.out.print(
                                "Enter Booking ID to Cancel: "
                        );

                        bookingManager.Cancel_booking(
                                customer, sc.nextLine().trim()
                        );
                        break;

                    case 5:
                        bookingManager.displayHistory(customer);
                        break;

                    case 6:
                        System.out.print("Enter Booking ID: ");

                        bookingManager.displayTicket(
                                customer, sc.nextLine().trim()
                        );
                        break;

                    case 7:
                        viewAvailableSlots(
                                sc, parkingSlot, bookingManager
                        );
                        break;

                    case 8:
                        System.out.println(
                                "Thank You For Using Advance Parking Booking App."
                        );
                        break;

                    default:
                        System.out.println("Invalid Choice.");
                }

            } while (choice != 8);

        } catch (Exception e) {

            System.out.println(
                    "Application error: " + e.getMessage()
            );
        }
    }


    // Register or Login
    private static Customer authenticate(
            Scanner sc,
            ArrayList<Customer> users) {

        while (true) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = readInt(sc, "Choose: ");

            if (choice == 1) {

                System.out.print("Enter Customer Name: ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println(
                            "Customer name cannot be empty."
                    );
                    continue;
                }

                if (findCustomer(users, name) != null) {
                    System.out.println(
                            "This customer is already registered in this session."
                    );
                    continue;
                }

                System.out.print("Create Password: ");
                String password = sc.nextLine();

                if (password.isEmpty()) {
                    System.out.println(
                            "Password cannot be empty."
                    );
                    continue;
                }

                users.add(new Customer(name, password));

                System.out.println(
                        "Registration Successful. Please Login."
                );

            } else if (choice == 2) {

                System.out.print("Enter Customer Name: ");
                String name = sc.nextLine().trim();

                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                Customer customer = findCustomer(users, name);

                if (customer != null
                        && customer.checkPassword(password)) {

                    System.out.println("Login Successful.");
                    return customer;
                }

                System.out.println(
                        "Invalid login. Register first in this session."
                );

            } else if (choice == 3) {

                return null;

            } else {

                System.out.println("Invalid Choice.");
            }
        }
    }


    // Find Registered Customer
    private static Customer findCustomer(
            ArrayList<Customer> users,
            String name) {

        for (Customer customer : users) {

            if (customer.getCustName().equalsIgnoreCase(name)) {
                return customer;
            }
        }

        return null;
    }


    // Create Advance Booking
    private static void advanceBooking(
            Scanner sc,
            Customer customer,
            ParkingSlot ps,
            Booking bookingManager) {

        System.out.print("Enter Vehicle Number Plate: ");
        String numberPlate = sc.nextLine().trim();

        if (numberPlate.isEmpty()) {
            System.out.println("Number plate cannot be empty.");
            return;
        }

        System.out.println("1. Car");
        System.out.println("2. Bike");

        int typeChoice = readInt(sc, "Select Vehicle Type: ");

        String vehicleType;

        if (typeChoice == 1) {
            vehicleType = "Car";
        } else if (typeChoice == 2) {
            vehicleType = "Bike";
        } else {
            System.out.println("Only Car and Bike are allowed.");
            return;
        }

        LocalDate entryDate = readDate(
                sc, "Enter Entry Date (YYYY-MM-DD): "
        );

        LocalTime entryTime = readTime(
                sc, "Enter Entry Time (HH:MM): "
        );

        LocalDate exitDate = readDate(
                sc, "Enter Expected Exit Date (YYYY-MM-DD): "
        );

        LocalTime exitTime = readTime(
                sc, "Enter Expected Exit Time (HH:MM): "
        );

        LocalDateTime entry =
                LocalDateTime.of(entryDate, entryTime);

        LocalDateTime exit =
                LocalDateTime.of(exitDate, exitTime);

        if (!exit.isAfter(entry)) {
            System.out.println(
                    "Expected exit must be after entry."
            );
            return;
        }

        if (entry.isBefore(LocalDateTime.now().minusMinutes(1))) {
            System.out.println(
                    "Entry date/time cannot be in the past."
            );
            return;
        }

        Vehicle vehicle;

        try {

            vehicle = new Vehicle(numberPlate, vehicleType);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
            return;
        }

        // Display Available Slots
        if (vehicleType.equals("Car")) {

            ps.car_parking_slot(
                    entry, exit, bookingManager.getAllBookings()
            );

        } else {

            ps.bike_parking_slot(
                    entry, exit, bookingManager.getAllBookings()
            );
        }

        int selectedSlot = readInt(
                sc, "Select Slot Number (1-25): "
        );

        BookingRecord record = bookingManager.Advance_booking(
                customer,
                vehicle,
                selectedSlot,
                entry,
                exit
        );

        if (record != null) {
            ParkingTicket.displayBookingTicket(record);
        }
    }


    // Display Slots for a Selected Time
    private static void viewAvailableSlots(
            Scanner sc,
            ParkingSlot ps,
            Booking bookingManager) {

        System.out.println("1. Car");
        System.out.println("2. Bike");

        int typeChoice = readInt(sc, "Select Vehicle Type: ");

        if (typeChoice != 1 && typeChoice != 2) {
            System.out.println("Only Car and Bike are allowed.");
            return;
        }

        LocalDate entryDate = readDate(
                sc, "Enter Entry Date (YYYY-MM-DD): "
        );

        LocalTime entryTime = readTime(
                sc, "Enter Entry Time (HH:MM): "
        );

        LocalDate exitDate = readDate(
                sc, "Enter Exit Date (YYYY-MM-DD): "
        );

        LocalTime exitTime = readTime(
                sc, "Enter Exit Time (HH:MM): "
        );

        LocalDateTime entry =
                LocalDateTime.of(entryDate, entryTime);

        LocalDateTime exit =
                LocalDateTime.of(exitDate, exitTime);

        if (!exit.isAfter(entry)) {
            System.out.println(
                    "Exit must be after entry."
            );
            return;
        }

        if (typeChoice == 1) {

            ps.car_parking_slot(
                    entry, exit, bookingManager.getAllBookings()
            );

        } else {

            ps.bike_parking_slot(
                    entry, exit, bookingManager.getAllBookings()
            );
        }
    }


    // Read an Integer Safely
    private static int readInt(
            Scanner sc,
            String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please Enter a Valid Number."
                );
            }
        }
    }


    // Read a Valid Date
    private static LocalDate readDate(
            Scanner sc,
            String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine().trim();

            try {

                return LocalDate.parse(input);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid Date. Example: 2026-10-25"
                );
            }
        }
    }


    // Read a Valid Time
    private static LocalTime readTime(
            Scanner sc,
            String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine().trim();

            try {

                return LocalTime.parse(input);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid Time. Example: 10:30 or 15:00"
                );
            }
        }
    }
}

// ---- from DatabaseTest.java ----
class DatabaseTest {

    static void run() {

        try (Connection con =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "Database Connected Successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database Connection Failed."
            );

            e.printStackTrace();
        }
    }
}

// ======================================================================
// MODULE: FOR KIDS
// ======================================================================

// ---- from KidsModule.java ----
class KidsModule {

    public static final Scanner sc = ProjectHub.sc;
    private static final Random rand = new Random();

    public static void showScore(int score, int total) {
        System.out.println("\nYour score: " + score + " / " + total);
        if (score == total) {
            System.out.println("Perfect! You are a superstar! \u2605");
        } else if (score >= total / 2) {
            System.out.println("Great job! Keep going!");
        } else {
            System.out.println("Nice try! Practice makes perfect.");
        }
    }

    public static void numberGuess() {
        int secret = rand.nextInt(20) + 1;
        int attempts = 0;
        int guess;

        System.out.println("\n--- Number Guessing ---");
        System.out.println("I am thinking of a number between 1 and 20.");

        do {
            System.out.print("Your guess: ");
            guess = sc.nextInt();
            sc.nextLine();
            attempts++;

            if (guess < secret) {
                System.out.println("Too low! Try a bigger number.");
            } else if (guess > secret) {
                System.out.println("Too high! Try a smaller number.");
            }
        } while (guess != secret);

        System.out.println("Hooray! You got it in " + attempts + " tries! \uD83C\uDF89");
    }

    public static void mathQuiz() {
        int total = 5;
        int score = 0;

        System.out.println("\n--- Math Quiz ---");
        System.out.println("Answer " + total + " questions!");

        for (int i = 1; i <= total; i++) {
            int a = rand.nextInt(10) + 1;
            int b = rand.nextInt(10) + 1;
            int op = rand.nextInt(3);
            int correct;
            char symbol;

            if (op == 0) {
                correct = a + b;
                symbol = '+';
            } else if (op == 1) {
                if (a < b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                correct = a - b;
                symbol = '-';
            } else {
                correct = a * b;
                symbol = '*';
            }

            System.out.print("Q" + i + ": " + a + " " + symbol + " " + b + " = ");
            int answer = sc.nextInt();
            sc.nextLine();

            if (answer == correct) {
                System.out.println("Correct! \u2713");
                score++;
            } else {
                System.out.println("Not quite. The answer is " + correct + ".");
            }
        }

        showScore(score, total);
    }

    public static void rockPaperScissors() {
        int rounds = 5;
        int wins = 0, losses = 0, draws = 0;

        System.out.println("\n--- Rock Paper Scissors ---");
        System.out.println("Best of " + rounds + " rounds!");

        for (int r = 1; r <= rounds; r++) {
            System.out.println("\nRound " + r);
            System.out.println("1. Rock   2. Paper   3. Scissors");

            System.out.print("Your pick (1-3): ");
            int player = sc.nextInt();
            sc.nextLine();
            while (player < 1 || player > 3) {
                System.out.println("Please pick 1, 2 or 3.");
                System.out.print("Your pick (1-3): ");
                player = sc.nextInt();
                sc.nextLine();
            }
            int computer = rand.nextInt(3) + 1;

            System.out.println("You: " + moveName(player) + " | Computer: " + moveName(computer));

            if (player == computer) {
                System.out.println("It's a draw!");
                draws++;
            } else if ((player == 1 && computer == 3)
                    || (player == 2 && computer == 1)
                    || (player == 3 && computer == 2)) {
                System.out.println("You win this round!");
                wins++;
            } else {
                System.out.println("Computer wins this round.");
                losses++;
            }
        }

        System.out.println("\nWins: " + wins + " | Losses: " + losses + " | Draws: " + draws);
        if (wins > losses) {
            System.out.println("You are the champion! \uD83C\uDFC6");
        } else if (wins < losses) {
            System.out.println("Computer won this time. Try again!");
        } else {
            System.out.println("It's a tie overall!");
        }
    }

    private static String moveName(int move) {
        switch (move) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
            default:
                return "Unknown";
        }
    }

    public static void wordScramble() {
        String[] words = {"apple", "tiger", "banana", "elephant", "school",
                          "monkey", "orange", "rabbit", "garden", "pencil"};
        String word = words[rand.nextInt(words.length)];
        String scrambled = shuffleWord(word);

        System.out.println("\n--- Word Scramble ---");
        System.out.println("Unscramble this word: " + scrambled.toUpperCase());

        int maxTries = 3;
        for (int t = 1; t <= maxTries; t++) {
            System.out.print("Your answer (" + t + "/" + maxTries + "): ");
            String guess = sc.nextLine().trim();

            if (guess.equalsIgnoreCase(word)) {
                System.out.println("Awesome! The word is " + word + ". \uD83C\uDF89");
                return;
            }
            System.out.println("Not quite, try again!");
        }

        System.out.println("The correct word was: " + word);
    }

    private static String shuffleWord(String word) {
        char[] letters = word.toCharArray();
        String result;

        do {
            for (int i = letters.length - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                char temp = letters[i];
                letters[i] = letters[j];
                letters[j] = temp;
            }
            result = new String(letters);
        } while (result.equals(word));

        return result;
    }

    public static void oddOneOut() {
        ArrayList<String[]> questions = new ArrayList<>();
        questions.add(new String[]{"Dog", "Cat", "Cow", "Car", "Car"});
        questions.add(new String[]{"Apple", "Banana", "Mango", "Chair", "Chair"});
        questions.add(new String[]{"Red", "Blue", "Green", "Table", "Table"});
        questions.add(new String[]{"Sparrow", "Eagle", "Parrot", "Fish", "Fish"});
        questions.add(new String[]{"Pen", "Pencil", "Eraser", "Banana", "Banana"});

        Collections.shuffle(questions);

        int total = 3;
        int score = 0;

        System.out.println("\n--- Odd One Out ---");
        System.out.println("Type the word that does not belong.");

        for (int i = 0; i < total; i++) {
            String[] q = questions.get(i);
            String odd = q[4];

            ArrayList<String> options = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                options.add(q[k]);
            }
            Collections.shuffle(options);

            System.out.println("\nQ" + (i + 1) + ": " + String.join(", ", options));
            System.out.print("Odd one out: ");
            String answer = sc.nextLine().trim();

            if (answer.equalsIgnoreCase(odd)) {
                System.out.println("Correct! \u2713");
                score++;
            } else {
                System.out.println("Not quite. The odd one was " + odd + ".");
            }
        }

        showScore(score, total);
    }
}

// ---- from kidsMenu.java ----
class KidsMenu {

    static void run() {
        kidsMenu();
    }

    public static void kidsMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== FOR KIDS =====");
            System.out.println("1. Number Guessing");
            System.out.println("2. Math Quiz");
            System.out.println("3. Rock Paper Scissors");
            System.out.println("4. Word Scramble");
            System.out.println("5. Odd One Out");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice (1-6): ");
            int choice = KidsModule.sc.nextInt();
            KidsModule.sc.nextLine();

            switch (choice) {
                case 1:
                    KidsModule.numberGuess();
                    break;
                case 2:
                    KidsModule.mathQuiz();
                    break;
                case 3:
                    KidsModule.rockPaperScissors();
                    break;
                case 4:
                    KidsModule.wordScramble();
                    break;
                case 5:
                    KidsModule.oddOneOut();
                    break;
                case 6:
                    System.out.println("Going back to Main Menu...");
                    return;
                default:
                    System.out.println("Oops! Please choose a number from 1 to 6.");
                    continue;
            }
        }

        System.out.println("Thanks for playing! Returning to Main Menu...");
    }
}

// ======================================================================
// MODULE: TOOLS & UTILITIES
// ======================================================================

// ---- from ToolsAndUtilities.java ----
class CalculatorTool {

    void use(Scanner sc) {
        System.out.println("\n--- Calculator ---");
        System.out.println(" 1. Addition (+)");
        System.out.println(" 2. Subtraction (-)");
        System.out.println(" 3. Multiplication (x)");
        System.out.println(" 4. Division (/)");
        System.out.println(" 5. Modulus / Remainder (%)");
        System.out.println(" 6. Square Root");
        System.out.println(" 7. Cube Root");
        System.out.println(" 8. Power / Exponent (first number ^ second number)");
        System.out.println(" 9. Square and Cube of a number");
        System.out.println("10. Percentage (first number % of second number)");
        System.out.println("11. Factorial");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        double a = 0;
        double b = 0;

        if ((choice >= 1 && choice <= 5) || choice == 8 || choice == 10) {
            System.out.print("Enter first number : ");
            a = sc.nextDouble();
            System.out.print("Enter second number : ");
            b = sc.nextDouble();
        } else if (choice == 6 || choice == 7 || choice == 9 || choice == 11) {
            System.out.print("Enter a number : ");
            a = sc.nextDouble();
        }

        switch (choice) {
            case 1:
                System.out.println("Result : " + (a + b));
                break;
            case 2:
                System.out.println("Result : " + (a - b));
                break;
            case 3:
                System.out.println("Result : " + (a * b));
                break;
            case 4:
                if (b == 0) {
                    System.out.println("Cannot divide by zero!");
                } else {
                    System.out.println("Result : " + (a / b));
                }
                break;
            case 5:
                if (b == 0) {
                    System.out.println("Cannot divide by zero!");
                } else {
                    System.out.println("Remainder : " + (a % b));
                }
                break;
            case 6:
                if (a < 0) {
                    System.out.println("Square root of a negative number is not possible!");
                } else {
                    System.out.println("Square Root : " + Math.sqrt(a));
                }
                break;
            case 7:
                System.out.println("Cube Root : " + Math.cbrt(a));
                break;
            case 8:
                System.out.println("Result : " + Math.pow(a, b));
                break;
            case 9:
                System.out.println("Square : " + (a * a));
                System.out.println("Cube   : " + (a * a * a));
                break;
            case 10:
                System.out.println(a + "% of " + b + " = " + (a * b / 100));
                break;
            case 11:
                int n = (int) a;
                if (n < 0 || n > 20) {
                    System.out.println("Enter a whole number from 0 to 20!");
                } else {
                    long fact = 1;
                    for (int i = 1; i <= n; i++) {
                        fact = fact * i;
                    }
                    System.out.println("Factorial : " + fact);
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}

class TemperatureTool {

    void use(Scanner sc) {
        System.out.println("\n--- Temperature Convertor ---");
        System.out.println(" 1. Celsius to Fahrenheit");
        System.out.println(" 2. Fahrenheit to Celsius");
        System.out.println(" 3. Celsius to Kelvin");
        System.out.println(" 4. Kelvin to Celsius");
        System.out.println(" 5. Fahrenheit to Kelvin");
        System.out.println(" 6. Kelvin to Fahrenheit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.print("Enter temperature : ");
        double t = sc.nextDouble();

        double result = 0;
        String unit = "";

        switch (choice) {
            case 1:
                result = t * 9 / 5 + 32;
                unit = "F";
                break;
            case 2:
                result = (t - 32) * 5 / 9;
                unit = "C";
                break;
            case 3:
                result = t + 273.15;
                unit = "K";
                break;
            case 4:
                result = t - 273.15;
                unit = "C";
                break;
            case 5:
                result = (t - 32) * 5 / 9 + 273.15;
                unit = "K";
                break;
            case 6:
                result = (t - 273.15) * 9 / 5 + 32;
                unit = "F";
                break;
        }

        System.out.println("Result : " + Math.round(result * 100) / 100.0 + " " + unit);
    }
}

class TimeTool {

    void use(Scanner sc) {
        System.out.println("\n--- Time ---");
        System.out.println(" 1. Current Time and Date");
        System.out.println(" 2. Date and Time After / Before Some Days");
        System.out.println(" 3. Stopwatch");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Current Date and Time : " + new java.util.Date());

        } else if (choice == 2) {
            System.out.print("1. After   2. Before : ");
            int type = sc.nextInt();
            System.out.print("Enter number of days : ");
            int days = sc.nextInt();

            long now = System.currentTimeMillis();
            long change = days * 24L * 60 * 60 * 1000;

            if (type == 1) {
                System.out.println("Date and Time : " + new java.util.Date(now + change));
            } else if (type == 2) {
                System.out.println("Date and Time : " + new java.util.Date(now - change));
            } else {
                System.out.println("Invalid choice!");
            }

        } else if (choice == 3) {
            System.out.print("Type start and press Enter : ");
            sc.next();
            long start = System.currentTimeMillis();

            System.out.print("Type stop and press Enter  : ");
            sc.next();
            long end = System.currentTimeMillis();

            System.out.println("Elapsed Time : " + (end - start) / 1000.0 + " seconds");

        } else {
            System.out.println("Invalid choice!");
        }
    }
}

class CurrencyTool {
    // Approximate rates - change them whenever you like
    double usdToInr = 94.0;
    double eurToInr = 110.0;
    double gbpToInr = 127.0;

    void use(Scanner sc) {
        System.out.println("\n--- Currency Convertor ---");
        System.out.println(" 1. Indian Rupee to US Dollar (INR -> USD)");
        System.out.println(" 2. US Dollar to Indian Rupee (USD -> INR)");
        System.out.println(" 3. INR to Euro (EUR)");
        System.out.println(" 4. Euro to INR");
        System.out.println(" 5. INR to British Pound (GBP)");
        System.out.println(" 6. British Pound to INR");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.print("Enter amount : ");
        double amount = sc.nextDouble();

        double result = 0;
        String unit = "";

        switch (choice) {
            case 1:
                result = amount / usdToInr;
                unit = "USD";
                break;
            case 2:
                result = amount * usdToInr;
                unit = "INR";
                break;
            case 3:
                result = amount / eurToInr;
                unit = "EUR";
                break;
            case 4:
                result = amount * eurToInr;
                unit = "INR";
                break;
            case 5:
                result = amount / gbpToInr;
                unit = "GBP";
                break;
            case 6:
                result = amount * gbpToInr;
                unit = "INR";
                break;
        }

        System.out.println("Result : " + Math.round(result * 100) / 100.0 + " " + unit);
    }
}

class ToolsAndUtilities {
    static void run() {

        Scanner sc = ProjectHub.sc;

        CalculatorTool calculator = new CalculatorTool();
        TemperatureTool temperature = new TemperatureTool();
        TimeTool time = new TimeTool();
        CurrencyTool currency = new CurrencyTool();

        int choice = 0;

        while (choice != 5) {
            System.out.println("\n========= TOOLS & UTILITIES =========");
            System.out.println(" 1. Calculator");
            System.out.println(" 2. Temperature Convertor");
            System.out.println(" 3. Time");
            System.out.println(" 4. Currency Convertor");
            System.out.println(" 5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    calculator.use(sc);
                    break;
                case 2:
                    temperature.use(sc);
                    break;
                case 3:
                    time.use(sc);
                    break;
                case 4:
                    currency.use(sc);
                    break;
                case 5:
                    System.out.println("Going back to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number from 1 to 5.");
            }
        }
    }
}

// ======================================================================
// MODULE: SHOPPING CART
// ======================================================================

// ---- from PaymentMethod.java ----
interface PaymentMethod {

    // Process the Selected Payment Method
    boolean processPayment(double amount, Scanner sc);

    // Return Payment Method Name
    String getMethodName();
}

// ---- from CashOnDelivery.java ----
class CashOnDelivery implements PaymentMethod {

    // Process Cash on Delivery
    @Override
    public boolean processPayment(double amount, Scanner sc) {

        System.out.println("\n========== CASH ON DELIVERY ==========");

        System.out.println(
                "Order Confirmed. Payment Will Be Collected on Delivery."
        );

        System.out.println("Amount Due : Rs. " + amount);

        return true;
    }

    // Return Method Name
    @Override
    public String getMethodName() {
        return "Cash on Delivery";
    }
}

// ---- from OnlinePayment.java ----
class OnlinePayment implements PaymentMethod {

    // Simulate Online Payment
    @Override
    public boolean processPayment(double amount, Scanner sc) {

        System.out.println("\n========== ONLINE PAYMENT ==========");

        System.out.println("Amount to Pay : Rs. " + amount);

        System.out.print("Confirm Demo Payment? (Y/N) : ");

        String choice = sc.nextLine().trim();

        if (choice.equalsIgnoreCase("Y")) {

            System.out.println("Demo Payment Successful.");
            return true;
        }

        System.out.println("Payment Cancelled.");
        return false;
    }

    // Return Method Name
    @Override
    public String getMethodName() {
        return "Online Payment (Demo)";
    }
}

// ---- from Product.java ----
class Product {

    private final int productId;
    private final String productName;
    private final double price;
    private int availableQuantity;

    // Create Product Object
    public Product(int productId, String productName,
                   double price, int availableQuantity) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    // Get Product ID
    public int getProductId() {
        return productId;
    }

    // Get Product Name
    public String getProductName() {
        return productName;
    }

    // Get Product Price
    public double getPrice() {
        return price;
    }

    // Get Available Quantity
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    // Reduce Stock When Product Is Added to Cart
    public boolean reduceStock(int quantity) {

        if (quantity <= 0 || quantity > availableQuantity) {
            return false;
        }

        availableQuantity -= quantity;
        return true;
    }

    // Restore Stock When Product Is Cancelled
    public void addStock(int quantity) {

        if (quantity > 0) {
            availableQuantity += quantity;
        }
    }

    // Display Product Details
    public void displayProductDetails() {

        System.out.println("-----------------------------------");
        System.out.println("Product ID         : " + productId);
        System.out.println("Product Name       : " + productName);
        System.out.println("Product Price      : Rs. " + price);
        System.out.println("Available Quantity : " + availableQuantity);
    }
}

// ---- from CartItem.java ----
class CartItem {

    private final Product product;
    private int quantity;

    // Create Cart Item
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Get Product
    public Product getProduct() {
        return product;
    }

    // Get Selected Quantity
    public int getQuantity() {
        return quantity;
    }

    // Increase Quantity
    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    // Calculate Individual Product Total
    public double calculateItemTotal() {
        return product.getPrice() * quantity;
    }

    // Display Selected Product Details
    public void displayItemDetails() {

        System.out.println("Product ID    : "
                + product.getProductId());

        System.out.println("Product Name  : "
                + product.getProductName());

        System.out.println("Quantity      : " + quantity);

        System.out.println("Individual Price : Rs. "
                + product.getPrice());

        System.out.println("Item Total    : Rs. "
                + calculateItemTotal());

        System.out.println("-----------------------------------");
    }
}

// ---- from Cart.java ----
class Cart {

    private final ArrayList<CartItem> cartItems =
            new ArrayList<>();

    // Add Product to Cart and Reduce Available Stock
    public boolean addProduct(Product product, int quantity) {

        // Validate Product
        if (product == null) {
            System.out.println("Product Not Found.");
            return false;
        }

        // Validate Quantity
        if (quantity <= 0) {
            System.out.println(
                    "Quantity Must Be Greater Than Zero."
            );
            return false;
        }

        // Check Available Stock
        if (quantity > product.getAvailableQuantity()) {

            System.out.println("Insufficient Stock.");
            System.out.println(
                    "Available Quantity : "
                            + product.getAvailableQuantity()
            );

            return false;
        }

        // Reduce Stock Immediately
        if (!product.reduceStock(quantity)) {
            System.out.println("Unable to Update Stock.");
            return false;
        }

        // Check Whether Product Already Exists in Cart
        for (CartItem item : cartItems) {

            if (item.getProduct().getProductId()
                    == product.getProductId()) {

                item.increaseQuantity(quantity);

                System.out.println(
                        "Product Quantity Updated Successfully."
                );

                System.out.println(
                        "Remaining Stock : "
                                + product.getAvailableQuantity()
                );

                return true;
            }
        }

        // Add New Product to Cart
        cartItems.add(new CartItem(product, quantity));

        System.out.println(
                "Product Added to Cart Successfully."
        );

        System.out.println(
                "Remaining Stock : "
                        + product.getAvailableQuantity()
        );

        return true;
    }

    // Cancel Product and Restore Its Stock
    public boolean removeProduct(int productId) {

        for (int i = 0; i < cartItems.size(); i++) {

            CartItem item = cartItems.get(i);

            if (item.getProduct().getProductId() == productId) {

                // Restore the Cancelled Product Quantity
                item.getProduct().addStock(item.getQuantity());

                // Remove Product from Cart
                cartItems.remove(i);

                System.out.println(
                        "Product Cancelled Successfully."
                );

                System.out.println(
                        "Restored Stock : "
                                + item.getProduct().getAvailableQuantity()
                );

                return true;
            }
        }

        System.out.println("Product ID Not Found in Cart.");
        return false;
    }

    // Display All Cart Items
    public void displayCart() {

        if (cartItems.isEmpty()) {
            System.out.println("Your Cart Is Empty.");
            return;
        }

        System.out.println("\n========== SHOPPING CART ==========");

        for (CartItem item : cartItems) {
            item.displayItemDetails();
        }

        System.out.println(
                "Total Bill : Rs. " + calculateTotal()
        );
    }

    // Calculate Total Bill
    public double calculateTotal() {

        double total = 0;

        for (CartItem item : cartItems) {
            total += item.calculateItemTotal();
        }

        return total;
    }

    // Check Whether Cart Is Empty
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Restore All Remaining Cart Stock If Order Is Cancelled
    public void releaseAllStock() {

        for (CartItem item : cartItems) {
            item.getProduct().addStock(item.getQuantity());
        }

        cartItems.clear();

        System.out.println(
                "Cart Cancelled. All Selected Stock Has Been Restored."
        );
    }

    // Clear Cart After Successful Order
    // Do Not Restore Stock Here
    public void clearCart() {
        cartItems.clear();
    }
}

// ---- from User.java ----
class User {

    private final String username;
    private final String password;

    // Create User Object
    public User(String username, String password) {
        this.username = username.trim();
        this.password = password;
    }

    // Get Username
    public String getUsername() {
        return username;
    }

    // Validate Password
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}

// ---- from Main_Shopping.java ----
class Main_Shopping {

    static void run() {

        Scanner sc = ProjectHub.sc;

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();

        // Sample Product Inventory
        products.add(new Product(101, "Mouse", 499, 10));
        products.add(new Product(102, "Keyboard", 999, 8));
        products.add(new Product(103, "Headphones", 1499, 5));
        products.add(new Product(104, "USB Cable", 199, 20));
        products.add(new Product(105, "Laptop Stand", 799, 6));

        products.add(new Product(106, "Laptop", 49999, 5));
        products.add(new Product(107, "Smartphone", 15999, 10));
        products.add(new Product(108, "Tablet", 12999, 7));
        products.add(new Product(109, "Monitor", 8999, 6));
        products.add(new Product(110, "Webcam", 2499, 8));

        products.add(new Product(111, "Speakers", 1799, 12));
        products.add(new Product(112, "Power Bank", 1499, 15));
        products.add(new Product(113, "Charger", 699, 20));
        products.add(new Product(114, "Wireless Mouse", 799, 14));
        products.add(new Product(115, "Mechanical Keyboard", 2499, 6));

        products.add(new Product(116, "External SSD", 5999, 5));
        products.add(new Product(117, "Pen Drive", 499, 25));
        products.add(new Product(118, "Router", 1999, 9));
        products.add(new Product(119, "Microphone", 2999, 7));
        products.add(new Product(120, "Gaming Controller", 3499, 8));

        System.out.println("======================================");
        System.out.println("       SHOPPING MANAGEMENT APP");
        System.out.println("======================================");

        // Login or Register
        User user = authenticate(sc, users);

        if (user == null) {
            System.out.println("Application Closed.");

            return;
        }

        System.out.println(
                "\nWelcome To Our System, " + user.getUsername() + "!"
        );

        Cart cart = new Cart();

        // Display Products and Accept User Input
        while (true) {

            displayProducts(products);

            System.out.println();

            System.out.print(
                    "Enter Product Name (NULL to Finish Shopping): "
            );

            String productName = sc.nextLine().trim();

            // Finish Shopping
            if (productName.equalsIgnoreCase("NULL")) {
                break;
            }

            // Find Selected Product
            Product selectedProduct =
                    findProductByName(products, productName);

            if (selectedProduct == null) {
                System.out.println("Product Not Found.");
                continue;
            }

            // Check Whether Product Is Available
            if (selectedProduct.getAvailableQuantity() == 0) {
                System.out.println("This Product Is Out of Stock.");
                continue;
            }

            // Enter Selected Quantity
            int quantity = readInt(sc, "Enter Quantity : ");

            if (quantity <= 0) {
                System.out.println(
                        "Quantity Must Be Greater Than Zero."
                );
                continue;
            }

            // Add Product and Reduce Stock Immediately
            boolean added = cart.addProduct(
                    selectedProduct, quantity
            );

            if (added) {

                System.out.println("\nSelected Product Details:");

                selectedProduct.displayProductDetails();

                System.out.println(
                        "Selected Quantity : " + quantity
                );

                System.out.println(
                        "Selected Item Total : Rs. "
                                + selectedProduct.getPrice() * quantity
                );
            }

            System.out.print("\nAdd Another Product? (Y/N) : ");

            String more = sc.nextLine().trim();

            if (more.equalsIgnoreCase("N")) {
                break;
            }
        }

        // Check Whether Cart Is Empty
        if (cart.isEmpty()) {

            System.out.println("Your Cart Is Empty.");
            System.out.println("Thank You For Visiting.");

            return;
        }

        // Display Cart Before Cancellation
        cart.displayCart();

        // Cancel Products If Required
        while (!cart.isEmpty()) {

            System.out.print(
                    "\nDo You Want to Cancel Any Item? (Y/N) : "
            );

            String choice = sc.nextLine().trim();

            if (!choice.equalsIgnoreCase("Y")) {
                break;
            }

            cart.displayCart();

            int productId = readInt(
                    sc, "Enter Product ID to Cancel : "
            );

            cart.removeProduct(productId);

            if (cart.isEmpty()) {
                System.out.println("Your Cart Is Empty.");
                break;
            }

            cart.displayCart();
        }

        // Stop If All Items Were Cancelled
        if (cart.isEmpty()) {

            System.out.println("No Products to Purchase.");

            return;
        }

        // Select Payment Method
        System.out.println("\n========== PAYMENT METHOD ==========");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Online Payment");

        int paymentChoice = readInt(
                sc, "Select Payment Method : "
        );

        PaymentMethod paymentMethod;

        switch (paymentChoice) {

            case 1:
                paymentMethod = new CashOnDelivery();
                break;

            case 2:
                paymentMethod = new OnlinePayment();
                break;

            default:
                System.out.println("Invalid Payment Method.");

                // Restore Stock Because the Order Is Not Confirmed
                cart.releaseAllStock();

                return;
        }

        // Calculate Final Bill
        double total = cart.calculateTotal();

        // Process Payment
        boolean paymentSuccessful =
                paymentMethod.processPayment(total, sc);

        // Restore Stock If Payment Is Cancelled
        if (!paymentSuccessful) {

            System.out.println(
                    "Order Not Confirmed. Payment Was Cancelled."
            );

            cart.releaseAllStock();

            return;
        }

        // Stock Is Already Deducted. Do Not Deduct It Again.

        // Display Final Bill
        System.out.println("\n======================================");
        System.out.println("              FINAL BILL");
        System.out.println("======================================");

        cart.displayCart();

        System.out.println(
                "Customer Name : " + user.getUsername()
        );

        System.out.println(
                "Payment Method : " + paymentMethod.getMethodName()
        );

        System.out.println("Final Amount : Rs. " + total);
        System.out.println("Order Status : CONFIRMED");

        System.out.println("======================================");
        System.out.println("Thank You For Shopping!");

        // Clear Cart Without Restoring Stock
        cart.clearCart();

    }


    // Register or Login
    static User authenticate(
            Scanner sc, ArrayList<User> users) {

        while (true) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = readInt(sc, "Enter Choice : ");

            switch (choice) {

                case 1:

                    System.out.print("Enter Username : ");

                    String username = sc.nextLine().trim();

                    if (username.isEmpty()) {
                        System.out.println(
                                "Username Cannot Be Empty."
                        );
                        break;
                    }

                    if (findUser(users, username) != null) {
                        System.out.println(
                                "Username Already Exists."
                        );
                        break;
                    }

                    System.out.print("Create Password : ");

                    String password = sc.nextLine();

                    if (password.isEmpty()) {
                        System.out.println(
                                "Password Cannot Be Empty."
                        );
                        break;
                    }

                    users.add(new User(username, password));

                    System.out.println(
                            "Registration Successful. Please Login."
                    );

                    break;

                case 2:

                    System.out.print("Enter Username : ");

                    String loginName = sc.nextLine().trim();

                    System.out.print("Enter Password : ");

                    String loginPassword = sc.nextLine();

                    User user = findUser(users, loginName);

                    if (user != null
                            && user.checkPassword(loginPassword)) {

                        System.out.println("Login Successful.");
                        return user;
                    }

                    System.out.println(
                            "Invalid Username or Password."
                    );

                    break;

                case 3:
                    return null;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }


    // Find Registered User
    static User findUser(
            ArrayList<User> users, String username) {

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }


    // Display Available Products
    static void displayProducts(ArrayList<Product> products) {

        System.out.println("\n========== AVAILABLE PRODUCTS ==========");

        for (Product product : products) {
            product.displayProductDetails();
        }

        System.out.println("========================================");
    }


    // Find Product Using Product Name
    static Product findProductByName(
            ArrayList<Product> products, String productName) {

        for (Product product : products) {

            if (product.getProductName()
                    .equalsIgnoreCase(productName)) {

                return product;
            }
        }

        return null;
    }


    // Read Integer Without Crashing on Invalid Input
    static int readInt(Scanner sc, String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {

                System.out.println(
                        "Please Enter a Valid Number."
                );
            }
        }
    }
}

// ======================================================================
// MODULE: TYPING SPEED ENHANCER
// ======================================================================

// ---- from TypingSpeed.java ----
class TypingSpeed {

    static String[] easy = {
        "The sun is hot today.",
        "I like to eat apples and mangoes.",
        "My dog runs fast in the park.",
        "Please close the door slowly.",
        "We play cricket every Sunday.",
        "The cat sleeps on the soft sofa."
    };

    static String[] medium = {
        "Big Black Beetle Bite Big Black Bear!",
        "She sells sea shells by the sea shore!",
        "Peter Piper picked a peck of pickled peppers.",
        "Red lorry, yellow lorry, red lorry, yellow lorry.",
        "I scream, you scream, we all scream for ice cream!",
        "A proper copper coffee pot is hard to find."
    };

    static String[] hard = {
        "Order #4521 will arrive on 15th March at 9:30 AM, so please don't be late!",
        "The quick brown fox jumps over the lazy dog 25 times every morning.",
        "Java was released in 1995, and it is still used to build apps in 2026.",
        "Call 98765-43210 before 6:00 PM to get a 20% discount on all items.",
        "Programming is 10% writing code and 90% understanding why it doesn't work.",
        "Mr. Sharma paid Rs. 2,500 for 4 books, 3 pens and 1 bag on 12/08/2025."
    };

    static String getRank(double wpm) {
        if (wpm >= 60) {
            return "Pro";
        } else if (wpm >= 40) {
            return "Good";
        } else if (wpm >= 20) {
            return "Average";
        } else {
            return "Beginner";
        }
    }

    static void countdown() throws InterruptedException {
        System.out.println("Get ready...");
        for (int i = 3; i >= 1; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
        System.out.println("GO!");
    }

    // Prints ^ under every wrong or extra letter
    static void showMistakes(String original, String typed) {
        System.out.println("Sentence  : " + original);
        System.out.println("You typed : " + typed);
        System.out.print("Mistakes  : ");
        for (int i = 0; i < typed.length(); i++) {
            if (i < original.length() && typed.charAt(i) == original.charAt(i)) {
                System.out.print(" ");
            } else {
                System.out.print("^");
            }
        }
        System.out.println();
    }

    // Plays one round and returns the WPM (0 if the round is not counted)
    static double playRound(Scanner sc, String original, int timeLimit) {
        System.out.println();
        System.out.println("===================================");
        System.out.println(original);
        System.out.println("===================================");
        System.out.print("Type here! --> ");

        long startTime = System.nanoTime();
        String typed = sc.nextLine();
        long endTime = System.nanoTime();

        double timeTaken = (endTime - startTime) / 1_000_000_000.0;
        int words = original.split(" ").length;
        int maxLength = Math.max(original.length(), typed.length());
        int errors = 0;

        for (int i = 0; i < maxLength; i++) {
            if (i >= original.length() || i >= typed.length() || original.charAt(i) != typed.charAt(i)) {
                errors++;
            }
        }

        double accuracy = ((double) (maxLength - errors) / maxLength) * 100;
        double wpm = (words / timeTaken) * 60;

        System.out.println();
        showMistakes(original, typed);
        System.out.printf("Time Taken : %.2f seconds%n", timeTaken);
        System.out.println("Words      : " + words);
        System.out.println("Errors     : " + errors);
        System.out.printf("Accuracy   : %.2f%%%n", accuracy);

        if (timeTaken > timeLimit) {
            System.out.println("Time's up! No score for this round.");
            return 0;
        }
        if (accuracy < 50) {
            System.out.println("Too many mistakes! No score for this round.");
            return 0;
        }

        System.out.printf("WPM        : %.2f%n", wpm);
        System.out.println("Rank       : " + getRank(wpm));
        return wpm;
    }

    static void run() throws InterruptedException {

        Scanner sc = ProjectHub.sc;
        Random random = new Random();
        double highScore = 0;
        String again = "yes";

        System.out.println("+==============================================+");
        System.out.println("|  >> SYSTEM INITIALIZED                       |");
        System.out.println("|  >> WRITE & SPEED                            |");
        System.out.println("|  >> TYPING SPEED ANALYZER v1.0               |");
        System.out.println("|  >> READY TO TEST YOUR SKILLS...             |");
        System.out.println("+==============================================+");

        while (again.equalsIgnoreCase("yes")) {

            System.out.print("\nEnter your name : ");
            String name = sc.nextLine();

            System.out.println("1. Easy   2. Medium   3. Hard");
            System.out.print("Select level : ");
            String level = sc.nextLine().trim();

            String[] sentences = easy;
            int timeLimit = 20;

            if (level.equals("2")) {
                sentences = medium;
                timeLimit = 30;
            } else if (level.equals("3")) {
                sentences = hard;
                timeLimit = 40;
            }

            int start = random.nextInt(sentences.length);
            double total = 0;
            double best = 0;

            for (int round = 1; round <= 3; round++) {
                System.out.println("\n##### ROUND " + round + " of 3 #####");
                countdown();

                double wpm = playRound(sc, sentences[(start + round) % sentences.length], timeLimit);

                total = total + wpm;
                if (wpm > best) {
                    best = wpm;
                }
            }

            double average = total / 3;

            System.out.println("\n===== FINAL RESULT =====");
            System.out.println("Player      : " + name);
            System.out.printf("Average WPM : %.2f%n", average);
            System.out.printf("Best WPM    : %.2f%n", best);
            System.out.println("Rank        : " + getRank(average));

            if (average > highScore) {
                highScore = average;
                System.out.println("*** NEW HIGH SCORE! ***");
            }

            System.out.print("\nPlay again? (yes/no) : ");
            again = sc.nextLine().trim();
        }

        System.out.println("Thank you for playing! Goodbye!");

    }
}