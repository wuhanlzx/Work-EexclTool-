package com.tansun.casetransfertool.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test9 {
	
	
	
	
	
		public static void main(String[] args) {
			
				String s = "����������";
			
			
				if(s.trim().contains("������")){
					s=s.replaceAll("������", "").trim();
				}
			
				System.out.println(s);
		}
}
