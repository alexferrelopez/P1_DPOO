package Persistance;

import Business.Edition;
import Business.trials.Trial;

import java.io.IOException;
import java.util.List;

/**
 * Reads and writes Editions.
 */
public interface EditionDAO {

    /**
     * Saves all data to file (JSON or CSV).
     * @param editions list of editions.
     * @param trialList list of trials.
     */
    void save(List<Edition> editions, List<Trial> trialList);

    /**
     * Returns list of Editions from a file (JSON or CSV).
     * @param trials list of trials.
     * @return list of editions.
     * @throws IOException standard IO exception.
     */
    List<Edition> getAll(List<Trial> trials) throws IOException;
}
