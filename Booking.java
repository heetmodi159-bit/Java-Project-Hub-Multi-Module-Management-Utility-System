package Advance_Parking_System;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private final ParkingSlot ps;
    private final ArrayList<BookingRecord> all_bookings =
            new ArrayList<>();

    private int next_booking_number = 1001;


    // Create Booking Manager
    public Booking(ParkingSlot ps) {
        this.ps = ps;
    }


    // Book a selected parking slot
    public BookingRecord Advance_booking(
            Customer customer,
            Vehicle vehicle,
            int selected_slot,
            LocalDateTime entry,
            LocalDateTime exit) {

        if (customer == null || vehicle == null) {
            System.out.println(
                    "Customer and vehicle details are required."
            );
            return null;
        }

        if (entry == null || exit == null || !exit.isAfter(entry)) {
            System.out.println(
                    "Expected exit must be after entry."
            );
            return null;
        }

        if (!ps.isValidSlot(
                vehicle.getVehicleType(),
                selected_slot)) {

            System.out.println("Invalid slot number.");
            return null;
        }

        if (hasOverlappingBookingForVehicle(
                vehicle.getNumberPlate(), entry, exit)) {

            System.out.println(
                    "This vehicle already has a booking during that time."
            );
            return null;
        }

        if (!ps.isSlotAvailable(
                vehicle.getVehicleType(),
                selected_slot,
                entry,
                exit,
                all_bookings)) {

            System.out.println(
                    "This slot is already booked for the selected time."
            );
            return null;
        }

        String bookingId = "B" + next_booking_number++;

        BookingRecord record = new BookingRecord(
                bookingId,
                customer.getCustName(),
                vehicle,
                selected_slot,
                entry,
                exit
        );

        all_bookings.add(record);

        System.out.println("Booking Confirmed Successfully.");
        System.out.println("Booking ID : " + record.booking_id);
        System.out.println("Selected Slot : " + slotLabel(record));

        return record;
    }


    // Prevent overlapping bookings for the same vehicle
    private boolean hasOverlappingBookingForVehicle(
            String numberPlate,
            LocalDateTime entry,
            LocalDateTime exit) {

        for (BookingRecord booking : all_bookings) {

            if (booking.status.equals("CANCELLED")
                    || !booking.vehicle.getNumberPlate()
                    .equalsIgnoreCase(numberPlate)) {

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

            if (entry.isBefore(existingExit)
                    && exit.isAfter(existingEntry)) {

                return true;
            }
        }

        return false;
    }


    // Cancel a booking while retaining it in history
    public boolean Cancel_booking(
            Customer customer,
            String bookingId) {

        BookingRecord booking = findOwnedBooking(customer, bookingId);

        if (booking == null) {
            System.out.println(
                    "Booking not found for this account."
            );
            return false;
        }

        if (!booking.status.equals("BOOKED")) {
            System.out.println(
                    "Only a booking with status BOOKED can be cancelled."
            );
            return false;
        }

        booking.status = "CANCELLED";

        System.out.println("Booking Cancelled Successfully.");
        System.out.println("Released Slot : " + slotLabel(booking));

        return true;
    }


    // Check in a vehicle and mark booking as active
    public BookingRecord checkIn(
            Customer customer,
            String bookingId) {

        BookingRecord booking = findOwnedBooking(customer, bookingId);

        if (booking == null) {
            System.out.println(
                    "Booking not found for this account."
            );
            return null;
        }

        if (!booking.status.equals("BOOKED")) {
            System.out.println(
                    "Only a booking with status BOOKED can check in."
            );
            return null;
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(booking.scheduled_entry)) {
            System.out.println(
                    "It is too early to check in for this booking."
            );
            System.out.println(
                    "Scheduled Entry : " + booking.scheduled_entry
            );
            return null;
        }

        if (!now.isBefore(booking.expected_exit)) {
            System.out.println(
                    "The scheduled parking period has ended. Check-in is not allowed."
            );
            return null;
        }

        booking.actual_entry = now;
        booking.status = "ACTIVE";

        System.out.println("Vehicle Checked In Successfully.");
        System.out.println(
                "Actual Entry Time : " + booking.actual_entry
        );

        return booking;
    }


    // Check out a vehicle and calculate final fare
    public BookingRecord checkOut(
            Customer customer,
            String bookingId) {

        BookingRecord booking = findOwnedBooking(customer, bookingId);

        if (booking == null) {
            System.out.println(
                    "Booking not found for this account."
            );
            return null;
        }

        if (!booking.status.equals("ACTIVE")
                || booking.actual_entry == null) {

            System.out.println(
                    "Vehicle must be checked in before checkout."
            );
            return null;
        }

        booking.actual_exit = LocalDateTime.now();

        if (!booking.actual_exit.isAfter(booking.actual_entry)) {
            booking.actual_exit =
                    booking.actual_entry.plusSeconds(1);
        }

        booking.status = "COMPLETED";

        Duration actualDuration = Duration.between(
                booking.actual_entry,
                booking.actual_exit
        );

        Payment payment = new Payment();

        payment.calculate_fare(
                booking.vehicle,
                actualDuration
        );

        ParkingTicket.displayFinalTicket(
                booking,
                payment,
                actualDuration
        );

        return booking;
    }


    // Display booking history for the logged-in customer
    public void displayHistory(Customer customer) {

        boolean found = false;

        System.out.println("\n=========== MY BOOKING HISTORY ===========");

        for (BookingRecord booking : all_bookings) {

            if (booking.customer_name.equalsIgnoreCase(
                    customer.getCustName())) {

                found = true;

                System.out.println(
                        "Booking ID : " + booking.booking_id
                                + " | Vehicle : "
                                + booking.vehicle.getNumberPlate()
                                + " | Slot : " + slotLabel(booking)
                                + " | Entry : " + booking.scheduled_entry
                                + " | Exit : " + booking.expected_exit
                                + " | Status : " + booking.status
                );
            }
        }

        if (!found) {
            System.out.println("No bookings found.");
        }
    }


    // Display a booking ticket using its booking ID
    public void displayTicket(
            Customer customer,
            String bookingId) {

        BookingRecord booking =
                findOwnedBooking(customer, bookingId);

        if (booking == null) {
            System.out.println(
                    "Booking not found for this account."
            );
            return;
        }

        ParkingTicket.displayBookingTicket(booking);
    }


    // Return bookings for slot availability checking
    public List<BookingRecord> getAllBookings() {
        return all_bookings;
    }


    // Find a booking belonging to the logged-in customer
    public BookingRecord findOwnedBooking(
            Customer customer,
            String bookingId) {

        if (customer == null || bookingId == null) {
            return null;
        }

        for (BookingRecord booking : all_bookings) {

            if (booking.booking_id.equalsIgnoreCase(
                    bookingId.trim())
                    && booking.customer_name.equalsIgnoreCase(
                    customer.getCustName())) {

                return booking;
            }
        }

        return null;
    }


    // Generate the Car or Bike slot label
    public String slotLabel(BookingRecord booking) {

        return booking.vehicle.getVehicleType().equalsIgnoreCase("Car")
                ? "C" + String.format("%02d", booking.selected_slot)
                : "B" + String.format("%02d", booking.selected_slot);
    }
}