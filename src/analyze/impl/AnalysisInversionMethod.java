package analyze.impl;

import interfaces.IAnalysisDependenceMethod;

import java.util.Arrays;


/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class AnalysisInversionMethod implements IAnalysisDependenceMethod {
    @Override
    public int calcEstimate(double[] array) {
        int[] inversionArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            //создаем массив инверсий для каждого элемента исходного массива
            inversionArray[i] = getInversionByIndex(i, array);
        }
        return sum(inversionArray);
    }

    //Сумирует массив целых чисел
    private int sum(int[] inversions) {
        int res = 0;
        for (int inversion : inversions) {
            res = res + inversion;
        }
        return res;
    }

    /*Возвращает количество чисел, больших числа с индексом index
     в массиве  data, индекс которых больше индекса данного числа
     */
    private int getInversionByIndex(int index, double[] data) {
        int inversion = 0;
        for (int i = (index+1); i < data.length; i++) {
            //data(index) > data(i) при i > index
            if (data[index] > data[i]) {
                inversion++;
            }
        }
        return inversion;
    }
}
