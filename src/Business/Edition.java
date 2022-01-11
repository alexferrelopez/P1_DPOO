package Business;

import java.util.ArrayList;
import java.util.List;

public class Edition implements Cloneable{
    private List<Trial> trials;
    private List<Player> players;
    private int year;
    private int numPlayers;

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPLayers) {
        this.numPlayers = numPLayers;
    }

    public List<Trial> getTrials() {
        List<Trial> copy = new ArrayList<>();

        for (Trial trial : trials) {
            try {
                copy.add((Trial)trial.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return copy;
    }

    public void setTrials(List<Trial> trials) {
        this.trials = trials;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        String trialsString;
        if (trials == null || trials.isEmpty()) {
            trialsString = "\tNo Trials\n";
        }
        else {
            trialsString = "\t" + 1 + "- " + trials.get(0).getName() + " (Paper publication)\n";
            for (int i = 1; i < trials.size(); i++) {
                trialsString = trialsString.concat("\t" + (i+1) + "- " + trials.get(i).getName() + " (Paper publication)\n");
            }
        }
        String aux =
                "Year: " + year + "\n" +
                "Players: " + numPlayers + "\n" +
                "Trials: \n";

        return aux.concat(trialsString);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Edition clone = (Edition) super.clone();

        List<Trial> clonedTrials = new ArrayList<>();

        for (Trial trial: trials) {
            clonedTrials.add((Trial) trial.clone());
        }

        clone.setTrials(clonedTrials);

        List<Player> clonedPlayers = new ArrayList<>();

        if (players != null && !players.isEmpty()) {
            for (Player player : players) {
                clonedPlayers.add((Player) player.clone());
            }
        }

        clone.setPlayers(clonedPlayers);

        return clone;
    }
}
