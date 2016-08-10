package com.oneapm.research.correlation;

import com.oneapm.research.correlation.web.model.IndependentNum;
import java.util.ArrayList;
import java.lang.Math;



/** 
 * @author alan-king 
 * 
 */  
public class PearsonCoefficientCalculate {  
      
	public static IndependentNum calculatePearson(ArrayList<Double> xList, ArrayList<Double> yList){
	    IndependentNum timeserieResult = new IndependentNum();
        double denominator = calculateDenominator(xList, yList);
        if(denominator < 1e-4) {
            timeserieResult.coefficient = Double.NaN;
        }
        else
		    timeserieResult.coefficient = calculateNumerator(xList, yList)/denominator;
        timeserieResult.timeserie = yList;
        return timeserieResult;
	}
	
    public static double calculateDenominator(ArrayList<Double> xList, ArrayList<Double> yList){  
        double standardDifference = 0.0;  
        int size = xList.size();  
        double xAverage = 0.0;  
        double yAverage = 0.0;  
        double xException = 0.0;  
        double yException = 0.0;  
        double temp = 0.0;  
        for(int i=0;i<size;i++){  
            temp += xList.get(i);  
        }  
        xAverage = temp/size;  
          
        for(int i=0;i<size;i++){  
            temp += yList.get(i);  
        }  
        yAverage = temp/size;  
          
        for(int i=0;i<size;i++){  
            xException += Math.pow(xList.get(i)-xAverage, 2);  
            yException += Math.pow(yList.get(i)-yAverage, 2);  
        }  
        //calculate denominator of   
        System.out.println(Math.sqrt(xException*yException));
        return standardDifference = Math.sqrt(xException*yException);
    }
    
    public static double calculateNumerator(ArrayList<Double> xList, ArrayList<Double> yList){  
        double result =0.0;  
        double xAverage = 0.0;  
        double temp = 0.0;  
          
        int xSize = xList.size();  
        for(int x=0;x<xSize;x++){  
            temp += xList.get(x);  
        }  
        xAverage = temp/xSize;  
          
        double yAverage = 0.0;  
        temp = 0.0;  
        int ySize = yList.size();  
        for(int x=0;x<ySize;x++){  
            temp += yList.get(x);  
        }  
        yAverage = temp/ySize;  
          
        //double sum = 0.0;  
        for(int x=0;x<xSize;x++){  
            result+=(xList.get(x)-xAverage)*(yList.get(x)-yAverage);  
        }  
        return Math.abs(result);
    }
}  