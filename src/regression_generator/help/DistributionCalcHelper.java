package regression_generator.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

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
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            PrintWriter pw = new PrintWriter(f);
            for (double anArray : array) {
                pw.println(anArray);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
