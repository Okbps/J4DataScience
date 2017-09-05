package ch10VisualAudio;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static util.Globals.RESOURCE_FOLDER;

public class FacesDetectionExample {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceDetector = new CascadeClassifier(RESOURCE_FOLDER + "lbpcascade_frontalface.xml");

        Mat image = Imgcodecs.imread(RESOURCE_FOLDER + "pic/image_10_004.jpg");

        MatOfRect faceVectors = new MatOfRect();

        faceDetector.detectMultiScale(image, faceVectors);

        System.out.println(faceVectors.toArray().length + " faces found");

        for (Rect rect : faceVectors.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        Imgcodecs.imwrite(RESOURCE_FOLDER + "pic/faceDetection.png", image);
    }
}
