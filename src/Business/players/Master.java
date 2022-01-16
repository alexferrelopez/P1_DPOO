    package Business.players;

public class Master implements Player {
    private String name;
    private int PI_count = 5;
    public static final String TYPE = "Master";
    private String type = TYPE;

    public Master(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPI_count() {
        return PI_count;
    }

    @Override
    public void addPICount(int points) {
        if (points<0) {
            this.PI_count += points/2;
        }
        this.PI_count += points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isEliminated() {
        return PI_count <= 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", PI_count=" + PI_count +
                '}';
    }

    @Override
    public void setPI_count(int parseInt) {
        this.PI_count = parseInt;
    }
}
