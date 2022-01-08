package Persistance;

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

public class  TrialCsvDAO implements TrialDAO{

    @Override
    public void save(List<Trial> trials) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./files/trials.csv")));
            for (int i = 0; i < trials.size(); i++) {
                String linia = i +
                        "," +
                        trials.get(i).getName() +
                        "," +
                        trials.get(i).getAcceptance() +
                        "," +
                        trials.get(i).getRevision() +
                        "," +
                        trials.get(i).getRejection() +
                        "," +
                        trials.get(i).getJournalName() +
                        "," +
                        trials.get(i).getJorunalQuartile();
                bw.write(linia);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trial> getAll() {
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
        return Collections.emptyList();
    }
}
