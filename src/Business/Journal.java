package Business;

import java.util.Objects;

public class Journal {
    private String name;
    private String quartile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return name.equals(journal.name) && quartile.equals(journal.quartile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quartile);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuartile() {
        return quartile;
    }

    public void setQuartile(String quartile) {
        this.quartile = quartile;
    }
}
