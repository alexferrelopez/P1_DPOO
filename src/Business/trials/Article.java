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

    /**
     * Constructor to create an Article.
     * @param name name of the Article.
     * @param acceptance chances to pass the trial (percentatge)
     * @param revision chances to need to revise the trial (percentatge)
     * @param rejection chances fail the trial (percentatge)
     * @param nameJournal name of the Journal.
     * @param quartile Quartile of the year (modifies the amount of points earned/lost depending on this value).
     */
    public Article(String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        super(name);
        journal = new Journal();
        this.acceptance = acceptance;
        this.revision = revision;
        this.rejection = rejection;
        journal.setName(nameJournal);
        journal.setQuartile(quartile);
    }

    /**
     * Executes Article. For every player a random number is generated, if the number lands on a range inside the
     * approval percentatge, the player is added to statusList as passed. If it lands on the rejection percentatge,
     * the player is added to statusList as failed. Else the player adds +1 to the times he has been revised,
     * a new number is generated until he fails or passes.
     * @param playerList list of players (copy).
     * @return returns an object to store the statusList and the amount of times the list has been revised for
     *         each player.
     */
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

    /**
     * Custom toString modified to display information about the Article.
     * @return information of the trial in a String.
     */
    @Override
    public String toString() {
        String type = "Paper publication";
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Journal: " + journal.getName() + " (" + journal.getQuartile() + ")\n" +
                "Chance: " + acceptance + "% acceptance, " + revision + "% revision, " + rejection + "% rejection\n";
    }

    /**
     * Custom equals for Article.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article trial = (Article) o;
        return acceptance == trial.acceptance && revision == trial.revision && rejection == trial.rejection && getName().equals(trial.getName()) && journal.equals(trial.journal);
    }

    /**
     * Custom hashCode for Article.
     * @return hash code created from the sequence of attributes for Article.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), acceptance, revision, rejection, journal, type);
    }

    /**
     * Processes the attributes of an Article to save them into a csv.
     * @return string of attributes
     */
    @Override
    public String toCSV() {
        return type+ "," +acceptance + "," + revision + "," + rejection + "," + getJournalName() + "," + getJournalQuartile();
    }

    /**
     * Getter for the type of Trial.
     * @return type of trial (Article)
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Getter for the name of the Journal.
     * @return name of the Journal.
     */
    public String getJournalName() {
        return journal.getName();
    }

    /**
     * Getter for the quartile of the Journal.
     * @return quartile of the Journal.
     */
    public String getJournalQuartile() {
        return journal.getQuartile();
    }
}
