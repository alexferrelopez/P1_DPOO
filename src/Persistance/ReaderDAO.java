package Persistance;
import Business.Edition;
import Business.Player;
import Business.Trial;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReaderDAO {
    public List<Trial> trialFromCSV () {
        Path pathTrials = Paths.get("/files/trials.csv");
        try {
            List<String> s  = Files.readAllLines(pathTrials);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Edition> editionFromCSV () {
        return null;
    }

    public void TrialToCSV (List<Trial> trials) { // Es el mateix que trialFromCSV al STARUML?
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/files/trials.csv")));
            for (int i = 0; i < trials.size(); i++) {
                StringBuffer linia = new StringBuffer();
                linia.append(i);
                linia.append(",");
                linia.append(trials.get(i).getName());
                linia.append(",");
                linia.append(trials.get(i).getAcceptance());
                linia.append(",");
                linia.append(trials.get(i).getRevision());
                linia.append(",");
                linia.append(trials.get(i).getRejection());
                linia.append(",");
                linia.append(trials.get(i).getJournalName());
                linia.append(",");
                linia.append(trials.get(i).getJorunalQuartile());
                bw.write(linia.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editionToCSV (List<Edition> editions, List<Trial> t) { // Es el mateix que editionFromCSV al STARUML?
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/files/editions.csv")));

            for (int i = 0; i < editions.size(); i++) {
                StringBuffer linia = new StringBuffer();
                linia.append(editions.get(i).getYear());
                linia.append(",");

                List<Player> players = editions.get(i).getPlayers();

                for (int j = 0; j < players.size(); j++) {
                    linia.append(players.get(i).getName());
                    linia.append(",");
                    linia.append(players.get(i).getPI_count());
                    linia.append(",");
                }
                
                List<Trial> trials = editions.get(i).getTrials();

                for (int j = 0; j < t.size(); j++) {
                    for (int k = 0; k < trials.size(); k++) {
                        if (t.get(j).equals(trials.get(k))) {
                            linia.append(j);
                            linia.append(",");
                        }
                    }
                }
                bw.write(linia.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
