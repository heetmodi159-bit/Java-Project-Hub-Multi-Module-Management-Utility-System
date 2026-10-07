package Advance_Parking_System;

public class Payment {
    int fare = 0;

    void calculate_fare(Vehicle v){
        if(v.vehicle_type.equals("Car")){
            fare += 50;
        }
        else if (v.vehicle_type.equals("Bike") || v.vehicle_type.equals("Scooter")) {
            fare += 30;
        }
    }

    int total_fare = this.fare + 50;

    void display_fare(){
        System.out.println("The Total Fare is : " + this.total_fare);
    }
}