package com.oneapm.research.correlation;
import com.google.common.base.Joiner;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by ruan on 16-5-25.
 */
public class TestCommon {
//    public static void outputToCsv(ArrayList<Double> coefficient, ArrayList<Integer> metricId, ArrayList<String> metricName, String outputFilename)
//            throws IOException {
//        int numData = coefficient.size();
//        if (numData < 1) return;
//        List<String[]> output_list = new ArrayList<String[]>();
//        for (int ii = 0; ii < numData; ++ii) {
//            String[] a_line = new String[3];
//            a_line[0] = Integer.toString(metricId.get(ii));
//            a_line[1] = metricName.get(ii);
//            a_line[2] = Double.toString(coefficient.get(ii));
//            output_list.add(ii, a_line);
//        }
//        writeToFile(outputFilename, output_list);
//    }

//    public static void outputToCsv1(double[][] realValue, double[][] canValue, String outputFilename)
//            throws IOException {
//        int numData = realValue.length;
//        if (numData < 1) return;
//        List<String[]> output_list = new ArrayList<>();
//        int dimen = realValue[0].length;
//        for (int ii = 0; ii < numData; ++ii) {
//            String[] a_line = new String[dimen * 2];
//            for (int j = 0; j < dimen; ++j) {
//                a_line[j] = Double.toString(realValue[ii][j]);
//            }
//            for (int j = dimen; j < 2 * dimen; ++j) {
//                a_line[j] = Double.toString(canValue[ii][j - dimen]);
//            }
//            output_list.add(ii, a_line);
//        }
//        writeToFile(outputFilename, output_list);
//    }

    public static void writeToFile(String outputFilename, ArrayList<ArrayList<String>> output_list) throws IOException {
        File file = new File(outputFilename);
        OutputStream fop = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fop));
        for (ArrayList<String> element : output_list) {
            String csvLine = Joiner.on(",").join(element);
            writer.write(csvLine);
            writer.newLine();
        }
        writer.close();
    }


}
