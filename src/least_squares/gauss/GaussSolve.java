package least_squares.gauss;

import java.util.Arrays;

public class GaussSolve {
    private  double[][] A;
    private  double[] b;
    private boolean isAlreadySolved = false;
    public GaussSolve(double[][] a, double[] b) {
        A = a.clone();
        this.b = b.clone();
    }

    private  void gaussDirectFlow() {
        double r, t;
        for (int k = 0; k < A.length; k++) {
            //Строка с Номером k делиться на диагональный элемент.
            t = A[k][k];
            b[k] = b[k] / t;
            /* Строка k умножается на 1/t*/
            A = new Matrix(A).rowMpl(k, (1 / t)).toArray();
            /* Для всех строк ниже k-ой */
            for (int j = k + 1; j < A[0].length; j++) {
                r = A[j][k];
                /*Str(j) -> Str(j)-r*Str(k)*/
                A = new Matrix(A).strPlus(k, j, -r).toArray();
                b[j] = b[j] - r * b[k];
            }
        }
    }

    private  void gaussRiversal() {
        double r, t;
        for (int k = (A.length - 1); k >= 1; k--) {
            t = A[k][k];
            b[k] = b[k] / t;
            A = new Matrix(A).rowMpl(k, (1 / t)).toArray();
            for (int j = k - 1; j >= 0; j--) {
                r = A[j][k];
                /*Str(j) -> Str(j)-r*Str(k)*/
                A = new Matrix(A).strPlus(k, j, -r).toArray();
                b[j] = b[j] - r * b[k];
            }
        }
    }


    public double[] solve() {
        if(!isAlreadySolved){
        gaussDirectFlow();
        gaussRiversal();
        isAlreadySolved = true;
        }
        return b;
    }

}