package ch07NN;

/**
 * THIS CODE ADAPTED FROM:
 * http://www.informit.com/articles/article.aspx?p=30596&seqNum=5
 * Neural Network
 * Feedforward Backpropagation Neural Network
 * Written in 2002 by Jeff Heaton(http://www.jeffheaton.com)
 *
 * This class is released under the limited GNU public
 * license (LGPL).
 *
 * @author Jeff Heaton
 * @version 1.0
 */

public class SampleNeuralNetwork {
    private double errors;
    private int inputNeurons;
    private int outputNeurons;
    private int hiddenNeurons;
    private int totalNeurons;
    private int weights;
    private double learningRate;
    private double outputResults[];
    private double resultsMatrix[];
    private double lastErrors[];
    private double changes[];
    private double thresholds[];
    private double weightChanges[];
    private double allThresholds[];
    private double threshChanges[];
    private double momentum;
    private double errorChanges[];

    public static void main(String[] args) {
        double xorIn[][] = {
                {0.0, 0.0},
                {1.0, 0.0},
                {0.0, 1.0},
                {1.0, 1.0}
        };

        double xorExpected[][] = {{0.0}, {1.0}, {1.0}, {0.0}};

        SampleNeuralNetwork network = new SampleNeuralNetwork(2,3,1,0.7, 0.9);

        for (int runCnt = 0; runCnt < 10000; runCnt++) {
            for (int loc = 0; loc < xorIn.length; loc++) {
                network.calcOutput(xorIn[loc]);
                network.calcError(xorExpected[loc]);
                network.train();
            }
            System.out.println(
                    "Trial #" + runCnt + ", Error: " + network.getError(xorIn.length));
        }
    }

    public SampleNeuralNetwork(
            int inputCount, int hiddenCount, int outputCount, double learningRate, double momentum)
    {
        this.outputNeurons  = outputCount;
        this.hiddenNeurons  = hiddenCount;
        this.inputNeurons   = inputCount;
        this.learningRate   = learningRate;
        this.momentum       = momentum;
        this.totalNeurons   = inputCount + hiddenCount + outputCount;
        this.weights        = inputCount * hiddenCount + hiddenCount * outputCount;

        this.outputResults  = new double[totalNeurons];
        this.resultsMatrix  = new double[weights];
        this.weightChanges  = new double[weights];
        this.thresholds     = new double[totalNeurons];
        this.errorChanges   = new double[totalNeurons];
        this.lastErrors     = new double[totalNeurons];
        this.allThresholds  = new double[totalNeurons];
        this.changes        = new double[weights];
        this.threshChanges  = new double[totalNeurons];

        reset();
    }

    /**
     * Returns the root mean square error for a complete training set.
     *
     * @param len The length of a complete training set.
     * @return The current error for the neural network.
     */
    public double getError(int len) {
        double err = Math.sqrt(errors / (len * outputNeurons));
        errors = 0; // clear the accumulator
        return err;

    }

    /**
     * The threshold method. You may wish to override this class to provide other
     * threshold methods.
     *
     * @param sum The activation from the neuron.
     * @return The activation applied to the threshold method.
     */
    public double threshold(double sum) {
        return 1.0 / (1 + Math.exp(-1.0 * sum));
    }

    /**
     * Compute the output for a given input to the neural network.
     *
     * @param input The input provide to the neural network.
     * @return The results from the output neurons.
     */
    public double[] calcOutput(double input[]) {
        int loc, pos;
        final int hiddenIndex = inputNeurons;
        final int outIndex = inputNeurons + hiddenNeurons;

        for (loc = 0; loc < inputNeurons; loc++) {
            outputResults[loc] = input[loc];
        }

        int rLoc = 0;
        for (loc = hiddenIndex; loc < outIndex; loc++) {
            double sum = thresholds[loc];
            for (pos = 0; pos < inputNeurons; pos++) {
                sum += outputResults[pos] * resultsMatrix[rLoc++];
            }
            outputResults[loc] = threshold(sum);
        }

        double result[] = new double[outputNeurons];
        for (loc = outIndex; loc < totalNeurons; loc++) {
            double sum = thresholds[loc];

            for (pos = hiddenIndex; pos < outIndex; pos++) {
                sum += outputResults[pos] * resultsMatrix[rLoc++];
            }
            outputResults[loc] = threshold(sum);
            result[loc-outIndex] = outputResults[loc];
        }

        return result;
    }


    /**
     * Calculate the error for the recogntion just done.
     *
     * @param ideal What the output neurons should have yielded.
     */
    public void calcError(double ideal[]) {
        int loc, pos;
        final int hiddenIndex = inputNeurons;
        final int outputIndex = inputNeurons + hiddenNeurons;

        // clear hidden layer errors
        for (loc = inputNeurons; loc < totalNeurons; loc++) {
            lastErrors[loc] = 0;
        }

        // layer errors and deltas for output layer
        for (loc = outputIndex; loc < totalNeurons; loc++) {
            lastErrors[loc] = ideal[loc - outputIndex] - outputResults[loc];
            errors += lastErrors[loc] * lastErrors[loc];
            errorChanges[loc] = lastErrors[loc] * outputResults[loc] * (1 - outputResults[loc]);
        }

        // hidden layer errors
        int locx = inputNeurons * hiddenNeurons;

        for (loc = outputIndex; loc < totalNeurons; loc++) {
            for (pos = hiddenIndex; pos < outputIndex; pos++) {
                changes[locx] += errorChanges[loc] * outputResults[pos];
                lastErrors[pos] += resultsMatrix[locx] * errorChanges[loc];
                locx++;
            }
            allThresholds[loc] += errorChanges[loc];
        }

        // hidden layer deltas
        for (loc = hiddenIndex; loc < outputIndex; loc++) {
            errorChanges[loc] = lastErrors[loc] * outputResults[loc] * (1 - outputResults[loc]);
        }

        // input layer errors
        locx = 0; // offset into weight array
        for (loc = hiddenIndex; loc < outputIndex; loc++) {
            for (pos = 0; pos < hiddenIndex; pos++) {
                changes[locx] += errorChanges[loc] * outputResults[pos];
                lastErrors[pos] += resultsMatrix[locx] * errorChanges[loc];
                locx++;
            }
            allThresholds[loc] += errorChanges[loc];
        }
    }

    /**
     * Modify the weight matrix and thresholds based on the last call to
     * calcError.
     */
    public void train() {
        int loc;
        for (loc = 0; loc < resultsMatrix.length; loc++) {
            weightChanges[loc] = (learningRate * changes[loc]) + (momentum * weightChanges[loc]);
            resultsMatrix[loc] += weightChanges[loc];
            changes[loc] = 0;
        }
        for (loc = inputNeurons; loc < totalNeurons; loc++) {
            threshChanges[loc] = learningRate * allThresholds[loc] + (momentum * threshChanges[loc]);
            thresholds[loc] += threshChanges[loc];
            allThresholds[loc] = 0;
        }
    }

    public void reset() {
        int loc;

        for (loc = 0; loc < totalNeurons; loc++) {
            thresholds[loc] = 0.5 - (Math.random());
            threshChanges[loc] = 0;
            allThresholds[loc] = 0;
        }
        for (loc = 0; loc < resultsMatrix.length; loc++) {
            resultsMatrix[loc] = 0.5 - (Math.random());
            weightChanges[loc] = 0;
            changes[loc] = 0;
        }
    }
}
