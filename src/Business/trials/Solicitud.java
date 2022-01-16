package Business.trials;

import Business.Edition;
import Business.TrialResult;

import java.util.List;

public class Solicitud implements Trial{
    private String name;
    private String entitat;
    private int pressupost;
    public static final String TYPE = "Solicitud";
    private String type = TYPE;

    public Solicitud(String name, String entitat, int pressupost) {
        this.name = name;
        this.entitat = entitat;
        this.pressupost = pressupost;
    }

    @Override
    public String toString() {
        String type = "Budget request)";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Entity: " + entitat +"\n" +
                "Budget: "+ pressupost +"\n";
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
    public String executeTrial(int numPlayers, TrialResult trialResult, Edition edition) {
        return null;
    }

    @Override
    public String trialResultToString(TrialResult trialResult, Edition edition) {
        return Trial.super.trialResultToString(trialResult, edition);
    }

    @Override
    public List<Integer> assignPI(List<Boolean> statusList) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toCSV() {
        return type+ "," + entitat + "," + pressupost;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void setType(String s) {

    }
}
