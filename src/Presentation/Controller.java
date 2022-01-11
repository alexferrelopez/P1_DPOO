package Presentation;

import Business.BusinessManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Controller {
    UIManager uiManager = new UIManager();
    BusinessManager bm = new BusinessManager();

    public void run () {
        //int option = uiManager.requestRole();

        //requestFile (it plays before requesting a role)
        switch (uiManager.requestFile()) {
            case 1 -> loadCsv();
            case 2 -> loadJson();
        }

        switch (uiManager.requestRole()) {
            case 1 -> executeComposer();
            case 2 -> executeConductor();
        }

        bm.saveData();
    }

    private void loadCsv() {
        bm.loadFromCsv();
    }

    private void loadJson() {
        bm.loadFromJson();
    }

    public void executeConductor() {
        uiManager.spacing();
        uiManager.showMessage("Entering execution mode...");
        uiManager.spacing();

    }
    public void executeComposer() {
        int optionComposer;
        uiManager.showMessage("\nEntering management mode...");
        do {
            optionComposer = uiManager.requestComposerOp();
            switch (optionComposer) {
                case 1 -> executeTrialTypes();
                case 2 -> executeEdition();
                case 3 -> uiManager.showMessage("\nShutting down...");
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
        int type;                    //I guess ill use this in a later stage
        type = uiManager.requestTrialType();

        String trialName = uiManager.askForString("Enter the trial's name: ");

        String nameJournal = uiManager.askForString("Enter the journal's name: ");
        String quartile = uiManager.askForString("Enter the journal’s quartile: ").toUpperCase(Locale.ROOT);

        while (!(quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4"))) {
            uiManager.showMessage("Quartile options must be: Q1, Q2, Q3 or Q4.");
            quartile = uiManager.askForString("Enter the journal’s quartile: ");
        }

        int acceptance = uiManager.requestTrialNumber("Enter the acceptance probability: ");
        int revision = uiManager.requestTrialNumber("Enter the revision probability: ");
        int rejection = uiManager.requestTrialNumber("Enter the rejection probability: ");

        while (acceptance+rejection+revision != 100) {
            uiManager.showMessage("Acceptance probability + revision probability + rejection probability must sum up to 100.");
            acceptance = uiManager.requestTrialNumber("Enter the acceptance probability: ");
            revision = uiManager.requestTrialNumber("Enter the revision probability: ");
            rejection = uiManager.requestTrialNumber("Enter the rejection probability: ");
        }

        bm.createTrial(trialName, acceptance, revision, rejection, nameJournal, quartile);

        uiManager.showMessage("\nThe trial was created successfully!");
    }
    public void listTrial() {
        uiManager.showTrialList(bm.getTrials());
    }
    public void deleteTrial() {
        boolean done;
        do {
            done = bm.deleteTrial(uiManager.requestDeletedTrial(bm.getTrials()));
        } while (!done);
    }

    // TODO cambiar el modo en que pasamos los nombres
    public void executeEdition() {
        do {
            uiManager.spacing();
            switch (uiManager.requestEditionOp()) {
                case 1 -> createEdition(); //create
                case 2 -> listEdition(); //list
                case 3 -> duplicateEdition(); //duplicate
                case 4 -> deleteEdition(); //delete
                case 5 -> {
                    uiManager.spacing(); //back
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
        do {
            year = uiManager.askForInteger("Enter the edition's year: ");
            if (year < (date.getYear()+1900) && year != Integer.MIN_VALUE) { //TODO la segunda condicion q busca? Quitar si no hace falta
                uiManager.showMessage("ERROR: The year has to be for current or future events");
            }
        } while(year < (date.getYear()+1900));
        do {
            players = uiManager.askForInteger("Enter the initial number of players: ");
            if ((players > 6 || players < 1) && players != Integer.MIN_VALUE) {
                uiManager.showMessage("ERROR: The number of players has to be between 1 and 5");
            }
        } while (players > 6 || players < 1);
        do {
            trials = uiManager.askForInteger("Enter the number of trials: ");
            if ((trials < 3 || trials > 12) && trials != Integer.MIN_VALUE) {
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
                trialNum = uiManager.askForInteger("Pick a trial (" + (i+1) + "/" + trials + "): ")-1; //le resto 1 para q corresponda con el indice de la lista.
                if (trialNum < 0 || trialNum > bm.trialLength()-1) {
                    uiManager.showMessage("Error: Trial number has to be between "+ 1 + " and " + bm.trialLength() + ".");
                }
            } while (trialNum < 0 || trialNum > bm.trialLength()-1);
            trialList.add(trialNum);
        }

        uiManager.spacing();

        bm.createEdition(trialList,year, players);

        uiManager.showMessage("The editions was created successfully!");
    }

    private void listAllEditions() {
        bm.sortEditionsByYear();
        for (int i = 0; i < bm.editionLength(); i++) {
            uiManager.showTabulatedMessage((i+1)+") The Trials " + bm.getEditions().get(i).getYear());
        }
        uiManager.spacing();
        uiManager.showTabulatedMessage(bm.editionLength()+1+") Back");
        uiManager.spacing();
    }

    public void listEdition() {
         uiManager.spacing();
         uiManager.showMessage("Here are the current editions, do you want to see more details or go back?");
         uiManager.spacing();
         listAllEditions();
         // TODO touch the dao and business
         int option = uiManager.askForInteger("Enter an option: ");
            if (option > 0 && option < bm.editionLength()+1) {
                uiManager.spacing();
                System.out.println(bm.printEdition(option-1));
            } else if (option == bm.editionLength()+1) {
            } else {
                uiManager.spacing();
                uiManager.showMessage("ERROR: You must put a value between 1 and "+ (bm.editionLength()+1));
            }
    }
    public void duplicateEdition() {
        uiManager.spacing();
        uiManager.showMessage("Which edition do you want to clone? ");
        uiManager.spacing();
        listAllEditions();
        int option = uiManager.askForInteger("Enter an option: ");
        if (option > 0 && option < bm.editionLength()+1) {
            uiManager.spacing();
            int year = uiManager.askForInteger("Enter the new edition's year: ");
            int players = uiManager.askForInteger("Enter the new edition's initial number of players: ");
            bm.duplicateEdition(option-1,year,players);
            uiManager.spacing();
            uiManager.showMessage("The edition was cloned successfully!");
        } else if (option == bm.editionLength()+1) {
        } else if (option != Integer.MIN_VALUE){
            uiManager.spacing();
            uiManager.showMessage("ERROR: You must put a value between 1 and "+ (bm.editionLength()+1));
        }
    }

    public void deleteEdition() {
        uiManager.spacing();
        uiManager.showMessage("Which edition do you want to delete?");
        uiManager.spacing();
        listAllEditions();
        int option = uiManager.askForInteger("Enter an option: ");

        if (option > 0 && option < bm.editionLength()+1) {
            uiManager.spacing();
            int value = uiManager.askForInteger("Enter the edition's year for confirmation: ");

            if (value == bm.getEditions().get(option-1).getYear()) {
                bm.deleteEdition(option - 1);
                uiManager.spacing();
                uiManager.showMessage("The edition was successfully deleted.");
            }

            else uiManager.showMessage("The option and the year introduced do not match.");
        } else if (option == bm.editionLength()+1) {
        } else if (option != Integer.MIN_VALUE){
            uiManager.spacing();
            uiManager.showMessage("ERROR: You must put a value between 1 and "+ (bm.editionLength()+1));
        }
    }
}