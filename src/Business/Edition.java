package Business;

import java.util.List;

public class Edition {
    private List<Trial> trials;
    private List<Player> players;
    private int year;

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
}
