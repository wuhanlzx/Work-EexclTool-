package com.tansun;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
	public static void main(String[] args) {
		 ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		 	
		 	q.offer(null);
		 	
		 	  //从头获取元素,删除该元素
		    System.out.println(q.poll());
		   
		 
		 
		 
		 
	}
}
