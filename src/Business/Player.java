package Business;

public class Player {
    private String name;
    private int PI_count;

    public Player(String name, int PI_count) {
        this.name = name;
        this.PI_count = PI_count;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPI_count() {
        return PI_count;
    }

    public void setPI_count(int PI_count) {
        this.PI_count = PI_count;
    }

    public void changePI (Player player, Edition edition) {

    }

    public boolean isEliminated () {
        return PI_count <= 0;
    }
}
