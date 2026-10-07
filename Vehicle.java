package Advance_Parking_System;

public class Vehicle {
    String vehicle_type;

    Vehicle(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    void display_vehicle_details() {
        System.out.println("The Advance_Parking_System.Vehicle Type : " + this.vehicle_type);
    }
}