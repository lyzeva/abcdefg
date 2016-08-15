package com.oneapm.research.granger;

/**
 * Created by ruan on 16-6-15.
 */
public class GrangerCausalityStrategy_Bivariate extends GrangerCausality {

    public GrangerCausalityStrategy_Bivariate(int aLagSize) {
        this(CRITICAL_VALUE, aLagSize);
    }

    public GrangerCausalityStrategy_Bivariate(double cv, int aLagSize) {
        super(cv, aLagSize);
    }

    @Override
    public GrangerCausalIndicator apply(double[] ... series) {
        return apply(series[0], series[1]);
    }

    public GrangerCausalIndicator apply(double[] y, double[] x) {
        if (x == null || y == null || x.length != y.length)
             return null;
        if (x.equals(y)) return null;
        if (super.checkDataSizeConstraints(y.length, 2, super.lagSize)) {
            double[][] laggedY = createLaggedSide(y);
            double[][] laggedXY = createLaggedSide(x, y);
            double[] strippedY = strip(y);
            return super.performGranger(laggedY.length, strippedY, laggedY, 1, laggedXY, 2);
        }
        return null;
    }

}
