    package Business.players;

public class Doctor implements Player {
    private String name;
    private int PI_count = 5;
    public static final String TYPE = "Doctor";
    private String type = TYPE;

    public Doctor(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        if (points == 100) {
            points = 3;
        }
        else if (points == 1000) {
            points = 5;
        }
        else if (points == 10000) {
            points = PI_count/2 + PI_count%2;
        }

        if (points<0) {
            this.PI_count += points/2;
        }
        else this.PI_count += points*2;
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
