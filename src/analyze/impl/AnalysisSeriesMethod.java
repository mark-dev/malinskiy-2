package analyze.impl;

import interfaces.IAnalysisDependenceMethod;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class AnalysisSeriesMethod implements IAnalysisDependenceMethod {
    @Override
    public int calcEstimate(double[] array) {
        //Считаем моду
        double fashion =  calcMathFashion(array);
        //генерим массив знаков(больше или меньше моды)
        boolean[] signArray = generateSignArray(array, fashion);
        return  calcSeriesCount(signArray);
    }

    //возвращает моду для массива
    private double calcMathFashion(double[] array) {
        double[] cloned = array.clone();
        //сортирует клонированный объект, чтобы не изменять оригинальный
        Arrays.sort(cloned);
        //значение моды(выборочной медианы)
        // зависит от размера массива
        if(cloned.length%2 == 0){
            int mid = cloned.length / 2;
            return (cloned[mid] + cloned[mid-1])/2;
        }
        else
        {
            return cloned[cloned.length/2+1];
        }
    }

    //true если значение массива больше чем мода
    private boolean isGreaterThan(double value, double fashion) {
        return value > fashion;
    }

    //
    private boolean[] generateSignArray(double[] array, double fashion) {
        boolean[] signArray = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            signArray[i] = isGreaterThan(array[i], fashion);
        }
        return signArray;
    }

    //возвращает количество серий для массива знаков( + или - -> больше или меньше моды)
    private int calcSeriesCount(boolean[] signArray) {
        int seriesCount = 1;
        for (int i = 1; i < signArray.length; i++) {
            boolean prev = signArray[i - 1];
            boolean current = signArray[i];
            if (prev != current) {
                //то знак изменился -> началась новая серия
                seriesCount++;
            }
        }
        return seriesCount;
    }

}
