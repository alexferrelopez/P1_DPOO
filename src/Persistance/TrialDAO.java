package Persistance;

import Business.trials.Trial;

import java.io.IOException;
import java.util.List;

/**
 * Reads and writes Trials.
 */
public interface TrialDAO {

    /**
     * Saves all data to file (JSON or CSV).
     * @param trials list of trials from BusinessManager.
     */
    void save (List<Trial> trials);

    /**
     * returns list of Trials from a file (JSON or CSV).
     * @return list of trials given to BusinessManager.
     * @throws IOException standard IO exception.
     */
    List<Trial> getAll () throws IOException;
}
