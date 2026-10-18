package Advance_Parking_System;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BookingDatabase {

    // Save a New Booking in MySQL
    public static void saveBooking(BookingRecord booking)
            throws SQLException {

        String sql = "INSERT INTO parking_bookings "
                + "(booking_id, customer_name, number_plate, vehicle_type, "
                + "slot_number, booking_date, scheduled_entry, expected_exit, "
                + "actual_entry, actual_exit, status, parking_fare, "
                + "advance_fare, extra_fare, total_fare) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, booking.booking_id);
            ps.setString(2, booking.customer_name);
            ps.setString(3, booking.vehicle.getNumberPlate());
            ps.setString(4, booking.vehicle.getVehicleType());
            ps.setInt(5, booking.selected_slot);
            ps.setDate(6, Date.valueOf(booking.booking_date));

            ps.setTimestamp(7,
                    Timestamp.valueOf(booking.scheduled_entry));

            ps.setTimestamp(8,
                    Timestamp.valueOf(booking.expected_exit));

            setNullableTimestamp(ps, 9, booking.actual_entry);
            setNullableTimestamp(ps, 10, booking.actual_exit);

            ps.setString(11, booking.status);
            ps.setInt(12, booking.parking_fare);
            ps.setInt(13, booking.advance_fare);
            ps.setInt(14, booking.extra_fare);
            ps.setInt(15, booking.total_fare);

            ps.executeUpdate();
        }
    }

    // Load Every Saved Booking into an ArrayList
    public static ArrayList<BookingRecord> loadBookings()
            throws SQLException {

        ArrayList<BookingRecord> bookings = new ArrayList<>();

        String sql = "SELECT * FROM parking_bookings "
                + "ORDER BY booking_date, booking_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Timestamp actualEntry =
                        rs.getTimestamp("actual_entry");

                Timestamp actualExit =
                        rs.getTimestamp("actual_exit");

                Vehicle vehicle = new Vehicle(
                        rs.getString("number_plate"),
                        rs.getString("vehicle_type")
                );

                BookingRecord booking = new BookingRecord(
                        rs.getString("booking_id"),
                        rs.getString("customer_name"),
                        vehicle,
                        rs.getInt("slot_number"),
                        rs.getDate("booking_date").toLocalDate(),
                        rs.getTimestamp("scheduled_entry").toLocalDateTime(),
                        rs.getTimestamp("expected_exit").toLocalDateTime(),
                        actualEntry == null
                                ? null : actualEntry.toLocalDateTime(),
                        actualExit == null
                                ? null : actualExit.toLocalDateTime(),
                        rs.getString("status"),
                        rs.getInt("parking_fare"),
                        rs.getInt("advance_fare"),
                        rs.getInt("extra_fare"),
                        rs.getInt("total_fare")
                );

                bookings.add(booking);
            }
        }

        return bookings;
    }

    // Save Booking Cancellation
    public static boolean cancelBooking(String bookingId)
            throws SQLException {

        String sql = "UPDATE parking_bookings "
                + "SET status = 'CANCELLED' "
                + "WHERE booking_id = ? AND status = 'BOOKED'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save Actual Vehicle Entry Time
    public static boolean saveEntryTime(
            String bookingId,
            LocalDateTime entry) throws SQLException {

        String sql = "UPDATE parking_bookings "
                + "SET actual_entry = ?, status = 'ACTIVE' "
                + "WHERE booking_id = ? AND status = 'BOOKED'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(entry));
            ps.setString(2, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save Exit Time and Final Fare
    public static boolean saveCheckout(
            String bookingId,
            LocalDateTime exit,
            Payment payment) throws SQLException {

        String sql = "UPDATE parking_bookings SET "
                + "actual_exit = ?, status = 'COMPLETED', "
                + "parking_fare = ?, advance_fare = ?, "
                + "extra_fare = ?, total_fare = ? "
                + "WHERE booking_id = ? AND status = 'ACTIVE'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(exit));
            ps.setInt(2, payment.getParkingFare());
            ps.setInt(3, payment.getAdvanceFare());
            ps.setInt(4, payment.getExtraFare());
            ps.setInt(5, payment.getTotalFare());
            ps.setString(6, bookingId);

            return ps.executeUpdate() == 1;
        }
    }

    // Save a Nullable Timestamp
    private static void setNullableTimestamp(
            PreparedStatement ps,
            int position,
            LocalDateTime value) throws SQLException {

        if (value == null) {
            ps.setNull(position, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(position, Timestamp.valueOf(value));
        }
    }
}