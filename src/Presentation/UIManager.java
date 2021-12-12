package Presentation;

import Business.Edition;
import Business.Trial;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UIManager {
    Scanner scanner = new Scanner(System.in);

    public int requestRole() {
        //printMenu1();       //small menu
        printMenu2();       //big cool menu

        do {
            System.out.println("\nWelcome to The Trials. Who are you?");
            System.out.println("\n\tA) The Composer ");
            System.out.println("\tB) This year’s Conductor");
            System.out.print("\nEnter a role: ");

            String input = scanner.nextLine();
            if (input.equals("a") || input.equals("A")) {
                return 1;
            }
            else if (input.equals("b") || input.equals("B")) {
                return 2;
            }
        } while (true);
    }

    public int requestComposerOp() {
        do {
            System.out.println("\nEntering management mode... \n" +
                    "\n\t1) Manage Trials " +
                    "\n\t2) Manage Editions " +
                    "\n\n\t3) Exit");
            System.out.print("\nEnter an option: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    System.out.println("\nShutting down...");
                    return 3;
            }
        } while (true);
    }

    public int requestTrialOp() {
        do {
            System.out.println("""
                            \n\ta) Create Trial
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
            }
        } while (true);
    }

    public void requestNewTrial() {
        // TODO implement here
    }

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

    public Edition requestNewEdition() {
        // TODO implement here
        return null;
    }

    public void showEditionList(List<Edition> editions) {
        // TODO implement here
    }

    public Edition duplicateEdition() {
        // TODO implement here
        return null;
    }

    public int requestDeletedEdition() {
        // TODO implement here
        return 0;
    }

    public void showTrialTypes() {
        // TODO implement here
    }

    public void showEdition() {
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

    private static void printMenu1 () {
        System.out.println("""
                   _________ _            _______      _       _
                  /__   __ \\ |__   ___   /__  __ \\_ __(_) __ _| |___
                \t / /  \\/  _ \\ / _ \\    / /  \\/ '__| |/ _` | / __|
                \t/ /    | | | | ___/   / /     | | | | (_| | \\__ \\
                \t\\/     |_| |_|\\___|   \\/      |_| |_|\\__,_|_|___/""");
    }

    public void showMessage (String message) {
        System.out.println(message);
    }

    private static void printMenu2 () {
        System.out.println("""

                __/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\___________________________________/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_____________________________________/\\\\\\\\\\\\_________________       \s
                 _\\///////\\\\\\/////__\\/\\\\\\__________________________________\\///////\\\\\\/////_____________________________________\\////\\\\\\_________________      \s
                  _______\\/\\\\\\_______\\/\\\\\\________________________________________\\/\\\\\\______________________/\\\\\\___________________\\/\\\\\\_________________     \s
                   _______\\/\\\\\\_______\\/\\\\\\_____________/\\\\\\\\\\\\\\\\__________________\\/\\\\\\________/\\\\/\\\\\\\\\\\\\\__\\///___/\\\\\\\\\\\\\\\\\\_______\\/\\\\\\_____/\\\\\\\\\\\\\\\\\\\\_    \s
                    _______\\/\\\\\\_______\\/\\\\\\\\\\\\\\\\\\\\____/\\\\\\/////\\\\\\_________________\\/\\\\\\_______\\/\\\\\\/////\\\\\\__/\\\\\\_\\////////\\\\\\______\\/\\\\\\____\\/\\\\\\//////__   \s
                     _______\\/\\\\\\_______\\/\\\\\\/////\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\__________________\\/\\\\\\_______\\/\\\\\\___\\///__\\/\\\\\\___/\\\\\\\\\\\\\\\\\\\\_____\\/\\\\\\____\\/\\\\\\\\\\\\\\\\\\\\_  \s
                      _______\\/\\\\\\_______\\/\\\\\\___\\/\\\\\\_\\//\\\\///////___________________\\/\\\\\\_______\\/\\\\\\_________\\/\\\\\\__/\\\\\\/////\\\\\\_____\\/\\\\\\____\\////////\\\\\\_ \s
                       _______\\/\\\\\\_______\\/\\\\\\___\\/\\\\\\__\\//\\\\\\\\\\\\\\\\\\\\_________________\\/\\\\\\_______\\/\\\\\\_________\\/\\\\\\_\\//\\\\\\\\\\\\\\\\/\\\\__/\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\\\_\s
                        _______\\///________\\///____\\///____\\//////////__________________\\///________\\///__________\\///___\\////////\\//__\\/////////__\\//////////__
                """);
    }

    public int requestTrialType() {
        do {
            System.out.println("\n\t--- Trial types ---\n");
            System.out.println("\t 1) Paper publication\n");
            System.out.print("Enter the trial's type: ");
            String input = scanner.nextLine();
            if(input.equals("1")) {
                System.out.println("");
                return 1;
            }
            else System.out.println("Only 1 type avaliable.");
        }while (true);


    }

    public String requestTrialString(String message) {
        System.out.print(message);
        return scanner.nextLine();
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
        }while (true);
    }
}
