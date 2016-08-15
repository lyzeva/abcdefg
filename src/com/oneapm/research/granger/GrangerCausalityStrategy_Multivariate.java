package com.oneapm.research.granger;

/**
 * Created by ruan on 16-6-15.
 */
public class GrangerCausalityStrategy_Multivariate extends GrangerCausality {

    public GrangerCausalityStrategy_Multivariate(int aLagSize) {
        this(CRITICAL_VALUE, aLagSize);
    }

    public GrangerCausalityStrategy_Multivariate(double cv, int aLagSize) {
        super(cv, aLagSize);
    }

    @Override
    public GrangerCausalIndicator apply(double[]... series) {
        if (checkDataSizeConstraints(series[0].length, series.length, super.lagSize)) {
            apply(0, 1, series, series.length);
        }
        return null;
    }

    private GrangerCausalIndicator apply(int yVectorRow, int xVectorRow, double[][] universe, int nbrVariablesInUniverse) {
        double[][] orderedUniverse = orderUniverse(universe, yVectorRow, xVectorRow);
        double[][] orderedUniverseWithoutX = removeRow(orderedUniverse, 1);
        double[][] laggedH0 = super.createLaggedSide(orderedUniverseWithoutX);
        double[][] laggedH1 = super.createLaggedSide(orderedUniverse);
        double[] strippedY = super.strip(universe[yVectorRow]);
        return super.performGranger(laggedH0.length, strippedY, laggedH0, nbrVariablesInUniverse - 1, laggedH1, nbrVariablesInUniverse);
    }

    protected double[][] removeRow(double[][] orig, int rowIndex) {
        if (orig == null) return null;
        int observations = orig[0].length;
        double[][] smallerMat = new double[orig.length - 1][observations];
        int smallerMatCount = 0;
        for (int row = 0; row < orig.length; ++row) {
            if (row != rowIndex) {
                System.arraycopy(orig, row, smallerMat, smallerMatCount++, 1);
            }
        }
        return smallerMat;
    }

    protected double[][] orderUniverse(double[][] universe, int yRow, int xRow) {
        if (universe == null) return null;
        int observations = universe[0].length;
        double[][] ordered = new double[universe.length][observations];
        System.arraycopy(universe, yRow, ordered, 0, 1);
        System.arraycopy(universe, xRow, ordered, 1, 1);
        int targetRow = 2;
        for (int i = 0; i < universe.length; ++i) {
            if (i != yRow && i != xRow)
                System.arraycopy(universe, i, ordered, targetRow++, 1);
        }
        return ordered;
    }
}
