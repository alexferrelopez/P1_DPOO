package Business.players;

public interface Player extends Cloneable {
    String getName();

    void setName(String name);

    int getPI_count();

    void addPICount(int points);

    boolean isEliminated();

    @Override
    String toString();

    Object clone() throws CloneNotSupportedException;

    void setPI_count(int parseInt);

    String getType();
}
