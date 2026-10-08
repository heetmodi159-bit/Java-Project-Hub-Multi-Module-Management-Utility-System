package Advance_Parking_System;

public class Payment {

    int fare = 0;
    int advance_fare = 50;

    void calculate_fare(Vehicle v) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {
            fare = 50;
        }
        else if (v.vehicle_type.equalsIgnoreCase("Bike")) {
            fare = 30;
        }
    }

    void display_fare() {

        int total_fare = fare + advance_fare;

        System.out.println("Parking Fare  : " + fare);
        System.out.println("Advance Fare  : " + advance_fare);
        System.out.println("Total Fare    : " + total_fare);
    }
}
