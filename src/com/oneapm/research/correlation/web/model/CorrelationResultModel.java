package com.oneapm.research.correlation.web.model;

import com.oneapm.research.correlation.TestCommon;
import com.oneapm.research.correlation.web.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by ruan on 16-7-12.
 */


public class CorrelationResultModel {
	public int baselineId ;
	public String baselineName ;
	public ArrayList<ArrayList<Double>> baselineNum = null;
	public ArrayList<CorrelationTuple> result = null;

	public CorrelationResultModel(){
		baselineName = Config.chooseMetricName;
		result = new ArrayList<>();
	}

	public CorrelationResultModel(ArrayList<CorrelationTuple> tupleList){
		baselineName = Config.chooseMetricName;
		result = tupleList;
	}
	
    public void outputToCsv(String outputFilename)
            throws IOException {
        int numData = result.size();
        if (numData < 1) return;
        List<String[]> output_list = new ArrayList<>();

		String[] a_line = new String[8];
		a_line[0] = "metricId";
		a_line[1] = "metricName";
		a_line[2] = "coefficient1";
		a_line[3] = "coefficient2";
		a_line[4] = "coefficient3";
		a_line[5] = "coefficient4";
		a_line[6] = "coefficient5";
		a_line[7] = "coefficient6";
		output_list.add(a_line);
        for (int ii = 0; ii < numData; ++ii) {
        	String[] b_line = new String[8];
            b_line[0] = Integer.toString(result.get(ii).metric_id);
            b_line[1] = result.get(ii).metric_name;
            b_line[2] = Double.toString(result.get(ii).num_result.get(0).coefficient);
			b_line[3] = Double.toString(result.get(ii).num_result.get(1).coefficient);
			b_line[4] = Double.toString(result.get(ii).num_result.get(2).coefficient);
			b_line[5] = Double.toString(result.get(ii).num_result.get(3).coefficient);
			b_line[6] = Double.toString(result.get(ii).num_result.get(4).coefficient);
			b_line[7] = Double.toString(result.get(ii).num_result.get(5).coefficient);
            output_list.add(b_line);
        }
        TestCommon.writeToFile(outputFilename, output_list);
    }
/*
	public void sortList(){
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "false");
		Collections.sort(result, new sortByCoefficient());
	}
*/
}
/*
class sortByCoefficient implements Comparator{

	private int dim;
	sortByCoefficient(int dimen){
		dim = dimen;
	}
	public int compare(Object o1,Object o2){
		CorrelationTuple c1 = (CorrelationTuple) o1;
		CorrelationTuple c2 = (CorrelationTuple) o2;
		if(c2.coefficient.get(dim)>c1.coefficient.get(dim))
			return 1;
		else if (c2.coefficient.get(dim)<c1.coefficient.get(dim))
			return -1;
		else
			return 0;
	}
}
*/
