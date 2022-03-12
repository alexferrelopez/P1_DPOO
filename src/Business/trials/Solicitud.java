package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solicitud extends Trial {
    private final String entitat;
    private final int pressupost;
    public static final String TYPE = "Solicitud";
    private final String type = TYPE;

    /**
     * Constructor to create a Solicitud.
     * @param name name of the Solicitud.
     * @param entitat name of the entitat.
     * @param pressupost amount of money for Solicitud.
     */
    public Solicitud(String name, String entitat, int pressupost) {
        super(name);
        this.entitat = entitat;
        this.pressupost = pressupost;
    }

    /**
     * Executes Solicitud. We use the formula to generate a value. If this value is bigger than the sum
     * of all PI count between all players, all players are added to statusList as passed. Else they are added to
     * statusList as failed.
     * @param playerList list of players (copy).
     * @return returns an object to store the statusList.
     */
    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int result = 0;

        for (Player player : playerList) {
            result += player.getPI_count();
        }

        int calcul = (int)(Math.log(pressupost) / Math.log(2));

        if (result > calcul) {
            for (int i = 0; i < playerList.size(); i++) {
                statusList.add(true);
            }
        }
        else {
            for (int i = 0; i < playerList.size(); i++) {
                statusList.add(false);
            }
        }

        return new TrialResult(statusList, null);
    }

    /**
     * Custom toString modified to display information about the Solicitud.
     * @return information of the trial in a String.
     */
    @Override
    public String toString() {
        String type = "Budget request)";
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Entity: " + entitat +"\n" +
                "Budget: "+ pressupost +"\n";
    }

    /**
     * Custom equals for Solicitud.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solicitud solicitud = (Solicitud) o;
        return pressupost == solicitud.pressupost && Objects.equals(getName(), solicitud.getName()) && Objects.equals(entitat, solicitud.entitat);
    }

    /**
     * Custom hashCode for Solicitud
     * @return hash code created from the sequence of attributes for Solicitud.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), entitat, pressupost, type);
    }

    /**
     * Processes the attributes of a Solicitud to save them into a csv.
     * @return string of attributes
     */
    @Override
    public String toCSV() {
        return type+ "," + entitat + "," + pressupost;
    }

    /**
     * Getter for the type of Trial
     * @return type of trial (Solicitud).
     */
    @Override
    public String getType() {
        return TYPE;
    }
}
