package Advance_Parking_System;

import java.time.Duration;

public class Payment {

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
