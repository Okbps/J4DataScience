package ch11MathParallel;

import org.jblas.DoubleMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class MatrixOperations {
    private double matrixA[][] = {
            {0.1950, 0.0311},
            {0.3588, 0.2203},
            {0.1716, 0.5931},
            {0.2105, 0.3242}
    };

    private double matrixB[][] = {
            {0.0502, 0.9823, 0.9472},
            {0.5732, 0.2694, 0.916}
    };

    public static void main(String[] args) {
        MatrixOperations mo = new MatrixOperations();
        mo.multiply();
        mo.multiplyJblas();
        mo.multiplyNd4j();
    }

    private void multiply(){
        int n = 4;
        int m = 2;
        int p = 3;

        double C[][] = new double[n][p];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                for (int j = 0; j < p; j++) {
                    C[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        System.out.println("\nResult");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.printf("%.4f ", C[i][j]);
            }
            System.out.println();
        }
    }

    private void multiplyJblas(){
        DoubleMatrix A = new DoubleMatrix(matrixA);
        DoubleMatrix B = new DoubleMatrix(matrixB);

        DoubleMatrix C = A.mmul(B);

        for (int i = 0; i < C.getRows(); i++) {
            System.out.println(C.getRow(i));
        }
    }

    private void multiplyNd4j(){
        double[] A = {0.1950, 0.0311, 0.3588, 0.2203, 0.1716, 0.5931, 0.2105, 0.3242};
        double[] B = {0.0502, 0.9823, 0.9472, 0.5732, 0.2694, 0.916};

        INDArray aINDArray = Nd4j.create(A, new int[]{4, 2}, 'c');
        INDArray bINDArray = Nd4j.create(B, new int[]{2, 3}, 'c');

        INDArray cINDArray = aINDArray.mmul(bINDArray);

        for (int i = 0; i < cINDArray.rows(); i++) {
            System.out.println(cINDArray.getRow(i));
        }
    }
}
