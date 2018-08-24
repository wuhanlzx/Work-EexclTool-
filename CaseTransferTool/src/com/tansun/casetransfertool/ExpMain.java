package com.tansun.casetransfertool;

import javax.swing.UIManager;

import com.tansun.casetransfertool.view.TransferWindow;
/**
 * 
* @ClassName: ExpMain
* @Description: 程序入口
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午10:52:26
*
 */
public class ExpMain {

	public static void main(String[] args) {
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName(); // 设置swing的样式设置成当前系统的样式

		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransferWindow window = new TransferWindow();
		window.open();
	}

}
