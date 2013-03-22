package regression_generator.impl;

import interfaces.IArrayGenerator;
import regression_generator.help.DistributionCalcHelper;
import regression_generator.help.NormalDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 02.03.13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class RegressionArrayGeneratorModel1 implements IArrayGenerator {
    //Параметры для нормального распределения
    private static final double M = 0.0;
    private static final double Sigma = 1.0;
    //Z(i) = alpha1*z1+alpha2*z2 + normalDistribution(M,Sigma)
    //Начальные коэффициенты(дано)
    private final double alpha1;
    private final double alpha2;
    //Два начальных члена последовательности
    private final double z1;
    private final double z2;

    public RegressionArrayGeneratorModel1(double alpha1, double alpha2, double z1, double z2) {
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public double[] generateSeries(int length) {
        NormalDistribution normalDistGen = new NormalDistribution(M, Sigma);
        double[] result = new double[length];
        result[0] = z1;
        result[1] = z2;
        double[] normal = normalDistGen.generateArray(length);
        //Проверить на сколько "хорошим" получилось нормальное распределение
        System.out.println("\nGaussian distribution info for array with " + length + " values");
        System.out.println("Histogram: " + Arrays.toString(DistributionCalcHelper.calcHistogram(normal, 5)));
        System.out.println("MX(" + "expected: " + M + "): " + DistributionCalcHelper.calcMx(normal));
        System.out.println("Sigma(" + "expected: " + Sigma + "): " + DistributionCalcHelper.calcSigma(normal) + "\n");

        for (int i = 2; i < length; i++) {
            result[i] = nextArrayValue(i, result, normal);
        }
        return result;
    }

    //Возвращает i-ое значение регрессионного массива
    double nextArrayValue(int i, double[] resultArray, double[] normalDistArray) {
        return nextArrayValueModel1(i, resultArray, normalDistArray);
    }

    //Функция по умолчанию для модели1
    final double nextArrayValueModel1(int i, double[] resultArray, double[] normalDistArray) {
        return alpha1 * resultArray[i - 1] +
                alpha2 * resultArray[i - 2] +
                normalDistArray[i];
    }
}
