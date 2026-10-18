import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

class KidsModule {

    public static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void showScore(int score, int total) {
        System.out.println("\nYour score: " + score + " / " + total);
        if (score == total) {
            System.out.println("Perfect! You are a superstar! \u2605");
        } else if (score >= total / 2) {
            System.out.println("Great job! Keep going!");
        } else {
            System.out.println("Nice try! Practice makes perfect.");
        }
    }

    public static void numberGuess() {
        int secret = rand.nextInt(20) + 1;
        int attempts = 0;
        int guess;

        System.out.println("\n--- Number Guessing ---");
        System.out.println("I am thinking of a number between 1 and 20.");

        do {
            System.out.print("Your guess: ");
            guess = sc.nextInt();
            sc.nextLine();
            attempts++;

            if (guess < secret) {
                System.out.println("Too low! Try a bigger number.");
            } else if (guess > secret) {
                System.out.println("Too high! Try a smaller number.");
            }
        } while (guess != secret);

        System.out.println("Hooray! You got it in " + attempts + " tries! \uD83C\uDF89");
    }

    public static void mathQuiz() {
        int total = 5;
        int score = 0;

        System.out.println("\n--- Math Quiz ---");
        System.out.println("Answer " + total + " questions!");

        for (int i = 1; i <= total; i++) {
            int a = rand.nextInt(10) + 1;
            int b = rand.nextInt(10) + 1;
            int op = rand.nextInt(3);
            int correct;
            char symbol;

            if (op == 0) {
                correct = a + b;
                symbol = '+';
            } else if (op == 1) {
                if (a < b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                correct = a - b;
                symbol = '-';
            } else {
                correct = a * b;
                symbol = '*';
            }

            System.out.print("Q" + i + ": " + a + " " + symbol + " " + b + " = ");
            int answer = sc.nextInt();
            sc.nextLine();

            if (answer == correct) {
                System.out.println("Correct! \u2713");
                score++;
            } else {
                System.out.println("Not quite. The answer is " + correct + ".");
            }
        }

        showScore(score, total);
    }

    public static void rockPaperScissors() {
        int rounds = 5;
        int wins = 0, losses = 0, draws = 0;

        System.out.println("\n--- Rock Paper Scissors ---");
        System.out.println("Best of " + rounds + " rounds!");

        for (int r = 1; r <= rounds; r++) {
            System.out.println("\nRound " + r);
            System.out.println("1. Rock   2. Paper   3. Scissors");

            System.out.print("Your pick (1-3): ");
            int player = sc.nextInt();
            sc.nextLine();
            while (player < 1 || player > 3) {
                System.out.println("Please pick 1, 2 or 3.");
                System.out.print("Your pick (1-3): ");
                player = sc.nextInt();
                sc.nextLine();
            }
            int computer = rand.nextInt(3) + 1;

            System.out.println("You: " + moveName(player) + " | Computer: " + moveName(computer));

            if (player == computer) {
                System.out.println("It's a draw!");
                draws++;
            } else if ((player == 1 && computer == 3)
                    || (player == 2 && computer == 1)
                    || (player == 3 && computer == 2)) {
                System.out.println("You win this round!");
                wins++;
            } else {
                System.out.println("Computer wins this round.");
                losses++;
            }
        }

        System.out.println("\nWins: " + wins + " | Losses: " + losses + " | Draws: " + draws);
        if (wins > losses) {
            System.out.println("You are the champion! \uD83C\uDFC6");
        } else if (wins < losses) {
            System.out.println("Computer won this time. Try again!");
        } else {
            System.out.println("It's a tie overall!");
        }
    }

    private static String moveName(int move) {
        switch (move) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
            default:
                return "Unknown";
        }
    }

    public static void wordScramble() {
        String[] words = {"apple", "tiger", "banana", "elephant", "school",
                          "monkey", "orange", "rabbit", "garden", "pencil"};
        String word = words[rand.nextInt(words.length)];
        String scrambled = shuffleWord(word);

        System.out.println("\n--- Word Scramble ---");
        System.out.println("Unscramble this word: " + scrambled.toUpperCase());

        int maxTries = 3;
        for (int t = 1; t <= maxTries; t++) {
            System.out.print("Your answer (" + t + "/" + maxTries + "): ");
            String guess = sc.nextLine().trim();

            if (guess.equalsIgnoreCase(word)) {
                System.out.println("Awesome! The word is " + word + ". \uD83C\uDF89");
                return;
            }
            System.out.println("Not quite, try again!");
        }

        System.out.println("The correct word was: " + word);
    }

    private static String shuffleWord(String word) {
        char[] letters = word.toCharArray();
        String result;

        do {
            for (int i = letters.length - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                char temp = letters[i];
                letters[i] = letters[j];
                letters[j] = temp;
            }
            result = new String(letters);
        } while (result.equals(word));

        return result;
    }

    public static void oddOneOut() {
        ArrayList<String[]> questions = new ArrayList<>();
        questions.add(new String[]{"Dog", "Cat", "Cow", "Car", "Car"});
        questions.add(new String[]{"Apple", "Banana", "Mango", "Chair", "Chair"});
        questions.add(new String[]{"Red", "Blue", "Green", "Table", "Table"});
        questions.add(new String[]{"Sparrow", "Eagle", "Parrot", "Fish", "Fish"});
        questions.add(new String[]{"Pen", "Pencil", "Eraser", "Banana", "Banana"});

        Collections.shuffle(questions);

        int total = 3;
        int score = 0;

        System.out.println("\n--- Odd One Out ---");
        System.out.println("Type the word that does not belong.");

        for (int i = 0; i < total; i++) {
            String[] q = questions.get(i);
            String odd = q[4];

            ArrayList<String> options = new ArrayList<>();
            for (int k = 0; k < 4; k++) {
                options.add(q[k]);
            }
            Collections.shuffle(options);

            System.out.println("\nQ" + (i + 1) + ": " + String.join(", ", options));
            System.out.print("Odd one out: ");
            String answer = sc.nextLine().trim();

            if (answer.equalsIgnoreCase(odd)) {
                System.out.println("Correct! \u2713");
                score++;
            } else {
                System.out.println("Not quite. The odd one was " + odd + ".");
            }
        }

        showScore(score, total);
    }
}