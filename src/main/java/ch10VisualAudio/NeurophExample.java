package ch10VisualAudio;

// http://neuroph.sourceforge.net/download.html
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static util.Globals.RESOURCE_FOLDER;

public class NeurophExample {
    public static void main(String[] args) {
        NeuralNetwork iRNet = NeuralNetwork.createFromFile(RESOURCE_FOLDER + "faces_net.nnet");
        ImageRecognitionPlugin iRFile = (ImageRecognitionPlugin) iRNet.getPlugin(ImageRecognitionPlugin.class);

        try{
            HashMap<String, Double> faceMap =
                    iRFile.recognizeImage(new File(RESOURCE_FOLDER + "pic/oval-face.jpg"));

            System.out.println(faceMap);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
