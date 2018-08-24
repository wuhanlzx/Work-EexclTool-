package com.tansun.casetransfertool.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test14 {
	
	private static String regex="ss[1-9]\\d*_";

	/*public static void main(String[] args) {
		//String regx="/^[s[1-9]\\d*_]*$/g";
		
		//String regex="^s\\/^[1-9]\\d*$/_";
		
		
	}*/
	
	public static void main(String args[]) {
		String str = "s20_   ";
		//String pattern = "^[s][1-9]\\d*$/_";
		//String pattern = "^[s][0-9]*[1-9][0-9]_*$";
		//String pattern = "^[s0-9_]+$";
		String pattern="^s[1-9][0-9]*_[\\s\\S]*";
		//String pattern="^[s][1-9][0-9]*[_]";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		System.out.println(m.matches());
	}
}
