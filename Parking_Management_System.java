package Advance_Parking_System;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Parking_Management_System {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ParkingSlot ps = new ParkingSlot();
        Booking booking = new Booking(ps);

        System.out.println("========================================");
        System.out.println("     ADVANCE PARKING BOOKING APP");
        System.out.println("========================================");

        System.out.print("Enter Customer Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        Customer customer = new Customer(name, password);

        System.out.println();
        System.out.println("Welcome To Advance Parking Booking App");
        customer.display_customer_details();

        int choice;

        do {
            System.out.println();
            System.out.println("=============== MAIN MENU ===============");
            System.out.println("1. Advance Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Display Car Slots");
            System.out.println("4. Display Bike Slots");
            System.out.println("5. View Vehicle Details");
            System.out.println("6. Exit");
            System.out.println("=========================================");

            choice = readInt(sc, "Enter Your Choice : ");

            switch (choice) {

                case 1:
                    advanceBooking(sc, ps, booking);
                    break;

                case 2:
                    cancelBooking(sc, ps, booking);
                    break;

                case 3:
                    ps.car_parking_slot();
                    break;

                case 4:
                    ps.bike_parking_slot();
                    break;

                case 5:
                    ps.display_psv_details();
                    break;

                case 6:
                    System.out.println("Thank You For Using Our App.");
                    break;

                default:
                    System.out.println("Invalid Choice. Try Again.");
            }

        } while (choice != 6);

        sc.close();
    }


    // Advance Booking Using User Input
    static void advanceBooking(
            Scanner sc, ParkingSlot ps, Booking booking) {

        System.out.print("Enter Vehicle Number Plate : ");
        String number_plate = sc.nextLine().trim();

        if (findVehicle(ps, number_plate) != null) {
            System.out.println("This Vehicle Already Has a Booking.");
            return;
        }

        System.out.println("1. Car");
        System.out.println("2. Bike");

        int type = readInt(sc, "Select Vehicle Type : ");

        String vehicle_type;

        if (type == 1) {
            vehicle_type = "Car";
        }
        else if (type == 2) {
            vehicle_type = "Bike";
        }
        else {
            System.out.println("Invalid Vehicle Type.");
            return;
        }

        LocalDate date = readDate(sc);

        LocalTime entry_time = readTime(sc, "Enter Entry Time (HH:MM) : ");
        LocalTime exit_time = readTime(sc, "Enter Expected Exit Time (HH:MM) : ");

        while (!exit_time.isAfter(entry_time)) {
            System.out.println("Exit Time Must Be After Entry Time.");
            exit_time = readTime(sc, "Enter Expected Exit Time Again (HH:MM) : ");
        }

        Vehicle v = new Vehicle(number_plate, vehicle_type);

        if (vehicle_type.equalsIgnoreCase("Car")) {
            ps.car_parking_slot();
        }
        else {
            ps.bike_parking_slot();
        }

        int selected_slot = readInt(sc, "Select Parking Slot Number : ");

        int previous_count;

        if (vehicle_type.equalsIgnoreCase("Car")) {
            previous_count = ps.car_slots.size();
        }
        else {
            previous_count = ps.bike_slots.size();
        }

        booking.Advance_booking(v, selected_slot);

        int current_count;

        if (vehicle_type.equalsIgnoreCase("Car")) {
            current_count = ps.car_slots.size();
        }
        else {
            current_count = ps.bike_slots.size();
        }

        if (current_count == previous_count) {
            return;
        }

        booking.booking_date = date;
        booking.entry_time = entry_time;
        booking.exit_time = exit_time;

        System.out.println();
        System.out.println("========== BOOKING DETAILS ==========");
        System.out.println("Vehicle Number Plate : " + v.number_plate);
        System.out.println("Vehicle Type         : " + v.vehicle_type);
        booking.display_booking_time();

        Payment payment = new Payment();
        payment.calculate_fare(v);

        System.out.println();
        System.out.println("========== PAYMENT DETAILS ==========");
        payment.display_fare();
    }


    // Cancel Booking Using Vehicle Number Plate
    static void cancelBooking(
            Scanner sc, ParkingSlot ps, Booking booking) {

        System.out.print("Enter Vehicle Number Plate : ");
        String number_plate = sc.nextLine().trim();

        Vehicle v = findVehicle(ps, number_plate);

        if (v == null) {
            System.out.println("Booking Not Found.");
            return;
        }

        booking.Cancel_booking(v);
    }


    // Find a Booked Vehicle Using Its Number Plate
    static Vehicle findVehicle(ParkingSlot ps, String number_plate) {

        for (Vehicle v : ps.car_slots) {
            if (v.number_plate.equalsIgnoreCase(number_plate)) {
                return v;
            }
        }

        for (Vehicle v : ps.bike_slots) {
            if (v.number_plate.equalsIgnoreCase(number_plate)) {
                return v;
            }
        }

        return null;
    }


    // Read a Valid Integer From User
    static int readInt(Scanner sc, String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Please Enter a Valid Number.");
            }
        }
    }


    // Read Booking Date From User
    static LocalDate readDate(Scanner sc) {

        while (true) {
            System.out.print("Enter Parking Date (YYYY-MM-DD) : ");
            String input = sc.nextLine().trim();

            try {
                LocalDate date = LocalDate.parse(input);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Past Dates Are Not Allowed.");
                }
                else {
                    return date;
                }
            }
            catch (DateTimeParseException e) {
                System.out.println("Invalid Date. Example: 2026-10-15");
            }
        }
    }


    // Read Time From User
    static LocalTime readTime(Scanner sc, String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            try {
                return LocalTime.parse(input);
            }
            catch (DateTimeParseException e) {
                System.out.println("Invalid Time. Example: 10:30 or 15:00");
            }
        }
    }
}
