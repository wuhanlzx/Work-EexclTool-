package com.tansun.casetransfertool.test;

public class Test12 {
	public static void main(String[] args) {
		/*String s="案例编号:取款后冲正（冲正有两去两回）201712180001";
		String baseSeq = s.substring(0, s.lastIndexOf("）")+1);
		System.out.println(baseSeq);*/
		
/*		String s="s2";
		String substring = s.substring(1);
		System.out.println(substring);*/
		String caseCode="案例编号:取款后冲正（冲正有两去两回）201712180001";
		double	baseSeq =Double .parseDouble(caseCode.substring(caseCode.lastIndexOf("）") + 1, caseCode.length()));
		System.out.println(baseSeq);
	}
}
