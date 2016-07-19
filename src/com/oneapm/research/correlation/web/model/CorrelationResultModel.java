package com.oneapm.research.correlation.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ruan on 16-7-12.
 */





public class CorrelationResultModel {
	public ArrayList<CorrelationTuple> result = new ArrayList<>();
	
	
	
	public void sortList(){
		Collections.sort(result, new sortByCoefficient());
	}
	
	class sortByCoefficient implements Comparator{
		public int compare(Object o1,Object o2){
			CorrelationTuple c1 = (CorrelationTuple) o1;
			CorrelationTuple c2 = (CorrelationTuple) o2;
			if(c1.coefficient < c2.coefficient)
				return 1;
			return 0;
		}
	}
	
}
