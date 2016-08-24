package com.oneapm.research.correlation;

import com.oneapm.research.correlation.web.Config;
import com.oneapm.research.correlation.web.model.*;
import com.oneapm.research.granger.*;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import java.lang.Math;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ruan on 16-6-1.
 */
public class MetricTypeScore {
	private ArrayList<ArrayList<Double>> perThroughputBaseline = null;
	private String applicationId;
	private String metricName;
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

	
	
	public boolean prepareRetrievedMetric(CorrelationResultModel resultModel) throws SQLException {
		// WebTransaction
		String selectMetricIdBaseline = "SELECT metric_id, metric_name from metric_name_entity_new where metric_name =\"" + metricName + "\" and application_id = " + applicationId;

		ResultSet resultMetricIdBaseline = metricnameConnection.getResultForSql(selectMetricIdBaseline);
		
		if(resultMetricIdBaseline.next()){
			metricId = resultMetricIdBaseline.getString(1);
			resultModel.baselineId = Integer.parseInt(metricId);
			perThroughputBaseline = getTimeserieById(resultModel.baselineId);
			resultModel.baselineNum = perThroughputBaseline;
			return true;
		}
		else{
			System.out.println("There is not such a metric name.");
			return false;
		}
		

//		HashMap<Integer, String> mapBaseline = CorrelationUtils.getMetricIdFromResultSet(resultMetricIdBaseline);
	}
	
	

	public void processing(CorrelationResultModel resultModel) throws SQLException,IOException {
		String selectMetricIdBaseline = "SELECT metric_id, metric_name from metric_name_entity_new where application_id = " + applicationId + " and metric_id != " + metricId;

		ResultSet resultMetricIdBaseline = metricnameConnection.getResultForSql(selectMetricIdBaseline);

		HashMap<Integer, String> mapBaseline = CorrelationUtils.getMetricIdFromResultSet(resultMetricIdBaseline);

		PearsonsCorrelation pearsonCalculator = new PearsonsCorrelation();


		for (Map.Entry<Integer, String> entry : mapBaseline.entrySet()){
			int metricIdBaseline = entry.getKey();
			String key = mapBaseline.get(metricIdBaseline);
			if (metricIdBaseline != resultModel.baselineId){
				CorrelationTuple tuple = new CorrelationTuple();
				tuple.metric_id = metricIdBaseline;
				tuple.metric_name = key;
				ArrayList<ArrayList<Double>> num = getTimeserieById(metricIdBaseline);
				if(num != null){
					ArrayList<IndependentNum> timeserieResults = new ArrayList<>();
					for(int i=0; i<num.size(); i++) {
						IndependentNum independentNum = new IndependentNum();
						independentNum.timeserie = num.get(i);
						ArrayList<Double> x = perThroughputBaseline.get(i), y = independentNum.timeserie;
						double a[] = new double[x.size()];
						double b[] = new double[y.size()];
						for(int j=0;j<a.length;j++){
							a[j] = x.get(j);
						}
						for(int j=0;j<b.length;j++){
							b[j] = y.get(j);
						}


//						independentNum.coefficient = PearsonCoefficientCalculate.calculatePearson(x, y);
						independentNum.coefficient = Math.abs(pearsonCalculator.correlation(a,b));

						GrangerCausalityStrategy_Bivariate grangerCalculator = new GrangerCausalityStrategy_Bivariate(Double.parseDouble(Config.CRITICAL_VALUE), Integer.parseInt(Config.aLagSize));
						//b为因，a为果;我们要找有metric中导致baselin metric的原因是什么
						GrangerCausalIndicator grangerIndicator = grangerCalculator.apply(b,a);
						if(grangerIndicator != null)
							independentNum.granger = grangerIndicator.getpValue();

						timeserieResults.add(independentNum);
					}
					tuple.num_result = timeserieResults;
					resultModel.result.add(tuple);
				}
			}
		}

    }


    public ArrayList<ArrayList<Double> > getTimeserieById(int metricIdBaseline) throws SQLException {
        ArrayList<ArrayList<Double>> result;
        String[] tables = {"metric_data_entity_pt1m_0", "metric_data_entity_pt1m_1", "metric_data_entity_pt1m_2", "metric_data_entity_pt1m_3", "metric_data_entity_pt1m_4"};

        Double minValue = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < tables.length; ++i) {
                String selectResult = "SELECT min(start_time_seconds) as num1 FROM " + tables[i];
                ResultSet resultSet = metricdataConnection.getResultForSql(selectResult);
                Double res =  CorrelationUtils.getSingleValueFromResultSet(resultSet);
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
        result = CorrelationUtils.getValueFromTimeResultSet(tmpBaseline, startTime, endTime);
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
