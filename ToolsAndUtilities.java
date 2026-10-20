package OS_System;

import java.util.Date;
import java.util.Scanner;

class CalculatorTool {

    void use(Scanner sc) {
        System.out.println("\n--- Calculator ---");
        System.out.println(" 1. Addition (+)");
        System.out.println(" 2. Subtraction (-)");
        System.out.println(" 3. Multiplication (x)");
        System.out.println(" 4. Division (/)");
        System.out.println(" 5. Modulus / Remainder (%)");
        System.out.println(" 6. Square Root");
        System.out.println(" 7. Cube Root");
        System.out.println(" 8. Power / Exponent (first number ^ second number)");
        System.out.println(" 9. Square and Cube of a number");
        System.out.println("10. Percentage (first number % of second number)");
        System.out.println("11. Factorial");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        double a = 0;
        double b = 0;

        if ((choice >= 1 && choice <= 5) || choice == 8 || choice == 10) {
            System.out.print("Enter first number : ");
            a = sc.nextDouble();
            System.out.print("Enter second number : ");
            b = sc.nextDouble();
        } else if (choice == 6 || choice == 7 || choice == 9 || choice == 11) {
            System.out.print("Enter a number : ");
            a = sc.nextDouble();
        }

        switch (choice) {
            case 1:
                System.out.println("Result : " + (a + b));
                break;
            case 2:
                System.out.println("Result : " + (a - b));
                break;
            case 3:
                System.out.println("Result : " + (a * b));
                break;
            case 4:
                if (b == 0) {
                    System.out.println("Cannot divide by zero!");
                } else {
                    System.out.println("Result : " + (a / b));
                }
                break;
            case 5:
                if (b == 0) {
                    System.out.println("Cannot divide by zero!");
                } else {
                    System.out.println("Remainder : " + (a % b));
                }
                break;
            case 6:
                if (a < 0) {
                    System.out.println("Square root of a negative number is not possible!");
                } else {
                    System.out.println("Square Root : " + Math.sqrt(a));
                }
                break;
            case 7:
                System.out.println("Cube Root : " + Math.cbrt(a));
                break;
            case 8:
                System.out.println("Result : " + Math.pow(a, b));
                break;
            case 9:
                System.out.println("Square : " + (a * a));
                System.out.println("Cube   : " + (a * a * a));
                break;
            case 10:
                System.out.println(a + "% of " + b + " = " + (a * b / 100));
                break;
            case 11:
                int n = (int) a;
                if (n < 0 || n > 20) {
                    System.out.println("Enter a whole number from 0 to 20!");
                } else {
                    long fact = 1;
                    for (int i = 1; i <= n; i++) {
                        fact = fact * i;
                    }
                    System.out.println("Factorial : " + fact);
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}

class TemperatureTool {

    void use(Scanner sc) {
        System.out.println("\n--- Temperature Convertor ---");
        System.out.println(" 1. Celsius to Fahrenheit");
        System.out.println(" 2. Fahrenheit to Celsius");
        System.out.println(" 3. Celsius to Kelvin");
        System.out.println(" 4. Kelvin to Celsius");
        System.out.println(" 5. Fahrenheit to Kelvin");
        System.out.println(" 6. Kelvin to Fahrenheit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.print("Enter temperature : ");
        double t = sc.nextDouble();

        double result = 0;
        String unit = "";

        switch (choice) {
            case 1:
                result = t * 9 / 5 + 32;
                unit = "F";
                break;
            case 2:
                result = (t - 32) * 5 / 9;
                unit = "C";
                break;
            case 3:
                result = t + 273.15;
                unit = "K";
                break;
            case 4:
                result = t - 273.15;
                unit = "C";
                break;
            case 5:
                result = (t - 32) * 5 / 9 + 273.15;
                unit = "K";
                break;
            case 6:
                result = (t - 273.15) * 9 / 5 + 32;
                unit = "F";
                break;
        }

        System.out.println("Result : " + Math.round(result * 100) / 100.0 + " " + unit);
    }
}

class TimeTool {

    void use(Scanner sc) {
        System.out.println("\n--- Time ---");
        System.out.println(" 1. Current Time and Date");
        System.out.println(" 2. Date and Time After / Before Some Days");
        System.out.println(" 3. Stopwatch");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Current Date and Time : " + new Date());

        } else if (choice == 2) {
            System.out.print("1. After   2. Before : ");
            int type = sc.nextInt();
            System.out.print("Enter number of days : ");
            int days = sc.nextInt();

            long now = System.currentTimeMillis();
            long change = days * 24L * 60 * 60 * 1000;

            if (type == 1) {
                System.out.println("Date and Time : " + new Date(now + change));
            } else if (type == 2) {
                System.out.println("Date and Time : " + new Date(now - change));
            } else {
                System.out.println("Invalid choice!");
            }

        } else if (choice == 3) {
            System.out.print("Type start and press Enter : ");
            sc.next();
            long start = System.currentTimeMillis();

            System.out.print("Type stop and press Enter  : ");
            sc.next();
            long end = System.currentTimeMillis();

            System.out.println("Elapsed Time : " + (end - start) / 1000.0 + " seconds");

        } else {
            System.out.println("Invalid choice!");
        }
    }
}

class CurrencyTool {
    // Approximate rates - change them whenever you like
    double usdToInr = 94.0;
    double eurToInr = 110.0;
    double gbpToInr = 127.0;

    void use(Scanner sc) {
        System.out.println("\n--- Currency Convertor ---");
        System.out.println(" 1. Indian Rupee to US Dollar (INR -> USD)");
        System.out.println(" 2. US Dollar to Indian Rupee (USD -> INR)");
        System.out.println(" 3. INR to Euro (EUR)");
        System.out.println(" 4. Euro to INR");
        System.out.println(" 5. INR to British Pound (GBP)");
        System.out.println(" 6. British Pound to INR");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.print("Enter amount : ");
        double amount = sc.nextDouble();

        double result = 0;
        String unit = "";

        switch (choice) {
            case 1:
                result = amount / usdToInr;
                unit = "USD";
                break;
            case 2:
                result = amount * usdToInr;
                unit = "INR";
                break;
            case 3:
                result = amount / eurToInr;
                unit = "EUR";
                break;
            case 4:
                result = amount * eurToInr;
                unit = "INR";
                break;
            case 5:
                result = amount / gbpToInr;
                unit = "GBP";
                break;
            case 6:
                result = amount * gbpToInr;
                unit = "INR";
                break;
        }

        System.out.println("Result : " + Math.round(result * 100) / 100.0 + " " + unit);
    }
}

public class ToolsAndUtilities {
    public static void toolsMenu(Scanner sc) {

        CalculatorTool calculator = new CalculatorTool();
        TemperatureTool temperature = new TemperatureTool();
        TimeTool time = new TimeTool();
        CurrencyTool currency = new CurrencyTool();

        int choice = 0;

        while (choice != 5) {
            System.out.println("\n========= TOOLS & UTILITIES =========");
            System.out.println(" 1. Calculator");
            System.out.println(" 2. Temperature Convertor");
            System.out.println(" 3. Time");
            System.out.println(" 4. Currency Convertor");
            System.out.println(" 5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    calculator.use(sc);
                    break;
                case 2:
                    temperature.use(sc);
                    break;
                case 3:
                    time.use(sc);
                    break;
                case 4:
                    currency.use(sc);
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number from 1 to 5.");
            }
        }
    }
}
