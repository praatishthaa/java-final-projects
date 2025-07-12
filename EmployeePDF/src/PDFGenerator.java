import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDFGenerator {

 public static void generateBatchPDFs(List<String[]> data, String fontPath) throws IOException {
    String folderName = "reports/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    Files.createDirectories(new File(folderName).toPath());

    String[] headers = data.get(0);  // Full 7 headers

    for (int i = 1; i < data.size(); i++) {
        String[] row = data.get(i);  // Full row
        String fileName = folderName + "/" + row[1].replaceAll("\\s+", "_") + ".pdf";
        createPDF(headers, row, fileName, fontPath);
    }
}


   public static void createPDF(String[] headers, String[] rowData, String fileName, String fontPath) throws IOException {
    PDDocument doc = new PDDocument();
    PDPage page = new PDPage(PDRectangle.A4);
    doc.addPage(page);

    PDType0Font font = PDType0Font.load(doc, new File(fontPath));
    PDPageContentStream cs = new PDPageContentStream(doc, page);

    float margin = 40f;
    float yStart = 700f;
    float rowHeight = 25f;
    float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
    int fontSize = 12;

    // ⚙️ Fixed widths scaled to 7 columns
    float[] colWidths = new float[headers.length];
        for (int col = 0; col < headers.length; col++) {
            float headerWidth = font.getStringWidth(headers[col]) / 1000 * fontSize;
            float dataWidth = font.getStringWidth(rowData[col]) / 1000 * fontSize;
            colWidths[col] = Math.max(headerWidth, dataWidth) + 10;
        }
    float totalWidth = 0;
    for (float w : colWidths) totalWidth += w;
    float scale = tableWidth / totalWidth;
    for (int i = 0; i < colWidths.length; i++) colWidths[i] *= scale;

    // 💬 Title
    cs.beginText();
    cs.setFont(font, 18);
    cs.newLineAtOffset(margin, yStart + 40);
    cs.showText("Employee Report");
    cs.endText();

    // 🖌️ Header background
    cs.setNonStrokingColor(Color.LIGHT_GRAY);
    cs.addRect(margin, yStart, tableWidth, rowHeight);
    cs.fill();
    cs.setNonStrokingColor(Color.BLACK);

    // 🧱 Table grid
    cs.setLineWidth(1f);
    for (int r = 0; r <= 2; r++) {
        float y = yStart - r * rowHeight;
        cs.moveTo(margin, y);
        cs.lineTo(margin + tableWidth, y);
    }
    float xPos = margin;
    for (int c = 0; c <= headers.length; c++) {
        cs.moveTo(xPos, yStart);
        cs.lineTo(xPos, yStart - 2 * rowHeight);
        if (c < headers.length) xPos += colWidths[c];
    }
    cs.stroke();

    // 🧾 Headers
    cs.beginText();
    cs.setFont(font, 12);
    float yOffset = yStart + 7;
    xPos = margin;
    for (int i = 0; i < headers.length; i++) {
        cs.newLineAtOffset(xPos + 2, yOffset);
        cs.showText(headers[i]);
        cs.newLineAtOffset(-(xPos + 2), 0);
        xPos += colWidths[i];
    }
    cs.endText();

   // 👤 Row data
xPos = margin;
yOffset = yStart - rowHeight + 7;
for (int i = 0; i < rowData.length; i++) {
    cs.beginText();
    cs.setFont(font, 12);
    cs.newLineAtOffset(xPos + 2, yOffset);
    cs.showText(rowData[i]);
    cs.endText();
    xPos += colWidths[i];
}


    // ⏱️ Timestamp
    cs.beginText();
    cs.setFont(font, 10);
    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    cs.newLineAtOffset(page.getMediaBox().getWidth() - margin - 150, 30);
    cs.showText("Generated on: " + timestamp);
    cs.endText();

    cs.close();
    doc.save(fileName);
    doc.close();
}

 public static List<String> wrapText(String text, PDType0Font font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float width = font.getStringWidth(testLine) / 1000 * fontSize;
            if (width > maxWidth) {
                if (line.length() > 0) lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(testLine);
            }
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines;
    }

}
