package com.tansun.casetransfertool.test;

import java.text.DecimalFormat;

public class Test2 {
	public static void main(String[] args) {
			String value="100.00";
			float parseFloat = Float.parseFloat(value); //�����Ƚ��ַ�ת��Ϊ�ַ���
			DecimalFormat df = new DecimalFormat("0.00");//ת����ʽ
			String format = df.format(parseFloat);
			System.out.println(format);
	}
}
