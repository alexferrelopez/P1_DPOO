package Business;

import java.util.List;

public interface ExecutableTrial {

    String executeTrial(int numPlayers, TrialResult trialResult, Edition edition);

    List<Integer> assignPI (List<Boolean> statusList);
}
