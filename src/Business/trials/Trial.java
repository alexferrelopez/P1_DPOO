package Business.trials;

import Business.Edition;
import Business.players.Player;
import Business.TrialResult;

import java.util.ArrayList;
import java.util.List;

public interface Trial extends Cloneable {
    String executeTrial(int numPlayers, TrialResult trialResult, Edition edition);

    default String trialResultToString(TrialResult trialResult, Edition edition) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Player> players = edition.getPlayers();
        List<Boolean> statusList = trialResult.getStatusList();
        int[] timesRevisedList = trialResult.getTimesRevisedList();
        List<Player> playersToRemove = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            stringBuilder.append("\n\t").append(player.getName()).append(" is submitting... ");
            stringBuilder.append("Revisions... ".repeat(Math.max(0, timesRevisedList[i])));

            if (statusList.get(i)) {
                stringBuilder.append("Accepted! ");
            } else {
                stringBuilder.append("Rejected. ");
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

    List<Integer> assignPI(List<Boolean> statusList);

    String getName();

    void setName(String name);

    Object clone() throws CloneNotSupportedException;

    String toCSV();

    String getType();

    void setType(String s);
}
