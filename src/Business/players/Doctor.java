    package Business.players;

public class Doctor extends Player {
    public static final String TYPE = "Doctor";
    private final String type = TYPE;

    /**
     * Constructor to set name for a Doctor
     * @param name player name
     */
    public Doctor(String name) {
        super(name);
    }

    /**
     * Increases PI count of the player, overridden to make doctors win 2x times
     * the regular amount of points earned per trial.
     * @param points PI to be increased.
     */
    @Override
    public void increasePI(int points) {
        super.increasePI(points*2);
    }

    /**
     * Decreases PI count of the player, overridden to make doctors lose half of
     * the regular amount of points earned per trial.
     * @param points PI to be decreased.
     */
    @Override
    public void decreasePI(int points) {
        super.decreasePI(points/2);
    }

    /**
     * Getter for the type of player
     * @return type of player (Doctor)
     */
    @Override
    public String getType() {
        return type;
    }
}
