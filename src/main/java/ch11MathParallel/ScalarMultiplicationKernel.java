package ch11MathParallel;

import com.aparapi.Kernel;

public class ScalarMultiplicationKernel extends Kernel{
    float[] inputMatrix;
    float outputMatrix [];

    public static void main(String[] args) {
        float inputMatrix[] = {3, 4, 5, 6, 7, 8, 9};
        ScalarMultiplicationKernel kernel = new ScalarMultiplicationKernel(inputMatrix);
        kernel.execute(inputMatrix.length);
        kernel.displayResult();
        kernel.dispose();
    }

    public ScalarMultiplicationKernel(float[] inputMatrix) {
        this.inputMatrix = inputMatrix;
        outputMatrix = new float[this.inputMatrix.length];
    }

    @Override
    public void run() {
        int globalID = this.getGlobalId();
        outputMatrix[globalID] = 2.0f * inputMatrix[globalID];
    }

    public void displayResult(){
        System.out.println("Result");
        for (float element : outputMatrix) {
            System.out.printf("%.4f ", element);
        }
        System.out.println();
    }
}
