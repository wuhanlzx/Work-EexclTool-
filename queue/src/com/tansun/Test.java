package com.tansun;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
	public static void main(String[] args) {
		 ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		 	
		 	q.offer(null);
		 	
		 	  //��ͷ��ȡԪ��,ɾ����Ԫ��
		    System.out.println(q.poll());
		   
		 
		 
		 
		 
	}
}
