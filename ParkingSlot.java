package Advance_Parking_System;

import java.util.ArrayList;

public class ParkingSlot {

    ArrayList<Vehicle> car_slots = new ArrayList<Vehicle>();
    ArrayList<Vehicle> bike_slots = new ArrayList<Vehicle>();

    ArrayList<Integer> car_slot_numbers = new ArrayList<Integer>();
    ArrayList<Integer> bike_slot_numbers = new ArrayList<Integer>();

    int total_car_slot = 25;
    int total_bike_slot = 25;


    // Display all Car Parking Slots
    void car_parking_slot() {
        System.out.println();
        System.out.println("Car Parking Slot : ");

        for (int i = 1; i <= total_car_slot; i++) {

            if (car_slot_numbers.contains(i)) {
                System.out.print("[🚗" + i + "] ");
            }
            else {
                System.out.printf("[C%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("Total Car Slots     : " + this.total_car_slot);
        System.out.println("Occupied Car Slots  : " + car_slots.size());
        System.out.println("Available Car Slots : " + available_space_car());
    }


    // Display all Bike Parking Slots
    void bike_parking_slot() {
        System.out.println();
        System.out.println("Bike Parking Slot : ");

        for (int i = 1; i <= total_bike_slot; i++) {

            if (bike_slot_numbers.contains(i)) {
                System.out.print("[🏍️" + i + "] ");
            }
            else {
                System.out.printf("[B%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("Total Bike Slots     : " + this.total_bike_slot);
        System.out.println("Occupied Bike Slots  : " + bike_slots.size());
        System.out.println("Available Bike Slots : " + available_space_bike());
    }


    // Book a Selected Parking Slot
    void Advance_booking(Vehicle v, int selected_slot) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {

            if (selected_slot >= 1 && selected_slot <= total_car_slot) {

                if (!car_slot_numbers.contains(selected_slot)) {

                    car_slots.add(v);
                    car_slot_numbers.add(selected_slot);

                    System.out.println("Car Slot Booked Successfully.");
                    System.out.println("Selected Slot : C" + selected_slot);
                }
                else {
                    System.out.println("This Car Slot is already booked.");
                }
            }
            else {
                System.out.println("Invalid Car Slot.");
            }
        }

        else if (v.vehicle_type.equalsIgnoreCase("Bike")) {

            if (selected_slot >= 1 && selected_slot <= total_bike_slot) {

                if (!bike_slot_numbers.contains(selected_slot)) {

                    bike_slots.add(v);
                    bike_slot_numbers.add(selected_slot);

                    System.out.println("Bike Slot Booked Successfully.");
                    System.out.println("Selected Slot : B" + selected_slot);
                }
                else {
                    System.out.println("This Bike Slot is already booked.");
                }
            }
            else {
                System.out.println("Invalid Bike Slot.");
            }
        }

        else {
            System.out.println("Only Car and Bike are allowed.");
        }
    }


    // Calculate Available Car Parking Slots
    int available_space_car() {
        return this.total_car_slot - car_slots.size();
    }


    // Calculate Available Bike Parking Slots
    int available_space_bike() {
        return this.total_bike_slot - bike_slots.size();
    }


    // Cancel Car or Bike Booking
    void Cancel_booking(Vehicle v) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {

            int index = car_slots.indexOf(v);

            if (index != -1) {

                int slot = car_slot_numbers.get(index);

                car_slots.remove(index);
                car_slot_numbers.remove(index);

                System.out.println("Car Booking Cancelled Successfully.");
                System.out.println("Released Slot : C" + slot);
            }
            else {
                System.out.println("Car Booking Not Found.");
            }
        }

        else if (v.vehicle_type.equalsIgnoreCase("Bike")) {

            int index = bike_slots.indexOf(v);

            if (index != -1) {

                int slot = bike_slot_numbers.get(index);

                bike_slots.remove(index);
                bike_slot_numbers.remove(index);

                System.out.println("Bike Booking Cancelled Successfully.");
                System.out.println("Released Slot : B" + slot);
            }
            else {
                System.out.println("Bike Booking Not Found.");
            }
        }

        else {
            System.out.println("Only Car and Bike are allowed.");
        }
    }


    // Display Details of All Parked Vehicles
    void display_psv_details() {

        System.out.println("=========== Car Details ===========");

        for (int i = 0; i < car_slots.size(); i++) {
            System.out.println("Slot : C" + car_slot_numbers.get(i));
            car_slots.get(i).display_vehicle_details();
        }

        System.out.println("=========== Bike Details ===========");

        for (int i = 0; i < bike_slots.size(); i++) {
            System.out.println("Slot : B" + bike_slot_numbers.get(i));
            bike_slots.get(i).display_vehicle_details();
        }
    }
}
