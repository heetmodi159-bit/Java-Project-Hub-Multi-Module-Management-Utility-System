package Advance_Parking_System;

public class Customer {

    private final String cust_name;
    private final String password;

    public Customer(String cust_name, String password) {
        this.cust_name = cust_name.trim();
        this.password = password;
    }

    public String getCustName() {
        return cust_name;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void display_customer_details() {
        System.out.println("Customer Name : " + cust_name);
    }
}