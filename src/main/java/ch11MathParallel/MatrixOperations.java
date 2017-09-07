package ch11MathParallel;

import org.jblas.DoubleMatrix;

public class MatrixOperations {
    public static void main(String[] args) {
        MatrixOperations mo = new MatrixOperations();
//        mo.multiply();
        mo.multiplyJblas();
    }

    void multiply(){
        int n = 4;
        int m = 2;
        int p = 3;

        double A[][] = {
                {0.1950, 0.0311},
                {0.3588, 0.2203},
                {0.1716, 0.5931},
                {0.2105, 0.3242}
        };

        double B[][] = {
                {0.0502, 0.9823, 0.9472},
                {0.5732, 0.2694, 0.916}
        };

        double C[][] = new double[n][p];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                for (int j = 0; j < p; j++) {
                    C[i][j] += A[i][k] * B[k][j];
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

    void multiplyJblas(){
        DoubleMatrix A = new DoubleMatrix(new double[][]{
                {0.1950, 0.0311},
                {0.3588, 0.2203},
                {0.1716, 0.5931},
                {0.2105, 0.3242}
        });

        DoubleMatrix B = new DoubleMatrix(new double[][]{
                {0.0502, 0.9823, 0.9472},
                {0.5732, 0.2694, 0.916}
        });

        DoubleMatrix C = A.mmul(B);

        for (int i = 0; i < C.getRows(); i++) {
            System.out.println(C.getRow(i));
        }
    }
}
