package com.tansun.casetransfertool.test;

import com.tansun.casetransfertool.utils.PinYin4JUtils;

public class PinYin4JTest {
	
	public static void main(String[] args) {
		String s="你好么你快乐么";
		
		String stringPinYin = PinYin4JUtils.ToPinyin(s);
		System.out.println(stringPinYin);
	}
	
	
}
