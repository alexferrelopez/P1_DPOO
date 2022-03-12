package Presentation;

import Business.BusinessManager;

import java.io.IOException;
import java.util.*;

public class Controller {
    private final UIManager uiManager = new UIManager();
    private final BusinessManager bm = new BusinessManager();

    public void run () {
        //requestFile (it plays before requesting a role)
        switch (uiManager.requestFile()) {
            case 1 -> {
                try {
                    loadCsv();
                } catch (IOException e) {
                    uiManager.showMessage("One or more Json files do not exist.");
                }
            }
            case 2 -> {
                try {
                    loadJson();
                } catch (IOException e) {
                    uiManager.showMessage("One or more Json files do not exist.");
                }
            }
        }

        switch (uiManager.requestRole()) {
            case 1 -> executeComposer();
            case 2 -> executeConductor();
        }

        bm.saveData();
        uiManager.showMessage("\nShutting down...");
    }

    private void loadCsv() throws IOException {
        bm.loadFromCsv();
    }

    private void loadJson() throws IOException {
        bm.loadFromJson();
    }

    public void executeConductor() {
        uiManager.spacing();
        uiManager.showMessage("Entering execution mode...\n");

        if (bm.editionYearExists()) {
            uiManager.showMessage("--- The Trials 2022 ---\n");
                if (bm.getCheckpoint() == 0) {
                    requestPlayerNames();
                }
                boolean stop = false;
                for (int i = 0; i < bm.getEditionNumTrials() && !stop; i++) {

                    System.out.print(bm.executeTrial());

                    if (bm.getRemainingPlayers() != 0) {
                        stop = requestContinuation();
                    } else stop = true;
                }
                uiManager.spacing();

            //} else uiManager.showMessage("No players in this edition.");
        } else {
            uiManager.showMessage("No edition is defined for the current year ("+ Calendar.getInstance().get(Calendar.YEAR) +").");
        }
    }

    private boolean requestContinuation() {
        boolean stop = false;
        String goNext = uiManager.askForString("Continue the execution? [yes/no]: ").toLowerCase(Locale.ROOT).trim();
        do {
            if (!goNext.equals("yes")) {
                if (goNext.equals("no")) {
                    stop = true;
                } else {
                    uiManager.showMessage("\nWrong input. Options are: \"yes\" or \"no\"\n");
                    goNext = uiManager.askForString("Continue the execution? [yes/no]: ").toLowerCase(Locale.ROOT).trim();
                }
            }
        } while (!goNext.equals("yes") && !(goNext.equals("no")));
        return stop;
    }

    private void requestPlayerNames() {
        for (int i = 0; i < bm.getEditionNumPlayers(); i++) {
            String playerName;
            do {
                playerName = uiManager.askForString("Enter the player’s name (" + (i + 1) + "/" + bm.getEditionNumPlayers() + "): ");
            } while (playerName.isEmpty());
            bm.addNewPLayer(playerName);
        }
    }

    public void executeComposer() {
        int optionComposer;
        uiManager.showMessage("\nEntering management mode...");
        do {
            optionComposer = uiManager.requestComposerOp();
            switch (optionComposer) {
                case 1 -> executeTrialTypes();
                case 2 -> executeEdition();
                case 3 -> {}
            }
        } while (optionComposer!= 3);
    }

    public void executeTrialTypes() {
        int optionTrial;
        do {
            optionTrial = uiManager.requestTrialOp();
            switch (optionTrial) {
                case 1: manageTrial();
                    break;
                case 2: listTrial();
                    break;
                case 3: deleteTrial();
                    break;
                case 4: break;
                default: uiManager.showMessage("ERROR: The number has to be between 1 to 4\n");
            }
        } while(optionTrial != 4);
    }
    public void manageTrial() {
        int type;

        type = uiManager.requestTrialType();

        if (type < 1 || type > 4) {
            uiManager.spacing();
            uiManager.showMessage("Trial does not exist");
            return;
        }

        int acceptance = 0;
        int revision = 0;
        int rejection = 0;
        String nameJournal = null;
        String quartile = null;
        String other = null;
        int probabilitat = 0;
        int credits = 0;
        int difficulty = 0;
        int budget= 0;

        String trialName;
        trialName = uiManager.askForString("Enter the trial's name: ");

        if (trialName.isEmpty() || bm.trialNameAlreadyExists(trialName)) {
            uiManager.spacing();
            uiManager.showMessage("Trial name can't be used by any other trial and can't be empty.");
            return;
        }

        switch (type) {
            case 1 -> {
                nameJournal = uiManager.askForString("Enter the journal's name: ");

                if (nameJournal.isEmpty()) {
                    uiManager.spacing();
                    uiManager.showMessage("Journal name can't be empty.");
                    return;
                }


                quartile = uiManager.askForString("Enter the journal’s quartile (Q1, Q2, Q3, Q4): ").toUpperCase(Locale.ROOT);

                if (!(quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4"))) {
                    uiManager.spacing();
                    uiManager.showMessage("Quartile does not exist");
                    return;
                }

                acceptance = uiManager.requestTrialNumber("Enter the acceptance probability: ");
                revision = uiManager.requestTrialNumber("Enter the revision probability: ");
                rejection = uiManager.requestTrialNumber("Enter the rejection probability: ");

                if (acceptance + rejection + revision != 100) {
                    uiManager.spacing();
                    uiManager.showMessage("Percentatges don't add up to 100.");
                    return;
                }
            }
            case 2 -> {

                other = uiManager.askForString("Enter the master’s name: ");

                if (other.isEmpty()) {
                    uiManager.spacing();
                    uiManager.showMessage("Master's name can't be empty.");
                    return;
                }

                credits = uiManager.askForInteger("Enter the master’s ECTS number (credits range is [60, 120]): ");

                if (credits<60 || credits>120) {
                    uiManager.spacing();
                    uiManager.showMessage("Credit number value is not inside the specified range.");
                    return;
                }

                probabilitat = uiManager.askForInteger("Enter the credit pass probability (probability range is [0, 100]): ");


                if (probabilitat<0 || probabilitat>100) {
                    uiManager.spacing();
                    uiManager.showMessage("Probability value is not inside the specified range.");
                    return;
                }
            }
            case 3 -> {
                other = uiManager.askForString("Enter the thesis field of study: ");

                if (other.isEmpty()) {
                    uiManager.spacing();
                    uiManager.showMessage("Field of study name can't be empty.");
                    return;
                }

                difficulty = uiManager.askForInteger("Enter the defense difficulty (budget range is [1, 10]): ");

                if (difficulty < 1 || difficulty > 10) {
                    uiManager.showMessage("Defense difficulty value is not inside the specified range");
                    return;
                }
            }
            case 4 -> {

                other = uiManager.askForString("Enter the entity’s name: ");

                if (other.isEmpty()) {
                    uiManager.spacing();
                    uiManager.showMessage("Entity name can't be empty.");
                    return;
                }

                budget = uiManager.askForInteger("Enter the budget amount (budget range is [1000, 2000000000]): ");

                if (budget < 1000 || budget > 2000000000) {
                    uiManager.spacing();
                    uiManager.showMessage("Budget value is not inside the specified range");
                    return;
                }
            }
        }

        bm.createTrial(type, trialName, other, acceptance, revision, rejection, nameJournal, quartile , probabilitat, credits, difficulty,budget);

        uiManager.showMessage("\nThe trial was created successfully!");
    }
    public void listTrial() {
        uiManager.showTrialList(bm.getTrials());
    }

    public void deleteTrial() {

        int index = uiManager.requestDeletedTrial(bm.getTrials());

        boolean done = false;

        if (index < bm.trialListLength()) {
            done = bm.deleteTrial(index);
        }

        uiManager.spacing();
        if (done) {
            uiManager.showMessage("The trial was successfully deleted.");
        }
        else if (index != bm.trialListLength()){
            uiManager.showMessage("Trial is being used by one more editions.");
        }
    }

    public void executeEdition() {
        do {
            uiManager.spacing();
            switch (uiManager.requestEditionOp()) {
                case 1 -> {
                    if (bm.trialListLength() != 0)
                        createEdition();        //create
                    else uiManager.showMessage("\nNo trials created yet.");
                }
                case 2 -> listEdition();        //list
                case 3 -> duplicateEdition();   //duplicate
                case 4 -> deleteEdition();      //delete
                case 5 -> {
                    uiManager.spacing();        //back
                    return;
                }
            }
        }while (true);
    }
    public void createEdition() {
        int year, players, trials;
        List<Integer> trialList = new ArrayList<>();
        Date date = new Date();
        uiManager.spacing();
        year = getYear(date);
        do {
            players = uiManager.askForInteger("Enter the initial number of players: ");
            if ((players > 6 || players < 1)) {
                uiManager.showMessage("ERROR: The number of players has to be between 1 and 5");
            }
        } while (players > 6 || players < 1);
        do {
            trials = uiManager.askForInteger("Enter the number of trials: ");
            if ((trials < 3 || trials > 12)) {
                uiManager.showMessage("ERROR: The number of trials has to be between 3 and 12");
            }
        } while(trials < 3 || trials > 12);

        uiManager.spacing();
        uiManager.showMessage("\t--- Trials ---");
        uiManager.spacing();
        uiManager.showList(bm.getTrials());
        uiManager.spacing();

        for (int i = 0; i < trials; i++) {
            int trialNum;
            do {
                trialNum = uiManager.askForInteger("Pick a trial (" + (i+1) + "/" + trials + "): ")-1;
                if (trialNum < 0 || trialNum > bm.trialListLength()-1) {
                    uiManager.showMessage("Error: Trial number has to be between "+ 1 + " and " + bm.trialListLength() + ".");
                }
            } while (trialNum < 0 || trialNum > bm.trialListLength()-1);
            trialList.add(trialNum);
        }

        uiManager.spacing();

        bm.createEdition(trialList,year, players);

        uiManager.showMessage("The editions was created successfully!");
    }

    private int getYear(Date date) {
        int year;
        do {
            year = uiManager.askForInteger("Enter the edition's year: ");
            if (year < BusinessManager.systemYear) {
                uiManager.showMessage("ERROR: The year has to be for current or future events");
            }
        } while(year < BusinessManager.systemYear);
        return year;
    }

    private void listAllEditions() {
        bm.sortEditionsByYear();
        for (int i = 0; i < bm.editionListLength(); i++) {
            uiManager.showTabulatedMessage((i+1)+") The Trials " + bm.getEditions().get(i).getYear());
        }
        uiManager.spacing();
        uiManager.showTabulatedMessage(bm.editionListLength()+1+") Back");
        uiManager.spacing();
    }

    public void listEdition() {
        if (bm.editionListLength() > 0 ) {
            uiManager.spacing();
            uiManager.showMessage("Here are the current editions, do you want to see more details or go back?");
            uiManager.spacing();
            listAllEditions();

            int option = uiManager.askForInteger("Enter an option: ");
            if (option > 0 && option < bm.editionListLength() + 1) {
                uiManager.spacing();
                System.out.println(bm.EditionToString(option - 1));
            } else {
                if (option != bm.editionListLength() + 1) {
                    uiManager.spacing();
                    uiManager.showMessage("ERROR: You must put a value between 1 and " + (bm.editionListLength() + 1));
                }
            }
        }
        else  {
            uiManager.spacing();
            uiManager.showMessage("There are no editions to show.");
        }
    }

    public void duplicateEdition() {
        uiManager.spacing();
        uiManager.showMessage("Which edition do you want to clone? ");
        uiManager.spacing();
        listAllEditions();
        int option = uiManager.askForInteger("Enter an option: ");
        if (option > 0 && option < bm.editionListLength()+1) {
            uiManager.spacing();
            int year, players;
            Date date = new Date();
            year = getYear(date);
            do {
                players = uiManager.askForInteger("Enter the initial number of players: ");
                if ((players > 6 || players < 1)) {
                    uiManager.showMessage("ERROR: The number of players has to be between 1 and 5");
                }
            } while (players > 6 || players < 1);

            boolean correctCreation = bm.duplicateEdition(option - 1, year, players);

            uiManager.spacing();

            if (correctCreation) {
                uiManager.showMessage("The edition was cloned successfully!");
            } else {
                uiManager.showMessage("The edition year already existed!");
            }

        } else {
            if (option != bm.editionListLength() + 1) {
                uiManager.spacing();
                uiManager.showMessage("ERROR: You must put a value between 1 and "+ (bm.editionListLength()+1));
            }
        }
    }

    public void deleteEdition() {
        uiManager.spacing();
        uiManager.showMessage("Which edition do you want to delete?");
        uiManager.spacing();
        listAllEditions();
        int option = uiManager.askForInteger("Enter an option: ");

        if (option > 0 && option < bm.editionListLength()+1) {
            uiManager.spacing();
            int value = uiManager.askForInteger("Enter the edition's year for confirmation: ");

            if (value == bm.getEditions().get(option-1).getYear()) {
                bm.deleteEdition(option - 1);
                uiManager.spacing();
                uiManager.showMessage("The edition was successfully deleted.");
            }

            else uiManager.showMessage("The option and the year introduced do not match.");
        } else {
            if (option != bm.editionListLength() + 1) {
                if (option != Integer.MIN_VALUE) {
                    uiManager.spacing();
                    uiManager.showMessage("ERROR: You must put a value between 1 and " + (bm.editionListLength() + 1));
                }
            }
        }
    }
}