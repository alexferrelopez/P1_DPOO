package Business.trials;

import Business.Edition;
import Business.TrialResult;

import java.util.List;

public class Estudi implements Trial{
    private String name;
    private String master;
    private int credits;
    private int probabilitat;
    public static final String TYPE = "Estudi";
    private String type = TYPE;

    public Estudi(String name, String master, int credits, int probabilitat) {
        this.name = name;
        this.master = master;
        this.credits = credits;
        this.probabilitat = probabilitat;
    }

    @Override
    public String toString() {
        String type = "Master studies";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Master: " + master + "\n" +
                "ECTS: " + credits + ", with a " + probabilitat + "% chance to pass each one\n";
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
        return type+ "," + master + "," + credits + "," + probabilitat;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void setType(String s) {

    }
}
