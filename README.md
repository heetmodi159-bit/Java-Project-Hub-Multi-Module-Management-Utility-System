ADVANCE PARKING BOOKING APP - JAVA CONSOLE PROJECT

FILES
- Main.java            User input, registration/login, and menu
- Customer.java        Customer details and password validation
- Vehicle.java         Vehicle number plate and Car/Bike type
- ParkingSlot.java     25 car slots, 25 bike slots, time-based availability
- Booking.java         Multiple bookings, cancellation, check-in, checkout, history
- BookingRecord.java   Details stored separately for each booking
- Payment.java         Parking fare, advance booking fare, and >4-hour extra fare
- ParkingTicket.java   Booking ticket and final parking ticket

REQUIREMENTS IMPLEMENTED
- Register and log in during the current program session
- Book a slot chosen by the user
- Check slot availability against the selected entry and exit date/time
- Keep multiple booking records with unique booking IDs
- Cancel a booking and make that time slot available again
- Keep cancelled bookings in history
- Mark bookings BOOKED, ACTIVE, COMPLETED, or CANCELLED
- Record actual check-in/check-out timestamps
- Calculate final fare from actual duration
- Add one extra vehicle parking fare when duration is MORE THAN 4 hours
- Generate booking and final parking tickets
- View booking history and a booking ticket
- Handle invalid menu numbers, dates, times, and slot selections

CURRENT FARE RULES
- Car parking fare: Rs. 50
- Bike parking fare: Rs. 30
- Advance booking fare: Rs. 50
- If actual parking duration is more than 4 hours, add one extra vehicle parking fare.

COMPILE
  javac Advance_Parking_System/*.java

RUN
  java Advance_Parking_System.Main

NOTES / LIMITATIONS
- Account and booking data are in memory only and are lost when the application closes.
- Registration and login work within the current program session; there is no database or persistent account storage yet.
- This is an educational console prototype. It does not process real payments and stores passwords in memory for the session only.
- Check-in and checkout use the computer's current date and time.
- The same slot can be booked for different non-overlapping time ranges.
