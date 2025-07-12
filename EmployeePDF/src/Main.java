import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String[]> data = CSVReader.readCSV("employees.csv");
        PDFGenerator.generateBatchPDFs(data, "fonts/times.ttf");
        System.out.println("PDFs generated successfully.");
    }
}
