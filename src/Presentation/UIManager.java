package Presentation;

import Business.Edition;
import Business.Trial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UIManager {
    Scanner scanner = new Scanner(System.in);

    public int requestRole() {
        printMenu1();             //small menu
        printMenu2();                //big cool menu

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
        // TODO implement here
        return 0;
    }

    public int requestTrialOp() {
        // TODO implement here
        return 0;
    }

    public void requestNewTrial() {
        // TODO implement here
    }

    public void showTrialList(List<Trial> trials) {
        // TODO implement here
    }

    public int requestDeletedTrial() {
        // TODO implement here
        return 0;
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

    public void showMessage(String content) {
        // TODO implement here
    }

    public void showList(ArrayList<String> list) {
        // TODO implement here
    }

    public static void printMenu1 () {
        System.out.println("""
                   _________ _            _______      _       _
                  /__   __ \\ |__   ___   /__  __ \\_ __(_) __ _| |___
                \t / /  \\/  _ \\ / _ \\    / /  \\/ '__| |/ _` | / __|
                \t/ /    | | | | ___/   / /     | | | | (_| | \\__ \\
                \t\\/     |_| |_|\\___|   \\/      |_| |_|\\__,_|_|___/""");
    }

    public static void printMenu2 () {
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

}
