package Persistance;

import Business.Trial;

import java.util.List;

public interface TrialDAO {

    void save (List<Trial> trials);

    List<Trial> getAll ();
}
