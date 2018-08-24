package com.tansun.casetransfertool.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test4 {
	
	public static void main(String[] args) throws ParseException {
		//Date date = new Date("2017-01-20");	
		 /* String inTime = "2014-08-13 09:20:40";  
	        String outTime = transferFormat(inTime);  
	        System.out.println(outTime);//打印结果为2014-08-13  
	        */
		String D1 = dealDateFormat("YYYY-MM-DD");
		SimpleDateFormat uiDF = new SimpleDateFormat(D1);
		String D2 = dealDateFormat("YYYY年MM月DD日");
		SimpleDateFormat interDF = new SimpleDateFormat(D2);
		Date date = uiDF.parse("2017-12-05");
		String format = interDF.format(date);
		System.out.println(format);		
	        
	}

	private static String transferFormat(String inTime) throws ParseException {
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");  
        Date tempDate =null;  
        String outTime = null;  
        tempDate = s1.parse(inTime);  
        outTime = s2.format(s2.parse(s1.format(tempDate)));  
        return outTime;  
	}
	
	
	public static String dealDateFormat(String date) {
		
		if(date.contains("YYYY")){
			 date = date.replace("YYYY", "yyyy");
		}
		
		if(date.contains("DD")){
			 date = date.replace("DD", "dd");
		}
		
		if(date.contains(":MM")){
			 date = date.replace(":MM", ":mm");
		}
		
		if(date.contains(":SS")){
			 date = date.replace(":SS", ":ss");
		}
		
		return date;
		
	}
	
	
}
