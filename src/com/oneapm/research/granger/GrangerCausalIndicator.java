package com.oneapm.research.granger;

/**
 * This is a data class file, which wraps the results of a granger-causality computation for easy access
 * Created by ruan on 16-6-14.
 */
public class GrangerCausalIndicator {

    // the result of the hypothesis test compared to the criticalValue
    private double pValue;
    // the critical value used to compare the hypotheses tests
    private double criticalValue;
    // the lag window size used in the granger computation
    private int lagSize;


    public GrangerCausalIndicator(double pv, double cv, int ls) {
        pValue = pv;
        criticalValue = cv;
        lagSize = ls;
    }

    public double getpValue() {
        return pValue;
    }

    public double getCriticalValue() {
        return criticalValue;
    }

    public int getLagSize() {
        return lagSize;
    }

    @Override
    public String toString() {
        return "Granger-Causality";
    }
}
