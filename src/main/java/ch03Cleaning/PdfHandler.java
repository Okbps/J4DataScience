package ch03Cleaning;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 17.06.2017.
 */
public class PdfHandler {
    public static void main(String[] args) {
        try {
            PDDocument document = PDDocument.load(new File(RESOURCE_FOLDER + "PDF File.pdf"));
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
