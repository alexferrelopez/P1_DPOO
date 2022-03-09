package Business.players;

public abstract class Player implements Cloneable{
    private final String name;
    private int PI_count = 5;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPI_count () {
        return PI_count;
    }

    public void processPIArticle(Boolean passed, String quartile) {
        if (passed) {
            if ("Q1".equals(quartile)) {
                incresePI(4);
            }
            else if ("Q2".equals(quartile)) {
                incresePI(3);
            }
            else if ("Q3".equals(quartile)) {
                incresePI(2);
            }
            else {
                incresePI(1);
            }
        }
        else {
            if ("Q1".equals(quartile)) {
                decreasePI(5);
            }
            else if ("Q2".equals(quartile)) {
                decreasePI(4);
            }
            else if ("Q3".equals(quartile)) {
                decreasePI(3);
            }
            else {
                decreasePI(2);
            }
        }
    }

    public void processPIEstudi(Boolean passed) {
        if (passed) {
            incresePI(3);
        } else {
            decreasePI(3);
        }
    }

    public void processPIDefensa(Boolean passed) {
        if (passed) {
            incresePI(5);
        } else {
            decreasePI(5);
        }
    }

    public void processPISolicitud(Boolean passed) {
        if (passed) {
            incresePI(PI_count / 2 + PI_count % 2);
        } else {
            decreasePI(2);
        }
    }

    public void decreasePI(int points) {
        PI_count -= points;
    }

    public void incresePI(int points) {
        PI_count += points;
    }

    public boolean isEliminated() {
        return PI_count <= 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", PI_count=" + PI_count +
                '}';
    }

    public abstract String getType();

    public void setPI_count(int pi) {
        this.PI_count = pi;
    }
}
