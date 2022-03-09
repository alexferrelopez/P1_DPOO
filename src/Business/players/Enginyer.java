package Business.players;

public class Enginyer extends Player {
    public static final String TYPE = "Enginyer";
    private String type = TYPE;

    public Enginyer(String name) {
        super(name);
    }

    @Override
    public void processPIEstudi(Boolean passed) {
        if (passed) {
            incresePI(10);
        } else {
            decreasePI(3);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
