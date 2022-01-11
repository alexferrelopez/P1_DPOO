package Business;

import java.util.List;

public interface ExecutableTrial {

    TrialResult executeTrial(int numPlayers);

    List<Integer> assignPI (List<Boolean> statusList);
}
