package Persistance;

import Business.trials.Trial;

import java.util.List;

public interface TrialDAO {

    void save (List<Trial> trials);

    List<Trial> getAll ();
}
