package com.tansun.casetransfertool.test;

import com.tansun.casetransfertool.utils.PinYin4JUtils;

public class PinYin4JTest {
	
	public static void main(String[] args) {
		String s="���ô�����ô";
		
		String stringPinYin = PinYin4JUtils.ToPinyin(s);
		System.out.println(stringPinYin);
	}
	
	
}
