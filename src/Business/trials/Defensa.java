package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class extension of Trial that provides a specific implementation of executeTrial.
 */
public class Defensa extends Trial {
    private final String campsEstudi;
    private final int dificultat;
    public static final String TYPE = "Defensa";
    private final String type = TYPE;

    /**
     * Constructor to create a Defensa.
     * @param name name of the Defensa.
     * @param campsEstudi camp d'estudi for Defensa.
     * @param dificultat difficulty of the Defensa.
     */
    public Defensa(String name, String campsEstudi, int dificultat) {
        super(name);
        this.campsEstudi = campsEstudi;
        this.dificultat = dificultat;
    }

    /**
     * Executes Defensa. We use the formula to generate a value, for every player if the number is bigger than
     * their PI count, the player is added to statusList as passed. If it is lower, the player is added to
     * statusList as failed.
     * @param playerList list of players (copy).
     * @return returns an object to store the statusList and the amount of times the list has been revised for
     *         each player.
     */
    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();

        int result = 0;
        for (int j = 0; j < dificultat; j++) {
            result += 2*j - 1;
        }
        result = (int) Math.sqrt(result);

        for (Player player : playerList) {
            if (player.getPI_count() > result) {
                statusList.add(true);
            } else {
                statusList.add(false);
            }
        }

        return new TrialResult(statusList, null);
    }

    /**
     * Custom toString modified to display information about the Defensa.
     * @return information of the trial in a String.
     */
    @Override
    public String toString() {
        String type = "Doctoral thesis defense";
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Enter the entity’s name: " + campsEstudi + "\n" +
                "Difficulty: " + dificultat+ "\n";
    }

    /**
     * Custom equals for Defensa.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Defensa defensa = (Defensa) o;
        return dificultat == defensa.dificultat && Objects.equals(getName(), defensa.getName()) && Objects.equals(campsEstudi, defensa.campsEstudi);
    }

    /**
     * Custom hashCode for Defensa
     * @return hash code created from the sequence of attributes for Defensa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), campsEstudi, dificultat, type);
    }

    /**
     * Processes the attributes of a Defensa to save them into a csv.
     * @return string of attributes
     */
    @Override
    public String toCSV() {
        return type+ "," + campsEstudi + "," + dificultat;
    }

    /**
     * Getter for the type of Trial
     * @return type of trial (Defensa)
     */
    @Override
    public String getType() {
        return TYPE;
    }
}
