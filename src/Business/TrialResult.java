package Business;

import java.util.ArrayList;

import java.util.List;

public class TrialResult {
    private int[] timesRevisedList;
    private List<Boolean> statusList;

    public TrialResult (int numPlayers) {
        timesRevisedList = new int[numPlayers];
        statusList = new ArrayList<>();
    }

    public int[] getTimesRevisedList() {
        return timesRevisedList;
    }

    public void setTimesRevisedList(int[] timesRevisedList) {
        this.timesRevisedList = timesRevisedList;
    }

    public List<Boolean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Boolean> statusList) {
        this.statusList = statusList;
    }
}
