package Business.trials;

import Business.Edition;
import Business.Journal;
import Business.TrialResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article implements Trial {
    private String name;
    private final int acceptance;
    private final int revision;
    private final int rejection;
    private final Journal journal;
    public static final String TYPE = "Article";
    private final String type = TYPE;

    public Article(String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        journal = new Journal();
        this.name = name;
        this.acceptance = acceptance;
        this.revision = revision;
        this.rejection = rejection;
        journal.setName(nameJournal);
        journal.setQuartile(quartile);
    }

    @Override
    public String executeTrial(int numPlayers, TrialResult trialResult, Edition edition) {
        List<Boolean> statusList = new ArrayList<>();
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

        List<Integer> piByPlayer = assignPI(trialResult.getStatusList());
        edition.setPis(piByPlayer);

        return trialResultToString(trialResult,edition);
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
        Article trial = (Article) o;
        return acceptance == trial.acceptance && revision == trial.revision && rejection == trial.rejection && name.equals(trial.name) && journal.equals(trial.journal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, acceptance, revision, rejection, journal);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toCSV() {
        return type+ "," +acceptance + "," + revision + "," + rejection + "," + getJournalName() + "," + getJorunalQuartile();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public String getJournalName() {
        return journal.getName();
    }

    public String getJorunalQuartile() {
        return journal.getQuartile();
    }


}