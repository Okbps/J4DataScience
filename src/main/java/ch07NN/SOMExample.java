package ch07NN;

import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;

import java.io.FileReader;

import static util.Globals.RESOURCE_FOLDER;

public class SOMExample {
    public static void main(String[] args) {
        SelfOrganizingMap som = new SelfOrganizingMap();
        final String trainingFileName = RESOURCE_FOLDER + "arff/iris.arff";

        try(FileReader trainingReader = new FileReader(trainingFileName)){
            Instances trainingInstances = new Instances(trainingReader);
            som.buildClusterer(trainingInstances);
            System.out.println(som.toString());

            Instances[]clusters = som.getClusterInstances();
            for (int i = 0; i < clusters.length; i++) {
                System.out.println("-------Custer " + i);
                clusters[i].forEach(System.out::println);
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
