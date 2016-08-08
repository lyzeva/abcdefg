package com.oneapm.research.correlation;

import com.oneapm.research.correlation.web.Config;
import com.oneapm.research.correlation.web.model.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ruan on 16-6-1.
 */
public class MetricTypeScore {
	private ArrayList<ArrayList<Double>> perThroughputBaseline = null;
	private ArrayList<ArrayList<Double>> perThroughputTimeseries = new ArrayList<>();
	private String applicationId = "10";
	private String metricName = "CPU/";
	private String metricId;
	private DataBaseConnection metricnameConnection = null, metricdataConnection = null;

	private String startTime = "", endTime = "";

	//remove "final" to make the number of metric in one type variable. by zuowei
	private int numOfMetric = 6;

	public void initJDBC() throws SQLException {
		metricnameConnection = new DataBaseConnection("mysql_driver", Config.mysqlipaddress, Config.mysqlport, Config.metricnamePath);
		metricdataConnection = new DataBaseConnection("mysql_driver", Config.mysqlipaddress, Config.mysqlport, Config.metricdataPath);
	}

	public MetricTypeScore() throws SQLException {
		initJDBC();
		applicationId = Config.applicationId;
		metricName = Config.chooseMetricName;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	
	
	public boolean preparedPerThroughput(CorrelationResultModel resultModel) throws SQLException {
		// WebTransaction
		String selectMetricIdBaseline = "SELECT metric_id, metric_name from metric_name_entity_new where metric_name =\"" + metricName + "\" and application_id = " + applicationId;

		ResultSet resultMetricIdBaseline = metricnameConnection.getResultForSql(selectMetricIdBaseline);
		
		if(resultMetricIdBaseline.next()){
			metricId = resultMetricIdBaseline.getString(1);
			resultModel.baselineId = Integer.parseInt(metricId);
			perThroughputBaseline = getDataFromDruid(resultModel.baselineId);
			resultModel.baselineNum = perThroughputBaseline;
			return true;
		}
		else{
			System.out.println("There is not such a metric name.");
			return false;
		}
		

//		HashMap<Integer, String> mapBaseline = CanaryUtils.getMetricIdFromResultSet(resultMetricIdBaseline);
	}
	
	

	public void processing(CorrelationResultModel resultModel) throws SQLException,IOException {
		String selectMetricIdBaseline = "SELECT metric_id, metric_name from metric_name_entity_new where application_id = " + applicationId + " and metric_id != " + metricId;

		ResultSet resultMetricIdBaseline = metricnameConnection.getResultForSql(selectMetricIdBaseline);

		HashMap<Integer, String> mapBaseline = CanaryUtils.getMetricIdFromResultSet(resultMetricIdBaseline);


		for (Map.Entry<Integer, String> entry : mapBaseline.entrySet()){
			int metricIdBaseline = entry.getKey();
			String key = mapBaseline.get(metricIdBaseline);
			if (metricIdBaseline != Integer.parseInt(metricId)){
				CorrelationTuple tuple = new CorrelationTuple();
				tuple.metric_id = metricIdBaseline;
				tuple.metric_name = key;
				tuple.num = getDataFromDruid(metricIdBaseline);
				for(int i=0; i<tuple.num.size(); i++) {
					tuple.coefficient.add(PearsonCoefficientCalculate.calculatePearson(perThroughputBaseline.get(i), tuple.num.get(i)));
				}
				resultModel.result.add(tuple);
			}
		}

    }


    public ArrayList<ArrayList<Double> > getDataFromDruid(int metricIdBaseline) throws SQLException {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        String[] tables = {"metric_data_entity_pt1m_0", "metric_data_entity_pt1m_1", "metric_data_entity_pt1m_2", "metric_data_entity_pt1m_3", "metric_data_entity_pt1m_4"};

        Double minValue = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < tables.length; ++i) {
                String selectResult = "SELECT min(start_time_seconds) as num1 FROM " + tables[i];
                ResultSet resultSet = metricdataConnection.getResultForSql(selectResult);
                Double res =  CanaryUtils.getSingleValueFromResultSet(resultSet);
                if (res > 1 && res < minValue) {
                    minValue = res;
                    minIndex = i;
                }
            }

            ArrayList<ResultSet> tmpBaseline = new ArrayList<>();
            for (int i = 0; i < tables.length; ++i) {
                String selectResultBaseline = "SELECT num1,num2,num3,num4,num5,num6, start_time_seconds FROM " + tables[(i + minIndex) % tables.length] + " WHERE start_time_seconds between " + startTime + " and " +
                        endTime + " and application_id = " + applicationId + " and metric_id = " +  metricIdBaseline + " ORDER BY start_time_seconds";
                ResultSet resultSetBaseline = metricdataConnection.getResultForSql(selectResultBaseline);

                tmpBaseline.add(resultSetBaseline);
            }
        result = CanaryUtils.getValueFromTimeResultSet(tmpBaseline, startTime, endTime);
        return result;
    }




    private boolean boolDivideByThroughput(ArrayList<ArrayList<Double> > metricList) {
        if (metricList == null || metricList.size() < 1)
            return false;
        int count = 0;
        for (int ii = 0; ii < metricList.size(); ++ii) {
            if ((Math.abs(metricList.get(ii).get(0) - 12.0) < 1e-10) || Math.abs(metricList.get(ii).get(0) - 1.0) < 1e-10) {
                ++count;
            }
        }
        return count >= metricList.size() * 0.9;
    }


 

    public void closeConnection() {
        metricnameConnection.closeConnection();
        metricdataConnection.closeConnection();
    }
}
