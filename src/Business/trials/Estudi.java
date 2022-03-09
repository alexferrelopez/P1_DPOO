package Business.trials;

import Business.EditionWrapper;
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

        List<Integer> piByPlayer = assignPI(statusList);

        return new TrialResult(statusList, timesRevisedList, piByPlayer);
    }

    @Override
    public String resultProcessing(TrialResult trialResult, EditionWrapper editionWrapper, List<Player> playerList) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Boolean> statusList = trialResult.getStatusList();
        int[] timesRevisedList = trialResult.getAuxInfo();

        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            stringBuilder.append("\n\t").append(player.getName()).append(" passed ").append(timesRevisedList[i]).append("/").append(credits).append(" ECTS.");

            if (statusList.get(i)) {
                stringBuilder.append("Congrats! ");
            } else {
                stringBuilder.append("Sorry... ");
            }

            stringBuilder.append("PI count: ");

            if (player.getPI_count() <= 0) {
                stringBuilder.append(0).append(" - Disqualified!");
            } else stringBuilder.append(player.getPI_count());
        }

        editionWrapper.removePlayers();

        return stringBuilder.toString();
    }

    @Override
    public List<Integer> assignPI(List<Boolean> statusList) {
        List<Integer> piList = new ArrayList<>();
        for (Boolean hasPassed : statusList) {
            if (hasPassed) {
                piList.add(100);
            }
            else {
                piList.add(-3);
            }
        }
        return piList;
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
