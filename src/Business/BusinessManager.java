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
    private final int systemYear = Calendar.getInstance().get(Calendar.YEAR);

    /**
     * loads files from CSV.
     */
    public void loadFromCsv() throws IOException {
        trialDAO = new TrialCsvDAO();
        trials = trialDAO.getAll();
        editionDAO = new EditionCsvDAO();
        editions = editionDAO.getAll(trials);
    }

    /**
     *
     * @throws IOException
     */
    public void loadFromJson() throws IOException {
        trialDAO = new TrialJsonDao();
        trials = trialDAO.getAll();
        editionDAO = new EditionJsonDAO();
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
     * deletes a trial from trials list
     * @param index
     * @return
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
     * Creates an edition
     * @param trialIndexes
     * @param year
     * @param numPlayers
     */
    public void createEdition (List<Integer> trialIndexes, int year, int numPlayers) {
        List<Trial> trialList = new ArrayList<>();

        for (Integer trialIndex : trialIndexes) {
            trialList.add(trials.get(trialIndex));
        }

        processDuplicateEdition(year, new Edition(trialList,new ArrayList<>() ,year,numPlayers));
    }

    /**
     * duplicates an edition depending on the index given
     * @param index
     * @param year
     * @param players
     */
    public void duplicateEdition (int index, int year, int players) {
        Edition duplicate;
        try {
            duplicate = (Edition) editions.get(index).clone();
            duplicate.getPlayers().clear();
            duplicate.setNumPlayers(players);
            duplicate.setYear(year);

            processDuplicateEdition(year, duplicate);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void processDuplicateEdition(int year, Edition duplicate) {
        boolean yearAlreadyExists = false;
        for (int i = 0; i < editions.size(); i++) {
            Edition edition = editions.get(i);
            if (edition.getYear() == year) {
                editions.set(i,duplicate);
                yearAlreadyExists = true;

                if (systemYear == year) {
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
     * deletes edition depending on index
     * @param index
     */
    public void deleteEdition (int index) {
        if (systemYear == editions.get(index).getYear()) {
            checkpoint = null;
        }
        editions.remove(index);
    }

    /**
     * return editions list size
     * @return
     */
    public int editionLength() {
        return editions.size();
    }

    /**
     * returns trials list size
     * @return
     */
    public int trialLength() {
        return trials.size();
    }

    /**
     * executes trial checkpoint allows us to resume just where we left it last time.
     * @return
     */
    public String executeTrial () {

        Edition edition = findEditionYear(systemYear);

        if (edition != null) {

            List<Trial> trialsEdition = edition.getTrials();

            if (checkpoint < trialsEdition.size()) {

                Trial trialToPlay = trialsEdition.get(checkpoint);

                TrialResult trialResult = trialToPlay.executeTrial(edition.getPlayers());

                String quartile = null;
                String type = trialToPlay.getType();

                if (type.equals(Article.TYPE)) {
                    Article article = (Article) trialToPlay;
                    quartile = article.getJorunalQuartile();
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

    private void processTrial(Trial trialToPlay, TrialResult trialResult, String type, List<Boolean> statusList, StringBuilder stringBuilder, List<Player> players) {
        switch (type) {
            case Article.TYPE -> {

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processArticle(trialResult, statusList, stringBuilder, i, player);
                }
            }
            case Estudi.TYPE -> {

                Estudi estudi = (Estudi) trialToPlay;

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processEstudi(trialResult, statusList, stringBuilder, estudi, i, player);
                }
            }
            case Defensa.TYPE ->{

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    processDefensa(statusList, stringBuilder, i, player);
                }
            }
            case Solicitud.TYPE ->{

                if (statusList.get(0)) {
                    stringBuilder.append("\n\t").append("The research group got the budget!\n");
                }

                for (Player player : players) {
                    processSolicitud(stringBuilder, player);
                }
            }
        }
    }

    private void addSuffixOrPrefix(StringBuilder stringBuilder, Player player) {
        if (player.getType().equals(Master.TYPE)) {
            stringBuilder.append("Master ");
        }

        stringBuilder.append(player.getName());

        if (player.getType().equals(Doctor.TYPE)) {
            stringBuilder.append(", Phd");
        }
    }

    private void addPointStateMessage(Player player, StringBuilder stringBuilder) {
        if (player.isEliminated()) {
            stringBuilder.append("0");
            stringBuilder.append(" - Disqualified");
        }
        else stringBuilder.append(player.getPI_count());
    }

    private void processArticle(TrialResult resultTrial, List<Boolean> statusList, StringBuilder stringBuilder, int i, Player player) {
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

    private void processEstudi(TrialResult resultTrial, List<Boolean> statusList, StringBuilder stringBuilder, Estudi estudi, int i, Player player) {
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

    private void processDefensa(List<Boolean> statusList, StringBuilder stringBuilder, int i, Player player) {
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

    private void processSolicitud(StringBuilder stringBuilder, Player player) {
        stringBuilder.append("\n\t");

        addSuffixOrPrefix(stringBuilder, player);

        stringBuilder.append(". ");

        stringBuilder.append("PI count: ");

        addPointStateMessage(player, stringBuilder);
    }

    private Edition findEditionYear(int systemYear) {
        for (Edition edition1 : editions) {
            int editionYear = edition1.getYear();
            if (systemYear == editionYear) {
                return edition1;
            }
        }
        return null;
    }

    //saveData: guarda en ambos ficheros para que en la siguiente ejecucion los ficheros estén en el mismo estado
    public void saveData () {
        if (trialDAO instanceof TrialCsvDAO) {
            new TrialJsonDao().save(trials);
            new EditionJsonDAO().save(editions,trials);
        }
        else {
            new TrialCsvDAO().save(trials);
            new EditionCsvDAO().save(editions,trials);
        }
        editionDAO.save(editions, trials);
        trialDAO.save(trials);
        executionCheckpointDAO.save(checkpoint);
    }

    /**
     * returns a copy of the list trials
     * @return
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
     * returns a copy of the list editions
     * @return
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
     * prints edition using its own toString (modified by us)
     * @param index
     * @return
     */
    public String printEdition(int index) {
        return editions.get(index).toString();
    }

    /**
     * sorts edition by year
     */
    public void sortEditionsByYear() {
        editions.sort(Comparator.comparingInt(Edition::getYear));
    }

    /**
     * simple getter of the checkpoint
     * @return
     */
    public Integer getCheckpoint() {
        return checkpoint;
    }

    /**
     * checks if edition exists.
     * @return
     */
    public boolean editionYearExists () {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return true;
        }
        return false;
    }

    /**
     * returns number of players in executable edition
     * @return
     */
    public int getEditionNumPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getNumPlayers();
        }
        return 0;
    }

    /**
     * adds player in editions
     * @param playerName
     */
    public void addNewPLayer(String playerName) {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) edition.addPlayer(playerName);
        }
    }

    public int getEditionNumTrials() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getTrials().size();
        }
        return 0;
    }

    public int getRemainingPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == systemYear) return edition.getPlayerListSize();
        }
        return 0;
    }

    public boolean trialNameAlreadyExists(String trialName) {
        for (Trial trial : trials) {
            if (Objects.equals(trial.getName(), trialName)) {
                return true;
            }
        }
        return false;
    }
}
