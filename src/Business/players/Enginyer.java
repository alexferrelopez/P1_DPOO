package Business.players;

/**
 * Class extension of Player that provides a specific implementation
 * to earn enough PI to evolve after playing an Estudi.
 */
public class Enginyer extends Player {
    public static final String TYPE = "Enginyer";
    private final String type = TYPE;

    /**
     * Constructor to set name for an Enginyer
     * @param name player name
     */
    public Enginyer(String name) {
        super(name);
    }

    /**
     * Processes the amount of points earned for an Estudi, overridden
     * to make any Enginyer evolve after passing the trial.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     */
    @Override
    public void processPIEstudi(Boolean passed) {
        if (passed) {
            setPI_count(10);
        } else {
            decreasePI(3);
        }
    }

    /**
     * Getter for the type of player
     * @return type of player (Enginyer)
     */
    @Override
    public String getType() {
        return type;
    }
}
