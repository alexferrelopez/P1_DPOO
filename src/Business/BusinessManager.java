package Business;

import Business.trials.*;
import Persistance.*;

import java.util.*;

public class BusinessManager {
    private EditionDAO editionDAO;
    private TrialDAO trialDAO;
    private List<Trial> trials = new ArrayList<>();
    private List<Edition> editions = new ArrayList<>();
    private final ExecutionCheckpointDAO executionCheckpointDAO = new ExecutionCheckpointDAO();
    private Integer checkpoint = executionCheckpointDAO.getAll();

    /**
     * loads files from CSV.
     */
    public void loadFromCsv() {
        trialDAO = new TrialCsvDAO();
        trials = trialDAO.getAll();
        editionDAO = new EditionCsvDAO();
        editions = editionDAO.getAll(trials);
    }

    /**
     * loads files from Json
     */
    public void loadFromJson() {
        trialDAO = new TrialJsonDao();
        trials = trialDAO.getAll();
        editionDAO = new EditionJsonDAO();
        editions = editionDAO.getAll(trials);
    }

    /**
     * Creates a trial depending on the atribute type.
     * @param type
     * @param name
     * @param other
     * @param acceptance
     * @param revision
     * @param rejection
     * @param nameJournal
     * @param quartile
     * @param probabilitat
     * @param credits
     * @param dificulty
     * @param budget
     */
    public void createTrial(int type, String name, String other, int acceptance, int revision, int rejection, String nameJournal, String quartile, int probabilitat, int credits, int dificulty, int budget) {
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
                Trial trial = new Defensa(name,other,dificulty);
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
        if (index < trials.size() && index >= 0) {
            trials.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Creates an edition
     * @param trialIndexes
     * @param year
     * @param numPlayers
     */
    public void createEdition (List<Integer> trialIndexes, int year, int numPlayers) {
        Edition edition = new Edition();
        List<Trial> trialList = new ArrayList<>();
        
        edition.setYear(year);
        edition.setNumPlayers(numPlayers);

        for (Integer trialIndex : trialIndexes) {
            trialList.add(trials.get(trialIndex));
        }

        edition.setTrials(trialList);

        boolean yearAlreadyExists = false;
        for (int i = 0; i < editions.size(); i++) {
            Edition edition1 = editions.get(i);
            if (edition1.getYear() == year) {
                editions.set(i, edition);
                yearAlreadyExists = true;
                break;
            }
        }
        if (!yearAlreadyExists) {
            editions.add(edition);
        }
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

            boolean yearAlreadyExists = false;
            for (int i = 0; i < editions.size(); i++) {
                Edition edition = editions.get(i);
                if (edition.getYear() == year) {
                    editions.set(i,edition);
                    yearAlreadyExists = true;
                    break;
                }
            }
            if (!yearAlreadyExists) {
                editions.add(duplicate);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes edition depending on index
     * @param index
     */
    public void deleteEdition (int index) {
        checkpoint = null;
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
        int systemYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Edition edition : editions) {
            int editionYear = edition.getYear();
            if (systemYear == editionYear) {
                List<Trial> trialsEdition = edition.getTrials();
                if (checkpoint < trialsEdition.size()) {
                    Trial trialToPlay = trialsEdition.get(checkpoint);
                    TrialResult trialResult = new TrialResult(edition.getPlayerListSize());

                    String result;

                    String trialHeader = "\nTrial #" + (checkpoint+1) + " - " + trialToPlay.getName() +"\n";
                    String resultTrial = trialToPlay.executeTrial(edition.getPlayerListSize(), trialResult, edition);
                    result = trialHeader + resultTrial;

                    checkpoint++;
                    if (checkpoint == trialsEdition.size() || getRemainingPlayers() == 0) {
                        checkpoint = 0;

                        if (edition.allPLayersEliminated()) {
                            edition.clearPlayers();
                            return result + "\n\nTHE TRIALS " + editionYear + " HAVE ENDED - PLAYERS LOST";
                        } else {
                            edition.clearPlayers();
                            return result + "\n\nTHE TRIALS " + editionYear + " HAVE ENDED - PLAYERS WON";
                        }
                    }
                    return result;
                }
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
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) return true;
        }
        return false;
    }

    /**
     * returns number of players in executable edition
     * @return
     */
    public int getEditionNumPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) return edition.getNumPlayers();
        }
        return 0;
    }

    /**
     * adds player in editions
     * @param playerName
     */
    public void addNewPLayer(String playerName) {
        for (Edition edition : editions) {
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) edition.addPlayer(playerName);
        }
    }

    public int getEditionNumTrials() {
        for (Edition edition : editions) {
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) return edition.getTrials().size();
        }
        return 0;
    }

    public int getRemainingPlayers() {
        for (Edition edition : editions) {
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) return edition.getPlayerListSize();
        }
        return 0;
    }

    //////////////////////////////////////////
     ///     TEST DE DUPLICAR (EDITIONS)    ///
    //////////////////////////////////////////

/*
    public static void main(String[] args) {
        BusinessManager businessManager = new BusinessManager();

        businessManager.duplicateEdition(0 ,2010 , 2);

        System.out.println(businessManager.getEditions());
    }

 */
}
