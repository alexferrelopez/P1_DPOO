package Persistance;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class ExecutionCheckpointDAO {

    public void save(Integer checkpoint) {
        try {
            FileWriter fileWriter = new FileWriter("./files/checkpoint.txt");
            fileWriter.write(checkpoint.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getAll() {
        try {
            List<String> strings = Files.readAllLines(Path.of("./files/checkpoint.txt"));
            return Integer.parseInt(strings.get(0));
        } catch (IOException ignored) {
        }
        return null;
    }

    /*
    public static void main(String[] args) {
        ExecutionCheckpointDAO executionCheckpointDAO = new ExecutionCheckpointDAO();
        Integer A = executionCheckpointDAO.getAll();
        System.out.println(A);
    }
     */
}
