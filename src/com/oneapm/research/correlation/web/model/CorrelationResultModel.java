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
	public ArrayList<CorrelationTuple> result;

	public CorrelationResultModel(){
		baselineName = Config.chooseMetricName;
		result = new ArrayList<>();
	}

	public CorrelationResultModel(ArrayList<CorrelationTuple> tupleList){
		result = tupleList;
	}
	
    public void outputToCsv(String outputFilename)
            throws IOException {
        int numData = result.size();
        if (numData < 1) return;
        List<String[]> output_list = new ArrayList<>();

		String[] a_line = new String[3];
		a_line[0] = "metricId";
		a_line[1] = "metricName";
		a_line[2] = "coefficient";
		output_list.add(a_line);
        for (int ii = 0; ii < numData; ++ii) {
        	String[] b_line = new String[3];
            b_line[0] = Integer.toString(result.get(ii).metric_id);
            b_line[1] = result.get(ii).metric_name;
            b_line[2] = Double.toString(result.get(ii).coefficient.get(0));
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
