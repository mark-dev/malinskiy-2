package regression_generator.impl;

import interfaces.IArrayGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 15.03.13
 * Time: 20:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class RegressionArrayGeneratorModel2 extends RegressionArrayGeneratorModel1 implements IArrayGenerator {
    protected RegressionArrayGeneratorModel2(double alpha1, double alpha2, double z1, double z2) {
        super(alpha1, alpha2, z1, z2);
    }

    @Override
    protected double nextArrayValue(int i, double[] resultArray, double[] normalDistArray) {
        return super.nextArrayValueModel1(i,resultArray,normalDistArray) + additionalFunc(i);
    }

    protected abstract double additionalFunc(int i);
}
