package com.oneapm.research.correlation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ruan on 16-5-26.
 */
public class CorrelationUtils {
    private CorrelationUtils() {}

    public static ArrayList<ArrayList<Double>> getValueFromResultSet(ResultSet res) throws SQLException {
        if (res == null) {
            return  null;
        }
        ArrayList<ArrayList<Double> > value = new ArrayList<ArrayList<Double> >();

        ResultSetMetaData resultSetMetaData = res.getMetaData();
        int dimen = resultSetMetaData.getColumnCount() - 1;
        int count = 0;
        while (res.next()) {
            ArrayList<Double> temp = new ArrayList<>();
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
        int dimen = 0;
        int num = (Integer.parseInt(ed) - Integer.parseInt(st)) / 60 - 1;
        for (int i = 0; i < resBaseline.size(); ++i) {
            ResultSet rb = resBaseline.get(i);

            ArrayList<ArrayList<Double> > valueB = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = rb.getMetaData();
            dimen = resultSetMetaData.getColumnCount() - 1;
            ArrayList<String> timestampB = new ArrayList<>();
            int count = 0;
            while (rb.next()) {
                ArrayList<Double> temp = new ArrayList<>();
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
        for (int i = 0; i < baselineTime.size(); ++i) {
            int pos = DisFromBegin(baselineTime.get(i), st);
            for (int j = 0; j < dimen; ++j) {
                baseline[pos][j] = baselineValue.get(i).get(j);
            }
        }

        baselineValue.clear();
        boolean hasNoneZeroValue = false;
        for (int i = 0; i < dimen; ++i) {
            ArrayList<Double> tmpB = new ArrayList<>();
            for (int j = 0; j < num; ++j) {
                if(baseline[j][i] > 1e-10)
                    hasNoneZeroValue = true;
                tmpB.add(baseline[j][i]);
            }
            baselineValue.add(tmpB);
        }
        if(hasNoneZeroValue)
            return baselineValue;
        else
            return null;
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

    /***
     * Modified by zuowei on 2016-06-03
     * Calculate score without NO_DATA state.
     * @param metricStatesCount
     * @param weightInGroups
     * @return
     */


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
