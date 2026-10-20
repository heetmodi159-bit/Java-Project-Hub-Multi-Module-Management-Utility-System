package OS_System;

import java.time.Duration;

public class ParkingTicket {

    // Display the Advance Booking Ticket
    public static void displayBookingTicket(BookingRecord booking) {

        Duration estimatedDuration = Duration.between(
                booking.scheduled_entry,
                booking.expected_exit
        );

        Payment payment = new Payment();

        payment.calculate_fare(
                booking.vehicle,
                estimatedDuration
        );

        System.out.println("\n=============== BOOKING TICKET ===============");

        System.out.println("Booking ID       : " + booking.booking_id);
        System.out.println("Customer Name    : " + booking.customer_name);

        booking.vehicle.display_vehicle_details();

        System.out.println("Selected Slot    : " + slotLabel(booking));
        System.out.println("Booking Date     : " + booking.booking_date);
        System.out.println("Scheduled Entry  : " + booking.scheduled_entry);
        System.out.println("Expected Exit    : " + booking.expected_exit);
        System.out.println("Status           : " + booking.status);

        System.out.println("---------------- ESTIMATED FARE ----------------");

        payment.display_fare(estimatedDuration);

        System.out.println(
                "Note: Final fare is calculated at actual checkout."
        );

        System.out.println("===============================================");
    }

    // Display the Final Parking Ticket
    public static void displayFinalTicket(
            BookingRecord booking,
            Payment payment,
            Duration actualDuration) {

        System.out.println("\n=============== FINAL PARKING TICKET ===============");

        System.out.println("Ticket ID        : FT-" + booking.booking_id);
        System.out.println("Booking ID       : " + booking.booking_id);
        System.out.println("Customer Name    : " + booking.customer_name);

        booking.vehicle.display_vehicle_details();

        System.out.println("Parking Slot     : " + slotLabel(booking));
        System.out.println("Scheduled Entry  : " + booking.scheduled_entry);
        System.out.println("Expected Exit    : " + booking.expected_exit);
        System.out.println("Actual Entry     : " + booking.actual_entry);
        System.out.println("Actual Exit      : " + booking.actual_exit);
        System.out.println("Status           : " + booking.status);

        System.out.println("---------------- FINAL FARE ----------------");

        payment.display_fare(actualDuration);

        System.out.println("====================================================");
    }

    // Generate the Car or Bike Slot Label
    private static String slotLabel(BookingRecord booking) {

        if (booking.vehicle.getVehicleType().equalsIgnoreCase("Car")) {
            return "C" + String.format("%02d", booking.selected_slot);
        }

        return "B" + String.format("%02d", booking.selected_slot);
    }
}
