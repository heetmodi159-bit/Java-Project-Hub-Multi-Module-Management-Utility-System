package Shopping_App;

import java.util.Scanner;

interface PaymentMethod {

    // Process the Selected Payment Method
    boolean processPayment(double amount, Scanner sc);

    // Return Payment Method Name
    String getMethodName();
}