package Advance_Parking_System;

import java.util.Date;

public class Booking {

    ParkingSlot ps;

    // Create Booking Object
    Booking(ParkingSlot ps) {
        this.ps = ps;
    }


    // Book a Selected Parking Slot
    void Advance_booking(Vehicle v, int selected_slot) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {

            if (selected_slot >= 1 && selected_slot <= ps.total_car_slot) {

                if (!ps.car_slot_numbers.contains(selected_slot)) {

                    ps.car_slots.add(v);
                    ps.car_slot_numbers.add(selected_slot);

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

            if (selected_slot >= 1 && selected_slot <= ps.total_bike_slot) {

                if (!ps.bike_slot_numbers.contains(selected_slot)) {

                    ps.bike_slots.add(v);
                    ps.bike_slot_numbers.add(selected_slot);

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


    // Cancel a Car or Bike Booking
    void Cancel_booking(Vehicle v) {

        if (v.vehicle_type.equalsIgnoreCase("Car")) {

            int index = ps.car_slots.indexOf(v);

            if (index != -1) {

                int slot = ps.car_slot_numbers.get(index);

                ps.car_slots.remove(index);
                ps.car_slot_numbers.remove(index);

                System.out.println("Car Booking Cancelled Successfully.");
                System.out.println("Released Slot : C" + slot);
            }
            else {
                System.out.println("Car Booking Not Found.");
            }
        }

        else if (v.vehicle_type.equalsIgnoreCase("Bike")) {

            int index = ps.bike_slots.indexOf(v);

            if (index != -1) {

                int slot = ps.bike_slot_numbers.get(index);

                ps.bike_slots.remove(index);
                ps.bike_slot_numbers.remove(index);

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

    Date booking_date(Date booking_date){
        return booking_date;
    }
}