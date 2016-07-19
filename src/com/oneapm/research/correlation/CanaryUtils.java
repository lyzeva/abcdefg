package com.oneapm.research.correlation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ruan on 16-5-26.
 */
public class CanaryUtils {
    private CanaryUtils() {}


    public static ArrayList<ArrayList<Double>> getValueFromResultSet(ResultSet res) throws SQLException {
        if (res == null) {
            return  null;
        }
        ArrayList<ArrayList<Double> > value = new ArrayList<ArrayList<Double> >();

        ResultSetMetaData resultSetMetaData = res.getMetaData();
        int dimen = resultSetMetaData.getColumnCount() - 1;
        int count = 0;
        while (res.next()) {
            ArrayList<Double> temp = new ArrayList<Double>();
            for (int i = 1; i <= dimen; ++i) {
                temp.add(i - 1, res.getDouble(("num" + i)));
            }
            value.add(count++, temp);
        }
        return value;
    }

    public static ArrayList<ArrayList<Double>> getValueFromTimeResultSet(ArrayList<ResultSet> resBaseline,
                                                                                      String st, String ed) throws SQLException {
        if (resBaseline == null)
            return null;
        ArrayList<ArrayList<Double>> baselineValue = new ArrayList<>();
        ArrayList<String> baselineTime = new ArrayList<>();
        int num = (Integer.parseInt(ed) - Integer.parseInt(st)) / 60;
        int dimen = 0;
        for (int i = 0; i < resBaseline.size(); ++i) {
            ResultSet rb = resBaseline.get(i);

            ArrayList<ArrayList<Double> > valueB = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rb.getMetaData();
            dimen = resultSetMetaData.getColumnCount() - 1;
            ArrayList<String> timestampB = new ArrayList<>();
            int count = 0;
            while (rb.next()) {
                ArrayList<Double> temp = new ArrayList<Double>();
                for (int j = 1; j <= dimen; ++j) {
                    temp.add(j - 1, rb.getDouble(("num" + j)));
                }
                timestampB.add(count, rb.getString("start_time_seconds"));
                valueB.add(count++, temp);
            }

            baselineValue.addAll(valueB);
            baselineTime.addAll(timestampB);
        }

        double[][] baseline = new double[num][dimen];
        boolean[][] visited = new boolean[num][dimen];
        for (int i = 0; i < baselineTime.size(); ++i) {
            int pos = DisFromBegin(baselineTime.get(i), st);
            for (int j = 0; j < dimen; ++j) {
                baseline[pos][j] = baselineValue.get(i).get(j);
                visited[pos][j] = true;
            }
        }

        baselineValue.clear();
        for (int i = 0; i < dimen; ++i) {
            ArrayList<Double> tmpB = new ArrayList<>();
            for (int j = 0; j < num; ++j) {
            	//when there isn't data in a certain timestamp
        		if(j!=0 && j!=num-1 && !visited[j][i]){
        			baseline[j][i] = (baseline[j-1][i]+baseline[j+1][i])/2;
        		}
                tmpB.add(baseline[j][i]);
            }
            baselineValue.add(tmpB);
        }
        return baselineValue;
    }

    private static int DisFromBegin(String end, String start) {
        return (Integer.parseInt(end) - Integer.parseInt(start)) / 60;
    }

    public static HashMap<Integer, String> getMetricIdFromResultSet(ResultSet res) throws SQLException {
        if (res == null)
            return null;

        HashMap<Integer, String> value = new HashMap<Integer, String>();

        while (res.next()) {
            value.put(res.getInt(1), res.getString(2));
        }

        return value;
    }

    public static <T> void swap(T t1, T t2){
        T tmp = t1;
        t1 = t2;
        t2 = tmp;
    }

    public static boolean containOnlyZero(double[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (Math.abs(array[i]) > 1e-10)
                return false;
        }
        return true;
    }

    /***
     * Modified by zuowei on 2016-06-03
     * Calculate score without NO_DATA state.
     * @param metricStatesCount
     * @param weightInGroups
     * @return
     */
    public static Double calculateScore(double[] metricStatesCount, ArrayList<Double> weightInGroups) {
        double score = 0.0;
        if (metricStatesCount.length == weightInGroups.size()) {
            int sumNumMetric = 0;
            for (int i = 0; i < metricStatesCount.length; ++i) {
                sumNumMetric += metricStatesCount[i];
            }
            for (int i = 0; i < metricStatesCount.length; ++i) {
                score += (metricStatesCount[i] * weightInGroups.get(i));
            }
            if (sumNumMetric == 0)
                return Double.NaN;//return 1;
            return score / sumNumMetric;
        }
        return score;
    }

    public static double calculateCanaryScore(ArrayList<Double> oneCategoryScore, ArrayList<Double> weight) {
        double score = 0.0;
        double weightSum = 0.0;
        if (oneCategoryScore.size() == weight.size()) {
            for (int i = 0; i < oneCategoryScore.size(); ++i)
                if(!oneCategoryScore.get(i).equals(Double.NaN)) {
                    score += (oneCategoryScore.get(i) * weight.get(i));
                    weightSum += weight.get(i);
                }
        }
        return score / weightSum;
    }

    public static Double calculateScore(int categoryid, ArrayList<Double> metricStatesCount) {
        if (metricStatesCount == null || metricStatesCount.size() < 1) return Double.NaN;
        double score = 0.0;
        int count = 0;
        for (int i = 0; i < metricStatesCount.size(); ++i) {
            if (!metricStatesCount.get(i).equals(Double.NaN)) {
                ++count;
                score += metricStatesCount.get(i);
            }
        }
        return score / count;
    }


    public static Double getSingleValueFromResultSet(ResultSet res) throws SQLException {
        if (res == null)
            return null;
        while (res.next()) {
            return res.getDouble(1);
        }
        return null;
    }

    public static void removeMissingValuesByAveragingNeighbors(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (Double.isNaN(arr[i])) {
                double sum = 0.0;
                int count = 0;
                if (i - 1 >= 0 && !Double.isNaN(arr[i - 1])) {
                    sum += arr[i - 1];
                    count++;
                }
                if (i + 1 < arr.length && !Double.isNaN(arr[i + 1])) {
                    sum += arr[i + 1];
                    count++;
                }
                if (count != 0)
                    arr[i] = sum / count;
                else
                    arr[i] = 0.0;
            }
        }
    }
}
