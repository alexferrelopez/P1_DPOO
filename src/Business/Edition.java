package Business;

import Business.players.Doctor;
import Business.players.Enginyer;
import Business.players.Master;
import Business.players.Player;
import Business.trials.Trial;

import java.util.ArrayList;
import java.util.List;

public class Edition implements Cloneable, EditionWrapper {
    private List<Trial> trials;
    private List<Player> players;
    private int year;
    private int numPlayers;

    public Edition(List<Trial> trials, List<Player> players, int year, int numPlayers) {
        this.trials = trials;
        this.players = players;
        this.year = year;
        this.numPlayers = numPlayers;
    }

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

    public int getPlayerListSize () {
        if (players == null || players.isEmpty()) {
            return 0;
        } else return players.size();
    }

    public void setTrials(List<Trial> trials) {
        this.trials = trials;
    }

    public List<Player> getPlayers() {
        List<Player> copy = new ArrayList<>();

        for (Player player: players) {
            try {
                copy.add((Player) player.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return copy;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean allPLayersEliminated () {
        return players.isEmpty();
    }

    public void clearPlayers () {
        players.clear();
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
    /*
    public void setPis(List<Integer> piByPlayer) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.addPICount(piByPlayer.get(i));

            if (player.getPI_count() >= 10) {
                ascendPlayer(player);
            }
        }
    }^*/

    private void ascendPlayer(Player player) {
        String type = player.getType();

        switch (type) {
            case Enginyer.TYPE -> {
                players.add(new Master(player.getName()));
                players.remove(player);
            }
            case Master.TYPE -> {
                players.add(new Doctor(player.getName()));
                players.remove(player);
            }
        }
    }

    public void addPlayer(String playerName) {
        if (players == null || players.isEmpty()) {
            players = new ArrayList<>();
        }
        Player player =  new Enginyer(playerName);
        players.add(player);
    }

    public void removePlayers() {
        players.removeIf(player -> !player.isAlive());
    }

    @Override
    public void incrementPoints(List<Integer> piByPlayer) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.addPICount(piByPlayer.get(i));

            if (player.getPI_count() >= 10) {
                ascendPlayer(player);
            }
        }
    }
}
