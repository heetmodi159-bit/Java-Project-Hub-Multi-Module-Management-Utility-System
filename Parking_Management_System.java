package Advance_Parking_System;

import java.util.Scanner;

public class Parking_Management_System {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome To Parking System");

        System.out.println("====Login / Register====");

        System.out.print("Username : ");
        String user_name = sc.nextLine();

        System.out.print("Password : ");
        String password = sc.nextLine();

        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.car_parking_slot();
        parkingSlot.bike_parking_slot();

    }
}
