package Business.players;

public class Enginyer extends Player {
    public static final String TYPE = "Enginyer";
    private String type = TYPE;

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
