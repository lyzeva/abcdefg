package com.oneapm.research.correlation.web.model;

import com.oneapm.research.correlation.TestCommon;
import com.oneapm.research.correlation.web.Config;

import java.io.IOException;
import java.util.ArrayList;


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
        ArrayList<ArrayList<String>> output_list1 = new ArrayList<>();
		ArrayList<ArrayList<String>> output_list2= new ArrayList<>();

		ArrayList<String> a_line = new ArrayList<>();
		a_line.add("metricId");
		a_line.add("metricName");
		a_line.add("coefficient1");
		a_line.add("granger1");
		a_line.add("coefficient2");
		a_line.add("granger2");
		a_line.add("coefficient3");
		a_line.add("granger3");
		a_line.add("coefficient4");
		a_line.add("granger4");
		a_line.add("coefficient5");
		a_line.add("granger5");
		a_line.add("coefficient6");
		a_line.add("granger6");
		output_list1.add(a_line);
		a_line.clear();
        for (CorrelationTuple tuple : result) {
			ArrayList<String> list1_line = new ArrayList<>();
            list1_line.add( Integer.toString(tuple.metric_id) );
            list1_line.add( tuple.metric_name );
			int count = 0;
			for(IndependentNum independentnum : tuple.num_result){
				list1_line.add( Double.toString(independentnum.coefficient) );
				list1_line.add( Double.toString(independentnum.granger) );

				ArrayList<String> list2_line = new ArrayList<>();
				list2_line.add( Integer.toString(tuple.metric_id) );
				list2_line.add( tuple.metric_name );
				list2_line.add( "num" + (++count) );

				for(Double timeserieValue : independentnum.timeserie){
					list2_line.add( Double.toString(timeserieValue) );
				}
				output_list2.add(list2_line);
			}
            output_list1.add(list1_line);
        }
        TestCommon.writeToFile("scores", output_list1);
		TestCommon.writeToFile("values", output_list2);
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
