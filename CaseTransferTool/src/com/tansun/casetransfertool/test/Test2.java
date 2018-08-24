package com.tansun.casetransfertool.test;

import java.text.DecimalFormat;

public class Test2 {
	public static void main(String[] args) {
			String value="100.00";
			float parseFloat = Float.parseFloat(value); //必须先将字符转化为字符型
			DecimalFormat df = new DecimalFormat("0.00");//转换格式
			String format = df.format(parseFloat);
			System.out.println(format);
	}
}
