package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Defensa extends Trial {
    private final String campsEstudi;
    private final int dificultat;
    public static final String TYPE = "Defensa";
    private final String type = TYPE;

    public Defensa(String name, String campsEstudi, int dificultat) {
        super(name);
        this.campsEstudi = campsEstudi;
        this.dificultat = dificultat;
    }

    /**
     * calculates result
     * status added for every player
     * piByPlauer ready
     * points increased
     *
     * @param playerList
     * @return
     */
    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int[] timesRevisedList = new int[playerList.size()];

        for (int i = 0; i < playerList.size(); i++) {
            int result = 0;
            for (int j = 0; j < dificultat; j++) {
                result += 2*i - 1;
            }
            result = (int) Math.sqrt(result);
            if(playerList.get(i).getPI_count() > result) {
                statusList.add(true);
            }
            else {
                statusList.add(false);
            }
            timesRevisedList[i] = result;
        }

        return new TrialResult(statusList, timesRevisedList);
    }

    @Override
    public String toString() {
        String type = "Doctoral thesis defense";
        return "Trial: " + getName() + " (" + type + ")\n" +
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Defensa defensa = (Defensa) o;
        return dificultat == defensa.dificultat && Objects.equals(getName(), defensa.getName()) && Objects.equals(campsEstudi, defensa.campsEstudi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), campsEstudi, dificultat, type);
    }
}
