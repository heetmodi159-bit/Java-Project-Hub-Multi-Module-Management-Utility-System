package Advance_Parking_System;

public class Customer {
    int cust_id;
    String cust_name;

    Customer(int cust_id , String cust_name){
        this.cust_id = cust_id;
        this.cust_name = cust_name;
    }

    void display_customer_details(){
        System.out.println("The Advance_Parking_System.Customer ID : " + this.cust_id);
        System.out.println("The Advance_Parking_System.Customer Name : " + this.cust_name);
    }
}