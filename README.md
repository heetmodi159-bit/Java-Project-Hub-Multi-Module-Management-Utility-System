# ☕ Java Project Hub – Multi-Application Console System

A collection of **7 Java console applications** integrated into one menu-driven system. This project demonstrates Object-Oriented Programming (OOP), modular design, input validation, collections, date and time handling, and practical problem-solving using Java.

## 🚀 Applications Included

| No. | Application | Key Features |
|---:|---|---|
| 1 | 🚗 **Advance Parking Booking App** | Advance bookings, slot availability, check-in/check-out, cancellation, fare calculation, and parking tickets |
| 2 | 🛒 **Shopping Management App** | Product browsing, user registration/login, shopping cart, quantities, and demo payment methods |
| 3 | ⌨️ **Typing Speed Analyzer** | Typing tests, accuracy, error detection, words per minute (WPM), rankings, and high scores |
| 4 | 📇 **Contact Management** | Add, remove, update, search, view, and simulate calling contacts |
| 5 | 📝 **To-Do List Application** | Add, view, update, delete, complete, and search tasks |
| 6 | 🎮 **Kids Games** | Number Guessing, Math Quiz, Rock Paper Scissors, Word Scramble, and Odd One Out |
| 7 | 🧮 **Tools & Utilities** | Calculator, temperature converter, date/time utilities, stopwatch, and currency converter |

---

## 📌 1. Advance Parking Booking App

A parking management system for booking vehicle parking slots in advance and managing the complete parking process.

### Features

- Register and log in during the program session.
- Book available car or bike parking slots.
- Check availability based on entry and exit date/time.
- Maintain multiple bookings with unique booking IDs.
- Cancel eligible bookings while retaining their history.
- Manage booking statuses: `BOOKED`, `ACTIVE`, `COMPLETED`, and `CANCELLED`.
- Record actual check-in and check-out times.
- Calculate parking charges and generate tickets.
- View booking history and individual booking tickets.

### Current Parking Fare Rules

| Component | Fare |
|---|---:|
| Car parking | ₹50 |
| Bike parking | ₹30 |
| Advance booking | ₹50 |
| Parking duration exceeding 4 hours | One additional vehicle parking fare |

The actual final amount depends on how the booking and payment logic applies these charges.

### Main Files

`Customer.java`, `Vehicle.java`, `ParkingSlot.java`, `Booking.java`, `BookingRecord.java`, `Payment.java`, `ParkingTicket.java`, and the parking menu class.

---

## 🛒 2. Shopping Management App

A console-based shopping application that demonstrates product selection, cart management, and payment workflows.

### Features

- User registration and login.
- Display available products.
- Search for products by name.
- Select products and quantities.
- Add products to the cart.
- Manage or cancel cart selections.
- Choose between Cash on Delivery and demonstration online payment.
- Display the final shopping bill.

### Main Files

`Main_Shopping.java`, `Product.java`, `User.java`, `Cart.java`, `CartItem.java`, `PaymentMethod.java`, `CashOnDelivery.java`, and `OnlinePayment.java`.

*Payment options are educational demonstrations and do not process real transactions.*

---

## ⌨️ 3. Typing Speed Analyzer

A typing practice application that measures typing performance through timed rounds.

### Features

- Three difficulty levels: Easy, Medium, and Hard.
- Random sentence selection.
- Countdown before each round.
- Typing time measurement.
- Detection of incorrect and extra characters.
- Accuracy calculation.
- Words-per-minute (WPM) calculation.
- Performance rankings: Beginner, Average, Good, and Pro.
- Three-round average score and best score.
- Replay option and session high score.

### Main File

`TypingSpeed.java`

---

## 📇 4. Contact Management

A simple contact book that manages personal contact information.

### Features

- Add a contact with name, phone number, and email.
- Remove contacts after confirmation.
- Update existing contact information.
- Search by name or phone number.
- Display complete contact details.
- Simulate calling a saved contact.

### Main File

`ContactManagement.java`

### Supporting Classes

`Contact` and `ContactBook`.

*Calling is simulated in the console; the application does not make real phone calls.*

---

## 📝 5. To-Do List Application

A task management application for organizing daily activities.

### Features

- Add new tasks.
- View all tasks.
- Update task details.
- Delete tasks.
- Mark tasks as completed.
- Search for tasks.
- Navigate back to the main application menu.

### Main Files

`TodoApp.java` and `TaskManager.java`.

---

## 🎮 6. Kids Games

An interactive collection of small games designed for entertainment and basic logical thinking.

### Games Included

1. **Number Guessing** – Guess the hidden number.
2. **Math Quiz** – Solve mathematical questions.
3. **Rock Paper Scissors** – Play against the computer.
4. **Word Scramble** – Identify a word from scrambled letters.
5. **Odd One Out** – Find the item that does not belong.

### Main Files

`KidsMenu.java` and `KidsModule.java`.

---

## 🧮 7. Tools & Utilities

A collection of useful mathematical and everyday conversion tools.

### Calculator

- Addition, subtraction, multiplication, and division.
- Modulus/remainder.
- Square root and cube root.
- Power/exponent.
- Square and cube.
- Percentage.
- Factorial for supported whole numbers.

### Temperature Converter

- Celsius to Fahrenheit.
- Fahrenheit to Celsius.
- Celsius to Kelvin.
- Kelvin to Celsius.
- Fahrenheit to Kelvin.
- Kelvin to Fahrenheit.

### Date and Time Utilities

- Display current date and time.
- Calculate dates before or after a given number of days.
- Measure elapsed time using a stopwatch.

### Currency Converter

- INR to USD and USD to INR.
- INR to EUR and EUR to INR.
- INR to GBP and GBP to INR.

The currency converter uses predefined approximate exchange rates, which may not reflect current market rates.

### Main File

`ToolsAndUtilities.java`

### Supporting Classes

`CalculatorTool`, `TemperatureTool`, `TimeTool`, and `CurrencyTool`.

---

## 🛠️ Technologies and Concepts

- **Language:** Java
- **Interface:** Console / Command Line
- **Core Concepts:** Classes, Objects, Encapsulation, Methods, Constructors, Collections, Loops, Conditional Statements, and Exception Handling
- **Other Concepts:** Date/time operations, input validation, string handling, randomization, and menu navigation
- **Storage:** In-memory data in modules that have not been connected to persistent storage

## 📂 Example Project Structure

```text
Java-Project-Hub/
│
├── Advance_Parking_System/
│   ├── Main.java
│   ├── Parking_Management_System.java
│   ├── Customer.java
│   ├── Vehicle.java
│   ├── ParkingSlot.java
│   ├── Booking.java
│   ├── BookingRecord.java
│   ├── Payment.java
│   ├── ParkingTicket.java
│   ├── KidsMenu.java
│   ├── KidsModule.java
│   ├── TodoApp.java
│   ├── TaskManager.java
│   ├── TypingSpeed.java
│   ├── ToolsAndUtilities.java
│   └── ContactManagement.java
│
├── Shopping_App/
│   ├── Main_Shopping.java
│   ├── Product.java
│   ├── User.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── PaymentMethod.java
│   ├── CashOnDelivery.java
│   └── OnlinePayment.java
│
└── README.md
```

*This is an example structure. Adjust it to match your actual source folders, packages, and supporting files.*

## ⚙️ Requirements

- Java Development Kit (JDK)
- IntelliJ IDEA, Eclipse, VS Code, or another Java-compatible IDE
- Command Prompt or Terminal for command-line compilation and execution

## ▶️ How to Compile and Run

### Option 1: Run Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Ensure all source files are in their correct packages.
3. Open `Main.java` inside `Advance_Parking_System`.
4. Click **Run**.
5. Select an application from the main menu.

### Option 2: Compile Using the Terminal

If your Java package directories are directly inside the project root, run:

```bash
javac Advance_Parking_System/*.java Shopping_App/*.java
```

Then run the main application:

```bash
java Advance_Parking_System.Main
```

If your source code is inside a `src` directory, use your IDE's configured source root or adjust the compile command accordingly.

## 🔄 Main Menu Workflow

When the application starts, the main menu allows users to open any of the seven modules:

```text
========================================
       MULTI-MODULE MANAGEMENT SYSTEM
========================================
1. Advance Parking Management System
2. Kids Games Menu
3. To-Do List Application
4. Shopping Management App
5. Typing Speed Analyzer
6. Tools & Utilities
7. Contact Management
8. Exit Application
========================================
Enter Your Choice:
```

After completing a module, the user can return to the main menu and select another application.

## ⚠️ Limitations

- **In-memory storage:** Data may be lost when the application closes if a module does not use persistent storage.
- **Session-based accounts:** Registration and login are not persistent unless database storage is implemented.
- **Demo payments:** Shopping payment methods do not process real financial transactions.
- **Simulated calling:** Contact Management does not make real phone calls.
- **Approximate exchange rates:** Currency conversion uses predefined rates.
- **System time:** Parking check-in/check-out and time utilities rely on the computer's clock.
- **Security:** Production applications should use secure password hashing, validation, and appropriate data storage.

## 🚀 Future Enhancements

- [ ] Integrate MySQL for persistent storage.
- [ ] Add secure user authentication and password hashing.
- [ ] Improve exception handling and input validation.
- [ ] Add automated unit tests.
- [ ] Implement persistent contact, task, and shopping records.
- [ ] Add a real payment gateway to the shopping application.
- [ ] Create a graphical user interface.
- [ ] Add administrator controls to the parking system.
- [ ] Improve reporting and data export features.

## 🎯 Learning Outcomes

Through this project, developers can practise:

- Building modular Java applications.
- Applying object-oriented programming principles.
- Organizing classes and packages.
- Using collections to manage records.
- Validating user input.
- Working with dates, times, and durations.
- Implementing business rules and calculations.
- Connecting multiple console applications through a central menu.

## 👨‍💻 Author

**Java Project Hub – Multi-Application Console System**

An educational project developed to practise Java programming, object-oriented design, and the integration of multiple applications into one console-based system.

---

⭐ **If you find this project useful, consider giving the repository a star on GitHub!**
