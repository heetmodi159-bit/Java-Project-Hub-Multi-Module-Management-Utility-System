package Advance_Parking_System;

public class Vehicle {
    String number_plate;
    String vehicle_type;

    Vehicle(String number_plate, String vehicle_type) {
        this.number_plate = number_plate;
        this.vehicle_type = vehicle_type;
    }

    void display_vehicle_details() {
        System.out.println("Vehicle Number Plate : " + this.number_plate);
        System.out.println("Vehicle Type         : " + this.vehicle_type);
    }
}
