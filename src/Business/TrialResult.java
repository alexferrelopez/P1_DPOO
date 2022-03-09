package Business;

import java.util.ArrayList;
import java.util.List;

public class TrialResult {
    private final List<Boolean> statusList;
    private final int[] auxInfo;

    public TrialResult(List<Boolean> statusList, int[] auxInfo) {
        this.statusList = statusList;
        this.auxInfo = auxInfo;
    }

    public List<Boolean> getStatusList() {
        return new ArrayList<>(statusList);
    }

    public int[] getAuxInfo() {
        int[] result = new int[auxInfo.length];
        System.arraycopy(auxInfo,0,result,0, auxInfo.length);
        return result;
    }

}
