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
        return trials;
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
        if (trials == null || trials.isEmpty()) {
            return "No Trials";
        }
        String trialsString = 1 + "- " + trials.get(0).getName() + " (Paper publication)\n";
        for (int i = 1; i < trials.size(); i++) {
            trialsString = trialsString.concat(i+1 + "- " + trials.get(i).getName() + " (Paper publication)\n");
        }

        String aux =  "\n" +
                "Year:" +  year + "\n" +
                "Players: " + players + "\n" +
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

        for (Player player: players) {
            clonedPlayers.add((Player) player.clone());
        }

        clone.setPlayers(clonedPlayers);

        return clone;
    }
}
