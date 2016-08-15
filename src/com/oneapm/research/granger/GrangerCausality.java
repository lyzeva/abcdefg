package com.oneapm.research.granger;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.stream.DoubleStream;

/**
 * This is the abstract superclass for the bi- and multi- variate GrangerCausality following Grangers' idea for causality.
 * It basically describes, how useful one variable x is to predict the future development of variable y (see wikipedia for more information).
 * This is done by creating 2 hypotheses H0 and H1 and applying an F-Test on both to analyse the statistical evidence,
 * provided in the data models, whether one can safely reject H0 or not.
 * Created by ruan on 16-6-14.
 */
public abstract class GrangerCausality {
    // the critical value used to evalute, whether "x granger-causes y" or not
    // if the pValue > criticalValue => x granger-causes y
    public static double CRITICAL_VALUE = 0.85;

    protected int lagSize = 1;

    public GrangerCausality(double cv, int ls) {
        CRITICAL_VALUE = cv;
        lagSize = ls;
    }

    public abstract GrangerCausalIndicator apply(double[] ...series);

    protected GrangerCausalIndicator performGranger(int laggedH0Rows, double[] strippedY, double[][] laggedH0,
                                                    int variablesH0, double[][] laggedH1, int variablesH1) {
        try {
            OLSMultipleLinearRegression h0 = new OLSMultipleLinearRegression();
            OLSMultipleLinearRegression h1 = new OLSMultipleLinearRegression();

            h0.newSampleData(strippedY, laggedH0);
            h1.newSampleData(strippedY, laggedH1);

            double[] rs0 = h0.estimateResiduals();
            double[] rs1 = h1.estimateResiduals();

            double RSS0 = sqrSum(rs0);
            double RSS1 = sqrSum(rs1);
            int nbrParametersModels0 = this.lagSize * variablesH0;
            int nbrParametersModels1 = this.lagSize * variablesH1;

            double fTest = ((RSS0 - RSS1) / (nbrParametersModels1 - nbrParametersModels0)) / (RSS1 / (laggedH0Rows - nbrParametersModels1));

            FDistribution fDistribution = new FDistribution(this.lagSize, laggedH0Rows - 2 * this.lagSize - 1);
            double pValue = 1.0 - fDistribution.cumulativeProbability(fTest);

            return new GrangerCausalIndicator(pValue, CRITICAL_VALUE, this.lagSize);
        } catch (SingularMatrixException smex) {
            smex.printStackTrace();
            return null;
        }
    }

    protected double[][] createLaggedSide(double[]... a) {
        int n = a[0].length - this.lagSize;
        double[][] res = new double[n][this.lagSize * a.length];
        for (int i = 0; i < a.length; ++i) {
            double[] ai = a[i];
            for (int l = 0; l < this.lagSize; ++l) {
                for (int j = 0; j < n; ++j)
                    res[j][i * this.lagSize + l] = ai[l + j];
            }
        }
        return res;
    }

    protected double sqrSum(double[] a) {
        return DoubleStream.of(a).reduce(0.0, (accu, next) -> accu + next * next);
    }

    protected boolean checkDataSizeConstraints(int dataSize, int nbrOfVariables, int lagSize) {
        int observedDataSize = dataSize - lagSize;
        int nbrOfPredictors = nbrOfVariables * lagSize;
        if (observedDataSize <= nbrOfPredictors) {
            throw new RuntimeException(String.format("there is not enough data available (%d) to satisfy all predictors" +
                    " (%d) and the granger test constraints with (max lag size = %d).", observedDataSize, nbrOfPredictors, lagSize));
        }
        int maxLagSize = getMaximumLagSize(observedDataSize, nbrOfVariables);
        if (lagSize > maxLagSize) {
            throw new RuntimeException(String.format("the lag size (%d) is too big for the given data size (%d) and the granger " +
                    "test constraints wiht (max lag size = %d)", lagSize, dataSize, maxLagSize));
        }
        return true;
    }

    public static int getMaximumLagSize(int nbrOfObservations, int nbrOfVariables) {
        if (nbrOfObservations <= 0 || nbrOfVariables <= 0) {
            throw new RuntimeException(String.format("the number of observation (%d) or the number of variables (%d) is " +
                    "smaller than 1.", nbrOfObservations, nbrOfVariables));
        }
        if (nbrOfObservations < nbrOfVariables + 1) {
            throw new RuntimeException(String.format("there's not even enough data (%d) available for a lag size of 1 for" +
                    " the given variables (%d)", nbrOfObservations, nbrOfVariables));
        }
        return (int)Math.floor(nbrOfObservations / (nbrOfVariables + 1));
    }

    protected double[] strip(double[] a) {
        if (a.length < this.lagSize) {
            throw new RuntimeException(String.format("can't cut %d elements from array with length %d", this.lagSize, a.length));
        }
        double[] res = new double[a.length - this.lagSize];
        System.arraycopy(a, this.lagSize, res, 0, res.length);
        return res;
    }

}
