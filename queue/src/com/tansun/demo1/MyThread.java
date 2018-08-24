package com.tansun.demo1;

import java.util.HashMap;
import java.util.Map;

public class MyThread implements Runnable {
	
	
	private test1 test1;
	
	
	public MyThread(com.tansun.demo1.test1 test1) {
		this.test1 = test1;
	}

	

	@Override
	public void run() {
	Map<String,String> dataMap	= new HashMap<String,String>();
	dataMap.put("1","ÕÅÈı");
	
	
	String string = dataMap.get("1");
	test1.setData(string);

	}

}