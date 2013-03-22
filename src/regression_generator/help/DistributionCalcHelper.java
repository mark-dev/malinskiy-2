package regression_generator.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class DistributionCalcHelper {
    public static double calcMx(double[] array) {
        double m = 0;
        for (double anArray : array) {
            m = m + anArray;
        }
        return m / array.length;
    }

    public static double calcSigma(double[] array) {
        double mx = calcMx(array);
        double sum = 0;
        for (double anArray : array) {
            sum = sum + Math.pow((anArray - mx), 2);
        }
        return sum / array.length;
    }
    public static int[] calcHistogram(double[] array, int parts) {
        double min = min(array);
        double max = max(array);
        double step = (max - min) / parts;
        int[] histValues = new int[parts];
        for (int i = 0; i < histValues.length; i++) {
            int count = 0;   //Колво попаданий в текущий промежуток
            double right = min + step*(i+1);
            double left = min + step*(i);
            for (double anArray : array) {
                if (right > anArray && anArray > left) {
                    count++;
                } else if (i == 0 && anArray == min) {
                    count++;
                } else if (i == histValues.length - 1 && anArray == max) {
                    count++;
                }
            }
            histValues[i] = count;
        }
        return histValues;
    }
    private static double min(double[] array) {
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    private static double max(double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    public static void saveToFile(double[] array, File f) {
        if(f.exists()){
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(f);
            for (double anArray : array) {
                pw.println(anArray);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Заполняет (пустые) массивы с мат ожиданием и отклонением, на основании
    //списка массивов на которые был разбит исходный массив
    public  static void fillMxAndSigmaArrays(ArrayList<double[]> splitted,double[] mxArray,double[] sigmaArray){
        for(int i=0;i<splitted.size();i++){
            //Для каждой из частей массива
            // расчитываем мат ожидание
            // и среднее отклонение
            double[] partArray = splitted.get(i);
            mxArray[i] = DistributionCalcHelper.calcMx(partArray);
            sigmaArray[i] = DistributionCalcHelper.calcSigma(partArray);
        }
    }
    //Разбивает массив original на части размером parts
    public  static ArrayList<double[]> splitByParts(double[] original,int parts){
        if(original.length % parts !=0){
            throw new IllegalArgumentException("невозможно разделить массив на равные части(не делится нацело)");
        }
        ArrayList<double[]> result = new ArrayList<double[]>();
        for(int i=0;i<original.length;i = i + parts){
            //Копируем в tempArray начиная с позиции 0
            //массив original начиная с индекса i
            //длинной parts
            double[] tempArray = new double[parts];
            System.arraycopy(original, i, tempArray, 0, parts);
            result.add(tempArray);
        }
        return result;
    }
}
