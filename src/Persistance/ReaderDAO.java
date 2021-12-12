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
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    public List<Trial> trialFromCSV () {
        Path pathTrials = Paths.get("/files/trials.csv");
        try {
            List<String> s  = Files.readAllLines(pathTrials);
            List<Trial> trials = new ArrayList<>();

            for (int i = 0; i < s.size(); i++) {
                Trial aux = new Trial();
                String[] splitTrial = s.get(i).split(",");
                aux.setName(splitTrial[0]);
                aux.setAcceptance(Integer.parseInt(splitTrial[1]));
                aux.setRevision(Integer.parseInt(splitTrial[2]));
                aux.setRejection(Integer.parseInt(splitTrial[3]));
                aux.setJournalName(splitTrial[4]);
                aux.setJournalQuartile(splitTrial[5]);
                trials.add(aux);
            }

            return trials;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Edition> editionFromCSV (List<Trial> trials) {
        Path pathEditions = Paths.get("/files/editions.csv");
        try {
            List<String> s  = Files.readAllLines(pathEditions);
            List<Edition> editions = new ArrayList<>();

            for (int i = 0; i < s.size(); i++) {
                Edition aux = new Edition();
                String[] splitEdition = s.get(i).split(",");
                aux.setYear(Integer.parseInt(splitEdition[0]));

                int numPlayers = Integer.parseInt(splitEdition[1]);

                for (int j = 0; j < numPlayers; j++) {
                    //aux.



                }

                editions.add(aux);
            }

            return editions;
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void editionToCSV (List<Edition> editions, List<Trial> t) { // És el mateix que editionFromCSV al STARUML?
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/files/editions.csv")));

            for (int i = 0; i < editions.size(); i++) {
                StringBuffer linia = new StringBuffer();
                linia.append(editions.get(i).getYear());
                linia.append(",");

                List<Player> players = editions.get(i).getPlayers();

                linia.append(players.size());
                linia.append(",");

                for (int j = 0; j < players.size(); j++) {
                    linia.append(players.get(i).getName());
                    linia.append(",");
                    linia.append(players.get(i).getPI_count());
                    linia.append(",");
                }
                
                List<Trial> trials = editions.get(i).getTrials();

                linia.append(trials.size());
                linia.append(",");

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
