package ch01;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * Created by Aspire on 10.06.2017.
 */
public class OpticalCharacterRecognition {
    public static void main(String[] args) {
        recognize();
    }

    static void recognize(){
        ITesseract instance = new Tesseract();
        try {
            String result = instance.doOCR(new File("src/main/resources/OCRExample.jpg"));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
