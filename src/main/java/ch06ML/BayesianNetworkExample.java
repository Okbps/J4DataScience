package ch06ML;

import com.github.vangj.jbayes.inf.prob.Graph;
import com.github.vangj.jbayes.inf.prob.Node;
import com.github.vangj.jbayes.inf.prob.util.CsvUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static util.Globals.RESOURCE_FOLDER;

/**
 * Created by Aspire on 17.07.2017.
 */
public class BayesianNetworkExample {
    public static void main(String[] args) {
        Node storms     = Node.newBuilder().name("s").value("t").value("f").build();
        Node traffic    = Node.newBuilder().name("t").value("t").value("f").build();
        Node powerOut   = Node.newBuilder().name("p").value("t").value("f").build();
        Node alarm      = Node.newBuilder().name("a").value("t").value("f").build();
        Node overslept  = Node.newBuilder().name("o").value("t").value("f").build();
        Node lateToWork = Node.newBuilder().name("l").value("t").value("f").build();

        traffic     .addParent(storms);
        powerOut    .addParent(storms);
        lateToWork  .addParent(traffic);
        alarm       .addParent(powerOut);
        overslept   .addParent(alarm);
        lateToWork  .addParent(overslept);

        // conditional probability tables
        storms      .setCpt(new double[][]{{0.7, 0.3}});
        traffic     .setCpt(new double[][]{{0.8, 0.2}});
        powerOut    .setCpt(new double[][]{{0.5, 0.5}});
        alarm       .setCpt(new double[][]{{0.7, 0.3}});
        overslept   .setCpt(new double[][]{{0.5, 0.5}});
        lateToWork  .setCpt(new double[][]{{0.5, 0.5}, {0.5, 0.5}});

        Graph bayesGraph = new Graph();
        bayesGraph.addNode(storms);
        bayesGraph.addNode(traffic);
        bayesGraph.addNode(powerOut);
        bayesGraph.addNode(alarm);
        bayesGraph.addNode(overslept);
        bayesGraph.addNode(lateToWork);
        bayesGraph.sample(1000);

        double[]stormProb   = storms.probs();
        double[]trafProb    = traffic.probs();
        double[]powerProb   = powerOut.probs();
        double[]alarmProb   = alarm.probs();
        double[]overProb    = overslept.probs();
        double[]lateProb    = lateToWork.probs();

        System.out.println("Storm Probabilities");
        System.out.println("True: " + stormProb[0] + " False: " + stormProb[1]);
        System.out.println("Traffic Probabilities");
        System.out.println("True: " + trafProb[0] + " False: " + trafProb[1]);
        System.out.println("Power Outage Probabilities");
        System.out.println("True: " + powerProb[0] + " False: " + powerProb[1]);
        System.out.println("Alarm Probabilities");
        System.out.println("True: " + alarmProb[0] + " False: " + alarmProb[1]);
        System.out.println("Overslept Probabilities");
        System.out.println("True: " + overProb[0] + " False: " + overProb[1]);
        System.out.println("Late to Work Probabilities");
        System.out.println("True: " + lateProb[0] + " False: " + lateProb[1]);

        try{
            CsvUtil.saveSamples(
                    bayesGraph, new FileWriter(
                            new File(RESOURCE_FOLDER + "JBayesInfo.csv")
                    ));
        }catch (IOException e){
            e.printStackTrace();
        }

        bayesGraph.clearSamples();
    }
}
