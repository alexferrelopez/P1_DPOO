package Persistance;

import Business.trials.Trial;

import java.util.List;

public interface TrialDAO {

    /**
     * Saves all data to file (JSON or CSV)
     * @param trials list of trials from BusinessManager
     */
    void save (List<Trial> trials);

    /**
     * returns list of Trials from a file (JSON or CSV)
     * @return list of trials given to BusinessManager
     */
    List<Trial> getAll ();
}
