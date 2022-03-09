    package Business.players;

public class Doctor extends Player {
    public static final String TYPE = "Doctor";
    private String type = TYPE;

    public Doctor(String name) {
        super(name);
    }

    @Override
    public void incresePI(int points) {
        super.incresePI(points*2);
    }

    @Override
    public void decreasePI(int points) {
        super.decreasePI(points/2);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
