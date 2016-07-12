package com.oneapm.research.correlation.web;

import spark.Request;

import java.text.ParseException;

/**
 * Created by ruan on 16-7-12.
 */
public class Config {
    public static String ChooseMetricName = "CPU";

    public static String ChooseTopN = "5";

    public static String mysqlipaddress="jdbc:mysql://10.128.7.138";

    public static String mysqlport="3306";

    public static String mysqlpath="/metricname";

    public static String druidipaddress="jdbc:ONEAPM://10.128.7.136";

    public static String druidport="8082";

    public static String druidpath="/druid/v2";

    public static String withPastTimeData = "10800";

    public static String applicationId = "10";


    public static void fetchFromRequest(Request request) throws ParseException
    {
        ChooseMetricName = request.queryParams("ChooseMetricName");
        ChooseTopN = request.queryParams("ChooseTopN");
        mysqlipaddress = request.queryParams("mysqlipaddress");
        mysqlport = request.queryParams("mysqlport");
        mysqlpath = request.queryParams("mysqlpath");
        druidipaddress = request.queryParams("druidipaddress");
        druidport = request.queryParams("druidport");
        druidpath = request.queryParams("druidpath");
        withPastTimeData = Integer.parseInt(request.queryParams("withpasttimedata")) * 60 + "";
        applicationId = request.queryParams("applicationid");
    }
}
