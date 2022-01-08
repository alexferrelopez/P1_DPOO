package Business;

import Persistance.EditionCsvDAO;
import Persistance.EditionDAO;
import Persistance.TrialCsvDAO;
import Persistance.TrialDAO;

import java.util.ArrayList;
import java.util.List;

public class BusinessManager {
    private final EditionDAO editionDAO = new EditionCsvDAO();
    private final TrialDAO trialDAO = new TrialCsvDAO();
    private final List<Trial> trials = trialDAO.getAll();
    private final List<Edition> editions = editionDAO.getAll(trials);

    public void createTrial (String name, int acceptance, int revision, int rejection, String nameJournal, String quartile) {
        Trial trial = new Trial(name, acceptance, revision, rejection, nameJournal, quartile);
        trials.add(trial);
    }

    public boolean deleteTrial (int index) {
        if (index < trials.size() && index >= 0) {
            trials.remove(index);
            return false;
        }

        return true;
    }

    public void createEdition (int year, int numTrials, int numPlayers, List<Integer> trialIndexes) {
        Edition edition = new Edition();
        List<Trial> trialList = new ArrayList<>();
        
        edition.setYear(year);
        edition.setNumPlayers(numPlayers);

        for (Integer trialIndex : trialIndexes) {
            trialList.add(trials.get(trialIndex));
        }

        edition.setTrials(trialList);
        editions.add(edition);
    }

    public void duplicateEdition (int index, int year, int players) {
        Edition duplicate;
        try {
            duplicate = (Edition) editions.get(index).clone();
            duplicate.getPlayers().clear();
            duplicate.setNumPlayers(players);
            editions.add(duplicate);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    public boolean deleteEdition (int index) {
        if (index < editions.size() && index >= 0) {
            editions.remove(index);
            return true;
        }

        return false;
    }

    public int editionLength() {
        return editions.size();
    }

    public void executeTrial () {
        //TODO execute
    }

    public void saveData () {
        editionDAO.save(editions, trials);
        trialDAO.save(trials);
    }

    public List<Trial> getTrials() {
        List<Trial> copy = new ArrayList<>();

        for (Trial trial : trials) {
            try {
                copy.add((Trial) trial.clone());
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

      //////////////////////////////////////////
     ///          TEST DE DUPLICAR          ///
    //////////////////////////////////////////

/*
    public static void main(String[] args) {
        BusinessManager businessManager = new BusinessManager();

        businessManager.duplicateEdition(0 ,2010 , 2);

        System.out.println(businessManager.getEditions());
    }

 */
}
