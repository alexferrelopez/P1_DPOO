package Presentation;

import Business.BusinessManager;

import java.util.Locale;

public class Controller {
    UIManager uiManager = new UIManager();
    BusinessManager bm = new BusinessManager();

    public void run () {
        int option = uiManager.requestRole();
        switch (option) {
            case 1:
                executeComposer();
                break;
            case 2:
                executeConductor();
                break;
            default:
                uiManager.showMessage("You have to introduce a number between 1 and 2");
        }
    }


    public void executeConductor() {
        uiManager.showMessage("Hola\n");
    }

    public void executeComposer() {
        int optionComposer;
        uiManager.showMessage("\nEntering management mode...\n");
        do {
            optionComposer = uiManager.requestComposerOp();
            switch (optionComposer) {
                case 1: executeTrialTypes();
                        break;
                case 2: executeEdition();
                        break;
                case 3: uiManager.showMessage("\nShutting down...");
                        break;
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
            switch (uiManager.requestEditionOp()) {
                case 1:System.out.println(1); //create
                    break;
                case 2: System.out.println(2); //list
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
}
