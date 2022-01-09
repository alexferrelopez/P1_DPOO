package Persistance;

import Business.Edition;
import Business.Trial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditionJsonDAO implements EditionDAO{
    @Override
    public void save(List<Edition> editions, List<Trial> t) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter fileWriter = new FileWriter("./files/editions.json");
            gson.toJson(editions, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Edition> getAll(List<Trial> trials) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileReader fileReader = new FileReader("./files/editions.json");
            List<Edition> editions = Arrays.stream(gson.fromJson(fileReader, Edition[].class)).toList();
            fileReader.close();
            return editions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

      /////////////////////////////////////////
     //  SAVE AND WRITE JSON TEST (TRIALS)  //
    /////////////////////////////////////////

    /*
    public static void main(String[] args) {
        EditionDAO editionDAO = new EditionCsvDAO();
        TrialDAO trialDAO = new TrialCsvDAO();

        List<Edition> editions = editionDAO.getAll(trialDAO.getAll());

        EditionDAO editionDAO1 = new EditionJsonDAO();
        editionDAO1.save(editions, null);

        List<Edition> editions1 = editionDAO1.getAll(null);
        System.out.println(editions1);
    }*/
}
