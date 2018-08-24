package com.tansun.casetransfertool;

import javax.swing.UIManager;

import com.tansun.casetransfertool.view.TransferWindow;
/**
 * 
* @ClassName: ExpMain
* @Description: �������
* @company com.tansun
* @author lzx
* @date 2017-12-6 ����10:52:26
*
 */
public class ExpMain {

	public static void main(String[] args) {
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName(); // ����swing����ʽ���óɵ�ǰϵͳ����ʽ

		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransferWindow window = new TransferWindow();
		window.open();
	}

}
