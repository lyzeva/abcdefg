package com.oneapm.research.correlation;

import java.sql.SQLException;

import com.oneapm.research.correlation.web.model.*;

/**
 * Created by ruan on 16-7-12.
 */
public class CorrelationTest {

	private static MetricTypeScore metricTypeScore = null;


	public static CorrelationResultModel correlationTest(String startTime,String endTime) {
		boolean hasMetricName=false;
		public CorrelationResultModel currentResult = new CorrelationResultModel();
		try {
			metricTypeScore = new MetricTypeScore();
			metricTypeScore.setStartTime(startTime);
			metricTypeScore.setEndTime(endTime);
			hasMetricName = metricTypeScore.preparedPerThroughput(currentResult);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(hasMetricName) try{
			metricTypeScore.processing(currentResult);
			currentResult.outputToCsv("coefficient1.csv");
			currentResult.outputToCsv("coefficient2.csv");
		} catch(Exception e){
			e.printStackTrace();
		}
		else
			System.out.println("Don't have such a metric name!");

		metricTypeScore.closeConnection();

		return currentResult;
	}
}
