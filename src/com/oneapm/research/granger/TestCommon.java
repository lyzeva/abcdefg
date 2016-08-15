package com.oneapm.research.granger;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;


/**
 * Created by ruan on 16-6-15.
 */
public class TestCommon {
    public static String[] readTestSets(String regex) {
        File f = new File("./src/test/resources/");
        MyFilenameFilter filter = (new MyFilenameFilter(regex));
        String[] files = f.list(filter);
        return files;
    }

    public static String getResourceAsString(String resource) throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(resource);
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer);
        return writer.toString();
    }

    public static Object[] readFromCSV(String filename) throws IOException,ParseException {
        String[] lines = getResourceAsString(filename).split("\n");
        int numData = lines.length;
        int dimension = lines[0].split(",").length - 1;

        double[][] series = new double[numData - 1][dimension];
        String[] timestamps = new String[numData - 1];
        for (int i = 1; i < numData; ++i) {
            lines[i] = lines[i].replaceAll("[\r\n]", "");
            String[] values = lines[i].split(",");
            timestamps[i - 1] = values[0];
            for (int j = 0; j < dimension; ++j) {
                series[i - 1][j] = Double.parseDouble(values[j + 1]);
            }
        }
        return new Object[]{filename, timestamps, series};
    }
}
