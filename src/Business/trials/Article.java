package Business.trials;

import Business.*;
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

        List<Integer> piByPlayer = assignPI(statusList);

        return new TrialResult(statusList, timesRevisedList, piByPlayer);
    }

    public String resultProcessing(TrialResult trialResult, EditionWrapper editionWrapper, List<Player> playerList) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Boolean> statusList = trialResult.getStatusList();
        int[] timesRevisedList = trialResult.getAuxInfo();

        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            stringBuilder.append("\n\t").append(player.getName()).append(" is submitting... ");
            stringBuilder.append("Revisions... ".repeat(Math.max(0, timesRevisedList[i])));

            if (statusList.get(i)) {
                stringBuilder.append("Accepted! ");
            } else {
                stringBuilder.append("Rejected. ");
            }

            stringBuilder.append("PI count: ");

            if (player.getPI_count() <= 0) {
                stringBuilder.append(0).append(" - Disqualified!");
            } else stringBuilder.append(player.getPI_count());
        }

        editionWrapper.removePlayers();

        return stringBuilder.toString();
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
