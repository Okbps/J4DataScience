package ch11MathParallel;

import com.aparapi.Kernel;

public class MatrixMultiplicationKernel extends Kernel{
    private float[] vectorA = {0.1950f, 0.0311f, 0.3588f, 0.2203f, 0.1716f, 0.5931f, 0.2105f, 0.3242f};
    private float[] vectorB = {0.0502f, 0.9823f, 0.9472f, 0.5732f, 0.2694f, 0.916f};
    private float[] vectorC;
    private int n, m, p;

    public MatrixMultiplicationKernel(int n, int m, int p) {
        this.n = n;
        this.m = m;
        this.p = p;
        this.vectorC = new float[n*p];
    }

    @Override
    public void run() {
        int i = getGlobalId();
        int j = this.getPassId();
        float value = 0;
        for (int k = 0; k < p; k++) {
            value += vectorA[k + i*m] * vectorB[k*p + j];
        }
        vectorC[i*p + j] = value;
    }

    public void displayResult(){
        System.out.println("Result");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.printf("%.4f ", vectorC[i * p + j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MatrixMultiplicationKernel kernel = new MatrixMultiplicationKernel(4, 2, 3);
        kernel.execute(6, 3);
        kernel.displayResult();
        kernel.dispose();
    }
}
