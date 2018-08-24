package com.tansun.casetransfertool.test;

public class Test15 {
	
	public static void main(String[] args) {
		String s="案例编号:取款后冲正（冲正有两去两回）201712180001";
		
		String s2 = s.substring(s.indexOf(":")+1, s.length()-12);
		
		System.out.println(s2);
	}
	
	
}
