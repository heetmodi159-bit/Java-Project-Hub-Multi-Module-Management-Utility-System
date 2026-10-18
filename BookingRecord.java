package Advance_Parking_System;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingRecord {

    final String booking_id;
    final String customer_name;
    final Vehicle vehicle;
    final int selected_slot;

    final LocalDate booking_date;
    final LocalDateTime scheduled_entry;
    final LocalDateTime expected_exit;

    LocalDateTime actual_entry;
    LocalDateTime actual_exit;

    String status;

    int parking_fare;
    int advance_fare;
    int extra_fare;
    int total_fare;

    // Constructor for a New Booking
    public BookingRecord(
            String booking_id,
            String customer_name,
            Vehicle vehicle,
            int selected_slot,
            LocalDateTime scheduled_entry,
            LocalDateTime expected_exit) {

        this(
                booking_id,
                customer_name,
                vehicle,
                selected_slot,
                LocalDate.now(),
                scheduled_entry,
                expected_exit,
                null,
                null,
                "BOOKED",
                0,
                50,
                0,
                0
        );
    }

    // Constructor for a Booking Loaded from MySQL
    public BookingRecord(
            String booking_id,
            String customer_name,
            Vehicle vehicle,
            int selected_slot,
            LocalDate booking_date,
            LocalDateTime scheduled_entry,
            LocalDateTime expected_exit,
            LocalDateTime actual_entry,
            LocalDateTime actual_exit,
            String status,
            int parking_fare,
            int advance_fare,
            int extra_fare,
            int total_fare) {

        this.booking_id = booking_id;
        this.customer_name = customer_name;
        this.vehicle = vehicle;
        this.selected_slot = selected_slot;

        this.booking_date = booking_date;
        this.scheduled_entry = scheduled_entry;
        this.expected_exit = expected_exit;

        this.actual_entry = actual_entry;
        this.actual_exit = actual_exit;

        this.status = status;

        this.parking_fare = parking_fare;
        this.advance_fare = advance_fare;
        this.extra_fare = extra_fare;
        this.total_fare = total_fare;
    }
}
