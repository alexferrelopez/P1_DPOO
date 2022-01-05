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
        Path pathTrials = Paths.get("./files/trials.csv");
        try {
            List<String> s  = Files.readAllLines(pathTrials);
            List<Trial> trials = new ArrayList<>();

            for (String value : s) {
                Trial aux = new Trial();
                String[] splitTrial = value.split(",");
                aux.setName(splitTrial[1]);
                aux.setAcceptance(Integer.parseInt(splitTrial[2]));
                aux.setRevision(Integer.parseInt(splitTrial[3]));
                aux.setRejection(Integer.parseInt(splitTrial[4]));
                aux.setJournalName(splitTrial[5]);
                aux.setJournalQuartile(splitTrial[6]);
                trials.add(aux);
            }

            return trials;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Edition> editionFromCSV (List<Trial> trials) {
        Path pathEditions = Paths.get("./files/editions.csv");
        try {
            List<String> s  = Files.readAllLines(pathEditions);
            List<Edition> editions = new ArrayList<>();

            for (String value : s) {
                Edition aux = new Edition();

                String[] splitEdition = value.split(",");
                aux.setYear(Integer.parseInt(splitEdition[0]));


                aux.setNumPlayers(Integer.parseInt(splitEdition[1]));
                int numPlayers = aux.getNumPlayers();
                List<Player> playerList = new ArrayList<>();

                int index = 2;

                for (int j = 0; j < numPlayers; j++) {
                    Player playerAux = new Player();
                    playerAux.setName(splitEdition[index]);
                    index++;
                    playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                    index++;
                    playerList.add(playerAux);
                }

                aux.setPlayers(playerList);

                int numTrials = Integer.parseInt(splitEdition[index]);
                index++;

                //int[] TrialIndexes = new int[numTrials];

                List<Trial> trialList = new ArrayList<>();

                for (int j = 0; j < numTrials; j++) {
                    Trial trialAux = trials.get(Integer.parseInt(splitEdition[index]));
                    index++;
                    trialList.add(trialAux);
                }

                aux.setTrials(trialList);

                editions.add(aux);
            }

            return editions;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void trialToCSV (List<Trial> trials) { // Es el mateix que trialFromCSV al STARUML?
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./files/trials.csv")));
            for (int i = 0; i < trials.size(); i++) {
                StringBuilder linia = new StringBuilder();
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
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./files/editions.csv")));

            for (Edition edition : editions) {
                StringBuilder linia = new StringBuilder();
                linia.append(edition.getYear());
                linia.append(",");

                List<Player> players = edition.getPlayers();

                if (players == null || players.isEmpty()) {
                    linia.append(0);
                    linia.append(",");
                } else {
                    linia.append(players.size());
                    linia.append(",");

                    for (Player player : players) {
                        linia.append(player.getName());
                        linia.append(",");
                        linia.append(player.getPI_count());
                        linia.append(",");
                    }
                }

                List<Trial> trials = edition.getTrials();

                if (trials == null || trials.isEmpty()) {
                    linia.append(0);
                } else {
                    linia.append(trials.size());
                    for (Trial trial : trials) {
                        for (int j = 0; j < t.size(); j++) {
                            if (t.get(j).equals(trial)) {
                                linia.append(",");
                                linia.append(j);
                            }
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


      //////////////////////////////////////////
     ///            TEST DE LECTURA         ///
    //////////////////////////////////////////


/*
    public static void main(String[] args) {
        List<Edition> editions = new ArrayList<>();
        List<Trial> trials = new ArrayList<>();

        Trial trial = new Trial();

        trial.setName("Trial Name 1");
        trial.setJournalQuartile("Q4");
        trial.setRejection(30);
        trial.setRevision(20);
        trial.setAcceptance(50);
        trial.setJournalName("Journal name 1");

        trials.add(trial);
        Trial trial2 = new Trial();

        trial2.setName("Trial name 2");
        trial2.setJournalQuartile("Q3");
        trial2.setRejection(20);
        trial2.setRevision(30);
        trial2.setAcceptance(50);
        trial2.setJournalName("Trial 1");

        trials.add(trial2);
        Trial trial3 = new Trial();

        trial3.setName("Trial name 3");
        trial3.setJournalQuartile("Q4");
        trial3.setRejection(20);
        trial3.setRevision(30);
        trial3.setAcceptance(50);
        trial3.setJournalName("Journal Name 2");

        trials.add(trial3);

        ReaderDAO readerDAO = new ReaderDAO();
        readerDAO.trialToCSV(trials);

        List<Trial> aux = readerDAO.trialFromCSV();
        System.out.println(aux.toString());

        Edition edition = new Edition();
        Edition edition2 = new Edition();

        edition2.setYear(2021);
        edition.setYear(2020);

        List<Trial> trials2 = new ArrayList<>();
        trials2.add(trial3);
        trials2.add(trial2);
        trials2.add(trial3);

        edition.setTrials(trials2);
        edition2.setTrials(trials);

        List<Player> playerList = new ArrayList<>();
        List<Player> playerList2 = new ArrayList<>();
        playerList.add(new Player("Joan", 0));
        playerList.add(new Player("Tomas", 2));
        playerList.add(new Player("Alex", 0));
        playerList.add(new Player("Marta", 4));

        edition.setPlayers(playerList);

        playerList2.add(new Player("Pol", 9));
        playerList2.add(new Player("Alex", 2));
        playerList2.add(new Player("Marti", 3));
        playerList2.add(new Player("Joan", 5));

        edition2.setPlayers(playerList2);

        editions.add(edition2);

        editions.add(edition);

        readerDAO.editionToCSV(editions, trials);

        List<Edition> editionsList = readerDAO.editionFromCSV(trials);

        System.out.println(editionsList);
    }*/

}
