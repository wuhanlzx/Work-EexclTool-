package com.tansun.casetransfertool.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成时间戳和四位的序列号
 */
public class Test13 {
	
	public static void main(String[] args) {
		
		SimpleDateFormat uiDF = new SimpleDateFormat("yyyyMMdd");
		
		String format = uiDF.format(new Date());
		
		//System.out.println(format);
		
		
		
		
	}
}
