package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estudi extends Trial {
    private final String master;
    private final int credits;
    private final int probabilitat;
    public static final String TYPE = "Estudi";
    private final String type = TYPE;

    public Estudi(String name, String master, int credits, int probabilitat) {
        super(name);
        this.master = master;
        this.credits = credits;
        this.probabilitat = probabilitat;
    }

    @Override
    public String toString() {
        String type = "Master studies";          //for now
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Master: " + master + "\n" +
                "ECTS: " + credits + ", with a " + probabilitat + "% chance to pass each one\n";
    }

    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int[] timesRevisedList = new int[playerList.size()];

        for (int i = 0; i < playerList.size(); i++) {
            int result = 0;

            for (int j = 0; j < credits; j++) {
                if (Math.random()*100 > 50) {
                    result++;
                }
            }

            if(credits/2 < result) {
                statusList.add(true);
            }
            else statusList.add(false);
            timesRevisedList[i] = result;
        }

        return new TrialResult(statusList, timesRevisedList);
    }

    @Override
    public String toCSV() {
        return type+ "," + master + "," + credits + "," + probabilitat;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudi estudi = (Estudi) o;
        return credits == estudi.credits && probabilitat == estudi.probabilitat && Objects.equals(getName(), estudi.getName()) && Objects.equals(master, estudi.master);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), master, credits, probabilitat, type);
    }
}
