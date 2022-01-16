package Business.trials;

import Business.Edition;
import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estudi implements Trial{
    private String name;
    private String master;
    private int credits;
    private int probabilitat;
    public static final String TYPE = "Estudi";
    private String type = TYPE;

    public Estudi(String name, String master, int credits, int probabilitat) {
        this.name = name;
        this.master = master;
        this.credits = credits;
        this.probabilitat = probabilitat;
    }

    @Override
    public String toString() {
        String type = "Master studies";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Master: " + master + "\n" +
                "ECTS: " + credits + ", with a " + probabilitat + "% chance to pass each one\n";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String executeTrial(int numPlayers, TrialResult trialResult, Edition edition) {
        List<Boolean> statusList = new ArrayList<>();
        int[] timesRevisedList = trialResult.getTimesRevisedList();

        for (int i = 0; i < numPlayers; i++) {
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
        trialResult.setStatusList(statusList);
        List<Integer> piByPlayer = assignPI(trialResult.getStatusList());
        edition.setPis(piByPlayer);

        return trialResultToString(trialResult,edition);
    }

    @Override
    public String trialResultToString(TrialResult trialResult, Edition edition) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Player> players = edition.getPlayers();
        List<Boolean> statusList = trialResult.getStatusList();
        List<Player> playersToRemove = new ArrayList<>();
        int[] timesRevisedList = trialResult.getTimesRevisedList();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            stringBuilder.append("\n\t").append(player.getName()).append(" passed ").append(timesRevisedList[i]).append("/").append(credits).append(" ECTS.");

            if (statusList.get(i)) {
                stringBuilder.append("Congrats! ");
            } else {
                stringBuilder.append("Sorry... ");
            }

            stringBuilder.append("PI count: ");

            if (player.getPI_count() <= 0) {
                stringBuilder.append(0).append(" - Disqualified!");
                playersToRemove.add(player);
            } else stringBuilder.append(player.getPI_count());
        }

        players.removeAll(playersToRemove);
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
    public void setType(String s) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudi estudi = (Estudi) o;
        return credits == estudi.credits && probabilitat == estudi.probabilitat && Objects.equals(name, estudi.name) && Objects.equals(master, estudi.master) && Objects.equals(type, estudi.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, master, credits, probabilitat, type);
    }
}
