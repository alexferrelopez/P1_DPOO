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

public class EditionCsvDAO implements EditionDAO{
    @Override
    public void save(List<Edition> editions, List<Trial> t) {

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

                boolean emptyList = false;
                for (int j = 0; j < numPlayers; j++) {
                    Player playerAux;

                    switch (splitEdition[index]) {
                        case Doctor.TYPE -> {
                            index++;
                            playerAux = new Doctor(splitEdition[index]);
                            if (splitEdition[index].equals("")) {
                                emptyList = true;
                                playerAux.setPI_count(0);
                            }
                            else playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                            index++;
                            playerList.add(playerAux);
                        }
                        case Enginyer.TYPE -> {
                            index++;
                            playerAux = new Enginyer(splitEdition[index]);
                            if (splitEdition[index].equals("")) {
                                emptyList = true;
                                playerAux.setPI_count(0);
                            }
                            else playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                            index++;
                            playerList.add(playerAux);
                        }
                        case Master.TYPE -> {
                            index++;
                            playerAux = new Master(splitEdition[index]);
                            if (splitEdition[index].equals("")) {
                                emptyList = true;
                                playerAux.setPI_count(0);
                            }
                            else playerAux.setPI_count(Integer.parseInt(splitEdition[index]));
                            index++;
                            playerList.add(playerAux);
                        }
                        default -> index+= 2;
                    }
                    index++;
                }

                if (emptyList) {
                    aux.setPlayers(new ArrayList<>());
                }
                else {
                    aux.setPlayers(playerList);
                }

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
            System.out.println("\tNo editions have been loaded\n");
        }

        return new ArrayList<>();
    }
}
