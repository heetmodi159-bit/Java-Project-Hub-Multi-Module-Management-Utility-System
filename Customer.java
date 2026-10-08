package Advance_Parking_System;

public class Customer {
    private String cust_name ;
    private String password;

    Customer(String cust_name, String password){
        this.cust_name = cust_name;
        this.password = password;
    }

    void display_customer_details(){
        System.out.println("The Advance_Parking_System.Customer Name : " + this.cust_name);
    }
}
