package Business.players;

public abstract class Player implements Cloneable{
    private String name;
    private int PI_count = 5;

    public Player(String name) {
        this.name = name;
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

    public void  addPICount(int points) {

        if (isAlive()) {
            if (points == 100) {
                points = 10;
            } else if (points == 1000) {
                points = 5;
            } else if (points == 10000) {
                points = PI_count / 2 + PI_count % 2;
            }
            this.PI_count += points;
        }
    }

    public boolean isAlive() {
        return PI_count > 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", PI_count=" + PI_count +
                '}';
    }

    public abstract String getType();

    public void setPI_count(int parseInt) {
        this.PI_count = parseInt;
    }
}
