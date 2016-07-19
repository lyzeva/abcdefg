package com.oneapm.research.correlation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.oneapm.research.correlation.web.model.*;

/**
 * Created by ruan on 16-7-12.
 */
public class CorrelationTest {
	public static CorrelationResultModel currentResult;
	
	private static MetricTypeScore metricTypeScore = null;


	public static CorrelationResultModel correlationTest(String startTime,String endTime) {
		try {
			metricTypeScore = new MetricTypeScore();
			metricTypeScore.setStartTime(startTime);
			metricTypeScore.setEndTime(endTime);
			metricTypeScore.preparedPerThroughput();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			currentResult=metricTypeScore.processing();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		metricTypeScore.closeConnection();

		return currentResult;
	}
}
