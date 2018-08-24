package com.tansun.demo1;

public class Test {
	public static void main(String[] args) {
		
		test1 test1 = new test1();
		
		MyThread myThread = new MyThread(test1);
		
		Thread thread = new Thread(myThread);
		thread.start();
	/*	try {
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println(test1.getData());
		
	}
}	
