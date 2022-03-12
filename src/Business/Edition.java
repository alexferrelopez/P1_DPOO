package Business;

import Business.players.Doctor;
import Business.players.Enginyer;
import Business.players.Master;
import Business.players.Player;
import Business.trials.*;

import java.util.ArrayList;
import java.util.List;

public class Edition implements Cloneable {
    private List<Trial> trials;
    private List<Player> players;
    private int year;
    private int numPlayers;

    /**
     * Constructor to create an Edition
     * @param trials list of trials specific to the edition.
     * @param players list of players specific to the edition.
     * @param year year of the edition.
     * @param numPlayers number of players participating in the edition.
     */
    public Edition(List<Trial> trials, List<Player> players, int year, int numPlayers) {
        this.trials = trials;
        this.players = players;
        this.year = year;
        this.numPlayers = numPlayers;
    }

    /**
     * Getter for the number of players in the edition.
     * @return number of players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Setter for the number of players in the edition.
     * @param numPLayers number of players.
     */
    public void setNumPlayers(int numPLayers) {
        this.numPlayers = numPLayers;
    }

    /**
     * Getter for the list of trials to be played in the edition.
     * @return list of trials (copy)
     */
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

    /**
     * Setter for the list of trials for an Edition.
     * @param trials list of trials.
     */
    public void setTrials(List<Trial> trials) {
        this.trials = trials;
    }

    /**
     * Getter for the list of players that will play in the edition.
     * @return list of players (copy)
     */
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

    /**
     * Setter for the list of players for an Edition.
     * @param players list of players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Checks if all players are eliminated.
     * @return true when player list is empty.
     */
    public boolean allPLayersEliminated () {
        return players.isEmpty();
    }

    /**
     * Clears the list of players.
     */
    public void clearPlayers () {
        players.clear();
    }

    /**
     * Getter for the year of the Edition.
     * @return year fo the Edition.
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter for the year of the Edition
     * @param year year of the Edition.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Custom toString modified to display information about the Edition.
     * @return information of the Edition in a String.
     */
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

    /**
     * Clone for every edition.
     * @return clone of the object.
     * @throws CloneNotSupportedException standard clone exception.
     */
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

    /**
     * Ascends players by looking if their PI_count is equal or greater than 10. Ascending an Enginyer to Master,
     * and a Master into a Doctor. Then these players are added to a list to return them as the ones that have evolved.
     * @return players evolved in this call.
     */
    public List<Player> ascendPlayers() {

        List<Player> evolvedPlayers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            String type = player.getType();

            if (player.getPI_count() >= 10 && !type.equals(Doctor.TYPE)) {

                switch (type) {
                    case Enginyer.TYPE -> players.set(i, new Master(player.getName()));

                    case Master.TYPE -> players.set(i, new Doctor(player.getName()));
                }

                try {
                    evolvedPlayers.add((Player) players.get(i).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return evolvedPlayers;
    }

    /**
     * Adds player to player list with the indicated name.
     * @param playerName name of the new player.
     */
    public void addPlayer(String playerName) {
        if (players == null || players.isEmpty()) {
            players = new ArrayList<>();
        }
        Player player =  new Enginyer(playerName);
        players.add(player);
    }

    /**
     * Removes all players that have been eliminated.
     */
    public void removePlayers() {
        players.removeIf(Player::isEliminated);
    }

    /**
     * Increments PI count for every player in the edition depending on the type of the trial.
     * @param statusList list that indicates if the player passed or failed.
     * @param type type of trial (Article, Estudi, Defensa or Solicitud).
     * @param quartile dictates the amount of points earned when a player plays an Article.
     */
    public void incrementPoints(List<Boolean> statusList, String type, String quartile) {

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            switch (type) {
                case Article.TYPE ->    player.processPIArticle     (statusList.get(i), quartile);
                case Estudi.TYPE ->     player.processPIEstudi      (statusList.get(i));
                case Defensa.TYPE ->    player.processPIDefensa     (statusList.get(i));
                case Solicitud.TYPE ->  player.processPISolicitud   (statusList.get(i));
            }
        }
    }
}
