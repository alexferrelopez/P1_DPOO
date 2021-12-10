package Business;

public class Trial {
    private String name;
    private int acceptance;
    private int revision;
    private  int rejection;
    private Journal journal;

    @Override
    public String toString() {
        String type = "Paper publication";          //for now
        return "Trial: " +name + " (" + type + ")\n" +
                "Journal: " + journal.getName() + " (" + journal.getQuartile() + ")\n" +
                "Chance: " + acceptance + "% acceptance, " + revision + "% revision, " + rejection + "% rejection\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getRejection() {
        return rejection;
    }

    public void setRejection(int rejection) {
        this.rejection = rejection;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
