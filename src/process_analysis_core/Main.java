package process_analysis_core;

import analyze.impl.AnalysisInversionMethod;
import analyze.impl.AnalysisSeriesMethod;
import interfaces.IArrayGenerator;
import regression_generator.impl.RegressionArrayGeneratorModel1;
import regression_generator.help.DistributionCalcHelper;
import regression_generator.impl.RegressionArrayGeneratorModel2;

import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        int N = 70;    //N
        int processArrayLength = 5000; //N1
        int parts = 100; //  N2
        double alpha1 = 0.9;
        double alpha2 = -0.6;
        //первые два члена регрессионной модели
        double z1 = -0.2;
        double z2 = 0.5;

       processLab1(N,processArrayLength,parts,alpha1,alpha2,z1,z2);
    }

    private static void processLab1(int N,int processArrayLength,int parts,double alpha1,double alpha2,double z1,double z2){
        IArrayGenerator generator = new RegressionArrayGeneratorModel1(alpha1,alpha2,z1,z2);
        AnalysisInversionMethod inversion  = new AnalysisInversionMethod();
        AnalysisSeriesMethod  series = new AnalysisSeriesMethod();


        //Генерируем N значений и для них считаем оценки методом инверсий и серий
        System.out.println("\nЗадание2:");
        double[] smallExperementalArray = generator.generateSeries(N);
        System.out.println("SmallExperementalArray("+N+")\n\tInversion: "
                +inversion.calcEstimate(smallExperementalArray)+ "\n\tSeries: "
                +series.calcEstimate(smallExperementalArray));
        /*
        Генерируем processArrayLength(N1) значений, разбиваем на parts(N2) частей
        и считаем оценки для массивов мат ожиданий и отклонения методом инверсий и серий
        */
        System.out.println("\nЗадание 3:");
        processAnalysis(generator,processArrayLength,parts);
        /*  Тоже самое, за исключением того что используется модель 2
            т.е. если раньше генерился массив x(i) то теперь генерится массив
            x(i) + additionalFunc(i)
        */
        System.out.println("Задание 4:");
        IArrayGenerator generator2 = new RegressionArrayGeneratorModel2(alpha1,alpha2,z1,z2){
            //Функция из модели 2
            @Override
            protected double additionalFunc(int i) {
                return (i - Math.pow(i,2) / 2000.0)*0.001;
            }
        };
        processAnalysis(generator2,processArrayLength,parts);
    }
    //Выполняет задание 3, используя заданный генератор массива
    //колво элементов которое необходимо сгенерить (processArrayLength)
    //Разбивает на  parts частей
    private static void processAnalysis(IArrayGenerator generator,
                                        int processArrayLength,
                                        int parts){

        //Генерим  processArrayLength значений
        double[] experementalData = generator.generateSeries(processArrayLength);
        File f = new File("out/"+generator.toString());
       DistributionCalcHelper.saveToFile(experementalData, f);
        //Разбиваем на части
        ArrayList<double[]> splitted = splitByParts(experementalData,parts);

        //Считаем  мат ожидания и отклонения для каждой части,
        // сохраняем в массив
        double[] mxArray = new double[splitted.size()];
        double[] sigmaArray = new double[splitted.size()];
        fillMxAndSigmaArrays(splitted,mxArray,sigmaArray);

        //Расчитываем по две оценки для каждого массива
        AnalysisInversionMethod inversion  = new AnalysisInversionMethod();
        AnalysisSeriesMethod  series = new AnalysisSeriesMethod();
        System.out.println("MX\n\tInversion: "
                + inversion.calcEstimate(mxArray)
                + "\n\tSeries: "+series.calcEstimate(mxArray));
        System.out.println("Sigma\n\tInversion: "
                + inversion.calcEstimate(sigmaArray)
                + "\n\tSeries: "+series.calcEstimate(sigmaArray));
    }
    //Заполняет (пустые) массивы с мат ожиданием и отклонением, на основании
    //списка массивов на которые был разбит исходный массив
    private  static void fillMxAndSigmaArrays(ArrayList<double[]> splitted,double[] mxArray,double[] sigmaArray){
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
    private  static ArrayList<double[]> splitByParts(double[] original,int parts){
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
