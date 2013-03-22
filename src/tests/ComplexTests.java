package tests;

import analyze.impl.AnalysisInversionMethod;
import analyze.impl.AnalysisSeriesMethod;
import junit.framework.Assert;
import org.junit.Test;
import regression_generator.help.DistributionCalcHelper;
import regression_generator.help.NormalDistribution;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class ComplexTests {
    @Test
    public void testCalcEstimate() throws Exception {
        AnalysisInversionMethod inversion = new AnalysisInversionMethod();
        AnalysisSeriesMethod series = new AnalysisSeriesMethod();
        NormalDistribution nd = new NormalDistribution(0,1);
        double[] exampleArray = new double[]{5.5,5.1,5.7,5.2,4.8,
                5.7,5.0,6.5,5.4,5.8,
                6.8,6.6,4.9,5.4,5.9,
                5.4,6.8,5.8,6.9,5.5};
        double[] exampleArray2 = new double[]{5,3,8,9,4,1,7,5};
        //Примеры из методички
        Assert.assertEquals(series.calcEstimate(exampleArray),13);  //стр 8
        Assert.assertEquals(inversion.calcEstimate(exampleArray),62); //стр 11
        Assert.assertEquals(inversion.calcEstimate(exampleArray2),14);  //стр 9

        int genLength = 1000;
        int tryCount = 1000;
        for(int i=0;i<tryCount;i++){
            Assert.assertEquals(sum(DistributionCalcHelper.calcHistogram(nd.generateArray(genLength),5)),genLength);
        }
    }
    //Сумирует массив целых чисел
    private int sum(int[] inversions) {
        int res = 0;
        for (int inversion : inversions) {
            res = res + inversion;
        }
        return res;
    }
}
