package Business.trials;

import Business.TrialResult;
import Business.players.Player;

import java.util.List;

public abstract class Trial implements Cloneable {
    private String name;

    public Trial(String name){
        this.name = name;
    }

    public abstract TrialResult executeTrial(List<Player> playerList);

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String toCSV();

    public abstract String getType();
    
}
