package Business.trials;

import Business.Edition;
import Business.players.Player;
import Business.TrialResult;

import java.util.ArrayList;
import java.util.List;

public interface Trial extends Cloneable {
    String executeTrial(int numPlayers, TrialResult trialResult, Edition edition);

    /**
     * Passes from results to String in Article, Overridden on every other implementation.
     * @param trialResult
     * @param edition
     * @return
     */
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

    /**
     * Assigns PI to players depending on their result, obtained previously in trialResultToString
     * @param statusList
     * @return
     */
    List<Integer> assignPI(List<Boolean> statusList);

    /**
     * Simple getter of trial name
     * @return
     */
    String getName();

    /**
     * Simple setter of trial name
     * @param name
     */
    void setName(String name);

    /**
     * Used to clone Trials to return a copy of trials to uiManager (to list trials in the end)
     * @return
     */
    Object clone() throws CloneNotSupportedException;

    /**
     * creates a string which includes all necessary atributes to save in trials.csv
     * @return
     */
    String toCSV();

    String getType();
}
