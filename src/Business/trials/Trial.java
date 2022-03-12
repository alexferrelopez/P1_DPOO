package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.List;

public abstract class Trial implements Cloneable {
    private final String name;

    /**
     * Constructor to create a Trial.
     * @param name name of the Trial.
     */
    public Trial(String name){
        this.name = name;
    }

    /**
     * Executes a Trial.
     * @param playerList list of players (copy).
     * @return returns an object to store the statusList and extra info about the result of the trial.
     */
    public abstract TrialResult executeTrial(List<Player> playerList);

    /**
     * Clone for every trial.
     * @return clone of the object.
     * @throws CloneNotSupportedException standard clone exception.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Custom toString modified to display information about the Trial.
     * @return information of the trial in a String.
     */
    public abstract String toString();

    /**
     * Custom equals for a Trial.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    public abstract boolean equals(Object o);

    /**
     * Custom hashCode for a Trial
     * @return hash code created from the sequence of attributes for the Trial.
     */
    public abstract int hashCode();

    /**
     * Getter for the name of a Trial
     * @return name of the trial.
     */
    public String getName() {
        return name;
    }

    /**
     * Processes the attributes of a Trial to save them into a csv.
     * @return string of attributes
     */
    public abstract String toCSV();

    /**
     * Getter for the type of Trial
     * @return type of trial (Article, Estudi, Defensa or Solicitud).
     */
    public abstract String getType();
    
}
