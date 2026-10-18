package Advance_Parking_System;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private final ParkingSlot ps;
    private final ArrayList<BookingRecord> all_bookings;
    private int next_booking_number = 1001;

    // Create Booking Manager and Load Saved Bookings
    public Booking(ParkingSlot ps) throws SQLException {

        this.ps = ps;
        this.all_bookings = BookingDatabase.loadBookings();

        for (BookingRecord booking : all_bookings) {

            try {

                if (booking.booking_id.startsWith("B")) {

                    int number = Integer.parseInt(
                            booking.booking_id.substring(1)
                    );

                    if (number >= next_booking_number) {
                        next_booking_number = number + 1;
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Skipping invalid booking ID: " + booking.booking_id
                );
            }
        }

        System.out.println(
                "Saved bookings loaded: " + all_bookings.size()
        );
    }

    // Book a Selected Parking Slot
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

        if (entry.isBefore(LocalDateTime.now().minusMinutes(1))) {
            System.out.println(
                    "Entry date/time cannot be in the past."
            );
            return null;
        }

        if (!ps.isValidSlot(
                vehicle.getVehicleType(), selected_slot)) {

            System.out.println("Invalid slot number.");
            return null;
        }

        if (hasOverlappingBookingForVehicle(
                vehicle.getNumberPlate(), entry, exit)) {

            System.out.println(
                    "This vehicle already has an overlapping booking."
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
                    "This slot is unavailable for the selected time."
            );
            return null;
        }

        String bookingId = "B" + next_booking_number;

        BookingRecord record = new BookingRecord(
                bookingId,
                customer.getCustName(),
                vehicle,
                selected_slot,
                entry,
                exit
        );

        try {

            // Save to MySQL Before Confirming the Booking
            BookingDatabase.saveBooking(record);

            all_bookings.add(record);
            next_booking_number++;

            System.out.println("Booking Confirmed Successfully.");
            System.out.println("Booking ID : " + record.booking_id);
            System.out.println("Selected Slot : " + slotLabel(record));

            return record;

        } catch (SQLException e) {

            System.out.println(
                    "Booking could not be saved: " + e.getMessage()
            );

            return null;
        }
    }

    // Prevent Overlapping Bookings for the Same Vehicle
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

    // Cancel Booking and Save the Status in MySQL
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
                    "Only BOOKED reservations can be cancelled."
            );
            return false;
        }

        try {

            if (!BookingDatabase.cancelBooking(booking.booking_id)) {
                System.out.println(
                        "Cancellation was not saved."
                );
                return false;
            }

            booking.status = "CANCELLED";

            System.out.println("Booking Cancelled Successfully.");
            System.out.println(
                    "Released Slot : " + slotLabel(booking)
            );

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Could not save cancellation: " + e.getMessage()
            );

            return false;
        }
    }
// Check In a Vehicle
public BookingRecord checkIn(
        Customer customer,
        String bookingId) {

    BookingRecord booking =
            findOwnedBooking(customer, bookingId);

    if (booking == null) {
        System.out.println("Booking not found.");
        return null;
    }

    if (!booking.status.equals("BOOKED")) {
        System.out.println(
                "Only BOOKED reservations can check in."
        );
        return null;
    }

    LocalDateTime now = LocalDateTime.now();

    if (now.isBefore(booking.scheduled_entry)) {
        System.out.println("It is too early to check in.");
        return null;
    }

    if (!now.isBefore(booking.expected_exit)) {
        System.out.println("The scheduled parking period has ended.");
        return null;
    }

    try {

        if (!BookingDatabase.saveEntryTime(
                booking.booking_id, now)) {

            System.out.println("Check-in was not saved.");
            return null;
        }

        booking.actual_entry = now;
        booking.status = "ACTIVE";

        System.out.println("Vehicle Checked In Successfully.");
        System.out.println("Actual Entry Time : " + now);

        return booking;

    } catch (SQLException e) {

        System.out.println(
                "Database error during check-in: " + e.getMessage()
        );

        return null;
    }
}


// Check Out a Vehicle and Calculate Final Fare
public BookingRecord checkOut(
        Customer customer,
        String bookingId) {

    BookingRecord booking =
            findOwnedBooking(customer, bookingId);

    if (booking == null) {
        System.out.println("Booking not found.");
        return null;
    }

    if (!booking.status.equals("ACTIVE")
            || booking.actual_entry == null) {

        System.out.println(
                "The vehicle must check in before checkout."
        );

        return null;
    }

    LocalDateTime actualExit = LocalDateTime.now();

    if (!actualExit.isAfter(booking.actual_entry)) {
        actualExit = booking.actual_entry.plusSeconds(1);
    }

    Duration duration = Duration.between(
            booking.actual_entry, actualExit
    );

    Payment payment = new Payment();

    payment.calculate_fare(booking.vehicle, duration);

    try {

        if (!BookingDatabase.saveCheckout(
                booking.booking_id, actualExit, payment)) {

            System.out.println("Checkout was not saved.");
            return null;
        }

        booking.actual_exit = actualExit;
        booking.status = "COMPLETED";

        booking.parking_fare = payment.getParkingFare();
        booking.advance_fare = payment.getAdvanceFare();
        booking.extra_fare = payment.getExtraFare();
        booking.total_fare = payment.getTotalFare();

        ParkingTicket.displayFinalTicket(
                booking, payment, duration
        );

        return booking;

    } catch (SQLException e) {

        System.out.println(
                "Database error during checkout: " + e.getMessage()
        );

        return null;
    }
}


// Display Booking History for the Logged-In Customer
public void displayHistory(Customer customer) {

    boolean found = false;

    System.out.println(
            "\n=========== MY BOOKING HISTORY ==========="
    );

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


// Display a Booking Ticket
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

    if (booking.status.equals("COMPLETED")
            && booking.actual_entry != null
            && booking.actual_exit != null) {

        Duration duration = Duration.between(
                booking.actual_entry,
                booking.actual_exit
        );

        Payment payment = new Payment();

        payment.calculate_fare(
                booking.vehicle, duration
        );

        ParkingTicket.displayFinalTicket(
                booking, payment, duration
        );

    } else {

        ParkingTicket.displayBookingTicket(booking);
    }
}


// Return Booking List for Slot Availability Checking
public List<BookingRecord> getAllBookings() {
    return all_bookings;
}


// Find a Booking Belonging to the Logged-In Customer
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


// Generate the Car or Bike Slot Label
public String slotLabel(BookingRecord booking) {

    return booking.vehicle.getVehicleType().equalsIgnoreCase("Car")
            ? "C" + String.format("%02d", booking.selected_slot)
            : "B" + String.format("%02d", booking.selected_slot);
}
}
