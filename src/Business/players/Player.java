package Business.players;

public interface Player extends Cloneable {
    /**
     * gets the name of the player
     * @return
     */
    String getName();

    /**
     * Sets the name of the player
     * @param name
     */
    void setName(String name);

    /**
     * Simple getter of PI from player
     * @return
     */
    int getPI_count();

    /**
     * Adds points to PI_count
     * @param points
     */
    void addPICount(int points);

    /**
     * Returns true if player is eliminated
     * @return
     */
    boolean isEliminated();

    /**
     * toString used to debug
     * @return
     */
    @Override
    String toString();

    /**
     * Used to clone players in duplicate Edition
     * @return
     * @throws CloneNotSupportedException
     */
    Object clone() throws CloneNotSupportedException;

    /**
     * Simple setter
     * @param parseInt
     */
    void setPI_count(int parseInt);

    /**
     * Getter to obtain the type of the player, which facilitates reading from files, and makes the json deserialize and serialize possible.
     * @return
     */
    String getType();
}
