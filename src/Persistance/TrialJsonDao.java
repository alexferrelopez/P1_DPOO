package Persistance;

import Business.Trial;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrialJsonDao implements TrialDAO{
    @Override
    public void save(List<Trial> trials) {
        Gson gson = new Gson();

        try {
            FileWriter fileWriter = new FileWriter("./files/trials.json");
            gson.toJson(trials, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trial> getAll() {
        Gson gson = new Gson();

        try {
            FileReader fileReader = new FileReader("./files/trials.json");
            List<Trial> trials = Arrays.stream(gson.fromJson(fileReader, Trial[].class)).toList();
            fileReader.close();
            return trials;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

      //////////////////////////////////
     //   SAVE AND WRITE JSON TEST   //
    //////////////////////////////////

    /*
    public static void main(String[] args) {
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


        TrialJsonDao trialJsonDao = new TrialJsonDao();
        trialJsonDao.save(trials);

        List<Trial> trials2 = trialJsonDao.getAll();
        System.out.println(trials2);
    }*/
}
