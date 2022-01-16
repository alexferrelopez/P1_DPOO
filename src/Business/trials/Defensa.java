package Business.trials;

import Business.Edition;
import Business.TrialResult;

import java.util.List;

public class Defensa implements Trial{
    private String name;
    private String campsEstudi;
    private int dificultat;
    public static final String TYPE = "Defensa";
    private String type = TYPE;

    public Defensa(String name, String campsEstudi, int dificultat) {
        this.name = name;
        this.campsEstudi = campsEstudi;
        this.dificultat = dificultat;
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
    public String toString() {
        String type = "Doctoral thesis defense";
        return "Trial: " +name + " (" + type + ")\n" +
                "Enter the entity’s name: " + campsEstudi + "\n" +
                "Difficulty: " + dificultat+ "\n";
    }

    @Override
    public String toCSV() {
        return type+ "," + campsEstudi + "," + dificultat;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void setType(String s) {

    }
}
