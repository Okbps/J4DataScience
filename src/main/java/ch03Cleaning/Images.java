package ch03Cleaning;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 21.06.2017.
 * requires opencv_java320.dll in classpath
 */
public class Images {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Images images = new Images();
//        images.changeContrast();
//        images.smooth();
//        images.brighten();
//        images.resize();
        images.convert();
    }

    void changeContrast(){
        Mat source = Imgcodecs.imread(RESOURCE_FOLDER + "GrayScaleParrot.png",
                Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.equalizeHist(source, destination);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "engancedParrot.jpg", destination);
    }

    void smooth(){
        Mat source = Imgcodecs.imread(RESOURCE_FOLDER + "cat.jpg");
        Mat destination = source.clone();
        for (int i = 0; i < 25; i++) {
            Mat sourceImage = destination.clone();
            Imgproc.blur(sourceImage, destination, new Size(3.0, 3.0));
        }
        Imgcodecs.imwrite(RESOURCE_FOLDER + "smoothCat.jpg", destination);
    }

    void brighten(){
        Mat source = Imgcodecs.imread(RESOURCE_FOLDER + "cat.jpg");
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        source.convertTo(destination, -1, 1, 50);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "brighterCat.jpg", destination);
    }

    void resize(){
        Mat source = Imgcodecs.imread(RESOURCE_FOLDER + "cat.jpg");
        Mat resizeImage = new Mat();
        Imgproc.resize(source, resizeImage, new Size(250, 250));
        Imgcodecs.imwrite(RESOURCE_FOLDER + "resizedCat.jpg", resizeImage);
    }

    void convert(){
        Mat source = Imgcodecs.imread(RESOURCE_FOLDER + "cat.jpg");
        Imgcodecs.imwrite(RESOURCE_FOLDER + "convertedCat.jpg", source);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "convertedCat.jpeg", source);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "convertedCat.webp", source);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "convertedCat.png", source);
        Imgcodecs.imwrite(RESOURCE_FOLDER + "convertedCat.tiff", source);
    }
}
