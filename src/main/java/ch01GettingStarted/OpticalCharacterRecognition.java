package ch01GettingStarted;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

import static util.Globals.RESOURCE_FOLDER;

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
            String result = instance.doOCR(new File(RESOURCE_FOLDER + "OCRExample.jpg"));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
