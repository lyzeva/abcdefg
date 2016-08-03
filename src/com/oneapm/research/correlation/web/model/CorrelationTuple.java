package com.oneapm.research.correlation.web.model;

import java.util.ArrayList;

public class CorrelationTuple{
	public int metric_id = 0;
	public String metric_name = "";
	public ArrayList<Double> coefficient;
	public ArrayList<ArrayList<Double>> num;
}