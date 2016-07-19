package com.oneapm.research.correlation.web;

import spark.Request;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Created by ruan on 16-7-12.
 */
public class Config {
    public static String chooseMetricName = "CPU/User Time";

    public static String chooseTopN = "5";

    public static String mysqlipaddress="jdbc:mysql://10.128.7.138";

    public static String mysqlport="3306";

    public static String metricnamePath= "/metricname";
    
    public static String metricdataPath= "/metricdata_pt1m";

    public static String withPastTimeData = "10800";

    public static String applicationId = "10";

	public static String starttime="1464620400";

	public static String endtime="1464638400";

	private static SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm" );

    public static void fetchFromRequest(Request request) throws ParseException
    {
        chooseMetricName = request.queryParams("chooseMetricName");
        chooseTopN = request.queryParams("chooseTopN");
        mysqlipaddress = request.queryParams("mysqlipaddress");
        mysqlport = request.queryParams("mysqlport");
        metricnamePath = request.queryParams("metricnamePath");
        metricdataPath = request.queryParams("metricdataPath");
        withPastTimeData = Integer.parseInt(request.queryParams("withpasttimedata")) * 60 + "";
        applicationId = request.queryParams("applicationid");
			Date d = new Date();

			endtime = format.format(d); // for ai-mysql
			starttime = format.format(new Date(d.getTime() - Integer.parseInt(withPastTimeData) * 1000));

			starttime = time2UnixTimeStamp(starttime);
			endtime = time2UnixTimeStamp(endtime);
    }
	private static String time2UnixTimeStamp(String input) throws ParseException{
		Date date = format.parse(input);
		long Temp = date.getTime() / 1000;
		return Temp + "";
	}

}
