package Presentation;

import Business.Edition;
import Business.Trial;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UIManager {
    Scanner scanner = new Scanner(System.in);
    private static void printTheTrials () {
        System.out.println("""
                 _____ _            _____      _       _    \s
                /__   \\ |__   ___  /__   \\_ __(_) __ _| |___\s
                  / /\\/ '_ \\ / _ \\   / /\\/ '__| |/ _` | / __|
                 / /  | | | |  __/  / /  | |  | | (_| | \\__ \\
                 \\/   |_| |_|\\___|  \\/   |_|  |_|\\__,_|_|___/
                                                            \s""");
    }

    public int requestRole() {
        printTheTrials();
        System.out.print("\nWelcome to The Trials. ");

        do {
            System.out.println("Who are you?\n");
            System.out.println("\tA) The Composer ");
            System.out.println("\tB) This year’s Conductor");
            System.out.print("\nEnter a role: ");

            String input = scanner.nextLine();
            if (input.equals("a") || input.equals("A")) {
                return 1;
            }
            else if (input.equals("b") || input.equals("B")) {
                return 2;
            }
            System.out.println("\nERROR: The value must be an option on the menu\n");
        } while (true);
    }
    public int requestComposerOp() {
        do {
            System.out.println(
                    """
                            \t1) Manage Trials\s
                            \t2) Manage Editions\s

                            \t3) Exit""");
            System.out.print("\nEnter an option: ");

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("\nERROR: The number has to be between 1 to 3\n");
            }
        } while (true);
    }
    public int requestTrialOp() {
        do {
            System.out.println("""
                                                
                    \ta) Create Trial
                    \tb) List Trials\s
                    \tc) Delete Trial Editions\s

                    \td) Back""");

            System.out.print("\nEnter an option: ");

            String input = scanner.nextLine();
            switch (input) {
                case "a":
                    return 1;
                case "b":
                    return 2;
                case "c":
                    return 3;
                case "d":
                    return 4;
                default: System.out.println("ERROR: The letter has to be between a to d\n");
            }
        } while (true);
    }
    public int requestEditionOp() {
        do {
            System.out.println("""
                            \ta) Create Edition
                            \tb) List Editions\s
                            \tc) Duplicate Edition\s
                            \td) Delete Edition\s

                            \te) Back""");

            System.out.print("\nEnter an option: ");

            String input = scanner.nextLine();
            switch (input) {
                case "a":
                    return 1;
                case "b":
                    return 2;
                case "c":
                    return 3;
                case "d":
                    return 4;
                case "e":
                    return 5;
                default: System.out.println("ERROR: The letter has to be between a to e\n");
            }
        } while (true);
    }
    public int requestTrialType() {
        do {
            try {
                System.out.println("\n\t--- Trial types ---\n");
                System.out.println("\t 1) Paper publication\n");
                System.out.print("Enter the trial's type: ");
                int input = Integer.parseInt(scanner.nextLine());

                switch (input) {
                    case 1: return 1;
                    default: System.out.println("\nERROR: Choose between the following options");
                }

            } catch (Exception e) {
                System.out.println("\nERROR: You have to put a number");
            }
        }while (true);
    }

    public void showMessage (String message) {
        System.out.println(message);
    }
    public void showTabulatedMessage(String message) {
        System.out.println("\t" + message);
    }
    public void spacing() {
        System.out.println();
    }

    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    public int askForInteger(String message) {
        try {
            System.out.print(message);
            int input = Integer.parseInt(scanner.nextLine());
            return input;
        } catch (Exception e) {
            System.out.println("ERROR: The input has to be an integer");
        }
        return Integer.MIN_VALUE;
    }

    // TODO and modificate
    public void showTrialList(List<Trial> trials) {

        System.out.println("\nHere are the current trials, do you want to see more details or go back?\n");

        int back;

        int selectTrial = 0;
        do {
            back = showList(trials);

            System.out.println("\n\t" + (back) + ") Back\n");

            try {
                System.out.print("Enter an option: ");
                selectTrial = scanner.nextInt();
                scanner.nextLine();
                if (selectTrial > 0 && selectTrial <= trials.size())
                    System.out.println("\n" + trials.get(selectTrial - 1).toString());
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("\nIntroduce a number please\n");
                scanner.nextLine();
            }
        } while (selectTrial != back);
    }
    public void requestNewTrial() {
        // TODO implement here
    }
    public int requestDeletedTrial(List<Trial> trials) {

        System.out.println("\nWhich edition do you want to delete?\n");

        int back;

        int selectTrial = 0;
        do {

            back = showList(trials);

            System.out.println("\n\t" + (back) + ") Back\n");

            try {
                System.out.print("Enter an option: ");
                selectTrial = scanner.nextInt();
                scanner.nextLine();
                if (selectTrial != back) {
                    System.out.print("\nEnter the trial's name for confirmation: ");
                    if (scanner.nextLine().trim().toLowerCase(Locale.ROOT).equals(trials.get(selectTrial - 1).getName().trim().toLowerCase(Locale.ROOT))) {
                        System.out.println("\nThe trial was successfully deleted.");
                        return selectTrial - 1;
                    } else System.out.println("\nError: the trial was not deleted\n");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("\nIntroduce a number please\n");
                scanner.nextLine();
            }
        } while (selectTrial != back);
        return back;
    }

    public void showTrialTypes() {
        // TODO implement here
    }
    public void showConductor() {
        // TODO implement here
    }

    public int showList(List<Trial> trials) {
        int back = 0;

        for (int i = 0; i < trials.size(); i++) {
            Trial trial = trials.get(i);
            System.out.println("\t" + (i + 1) + ") " + trial.getName());
            back = i + 2;
        }
        return back;
    }

    public int requestTrialNumber(String message) {
        do {
            try {
                System.out.print(message);
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input < 100 && input >= 0) {
                    return input;
                }
                else System.out.println("Number must be in range: 0 =< x < 100.");
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Introduce a number please");
                scanner.nextLine();
            }
        } while (true);
    }
}
