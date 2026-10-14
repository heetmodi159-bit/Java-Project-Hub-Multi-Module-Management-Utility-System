package Shopping_App;

import java.util.Scanner;

public class CashOnDelivery implements PaymentMethod {

    // Process Cash on Delivery
    @Override
    public boolean processPayment(double amount, Scanner sc) {

        System.out.println("\n========== CASH ON DELIVERY ==========");

        System.out.println(
                "Order Confirmed. Payment Will Be Collected on Delivery."
        );

        System.out.println("Amount Due : Rs. " + amount);

        return true;
    }

    // Return Method Name
    @Override
    public String getMethodName() {
        return "Cash on Delivery";
    }
}