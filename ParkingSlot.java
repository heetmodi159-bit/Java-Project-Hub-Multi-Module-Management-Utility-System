package Advance_Parking_System;

import java.util.ArrayList;

public class ParkingSlot {

    ArrayList<Vehicle> car_slots = new ArrayList<Vehicle>();
    ArrayList<Vehicle> bike_slots = new ArrayList<Vehicle>();
    int total_car_slot = 25;
    int total_bike_slot = 25;


    // Display all Car Parking Slots
    void car_parking_slot() {
        System.out.println();
        System.out.println("Parking Sloat : ");

        for (int i = 1; i <= total_car_slot; i++) {
            if (i <= car_slots.size()) {
                System.out.print("[🚗" + i + "]");
            } else {
                System.out.printf("[%02d] ", i);
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
        System.out.println("Parking Sloat : ");

        for (int i = 1; i <= total_bike_slot; i++) {
            if (i <= bike_slots.size()) {
                System.out.print("[\uD83C\uDFCD\uFE0F" + i + "]");
            } else {
                System.out.printf("[%02d] ", i);
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

    // Book a Parking Slot for the Vehicle
    void Advance_booking(Vehicle v) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {

            if (car_slots.size() < total_car_slot) {

                int slot = car_slots.size() + 1;
                car_slots.add(v);

                System.out.println("Car Slot Booked Successfully.");
                System.out.println("Assigned Slot : C" + slot);
            }
            else {
                System.out.println("All Car Slots are booked.");
            }
        }

        else if (v.vehicle_type.equalsIgnoreCase("Bike")) {

            if (bike_slots.size() < total_bike_slot) {

                int slot = bike_slots.size() + 1;
                bike_slots.add(v);

                System.out.println("Bike Slot Booked Successfully.");
                System.out.println("Assigned Slot : B" + slot);
            }
            else {
                System.out.println("All Bike Slots are booked.");
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

    void Cancel_booking(Vehicle v){
        if (v.vehicle_type.equalsIgnoreCase("Car")){
            if (car_slots.size() < total_car_slot){
                car_slots.remove(v);
                int slot = car_slots.size() - 1;

                System.out.println("Cancel Booking");
            }
            else {
                System.out.println("All Slots are booked.");
            }
        }

        else if (v.vehicle_type.equalsIgnoreCase("Bike")){
            if (bike_slots.size() < total_bike_slot){
                bike_slots.remove(v);
                int slot = car_slots.size() - 1;

                System.out.println("Cancel Booking");
            }
            else {
                System.out.println("All Slots are booked.");
            }
        }

        else {
            System.out.println("Only Car and Bike are allowed.");
        }
    }

    // Display Details of All Parked Vehicles
    void display_psv_details() {     // psv = Parking Slot Advance_Parking_System.Vehicle
        System.out.println("=========== Car Details ===========");

        for (Vehicle v : car_slots){
            v.display_vehicle_details();
        }

        System.out.println("=========== Bike Details ===========");

        for (Vehicle v : bike_slots){
            v.display_vehicle_details();
        }
    }
}
