package Business;

import java.util.Objects;

/**
 * Represents a Journal with a name and a quartile.
 */
public class Journal {
    private String name;
    private String quartile;

    /**
     * Custom equals for Journal.
     * @param o object to compare to
     * @return true if object has the same reference or attributes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return name.equals(journal.name) && quartile.equals(journal.quartile);
    }

    /**
     * Custom hashCode for Journal.
     * @return hash code created from the sequence of attributes for Article.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, quartile);
    }

    /**
     * Getter for the name of the Journal.
     * @return name of the Journal.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the Journal.
     * @param name name of the Journal
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the quartile of the Journal.
     * @return quartile of the Journal.
     */
    public String getQuartile() {
        return quartile;
    }

    /**
     * Setter for the quartile of the Journal.
     * @param quartile quartile of the Journal.
     */
    public void setQuartile(String quartile) {
        this.quartile = quartile;
    }
}
