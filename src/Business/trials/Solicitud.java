package Business.trials;

import Business.Edition;
import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solicitud implements Trial{
    private String name;
    private final String entitat;
    private final int pressupost;
    public static final String TYPE = "Solicitud";
    private final String type = TYPE;

    public Solicitud(String name, String entitat, int pressupost) {
        this.name = name;
        this.entitat = entitat;
        this.pressupost = pressupost;
    }

    @Override
    public String toString() {
        String type = "Budget request)";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Entity: " + entitat +"\n" +
                "Budget: "+ pressupost +"\n";
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
        List<Player> players = edition.getPlayers();
        int result = 0;
        for (int i = 0; i < numPlayers; i++) {
            result += players.get(i).getPI_count();
        }

        int calcul = (int)(Math.log(pressupost) / Math.log(2));

        if (result > calcul) {
            for (int i = 0; i < numPlayers; i++) {
                statusList.add(true);
            }
        }
        else {
            for (int i = 0; i < numPlayers; i++) {
                statusList.add(false);
            }
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

        if (statusList.get(0)) {
            stringBuilder.append("\n\t").append("The research group got the budget!\n");
        }
        for (Player player : players) {
            stringBuilder.append("\n\t").append(player.getName()).append(". ");


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
                piList.add(10000);
            }
            else {
                piList.add(-2);
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
        return type+ "," + entitat + "," + pressupost;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solicitud solicitud = (Solicitud) o;
        return pressupost == solicitud.pressupost && Objects.equals(name, solicitud.name) && Objects.equals(entitat, solicitud.entitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entitat, pressupost, type);
    }
}
