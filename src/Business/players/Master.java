package Business.players;

/**
 * Class extension of Player that provides a specific implementation
 * to decrease points and earn enough PI to evolve after playing a Defensa.
 */
public class Master extends Player {
    public static final String TYPE = "Master";
    private final String type = TYPE;

    /**
     * Constructor to set name for a Master
     * @param name player name
     */
    public Master(String name) {
        super(name);
    }

    /**
     * Processes the amount of points earned for a Estudi, overridden
     * to make any Master evolve after passing the trial.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     */
    @Override
    public void processPIDefensa(Boolean passed) {
        if (passed) {
            setPI_count(10);
        } else {
            decreasePI(5);
        }
    }

    /**
     * Decreases PI count of the player, overridden to make masters lose half of
     * the regular amount of points earned per trial.
     * @param points PI to be decreased.
     */
    @Override
    public void decreasePI(int points) {
        super.decreasePI(points/2);
    }

    /**
     * Getter for the type of player
     * @return type of player (Master)
     */
    @Override
    public String getType() {
        return type;
    }
}
