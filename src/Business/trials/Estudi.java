package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class extension of Trial that provides a specific implementation of executeTrial.
 */
public class Estudi extends Trial {
    private final String master;
    private final int credits;
    private final int probabilitat;
    public static final String TYPE = "Estudi";
    private final String type = TYPE;

    /**
     * Constructor to create a Defensa.
     * @param name name of the Estudi.
     * @param master master name of the Estudi;
     * @param credits credit number of the Estudi
     * @param probabilitat probability to pass the Estudi.
     */
    public Estudi(String name, String master, int credits, int probabilitat) {
        super(name);
        this.master = master;
        this.credits = credits;
        this.probabilitat = probabilitat;
    }

    /**
     * Executes Estudi. For every player we generate as many random values as total credits,
     * For every value generated that is bigger than the probability of passing a credit, result increases by 1.
     * If result ends up being more than half of the total credits, the player is added to statusList as passed.
     * If it is lower the player is added to statusList as failed.
     * @param playerList list of players (copy).
     * @return returns an object to store the statusList and the amount of credits passed per player.
     */
    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int[] passedCreditsPerPlayer = new int[playerList.size()];

        for (int i = 0; i < playerList.size(); i++) {
            int result = 0;

            for (int j = 0; j < credits; j++) {
                if (Math.random()*100 <= probabilitat) {
                    result++;
                }
            }

            if(result > credits / 2) {
                statusList.add(true);
            }
            else statusList.add(false);
            passedCreditsPerPlayer[i] = result;
        }

        return new TrialResult(statusList, passedCreditsPerPlayer);
    }

    /**
     * Custom toString modified to display information about the Estudi.
     * @return information of the trial in a String.
     */
    @Override
    public String toString() {
        String type = "Master studies";          //for now
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Master: " + master + "\n" +
                "ECTS: " + credits + ", with a " + probabilitat + "% chance to pass each one\n";
    }

    /**
     * Custom equals for Estudi.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudi estudi = (Estudi) o;
        return credits == estudi.credits && probabilitat == estudi.probabilitat && Objects.equals(getName(), estudi.getName()) && Objects.equals(master, estudi.master);
    }

    /**
     * Custom hashCode for Estudi
     * @return hash code created from the sequence of attributes for Estudi.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), master, credits, probabilitat, type);
    }

    /**
     * Processes the attributes of a Defensa to save them into a csv.
     * @return string of attributes
     */
    @Override
    public String toCSV() {
        return type+ "," + master + "," + credits + "," + probabilitat;
    }

    /**
     * Getter for the type of Trial
     * @return type of trial (Estudi)
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Getter for the amount of credits of a Master
     * @return number of credits.
     */
    public int getCredits() {
        return credits;
    }
}
