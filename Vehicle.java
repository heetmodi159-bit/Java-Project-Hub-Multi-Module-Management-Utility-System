package Advance_Parking_System;

public class Vehicle {

    private final String number_plate;
    private final String vehicle_type;

    public Vehicle(String number_plate, String vehicle_type) {

        if (number_plate == null || number_plate.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle number plate cannot be empty."
            );
        }

        if (vehicle_type == null ||
                (!vehicle_type.equalsIgnoreCase("Car")
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