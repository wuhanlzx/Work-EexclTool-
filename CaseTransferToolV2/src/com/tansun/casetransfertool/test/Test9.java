package com.tansun.casetransfertool.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test9 {
	
	
	
	
	
		public static void main(String[] args) {
			
				String s = "张三（反）";
			
			
				if(s.trim().contains("（反）")){
					s=s.replaceAll("（反）", "").trim();
				}
			
				System.out.println(s);
		}
}
