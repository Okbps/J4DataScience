package ch06ML;

import weka.associations.Apriori;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

import static util.Globals.RESOURCE_FOLDER;

public class AssociationAprioriExample {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(RESOURCE_FOLDER + "babies.arff"));
        Instances data = new Instances(br);
        br.close();

        Apriori apriori = new Apriori();
        apriori.setNumRules(100);
        apriori.setMinMetric(0.5);

        apriori.buildAssociations(data);
        System.out.println(apriori);
    }
}
