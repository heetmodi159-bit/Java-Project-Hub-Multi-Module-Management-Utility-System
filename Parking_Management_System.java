package Advance_Parking_System;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parking_Management_System {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Customer> users = new ArrayList<>();

        System.out.println("==========================================");
        System.out.println("      ADVANCE PARKING BOOKING APP");
        System.out.println("==========================================");

        Customer customer = authenticate(sc, users);

        if (customer == null) {
            System.out.println("Application Closed.");
            sc.close();
            return;
        }

        ParkingSlot parkingSlot = new ParkingSlot();
        Booking bookingManager = new Booking(parkingSlot);

        customer.display_customer_details();

        int choice;

        do {
            System.out.println("\n================ MAIN MENU ================");
            System.out.println("1. Advance Booking");
            System.out.println("2. Check In Vehicle");
            System.out.println("3. Check Out Vehicle / Final Ticket");
            System.out.println("4. Cancel Booking");
            System.out.println("5. My Bookings / History");
            System.out.println("6. View Booking Ticket");
            System.out.println("7. Check Available Slots");
            System.out.println("8. Exit");
            System.out.println("===========================================");

            choice = readInt(sc, "Enter Your Choice : ");

            switch (choice) {

                case 1:
                    advanceBooking(
                            sc, customer, parkingSlot, bookingManager
                    );
                    break;

                case 2:
                    checkIn(sc, customer, bookingManager);
                    break;

                case 3:
                    checkOut(sc, customer, bookingManager);
                    break;

                case 4:
                    cancelBooking(sc, customer, bookingManager);
                    break;

                case 5:
                    bookingManager.displayHistory(customer);
                    break;

                case 6:
                    System.out.print("Enter Booking ID : ");
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
                    System.out.println(
                            "Invalid Choice. Please Try Again."
                    );
            }

        } while (choice != 8);

        sc.close();
    }


    // Register a new account or log in
    private static Customer authenticate(
            Scanner sc,
            ArrayList<Customer> users) {

        while (true) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = readInt(sc, "Choose : ");

            if (choice == 1) {

                System.out.print("Enter Customer Name : ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println(
                            "Customer name cannot be empty."
                    );
                    continue;
                }

                if (findCustomer(users, name) != null) {
                    System.out.println(
                            "This customer name is already registered."
                    );
                    continue;
                }

                System.out.print("Create Password : ");
                String password = sc.nextLine();

                if (password.isEmpty()) {
                    System.out.println(
                            "Password cannot be empty."
                    );
                    continue;
                }

                Customer newCustomer =
                        new Customer(name, password);

                users.add(newCustomer);

                System.out.println(
                        "Registration Successful. Please log in to continue."
                );

            } else if (choice == 2) {

                System.out.print("Enter Customer Name : ");
                String name = sc.nextLine().trim();

                System.out.print("Enter Password : ");
                String password = sc.nextLine();

                Customer customer = findCustomer(users, name);

                if (customer != null
                        && customer.checkPassword(password)) {

                    System.out.println("Login Successful.");
                    return customer;
                }

                System.out.println(
                        "Invalid login. Register an account first in this session."
                );

            } else if (choice == 3) {

                return null;

            } else {

                System.out.println("Invalid Choice.");
            }
        }
    }


    // Find a registered customer
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


    // Create an advance booking using user input
    private static void advanceBooking(
            Scanner sc,
            Customer customer,
            ParkingSlot ps,
            Booking bookingManager) {

        System.out.print("Enter Vehicle Number Plate : ");
        String numberPlate = sc.nextLine().trim();

        if (numberPlate.isEmpty()) {
            System.out.println(
                    "Number plate cannot be empty."
            );
            return;
        }

        System.out.println("1. Car");
        System.out.println("2. Bike");

        int typeChoice = readInt(sc, "Select Vehicle Type : ");

        String vehicleType;

        if (typeChoice == 1) {
            vehicleType = "Car";
        } else if (typeChoice == 2) {
            vehicleType = "Bike";
        } else {
            System.out.println(
                    "Only Car and Bike are allowed."
            );
            return;
        }

        LocalDate entryDate = readDate(
                sc, "Enter Entry Date (YYYY-MM-DD) : "
        );

        LocalTime entryTime = readTime(
                sc, "Enter Entry Time (HH:MM) : "
        );

        LocalDate exitDate = readDate(
                sc, "Enter Expected Exit Date (YYYY-MM-DD) : "
        );

        LocalTime exitTime = readTime(
                sc, "Enter Expected Exit Time (HH:MM) : "
        );

        LocalDateTime entry =
                LocalDateTime.of(entryDate, entryTime);

        LocalDateTime exit =
                LocalDateTime.of(exitDate, exitTime);

        if (!exit.isAfter(entry)) {
            System.out.println(
                    "Expected exit date/time must be after entry date/time."
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
            vehicle = new Vehicle(
                    numberPlate, vehicleType
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

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
                sc, "Select Slot Number : "
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


    // Check in a vehicle
    private static void checkIn(
            Scanner sc,
            Customer customer,
            Booking bookingManager) {

        System.out.print("Enter Booking ID : ");

        bookingManager.checkIn(
                customer, sc.nextLine().trim()
        );
    }


    // Check out a vehicle and generate the final ticket
    private static void checkOut(
            Scanner sc,
            Customer customer,
            Booking bookingManager) {

        System.out.print("Enter Booking ID : ");

        bookingManager.checkOut(
                customer, sc.nextLine().trim()
        );
    }


    // Cancel an existing booking
    private static void cancelBooking(
            Scanner sc,
            Customer customer,
            Booking bookingManager) {

        System.out.print("Enter Booking ID to Cancel : ");

        bookingManager.Cancel_booking(
                customer, sc.nextLine().trim()
        );
    }


    // View available parking slots for a selected time
    private static void viewAvailableSlots(
            Scanner sc,
            ParkingSlot ps,
            Booking bookingManager) {

        System.out.println("1. Car");
        System.out.println("2. Bike");

        int typeChoice = readInt(
                sc, "Select Vehicle Type : "
        );

        if (typeChoice != 1 && typeChoice != 2) {
            System.out.println(
                    "Only Car and Bike are allowed."
            );
            return;
        }

        LocalDate entryDate = readDate(
                sc, "Enter Entry Date (YYYY-MM-DD) : "
        );

        LocalTime entryTime = readTime(
                sc, "Enter Entry Time (HH:MM) : "
        );

        LocalDate exitDate = readDate(
                sc, "Enter Expected Exit Date (YYYY-MM-DD) : "
        );

        LocalTime exitTime = readTime(
                sc, "Enter Expected Exit Time (HH:MM) : "
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


    // Read an integer without crashing on invalid input
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


    // Read a valid date
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
                        "Invalid Date. Example: 2026-10-15"
                );
            }
        }
    }


    // Read a valid time
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