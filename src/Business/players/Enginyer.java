package Business.players;

public class Enginyer extends Player {
    public static final String TYPE = "Enginyer";
    private String type = TYPE;

    public Enginyer(String name) {
        super(name);
    }

    @Override
    public void addPICount(int points) {
        super.addPICount(points);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
