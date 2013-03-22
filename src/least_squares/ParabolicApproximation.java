package least_squares;

import interfaces.ILeastSquaresModel;
import least_squares.gauss.GaussSolve;
import least_squares.gauss.Matrix;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 22.03.13
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class ParabolicApproximation implements ILeastSquaresModel {
    private double[] array; //Массив исходных данных
    //Приближает этот массив исходных данных как квадратичную функцию
    //y = ax^2 +  bx +  c
    private double a;
    private double b;
    private double c;

    public ParabolicApproximation(double[] array) {
        this.array = array;
        initCoefficients();
    }
    private void initCoefficients(){
        //A*X = b
        double[][] A = new double[3][3]; //Левая часть системы
        double[] B = new double[3]; //Правая часть

//        /*
//        a00   a01   a02 =   b0
//        a10   s11   s12 =   b1
//        s20   s21   s22 =   b2
//        */
        for (int i = 0; i < array.length; i++) {
            A[0][0] = A[0][0] + Math.pow(i, 4);
            A[0][1] = A[0][1] + Math.pow(i, 3);
            A[0][2] = A[0][2] + Math.pow(i, 2);
            B[0] = B[0] + Math.pow(i, 2) * array[i];

            A[1][0] = A[1][0] + Math.pow(i, 3);
            A[1][1] = A[1][1] + Math.pow(i, 2);
            A[1][2] = A[1][2] + i;
            B[1] = B[1] + i * array[i];

            A[2][0] = A[2][0] + Math.pow(i, 2);
            A[2][1] = A[2][1] + i;
            B[2] = B[2] + array[i];
        }
        A[2][2] = array.length;
        double[] systemSolve = new GaussSolve(A,B).solve();
        c = systemSolve[0];
        b = systemSolve[1];
        a = systemSolve[2];
        System.out.println("y = a+bi+c*i^2  ->"+"c: "+c +"\tb: "+b + "\ta: "+a);
    }
    //Возвращает приближение параболой для i-ой позиции
    @Override
    public double getApproximation(int i) {
       return a + b*i + c * Math.pow(i,2);
    }
}
