package OS_System;

import java.util.Random;
import java.util.Scanner;

public class TypingSpeed {

    static String[] easy = {
        "The sun is hot today.",
        "I like to eat apples and mangoes.",
        "My dog runs fast in the park.",
        "Please close the door slowly.",
        "We play cricket every Sunday.",
        "The cat sleeps on the soft sofa."
    };

    static String[] medium = {
        "Big Black Beetle Bite Big Black Bear!",
        "She sells sea shells by the sea shore!",
        "Peter Piper picked a peck of pickled peppers.",
        "Red lorry, yellow lorry, red lorry, yellow lorry.",
        "I scream, you scream, we all scream for ice cream!",
        "A proper copper coffee pot is hard to find."
    };

    static String[] hard = {
        "Order #4521 will arrive on 15th March at 9:30 AM, so please don't be late!",
        "The quick brown fox jumps over the lazy dog 25 times every morning.",
        "Java was released in 1995, and it is still used to build apps in 2026.",
        "Call 98765-43210 before 6:00 PM to get a 20% discount on all items.",
        "Programming is 10% writing code and 90% understanding why it doesn't work.",
        "Mr. Sharma paid Rs. 2,500 for 4 books, 3 pens and 1 bag on 12/08/2025."
    };

    static String getRank(double wpm) {
        if (wpm >= 60) {
            return "Pro";
        } else if (wpm >= 40) {
            return "Good";
        } else if (wpm >= 20) {
            return "Average";
        } else {
            return "Beginner";
        }
    }

    static void countdown() throws InterruptedException {
        System.out.println("Get ready...");
        for (int i = 3; i >= 1; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
        System.out.println("GO!");
    }

    // Prints ^ under every wrong or extra letter
    static void showMistakes(String original, String typed) {
        System.out.println("Sentence  : " + original);
        System.out.println("You typed : " + typed);
        System.out.print("Mistakes  : ");
        for (int i = 0; i < typed.length(); i++) {
            if (i < original.length() && typed.charAt(i) == original.charAt(i)) {
                System.out.print(" ");
            } else {
                System.out.print("^");
            }
        }
        System.out.println();
    }

    // Plays one round and returns the WPM (0 if the round is not counted)
    static double playRound(Scanner sc, String original, int timeLimit) {
        System.out.println();
        System.out.println("===================================");
        System.out.println(original);
        System.out.println("===================================");
        System.out.print("Type here! --> ");

        long startTime = System.nanoTime();
        String typed = sc.nextLine();
        long endTime = System.nanoTime();

        double timeTaken = (endTime - startTime) / 1_000_000_000.0;
        int words = original.split(" ").length;
        int maxLength = Math.max(original.length(), typed.length());
        int errors = 0;

        for (int i = 0; i < maxLength; i++) {
            if (i >= original.length() || i >= typed.length() || original.charAt(i) != typed.charAt(i)) {
                errors++;
            }
        }

        double accuracy = ((double) (maxLength - errors) / maxLength) * 100;
        double wpm = (words / timeTaken) * 60;

        System.out.println();
        showMistakes(original, typed);
        System.out.printf("Time Taken : %.2f seconds%n", timeTaken);
        System.out.println("Words      : " + words);
        System.out.println("Errors     : " + errors);
        System.out.printf("Accuracy   : %.2f%%%n", accuracy);

        if (timeTaken > timeLimit) {
            System.out.println("Time's up! No score for this round.");
            return 0;
        }
        if (accuracy < 50) {
            System.out.println("Too many mistakes! No score for this round.");
            return 0;
        }

        System.out.printf("WPM        : %.2f%n", wpm);
        System.out.println("Rank       : " + getRank(wpm));
        return wpm;
    }

    public static void typingMenu(Scanner sc) throws InterruptedException {

        Random random = new Random();
        double highScore = 0;
        String again = "yes";

        System.out.println("+==============================================+");
        System.out.println("|  >> SYSTEM INITIALIZED                       |");
        System.out.println("|  >> WRITE & SPEED                            |");
        System.out.println("|  >> TYPING SPEED ANALYZER v1.0               |");
        System.out.println("|  >> READY TO TEST YOUR SKILLS...             |");
        System.out.println("+==============================================+");

        while (again.equalsIgnoreCase("yes")) {

            System.out.print("\nEnter your name : ");
            String name = sc.nextLine();

            System.out.println("1. Easy   2. Medium   3. Hard");
            System.out.print("Select level : ");
            String level = sc.nextLine().trim();

            String[] sentences = easy;
            int timeLimit = 20;

            if (level.equals("2")) {
                sentences = medium;
                timeLimit = 30;
            } else if (level.equals("3")) {
                sentences = hard;
                timeLimit = 40;
            }

            int start = random.nextInt(sentences.length);
            double total = 0;
            double best = 0;

            for (int round = 1; round <= 3; round++) {
                System.out.println("\n##### ROUND " + round + " of 3 #####");
                countdown();

                double wpm = playRound(sc, sentences[(start + round) % sentences.length], timeLimit);

                total = total + wpm;
                if (wpm > best) {
                    best = wpm;
                }
            }

            double average = total / 3;

            System.out.println("\n===== FINAL RESULT =====");
            System.out.println("Player      : " + name);
            System.out.printf("Average WPM : %.2f%n", average);
            System.out.printf("Best WPM    : %.2f%n", best);
            System.out.println("Rank        : " + getRank(average));

            if (average > highScore) {
                highScore = average;
                System.out.println("*** NEW HIGH SCORE! ***");
            }

            System.out.print("\nPlay again? (yes/no) : ");
            again = sc.nextLine().trim();
        }

        System.out.println("Thank you for playing! Goodbye!");
        sc.close();
    }
}
