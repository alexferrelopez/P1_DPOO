package Presentation;

import Business.BusinessManager;
import Business.Edition;

import java.util.Date;
import java.util.Locale;

public class Controller {
    UIManager uiManager = new UIManager();
    BusinessManager bm = new BusinessManager();

    public void run () {
        //int option = uiManager.requestRole();
        switch (uiManager.requestRole()) {
            case 1 -> executeComposer();
            case 2 -> executeConductor();
        }
    }

    public void executeConductor() {
        uiManager.spacing();
        uiManager.showMessage("Entering execution mode...");
        uiManager.spacing();

    }
    public void executeComposer() {
        int optionComposer;
        uiManager.showMessage("\nEntering management mode...\n");
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
        boolean back;
        do {
            back = bm.deleteTrial(uiManager.requestDeletedTrial(bm.getTrials()));
        } while (!back);
    }

    public void executeEdition() {
        do {
            uiManager.spacing();
            switch (uiManager.requestEditionOp()) {
                case 1:createEdition(); //create
                    break;
                case 2: listEdition(); //list
                    break;
                case 3: System.out.println(3); //duplicate
                    break;
                case 4: System.out.println(4); //delete
                    break;
                case 5: uiManager.spacing(); //back
                    return;
            }
        }while (true);
    }
    public void createEdition() {
        int year, players, trials;
        Date date = new Date();
        uiManager.spacing();
        do {
            year = uiManager.askForInteger("Enter the edition's year: ");
            if (year < (date.getYear()+1900) && year != Integer.MIN_VALUE) {
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
        uiManager.showMessage("\t--- Trials ----");
        uiManager.spacing();
        uiManager.showList(bm.getTrials());
        uiManager.spacing();

        for (int i = 0; i < trials; i++) {
            uiManager.askForInteger("Pick a trial (" + (i+1) + "/" + trials + "): ");
        }
        uiManager.spacing();
        // TODO pasar todos los datos a persistencia
        uiManager.showMessage("The editions was created successfully!");

    }
    public void listEdition() {
         int length = bm.getEditions().size();
         uiManager.spacing();
         uiManager.showMessage("Here are the current editions, do you want to see more details or go back?");
         uiManager.spacing();
         for (int i = 0; i < length; i++) {
             uiManager.showMessage("\t"+(i+1)+") The Trials " + bm.getEditions().get(i).getYear());
         }
         uiManager.spacing();
         uiManager.showMessage("\t"+length+") Back");
    }

}
