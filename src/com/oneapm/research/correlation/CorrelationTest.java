package com.oneapm.research.correlation;

import java.sql.SQLException;


import com.oneapm.research.correlation.web.model.*;

/**
 * Created by ruan on 16-7-12.
 */
public class CorrelationTest {
	public static CorrelationResultModel currentResult;
	
	private static MetricTypeScore metricTypeScore = null;


	public static CorrelationResultModel correlationTest(String startTime,String endTime) {
		boolean hasMetricName=false;
		try {
			metricTypeScore = new MetricTypeScore();
			metricTypeScore.setStartTime(startTime);
			metricTypeScore.setEndTime(endTime);
			hasMetricName = metricTypeScore.preparedPerThroughput();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(hasMetricName) try{
			currentResult=metricTypeScore.processing();
			currentResult.outputToCsv("coefficient1.csv");
			currentResult.sortList();
			currentResult.outputToCsv("coefficient2.csv");
		} catch(Exception e){
			e.printStackTrace();
		}


		metricTypeScore.closeConnection();

		return currentResult;
	}
}
