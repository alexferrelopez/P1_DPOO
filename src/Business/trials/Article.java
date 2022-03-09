package Business.trials;

import Business.Journal;
import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article extends Trial {
    private final int acceptance;
    private final int revision;
    private final int rejection;
    private final Journal journal;
    public static final String TYPE = "Article";
    private final String type = TYPE;

    public Article(String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        super(name);
        journal = new Journal();
        this.acceptance = acceptance;
        this.revision = revision;
        this.rejection = rejection;
        journal.setName(nameJournal);
        journal.setQuartile(quartile);
    }

    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int[] timesRevisedList = new int[playerList.size()];

        for (int i = 0; i < playerList.size(); i++) {
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

        return new TrialResult(statusList, timesRevisedList);
    }

    @Override
    public String toString() {
        String type = "Paper publication";          //for now
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Journal: " + journal.getName() + " (" + journal.getQuartile() + ")\n" +
                "Chance: " + acceptance + "% acceptance, " + revision + "% revision, " + rejection + "% rejection\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article trial = (Article) o;
        return acceptance == trial.acceptance && revision == trial.revision && rejection == trial.rejection && getName().equals(trial.getName()) && journal.equals(trial.journal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), acceptance, revision, rejection, journal, type);
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
