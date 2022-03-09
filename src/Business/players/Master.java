    package Business.players;

public class Master extends Player {
    public static final String TYPE = "Master";
    private String type = TYPE;

    public Master(String name) {
        super(name);
    }

    @Override
    public void processPIDefensa(Boolean passed) {
        if (passed) {
            incresePI(10);
        } else {
            decreasePI(5);
        }
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
