class KidsMenu {

    public static void main(String[] args) {
        kidsMenu();
    }

    public static void kidsMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== FOR KIDS =====");
            System.out.println("1. Number Guessing");
            System.out.println("2. Math Quiz");
            System.out.println("3. Rock Paper Scissors");
            System.out.println("4. Word Scramble");
            System.out.println("5. Odd One Out");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice (1-6): ");
            int choice = KidsModule.sc.nextInt();
            KidsModule.sc.nextLine();

            switch (choice) {
                case 1:
                    KidsModule.numberGuess();
                    break;
                case 2:
                    KidsModule.mathQuiz();
                    break;
                case 3:
                    KidsModule.rockPaperScissors();
                    break;
                case 4:
                    KidsModule.wordScramble();
                    break;
                case 5:
                    KidsModule.oddOneOut();
                    break;
                case 6:
                    System.out.println("Going back to Main Menu...");
                    return;
                default:
                    System.out.println("Oops! Please choose a number from 1 to 6.");
                    continue;
            }
        }

        System.out.println("Thanks for playing! Returning to Main Menu...");
    }
}