package OS_System;

import java.util.Scanner;

public class OnlinePayment implements PaymentMethod {

    // Simulate Online Payment
    @Override
    public boolean processPayment(double amount, Scanner sc) {

        System.out.println("\n========== ONLINE PAYMENT ==========");

        System.out.println("Amount to Pay : Rs. " + amount);

        System.out.print("Confirm Demo Payment? (Y/N) : ");

        String choice = sc.nextLine().trim();

        if (choice.equalsIgnoreCase("Y")) {

            System.out.println("Demo Payment Successful.");
            return true;
        }

        System.out.println("Payment Cancelled.");
        return false;
    }

    // Return Method Name
    @Override
    public String getMethodName() {
        return "Online Payment (Demo)";
    }
}
