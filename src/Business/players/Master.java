    package Business.players;

public class Master extends Player {
    public static final String TYPE = "Master";
    private String type = TYPE;

    public Master(String name) {
        super(name);
    }

    @Override
    public void addPICount(int points) {
        if (isAlive()) {
            if (points == 100) {
                points = 3;
            } else if (points == 1000) {
                points = 10;
            } else if (points == 10000) {
                points = getPI_count() / 2 + getPI_count() % 2;
            }

            if (points < 0) {
                setPI_count(getPI_count() + points / 2);
            }
            setPI_count(points);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
