package Presentation;

import Business.BusinessManager;

import java.util.Locale;

public class Controller {
    UIManager uiManager = new UIManager();
    BusinessManager bm = new BusinessManager();

    public void run () {
        int option = uiManager.requestRole();

        if (option == 1) {
            int optionComposer = uiManager.requestComposerOp();

            if (optionComposer == 1) { //MANAGE TRIAL
                int optionTrial;
                do {
                    optionTrial = uiManager.requestTrialOp();

                    if (optionTrial == 1) { //CREATE TRIAL
                        int type = uiManager.requestTrialType();                    //I guess ill use this in a later stage

                        String trialName = uiManager.requestTrialString("Enter the trial's name: ");

                        String nameJournal = uiManager.requestTrialString("Enter the journal's name: ");
                        String quartile = uiManager.requestTrialString("Enter the journal’s quartile: ").toUpperCase(Locale.ROOT);

                        while (!(quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4"))) {
                            uiManager.showMessage("Quartile options must be: Q1, Q2, Q3 or Q4.");
                            quartile = uiManager.requestTrialString("Enter the journal’s quartile: ");
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
                    else if (optionTrial == 2) { //LIST TRIALS
                        uiManager.showTrialList(bm.getTrials());
                    }
                    else if (optionTrial == 3) { //DELETE TRIAL
                        boolean back;
                        do {
                            back = bm.deleteTrial(uiManager.requestDeletedTrial(bm.getTrials()));

                        } while (!back);

                    }
                } while (optionTrial != 4);
            }
            else if (optionComposer == 2) { //MANAGE EDITIONS
                //TODO MANAGING EDITIONS
            }
            //WE ARE OUT
        }
        else {
            //TODO ITS CONDUCTOR TIME
        }
    }
}
