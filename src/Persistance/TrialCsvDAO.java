package Persistance;

import Business.trials.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class  TrialCsvDAO implements TrialDAO{

    /**
     * Saves line by line the file trials.CSV.
     * @param trials list of trials from BusinessManager.
     */
    @Override
    public void save(List<Trial> trials) {
        if (!trials.isEmpty()) {
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./files/trials.csv")));
                for (int i = 0; i < trials.size(); i++) {
                    String linia = i +
                            "," +
                            trials.get(i).getName() +
                            "," +
                            trials.get(i).toCSV();
                    bw.write(linia);
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
     * Reads line by line the file editions.CSV.
     * @return list of trials.
     * @throws IOException standard IO exception.
     */
    @Override
    public List<Trial> getAll() throws IOException {
        Path pathTrials = Paths.get("./files/trials.csv");
        List<String> s  = Files.readAllLines(pathTrials);
        List<Trial> trials = new ArrayList<>();

        for (String value : s) {
            Trial trial = null;
            String[] splitTrial = value.split(",");
            String s1 = splitTrial[2];
            switch (s1) {
                case Article.TYPE ->
                        trial = new Article(splitTrial[1],
                        Integer.parseInt(splitTrial[3]),
                        Integer.parseInt(splitTrial[4]),
                        Integer.parseInt(splitTrial[5]),
                        splitTrial[6],
                        splitTrial[7]);
                case Defensa.TYPE ->
                        trial = new Defensa(splitTrial[1],
                        splitTrial[3],
                        Integer.parseInt(splitTrial[4]));
                case Estudi.TYPE ->
                        trial = new Estudi(splitTrial[1],
                        splitTrial[3],
                        Integer.parseInt(splitTrial[4]),
                        Integer.parseInt(splitTrial[5]));
                case Solicitud.TYPE ->
                        trial = new Solicitud(splitTrial[1],
                        splitTrial[3],
                        Integer.parseInt(splitTrial[4]));
            }
            trials.add(trial);
        }

        return trials;
    }
}
