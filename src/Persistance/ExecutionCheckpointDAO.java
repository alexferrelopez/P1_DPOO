package Persistance;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExecutionCheckpointDAO {

    /**
     * function made to allow us to save the execution of an edition after closing the executable.
     * @param checkpoint
     */
    public void save(Integer checkpoint) {
        try {
            FileWriter fileWriter = new FileWriter("./files/checkpoint.txt");
            if (checkpoint == null) {
                fileWriter.write("0");
            } else {
                fileWriter.write(checkpoint.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function made to allow us to resume the execution of an edition after closing the executable.
     * @return
     */
    public Integer getAll() {
        try {
            List<String> strings = Files.readAllLines(Path.of("./files/checkpoint.txt"));
            return Integer.parseInt(strings.get(0));
        } catch (IOException ignored) {
        }
        return 0;
    }

    /*
    public static void main(String[] args) {
        ExecutionCheckpointDAO executionCheckpointDAO = new ExecutionCheckpointDAO();
        Integer A = executionCheckpointDAO.getAll();
        System.out.println(A);
    }
     */
}
