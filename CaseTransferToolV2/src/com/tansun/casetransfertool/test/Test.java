package com.tansun.casetransfertool.test;

public class Test {
	
	public static void main(String[] args) {
		String sb=	"type(类型):常量|2";
			
		String[] inputData = sb.split(":");
		String names = inputData[0];
		int enNameIndex = names.indexOf("(");
		String s1 = names.substring(0, enNameIndex);
		String s2 = names.substring(enNameIndex+1,names.length()-1);
		String values = inputData[1];
		int valueIndex = values.indexOf("|");	
		String s3 = values.substring(0,valueIndex);
		String s4 = values.substring(valueIndex+1,values.length());
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
	}
}
