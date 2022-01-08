package Persistance;

import Business.Edition;
import Business.Trial;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditionJsonDAO implements EditionDAO{
    @Override
    public void save(List<Edition> editions, List<Trial> t) {
        Gson gson = new Gson();

        try {
            FileWriter fileWriter = new FileWriter("./files/trials.json");
            gson.toJson(editions, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Edition> getAll(List<Trial> trials) {
        Gson gson = new Gson();

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
}
