package Persistance;

import Business.Edition;
import Business.players.Player;
import Business.trials.Trial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditionJsonDAO implements EditionDAO{
    private final Gson gson;

    public EditionJsonDAO() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(Trial.class, new InterfaceAdapter());
        builder.registerTypeAdapter(Player.class, new PlayerInterfaceAdapter());

        gson = builder.create();
    }

    @Override
    public void save(List<Edition> editions, List<Trial> t) {
        if (!editions.isEmpty()) {
            try {
                FileWriter fileWriter = new FileWriter("./files/editions.json");
                gson.toJson(editions, fileWriter);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Edition> getAll(List<Trial> trials) {
        try {
            FileReader fileReader = new FileReader("./files/editions.json");
            List<Edition> editions = new ArrayList<>(Arrays.stream((gson.fromJson(fileReader, Edition[].class))).toList());
            fileReader.close();
            return editions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
