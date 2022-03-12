package Business;

import Business.players.Doctor;
import Business.players.Master;
import Business.players.Player;
import Business.trials.*;
import Persistance.*;

import java.io.IOException;
import java.util.*;

public class BusinessManager {
    private EditionDAO editionDAO;
    private TrialDAO trialDAO;
    private List<Trial> trials = new ArrayList<>();
    private List<Edition> editions = new ArrayList<>();
    private final ExecutionCheckpointDAO executionCheckpointDAO = new ExecutionCheckpointDAO();
    private Integer checkpoint = executionCheckpointDAO.getAll();
    public static final int systemYear = Calendar.getInstance().get(Calendar.YEAR);

    /**
     * Loads files from CSV.
     * @throws IOException standard IOException, used detect when a file does not exist.
     */
    public void loadFromCsv() throws IOException {
        trialDAO = new TrialCsvDAO();
        editionDAO = new EditionCsvDAO();
        trials = trialDAO.getAll();
        editions = editionDAO.getAll(trials);
    }

    /**
     * Loads files from Json.
     * @throws IOException standard IOException, used detect when a file does not exist.
     */
    public void loadFromJson() throws IOException {
        trialDAO = new TrialJsonDao();
        editionDAO = new EditionJsonDAO();
        trials = trialDAO.getAll();
        editions = editionDAO.getAll(trials);
    }

    /**
     * Creates a trial depending on the attribute type.
     * @param type field to determine the trial type: (Article, Estudi, Defensa, Sol·licitud)
     * @param name name of the trial
     * @param other variable field related to some trials: Estudi -> master name, Defensa -> camps d'Estudi, Sol·licitud -> Entitat
     * @param acceptance acceptance rate for Article type
     * @param revision revision rate for Article type
     * @param rejection rejection rate for Article type
     * @param nameJournal name of the Journal for Article type
     * @param quartile quartile (Q1,Q2,Q3,Q4) for Article type
     * @param probabilitat probability to pass for Estudi type
     * @param credits credits to pass in Estudi type
     * @param difficulty difficulty of Defensa type
     * @param budget budget for the Sol·licitud
     */
    public void createTrial(int type, String name, String other, int acceptance, int revision, int rejection, String nameJournal, String quartile, int probabilitat, int credits, int difficulty, int budget) {
        switch (type) {
            case 1 -> {
                Trial trial = new Article(name, acceptance, revision, rejection, nameJournal, quartile);
                trials.add(trial);
            }
            case 2 -> {
                Trial trial = new Estudi(name,other,credits,probabilitat);
                trials.add(trial);
            }
            case 3 -> {
                Trial trial = new Defensa(name,other,difficulty);
                trials.add(trial);
            }
            case 4 -> {
                Trial trial = new Solicitud(name,other,budget);
                trials.add(trial);
            }
        }

    }

    /**
     * Deletes a trial checking if the trial is being used by any edition.
     * @param index index of the trial to delete.
     * @return returns true if trial is not being used by any edition.
     */
    public boolean deleteTrial (int index) {

        for (Edition edition : editions) {
            List<Trial> editionTrials = edition.getTrials();
            for (Trial trial : editionTrials) {
                if (trial.getName().equals(trials.get(index).getName())) {
                    return false;
                }
            }
        }

        trials.remove(index);
        return true;
    }

    /**
     * Creates an edition. Checks if Edition already exists, if it does, it substitutes the new Edition for the old.
     * @param trialIndexes List of trials to add to the edition.
     * @param year Year of the edition.
     * @param numPlayers number of players that will play the edition.
     */
    public void createEdition (List<Integer> trialIndexes, int year, int numPlayers) {
        List<Trial> trialList = new ArrayList<>();

        for (Integer trialIndex : trialIndexes) {
            trialList.add(trials.get(trialIndex));
        }

        processAlreadyExistingYear(new Edition(trialList,new ArrayList<>() ,year,numPlayers));
    }

    /**
     * Duplicates an edition.
     * @param index index of the edition to duplicate.
     * @param year Year of the new edition.
     * @param numPlayers number of players that will play the edition.
     * @return true if edition year did not already exist.
     */
    public boolean duplicateEdition (int index, int year, int numPlayers) {
        Edition duplicate;

        for (Edition edition : editions) {
            if (edition.getYear() == editions.get(index).getYear()) {
                return false;
            }
        }

        try {
            duplicate = (Edition) editions.get(index).clone();
            duplicate.getPlayers().clear();
            duplicate.setNumPlayers(numPlayers);
            duplicate.setYear(year);
            return true;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Processes when an edition is created if the year already exists. If it does the edition is substituted for the
     * newly created one, resetting the checkpoint to ensure no errors if the edition is the same as the one being executed.
     * @param duplicate new edition to be added.
     */
    private void processAlreadyExistingYear(Edition duplicate) {
        boolean yearAlreadyExists = false;
        for (int i = 0; i < editions.size(); i++) {
            Edition edition = editions.get(i);

            if (edition.getYear() == duplicate.getYear()) {
                editions.set(i, duplicate);
                yearAlreadyExists = true;

                if (systemYear == edition.getYear()) {
                    checkpoint = null;
                }
                break;
            }
        }
        if (!yearAlreadyExists) {
            editions.add(duplicate);
        }
    }

    /**
     * Deletes an edition. Resetting the checkpoint if the edition is the one to be executed.
     * @param index index of the edition to delete.
     */
    public void deleteEdition (int index) {
        if (systemYear == editions.get(index).getYear()) {
            checkpoint = null;
        }
        editions.remove(index);
    }

    /**
     * Returns the size of the list of editions.
     * @return size of list editions.
     */
    public int editionListLength() {
        return editions.size();
    }

    /**
     * Returns the size of the list of trials.
     * @return size of list trials.
     */
    public int trialListLength() {
        return trials.size();
    }

    /**
     * Executes a Trial indicated by checkpoint.
     * Processes the result of the execution into a string.
     * Ascends the players who passed the 10 point mark (not the doctors).
     * Removes the players eliminated in the trial.
     * @return string to be shown to the user to reflect the result of the simulation.
     */
    public String executeTrial () {

        Edition edition = findEditionYear();

        if (edition != null) {

            List<Trial> trialsEdition = edition.getTrials();

            if (checkpoint < trialsEdition.size()) {

                Trial trialToPlay = trialsEdition.get(checkpoint);

                TrialResult trialResult = trialToPlay.executeTrial(edition.getPlayers());

                String quartile = null;
                String type = trialToPlay.getType();

                if (type.equals(Article.TYPE)) {
                    Article article = (Article) trialToPlay;
                    quartile = article.getJournalQuartile();
                }

                List<Boolean> statusList = trialResult.getStatusList();
                edition.incrementPoints(statusList, type, quartile);

                StringBuilder stringBuilder = new StringBuilder();

                List<Player> players = edition.getPlayers();
                processTrial(trialToPlay, trialResult, type, statusList, stringBuilder, players);

                List<Player> ascendedPlayers = edition.ascendPlayers();

                stringBuilder.append("\n\n");

                for (Player ascendedPlayer : ascendedPlayers) {
                    stringBuilder.append(ascendedPlayer.getName()).append(" is now a ");

                    if (ascendedPlayer.getType().equals(Master.TYPE)) {
                        stringBuilder.append("master");
                    }else {
                        stringBuilder.append("doctor");
                    }

                    stringBuilder.append(" (with ").append(ascendedPlayer.getPI_count()).append(" PI). ");
                }

                String resultTrial = String.valueOf(stringBuilder);

                String trialHeader = "\nTrial #" + (checkpoint + 1) + " - " + trialToPlay.getName() +"\n";
                checkpoint++;

                String  result = trialHeader + resultTrial;

                if (checkpoint == trialsEdition.size() || getRemainingPlayers() == 0) {
                    checkpoint = 0;

                    if (edition.allPLayersEliminated()) {
                        edition.clearPlayers();
                        return result + "\n\nTHE TRIALS " + edition.getYear() + " HAVE ENDED - PLAYERS LOST";
                    } else {
                        edition.clearPlayers();
                        return result + "\n\nTHE TRIALS " + edition.getYear() + " HAVE ENDED - PLAYERS WON";
                    }
                }

                edition.removePlayers();

                return result;
            }
        }
        return null;
    }

    /**
     * Decides which trial has to be executed. Adding the result to string-builder.
     * @param trialToPlay trial to play in this execution chosen by checkpoint attribute.
     * @param trialResult results of the trial after being executed.
     * @param type type of trial (Article, Estudi, Defensa or Solicitud).
     * @param statusList list of the boolean that indicate if players passed or failed.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param players list of players (clone)
     */
    private void processTrial(Trial trialToPlay, TrialResult trialResult, String type, List<Boolean> statusList, StringBuilder stringBuilder, List<Player> players) {
        switch (type) {
            case Article.TYPE -> {

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processPlayerArticle(trialResult, statusList, stringBuilder, i, player);
                }
            }
            case Estudi.TYPE -> {

                Estudi estudi = (Estudi) trialToPlay;

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processPlayerEstudi(trialResult, statusList, stringBuilder, estudi, i, player);
                }
            }
            case Defensa.TYPE ->{

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processPlayerDefensa(statusList, stringBuilder, i, player);
                }
            }
            case Solicitud.TYPE ->{

                if (statusList.get(0)) {
                    stringBuilder.append("\n\t").append("The research group got the budget!\n");
                }

                for (Player player : players) {
                    processPlayerSolicitud(stringBuilder, player);
                }
            }
        }
    }

    /**
     * Adds the suffix/prefix when executing a trial to those players that match
     * the title (Master and Doctor) to string-builder.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param player player that is being evaluated to have the suffix/prefix.
     */
    private void addSuffixOrPrefix(StringBuilder stringBuilder, Player player) {
        if (player.getType().equals(Master.TYPE)) {
            stringBuilder.append("Master ");
        }

        stringBuilder.append(player.getName());

        if (player.getType().equals(Doctor.TYPE)) {
            stringBuilder.append(", Phd");
        }
    }

    /**
     * Adds PI_count to string-builder. Also adds a message for Disqualified players.
     * @param player player that is being evaluated to be eliminated.
     * @param stringBuilder string-builder to compose the string to be returned.
     */
    private void addPointStateMessage(Player player, StringBuilder stringBuilder) {
        if (player.isEliminated()) {
            stringBuilder.append("0");
            stringBuilder.append(" - Disqualified");
        }
        else stringBuilder.append(player.getPI_count());
    }

    /**
     * Adds player result to string-builder for Article.
     * @param resultTrial result trial containing the amount of times being revised.
     * @param statusList list that indicates if the player passed or failed.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param i index for the lists to find information about the player.
     * @param player player that is being added to string-builder.
     */
    private void processPlayerArticle(TrialResult resultTrial, List<Boolean> statusList, StringBuilder stringBuilder, int i, Player player) {
        stringBuilder.append("\n\t");

        addSuffixOrPrefix(stringBuilder, player);

        stringBuilder.append(" is submitting...");

        int timesRejected = resultTrial.getAuxInfo()[i];
        stringBuilder.append("Revisions...".repeat(Math.max(0, timesRejected)));

        if (statusList.get(i)) {
            stringBuilder.append("Accepted! ");
        } else stringBuilder.append("Rejected. ");

        stringBuilder.append("PI count: ");

        addPointStateMessage(player, stringBuilder);
    }

    /**
     * Adds player result to string-builder for Estudi.
     * @param resultTrial result trial containing the amount of credits passed.
     * @param statusList list that indicates if the player passed or failed.
     * @param estudi trial being played.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param i index for the lists to find information about the player.
     * @param player player that is being added to string-builder.
     */
    private void processPlayerEstudi(TrialResult resultTrial, List<Boolean> statusList, StringBuilder stringBuilder, Estudi estudi, int i, Player player) {
        stringBuilder.append("\n\t");

        addSuffixOrPrefix(stringBuilder, player);

        stringBuilder.append(" passed ").append(resultTrial.getAuxInfo()[i]).append("/").append(estudi.getCredits()).append(" ECTS.");

        if (statusList.get(i)) {
            stringBuilder.append("Congrats! ");
        } else {
            stringBuilder.append("Sorry... ");
        }

        stringBuilder.append("PI count: ");

        addPointStateMessage(player, stringBuilder);
    }

    /**
     * Adds player result to string-builder for Defensa.
     * @param statusList list that indicates if the player passed or failed.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param i index for the lists to find information about the player.
     * @param player player that is being added to string-builder.
     */
    private void processPlayerDefensa(List<Boolean> statusList, StringBuilder stringBuilder, int i, Player player) {
        stringBuilder.append("\n\t");

        addSuffixOrPrefix(stringBuilder, player);

        stringBuilder.append(" was ");

        if (statusList.get(i)) {
            stringBuilder.append("successful. Congrats! ");
        } else {
            stringBuilder.append("unsuccessful. Sorry... ");
        }

        stringBuilder.append("PI count: ");

        addPointStateMessage(player, stringBuilder);
    }

    /**
     * Adds player result to string-builder for Solicitud.
     * @param stringBuilder string-builder to compose the string to be returned.
     * @param player player that is being added to string-builder.
     */
    private void processPlayerSolicitud(StringBuilder stringBuilder, Player player) {
        stringBuilder.append("\n\t");

        addSuffixOrPrefix(stringBuilder, player);

        stringBuilder.append(". ");

        stringBuilder.append("PI count: ");

        addPointStateMessage(player, stringBuilder);
    }

    /**
     * Finds the edition that matches the year introduced.
     * @return returns the edition found or null if it doesn't exist.
     */
    private Edition findEditionYear() {
        for (Edition edition1 : editions) {
            int editionYear = edition1.getYear();
            if (BusinessManager.systemYear == editionYear) {
                return edition1;
            }
        }
        return null;
    }



    /**
     * Saves progress in the same file format as requested at the start of the
     * execution so that the information can be used in the next execution.
     */
    public void saveData () {
        //I wanted to save the state of progress in both files when closing.
        /*
        if (trialDAO instanceof TrialCsvDAO) {
            new TrialJsonDao().save(trials);
            new EditionJsonDAO().save(editions,trials);
        }
        else {
            new TrialCsvDAO().save(trials);
            new EditionCsvDAO().save(editions,trials);
        }
         */
        editionDAO.save(editions, trials);
        trialDAO.save(trials);
        executionCheckpointDAO.save(checkpoint);
    }

    /**
     * Returns a copy of the list trials.
     * @return trial list (copy)
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
     * Returns a copy of the list editions.
     * @return editions list (copy)
     */
    public List<Edition> getEditions() {
        List<Edition> clone = new ArrayList<>();

        for (Edition edition: editions) {
            try {
                clone.add((Edition) edition.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return clone;
    }

    /**
     * Prints edition using its own toString (modified by us).
     * @param index index of the edition to be printed
     * @return String containing the information about the edition.
     */
    public String EditionToString(int index) {
        return editions.get(index).toString();
    }

    /**
     * Sorts edition by year in ascending order.
     */
    public void sortEditionsByYear() {
        editions.sort(Comparator.comparingInt(Edition::getYear));
    }

    /**
     * Getter for the checkpoint.
     * @return checkpoint value.
     */
    public Integer getCheckpoint() {
        return checkpoint;
    }

    /**
     * Checks if there is one edition corresponding to the current year.
     * @return true if edition exists.
     */
    public boolean editionYearExists () {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return true;
        }
        return false;
    }

    /**
     * Returns number of players in the edition corresponding to the current year.
     * @return number of players still playing.
     */
    public int getEditionNumPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getNumPlayers();
        }
        return 0;
    }

    /**
     * Adds player to the edition corresponding to the current year.
     * @param playerName name of the player being added.
     */
    public void addNewPLayer(String playerName) {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) edition.addPlayer(playerName);
        }
    }

    /**
     * Getter for the number of trials in the edition corresponding to the current year.
     * @return number of trials.
     */
    public int getEditionNumTrials() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getTrials().size();
        }
        return 0;
    }

    /**
     * Getter for the number of players in the edition corresponding to the current year.
     * @return number of players.
     */
    public int getRemainingPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getPlayerListSize();
        }
        return 0;
    }

    /**
     * Checks if a name of a trial exists in the list of trials.
     * @param trialName name to be checked.
     * @return true if the name already exists.
     */
    public boolean trialNameAlreadyExists(String trialName) {
        for (Trial trial : trials) {
            if (Objects.equals(trial.getName(), trialName)) {
                return true;
            }
        }
        return false;
    }
}
