package Persistance;

import Business.Edition;
import Business.trials.Trial;

import java.io.IOException;
import java.util.List;

public interface EditionDAO {


    /**
     * Saves all data to file (JSON or CSV)
     * @param editions
     * @param t
     */
    void save(List<Edition> editions, List<Trial> t);

    /**
     * returns list of Editions from a file (JSON or CSV)
     * @param trials
     * @return
     */
    List<Edition> getAll(List<Trial> trials) throws IOException;
}
