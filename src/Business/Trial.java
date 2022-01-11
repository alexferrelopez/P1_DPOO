package Business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trial implements Cloneable, ExecutableTrial{
    private String name;
    private int acceptance;
    private int revision;
    private int rejection;
    private final Journal journal;

    public Trial() {
        journal = new Journal();
    }

    public Trial(String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        journal = new Journal();
        this.name = name;
        this.acceptance = acceptance;
        this.revision = revision;
        this.rejection = rejection;
        journal.setName(nameJournal);
        journal.setQuartile(quartile);
    }

    @Override
    public TrialResult executeTrial (int numPlayers) {
        TrialResult trialResult = new TrialResult(numPlayers);
        List<Boolean> statusList = trialResult.getStatusList();
        int[] timesRevisedList = trialResult.getTimesRevisedList();
        for (int i = 0; i < numPlayers; i++) {
            boolean processedTrial = false;
            do {
                double randNumber = Math.random() * 100;
                if (randNumber <= acceptance) {
                    statusList.add(true);
                    processedTrial = true;
                }
                else if (randNumber <= revision+acceptance) {
                    timesRevisedList[i] ++;
                }
                else {
                    statusList.add(false);
                    processedTrial = true;
                }
            } while (!processedTrial);
        }
        trialResult.setStatusList(statusList);
        trialResult.setTimesRevisedList(timesRevisedList);
        return trialResult;
    }

    @Override
    public List<Integer> assignPI(List<Boolean> statusList) {
        List<Integer> piList = new ArrayList<>();
        for (Boolean hasPassed : statusList) {
            if (hasPassed) {
                if ("Q1".equals(getJorunalQuartile())) {
                    piList.add(4);
                }
                else if ("Q2".equals(getJorunalQuartile())) {
                    piList.add(3);
                }
                else if ("Q3".equals(getJorunalQuartile())) {
                    piList.add(2);
                }
                else {
                    piList.add(1);
                }
            }
            else {
                if ("Q1".equals(getJorunalQuartile())) {
                    piList.add(-5);
                }
                else if ("Q2".equals(getJorunalQuartile())) {
                    piList.add(-4);
                }
                else if ("Q3".equals(getJorunalQuartile())) {
                    piList.add(-3);
                }
                else {
                    piList.add(-2);
                }
            }
        }
        return piList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String type = "Paper publication";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Journal: " + journal.getName() + " (" + journal.getQuartile() + ")\n" +
                "Chance: " + acceptance + "% acceptance, " + revision + "% revision, " + rejection + "% rejection\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trial trial = (Trial) o;
        return acceptance == trial.acceptance && revision == trial.revision && rejection == trial.rejection && name.equals(trial.name) && journal.equals(trial.journal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, acceptance, revision, rejection, journal);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getRejection() {
        return rejection;
    }

    public void setRejection(int rejection) {
        this.rejection = rejection;
    }

    public String getJournalName() {
        return journal.getName();
    }

    public String getJorunalQuartile() {
        return journal.getQuartile();
    }

    public void setJournalName(String journalName) {
        journal.setName(journalName);
    }

    public void setJournalQuartile(String journalQuartile) {
        journal.setQuartile(journalQuartile);
    }
}
