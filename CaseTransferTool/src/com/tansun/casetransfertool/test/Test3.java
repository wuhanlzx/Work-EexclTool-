package com.tansun.casetransfertool.test;
/**
 * 
* @ClassName: Test3
* @Description: �ַ���12λ��������ಹ��
* @company com.tansun
* @author lzx
* @date 2017-12-1 ����3:23:13
*
 */
public class Test3 {
		public static void main(String[] args) {
			//���ֻ����int���͵���  ���ʺ�
			/*int s=1;
					
			String str = String.format("%010d", s);      
		    System.out.println(str); */	
			
			String format="21555555500.00";
		/*	
			String str="000000000000";
			
			String substring = str.substring(0,12-s.length());
			String newStr=substring+s;
			System.out.println(newStr);*/
			String[] split = format.split("\\.");
			String newValue=split[0]+split[1];
			if(newValue.length()<12){
			String str="000000000000";
			newValue=str.substring(0, 12-newValue.length())+newValue;
			}
			System.out.println(newValue);
		}
		
		
		
}
