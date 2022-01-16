package Business.trials;

import Business.Edition;
import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Defensa implements Trial{
    private String name;
    private String campsEstudi;
    private int dificultat;
    public static final String TYPE = "Defensa";
    private String type = TYPE;

    public Defensa(String name, String campsEstudi, int dificultat) {
        this.name = name;
        this.campsEstudi = campsEstudi;
        this.dificultat = dificultat;
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
            for (int j = 0; j < dificultat; j++) {
                result += 2*i - 1;
            }
            result = (int) Math.sqrt(result);
            if(edition.getPlayers().get(i).getPI_count() > result) {
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

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            stringBuilder.append("\n\t").append(player.getName()).append(" was ");

            if (statusList.get(i)) {
                stringBuilder.append("successful. Congrats! ");
            } else {
                stringBuilder.append("unsuccessful. Sorry... ");
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
                piList.add(1000);
            }
            else {
                piList.add(-5);
            }
        }
        return piList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String type = "Doctoral thesis defense";
        return "Trial: " +name + " (" + type + ")\n" +
                "Enter the entity’s name: " + campsEstudi + "\n" +
                "Difficulty: " + dificultat+ "\n";
    }

    @Override
    public String toCSV() {
        return type+ "," + campsEstudi + "," + dificultat;
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
        Defensa defensa = (Defensa) o;
        return dificultat == defensa.dificultat && Objects.equals(name, defensa.name) && Objects.equals(campsEstudi, defensa.campsEstudi) && Objects.equals(type, defensa.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, campsEstudi, dificultat, type);
    }
}
