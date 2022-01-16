package Persistance;

import Business.Edition;
import Business.trials.Trial;

import java.util.List;

public interface EditionDAO {

    void save(List<Edition> editions, List<Trial> t);

    List<Edition> getAll(List<Trial> trials);
}
