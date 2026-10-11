package Advance_Parking_System;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingSlot {

    public final int total_car_slot = 25;
    public final int total_bike_slot = 25;


    // Check whether the selected slot number is valid
    public boolean isValidSlot(String vehicleType, int slot) {

        if (vehicleType.equalsIgnoreCase("Car")) {
            return slot >= 1 && slot <= total_car_slot;
        }

        if (vehicleType.equalsIgnoreCase("Bike")) {
            return slot >= 1 && slot <= total_bike_slot;
        }

        return false;
    }


    // Check whether a slot is available for the selected time
    public boolean isSlotAvailable(
            String vehicleType,
            int selectedSlot,
            LocalDateTime entry,
            LocalDateTime exit,
            List<BookingRecord> bookings) {

        if (!isValidSlot(vehicleType, selectedSlot)
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


    // Display Car slots for the selected date and time
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
                System.out.printf("[🚗C%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println(
                "Available Car Slots for selected time: " + available
        );
    }


    // Display Bike slots for the selected date and time
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
                System.out.printf("[🏍️B%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println(
                "Available Bike Slots for selected time: " + available
        );
    }
}
