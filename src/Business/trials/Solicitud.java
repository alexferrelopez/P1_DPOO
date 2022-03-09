package Business.trials;

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

        return new TrialResult(statusList, null);
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
