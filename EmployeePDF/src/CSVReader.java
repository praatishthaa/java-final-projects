import java.io.*;
import java.util.*;

public class CSVReader {
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            // Don't use split(",") — use this to handle commas safely
            String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            data.add(row);
        }
        reader.close();
        return data;
    }
}
