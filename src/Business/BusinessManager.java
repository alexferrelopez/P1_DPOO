package Business;

import Persistance.*;

import java.util.*;

public class BusinessManager {
    private EditionDAO editionDAO;
    private TrialDAO trialDAO;
    private List<Trial> trials = Collections.emptyList();
    private List<Edition> editions = Collections.emptyList();
    private final ExecutionCheckpointDAO executionCheckpointDAO = new ExecutionCheckpointDAO();
    private Integer checkpoint = executionCheckpointDAO.getAll();

    public void loadFromCsv() {
        trialDAO = new TrialCsvDAO();
        trials = trialDAO.getAll();
        editionDAO = new EditionCsvDAO();
        editions = editionDAO.getAll(trials);
    }

    public void loadFromJson() {
        trialDAO = new TrialJsonDao();
        trials = trialDAO.getAll();
        editionDAO = new EditionJsonDAO();
        editions = editionDAO.getAll(trials);
    }

    public void createTrial (String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        Trial trial = new Trial(name, acceptance, revision, rejection, nameJournal, quartile);
        trials.add(trial);
    }

    public boolean deleteTrial (int index) {
        if (index < trials.size() && index >= 0) {
            trials.remove(index);
            return true;
        }
        return false;
    }

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

    public void deleteEdition (int index) {
        checkpoint = null;
        editions.remove(index);
    }

    public int editionLength() {
        return editions.size();
    }

    public int trialLength() {
        return trials.size();
    }


    public List<Integer> executeTrial () {
        for (Edition edition : editions) {
            if (edition.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                List<Trial> trials = edition.getTrials();
                List<Integer> piList = null;
                for (Trial trial : trials) {
                    TrialResult trialResult = trial.executeTrial(edition.getNumPlayers());
                    piList = trial.assignPI(trialResult.getStatusList());
                    List<Player> players = edition.getPlayers();
                    for (int i = 0; i < players.size(); i++) {
                        players.get(i).setPI_count(piList.get(i));
                    }
                }
                return piList;
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

    public String printEdition(int index) {
        return editions.get(index).toString();
    }

    public void sortEditionsByYear() {
        editions.sort(Comparator.comparingInt(Edition::getYear));
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
