package Business;

import java.util.ArrayList;
import java.util.List;

public class TrialResult {
    private final List<Boolean> statusList;
    private final int[] auxInfo;

    /**
     * Constructor to create a TrialResult
     * @param statusList list that indicates if the player passed or failed.
     * @param auxInfo array containing extra information about the execution for each player
     *                (used to store: number of times that an article has been revised,
     *                                number of credits passed ).
     */
    public TrialResult(List<Boolean> statusList, int[] auxInfo) {
        this.statusList = statusList;
        this.auxInfo = auxInfo;
    }

    /**
     * Getter for the list of status.
     * @return statusList (copy)
     */
    public List<Boolean> getStatusList() {
        return new ArrayList<>(statusList);
    }

    /**
     * Getter for the array of auxInfo.
     * @return array of auxInfo (copy)
     */
    public int[] getAuxInfo() {
        int[] result = new int[auxInfo.length];
        System.arraycopy(auxInfo,0,result,0, auxInfo.length);
        return result;
    }

}
