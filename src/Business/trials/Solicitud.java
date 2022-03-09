package Business.trials;

import Business.EditionWrapper;
import Business.TrialResult;
import Business.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solicitud extends Trial {
    private final String entitat;
    private final int pressupost;
    public static final String TYPE = "Solicitud";
    private final String type = TYPE;

    public Solicitud(String name, String entitat, int pressupost) {
        super(name);
        this.entitat = entitat;
        this.pressupost = pressupost;
    }

    @Override
    public String toString() {
        String type = "Budget request)";
        return "Trial: " + getName() + " (" + type + ")\n" +
                "Entity: " + entitat +"\n" +
                "Budget: "+ pressupost +"\n";
    }

    @Override
    public TrialResult executeTrial(List<Player> playerList) {
        List<Boolean> statusList = new ArrayList<>();
        int result = 0;

        for (Player player : playerList) {
            result += player.getPI_count();
        }

        int calcul = (int)(Math.log(pressupost) / Math.log(2));

        if (result > calcul) {
            for (int i = 0; i < playerList.size(); i++) {
                statusList.add(true);
            }
        }
        else {
            for (int i = 0; i < playerList.size(); i++) {
                statusList.add(false);
            }
        }

        List<Integer> piByPlayer = assignPI(statusList);

        return new TrialResult(statusList, null, piByPlayer);
    }

    @Override
    public String resultProcessing(TrialResult trialResult, EditionWrapper editionWrapper, List<Player> playerList) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Boolean> statusList = trialResult.getStatusList();

        if (statusList.get(0)) {
            stringBuilder.append("\n\t").append("The research group got the budget!\n");
        }
        for (Player player : playerList) {
            stringBuilder.append("\n\t").append(player.getName()).append(". ");


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
                piList.add(10000);
            }
            else {
                piList.add(-2);
            }
        }
        return piList;
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
        return pressupost == solicitud.pressupost && Objects.equals(getName(), solicitud.getName()) && Objects.equals(entitat, solicitud.entitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), entitat, pressupost, type);
    }
}
