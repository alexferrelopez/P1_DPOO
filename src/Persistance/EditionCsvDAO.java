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
import java.util.Collections;
import java.util.List;

public class EditionCsvDAO implements EditionDAO{
    @Override
    public void save(List<Edition> editions, List<Trial> t) {
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

    @Override
    public List<Edition> getAll(List<Trial> trials) {
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
            System.out.println("El fitxer no existeix.");
        }

        return Collections.emptyList();
    }
}