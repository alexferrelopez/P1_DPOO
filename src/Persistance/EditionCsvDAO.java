package Persistance;

import Business.Edition;
import Business.players.Doctor;
import Business.players.Enginyer;
import Business.players.Master;
import Business.players.Player;
import Business.trials.Trial;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and writes Editions from/to a CSV.
 */
public class EditionCsvDAO implements EditionDAO{
    /**
     * Saves line by line the file editions.CSV. Example -> year, number of players, (type of player, name, pi count,)*[number of players], number of trials, trial1 index, trial2 index...
     * When the player list is empty, commas are placed anyway.
     * @param editions list of editions.
     * @param trialList list of trials.
     */
    @Override
    public void save(List<Edition> editions, List<Trial> trialList) {

        if (!editions.isEmpty()) {
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./files/editions.csv")));

                for (Edition edition : editions) {
                    StringBuilder linia = new StringBuilder();
                    linia.append(edition.getYear());
                    linia.append(",");

                    List<Player> players = edition.getPlayers();

                    if (edition.getNumPlayers() == 0) {
                        linia.append(0);
                        linia.append(",");
                    } else {
                        linia.append(edition.getNumPlayers());
                        linia.append(",");
                        if (players !=null && !players.isEmpty()) {
                            for (Player player : players) {
                                linia.append(player.getType());
                                linia.append(",");
                                linia.append(player.getName());
                                linia.append(",");
                                linia.append(player.getPI_count());
                                linia.append(",");
                            }
                        }
                        else {
                            for (int i = 0; i < edition.getNumPlayers(); i++) {
                                linia.append(",");
                                linia.append(",");
                                linia.append(",");
                            }
                        }
                    }

                    List<Trial> trials = edition.getTrials();

                    if (trials == null || trials.isEmpty()) {
                        linia.append(0);
                    } else {
                        linia.append(trials.size());
                        for (Trial trial : trials) {
                            for (int j = 0; j < trialList.size(); j++) {
                                if (trialList.get(j).equals(trial)) {
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
    }

    /**
     * Reads line by line the file editions.CSV. Example -> year, number of players, (type of player, name, pi count,)*[number of players], number of trials, trial1 index, trial2 index...
     * @param trials list of trials.
     * @return list of editions.
     * @throws IOException standard IO exception.
     */
    @Override
    public List<Edition> getAll(List<Trial> trials) throws IOException {
        Path pathEditions = Paths.get("./files/editions.csv");
        List<String> s  = Files.readAllLines(pathEditions);
        List<Edition> editions = new ArrayList<>();

        for (String value : s) {
            String[] splitEdition = value.split(",");

            int numPlayers = Integer.parseInt(splitEdition[1]);
            List<Player> playerList = new ArrayList<>();

            int index = 2;

            for (int j = 0; j < numPlayers; j++) {
                Player playerAux;

                switch (splitEdition[index]) {
                    case Doctor.TYPE -> {
                        index++;
                        playerAux = new Doctor(splitEdition[index]);
                        index++;
                        if (splitEdition[index].equals("")) {
                            playerAux.setPI_count(0);
                        }
                        else playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                        index++;
                        playerList.add(playerAux);
                    }
                    case Enginyer.TYPE -> {
                        index++;
                        playerAux = new Enginyer(splitEdition[index]);
                        index++;
                        if (!splitEdition[index].equals("")) {
                            playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                        }
                        index++;
                        playerList.add(playerAux);
                    }
                    case Master.TYPE -> {
                        index++;
                        playerAux = new Master(splitEdition[index]);
                        index++;
                        if (splitEdition[index].equals("")) {
                            playerAux.setPI_count(0);
                        }
                        else playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                        index++;
                        playerList.add(playerAux);
                    }
                    default -> index+= 3;
                }
            }

            int numTrials = Integer.parseInt(splitEdition[index]);
            index++;

            List<Trial> trialList = new ArrayList<>();

            for (int j = 0; j < numTrials; j++) {
                Trial trialAux = trials.get(Integer.parseInt(splitEdition[index]));
                index++;
                trialList.add(trialAux);
            }

            editions.add(new Edition(trialList, playerList ,Integer.parseInt(splitEdition[0]),Integer.parseInt(splitEdition[1])));
        }

        return editions;
    }
}
