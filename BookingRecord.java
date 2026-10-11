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

    BookingRecord(
            String booking_id,
            String customer_name,
            Vehicle vehicle,
            int selected_slot,
            LocalDateTime scheduled_entry,
            LocalDateTime expected_exit) {

        this.booking_id = booking_id;
        this.customer_name = customer_name;
        this.vehicle = vehicle;
        this.selected_slot = selected_slot;

        this.booking_date = LocalDate.now();
        this.scheduled_entry = scheduled_entry;
        this.expected_exit = expected_exit;

        this.status = "BOOKED";
    }
}