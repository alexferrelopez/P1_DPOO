package Business.players;

public abstract class Player implements Cloneable{
    private final String name;
    private int PI_count = 5;

    /**
     * Constructor to set name for a Player
     * @param name player name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter for the name of a Player
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the PI count of a Player
     * @return PI count for the player.
     */
    public int getPI_count () {
        return PI_count;
    }

    /**
     * Processes the amount of points earned for an Article.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     * @param quartile dictates the amount of points earned when a player plays an Article.
     */
    public void processPIArticle(Boolean passed, String quartile) {
        if (passed) {
            if ("Q1".equals(quartile)) {
                increasePI(4);
            }
            else if ("Q2".equals(quartile)) {
                increasePI(3);
            }
            else if ("Q3".equals(quartile)) {
                increasePI(2);
            }
            else {
                increasePI(1);
            }
        }
        else {
            if ("Q1".equals(quartile)) {
                decreasePI(5);
            }
            else if ("Q2".equals(quartile)) {
                decreasePI(4);
            }
            else if ("Q3".equals(quartile)) {
                decreasePI(3);
            }
            else {
                decreasePI(2);
            }
        }
    }

    /**
     * Processes the amount of points earned for an Estudi.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     */
    public void processPIEstudi(Boolean passed) {
        if (passed) {
            increasePI(3);
        } else {
            decreasePI(3);
        }
    }

    /**
     * Processes the amount of points earned for a Defensa.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     */
    public void processPIDefensa(Boolean passed) {
        if (passed) {
            increasePI(5);
        } else {
            decreasePI(5);
        }
    }

    /**
     * Processes the amount of points earned for a Solicitud.
     * @param passed boolean used to either increase or decrease when a
     *               player passes the trial or fails it respectively.
     */
    public void processPISolicitud(Boolean passed) {
        if (passed) {
            increasePI(PI_count / 2 + PI_count % 2);
        } else {
            decreasePI(2);
        }
    }

    /**
     * Decreases PI count of the player.
     * @param points PI to be decreased.
     */
    public void decreasePI(int points) {
        PI_count -= points;
    }

    /**
     * Increases PI count of the player.
     * @param points PI to be increased.
     */
    public void increasePI(int points) {
        PI_count += points;
    }

    /**
     * Checks if player is eliminated.
     * @return returns true when PI count is 0 or less.
     */
    public boolean isEliminated() {
        return PI_count <= 0;
    }

    /**
     * Clones a player.
     * @return Copy of current player.
     * @throws CloneNotSupportedException standard clone exception.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Getter for the type of player
     * @return type of player (Doctor, Enginyer or Master)
     */
    public abstract String getType();

    /**
     * Setter for the PI_count for the player.
     * @param pi PI_count desired.
     */
    public void setPI_count(int pi) {
        this.PI_count = pi;
    }
}
