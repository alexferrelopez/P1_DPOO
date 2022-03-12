package Presentation;

import Business.trials.Trial;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UIManager {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints the title header
     */
    private static void printTheTrials () {
        System.out.println("""
                 _____ _            _____      _       _    \s
                /__   \\ |__   ___  /__   \\_ __(_) __ _| |___\s
                  / /\\/ '_ \\ / _ \\   / /\\/ '__| |/ _` | / __|
                 / /  | | | |  __/  / /  | |  | | (_| | \\__ \\
                 \\/   |_| |_|\\___|  \\/   |_|  |_|\\__,_|_|___/
                                                            \s""");
    }

    /**
     * Requests the file to use to the user.
     * @return 1 for CSV, 2 for JSON.
     */
    public int requestFile() {
        do {
            System.out.println("\nThe IEEE needs to know where your allegiance lies.\n");
            System.out.println("\tI) People’s Front of Engineering (CSV)");
            System.out.println("\tII) Engineering People’s Front (JSON)");
            System.out.print("\nPick a faction: ");

            String input = scanner.nextLine();
            if (input.equals("I")) {
                return 1;
            }
            else if (input.equals("II")) {
                return 2;
            }
            System.out.println("\nERROR: The value must be an option on the menu\n");
        } while (true);
    }

    /**
     * Requests role for the execution.
     * @return 1 for composer, 2 for conductor.
     */
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

    /**
     * Requests composer option.
     * @return options: 1 Manage Trials,
     *                  2 Manage Editions,
     *                  3 Close.
     */
    public int requestComposerOp() {
        do {
            System.out.println(
                    """
                            \n\t1) Manage Trials\s
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

    /**
     * Requests Trial option.
     * @return 1 Create Trial
     *         2 List Trials,
     *         3 Delete Trial,
     *         4 Go back.
     */
    public int requestTrialOp() {
        do {
            System.out.println("""
                                                
                    \ta) Create Trial
                    \tb) List Trials\s
                    \tc) Delete Trial\s

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

    /**
     * Requests Edition option.
     * @return 1 Create (or edit) Edition,
     *         2 List Editions,
     *         3 Duplicate Edition,
     *         4 Delete Edition,
     *         5 Back.
     */
    public int requestEditionOp() {
        do {
            System.out.println("""
                            \ta) Create (or edit) Edition
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

    /**
     * Requests Trial type.
     * @return 1 Paper publication,
     *         2 Master studies,
     *         3 Doctoral thesis,
     *         4 Budget request,
     *         5 Incorrect Input.
     */
    public int requestTrialType() {
        do {
            try {
                System.out.println("\n\t--- Trial types ---\n");
                System.out.println("\t 1) Paper publication");
                System.out.println("\t 2) Master studies");
                System.out.println("\t 3) Doctoral thesis");
                System.out.println("\t 4) Budget request\n");

                System.out.print("Enter the trial's type: ");
                int input = Integer.parseInt(scanner.nextLine());

                return switch (input) {
                    case 1 -> 1;
                    case 2 -> 2;
                    case 3 -> 3;
                    case 4 -> 4;
                    default -> 5;
                };

            } catch (Exception e) {
                System.out.println("\nERROR: You have to enter a number");
            }
        }while (true);
    }

    /**
     * Prints message passed.
     * @param message message to print.
     */
    public void showMessage (String message) {
        System.out.println(message);
    }

    /**
     * Prints message passed tabulated.
     * @param message message to print.
     */
    public void showTabulatedMessage(String message) {
        System.out.println("\t" + message);
    }

    /**
     * Prints spacing.
     */
    public void spacing() {
        System.out.println();
    }

    /**
     * Requests for a String to the user.
     * @param message message to request a specific value.
     * @return input string by the user (can be empty)
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Requests for an integer to the user.
     * @param message message to request a specific value.
     * @return number input from the user.
     */
    public int askForInteger(String message) {
        try {
            System.out.print(message);
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR: The input has to be an integer");
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Lists all trials and asks for input for a more detailed view of a single trial in the list.
     * @param trials trial list.
     */
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
                else if (selectTrial != back)
                    System.out.println("Trial does not exist.\n");
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("\nIntroduce a number please\n");
                scanner.nextLine();
            }
        } while (selectTrial != back);
    }

    /**
     * Requests a trial to delete.
     * @param trials list of trials
     * @return index in trial list of the desired trial.
     */
    public int requestDeletedTrial(List<Trial> trials) {

        System.out.println("\nWhich trial do you want to delete?\n");

        int back;

        int selectTrial = 0;
        do {

            back = showList(trials);

            System.out.println("\n\t" + (back) + ") Back");

            try {
                System.out.print("\nEnter an option: ");
                selectTrial = scanner.nextInt();
                scanner.nextLine();
                if (selectTrial != back && selectTrial > 0) {
                    System.out.print("\nEnter the trial's name for confirmation: ");
                    if (scanner.nextLine().trim().toLowerCase(Locale.ROOT).equals(trials.get(selectTrial - 1).getName().trim().toLowerCase(Locale.ROOT))) {
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

    /**
     * Shows all trial names.
     * @param trials list of trials.
     * @return size of list +1.
     */
    public int showList(List<Trial> trials) {
        int back = 0;

        for (int i = 0; i < trials.size(); i++) {
            Trial trial = trials.get(i);
            System.out.println("\t" + (i + 1) + ") " + trial.getName());
            back = i + 2;
        }
        return back;
    }

    /**
     * Requests a percentatge (0,100).
     * @param message message to request a specific value.
     * @return percentatge.
     */
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
