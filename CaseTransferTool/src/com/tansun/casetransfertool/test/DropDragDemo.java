package com.tansun.casetransfertool.test;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * 
* @ClassName: DropDragDemo
* @Description: �ļ���ק��demo
* @company com.tansun
* @author lzx
* @date 2017-12-6 ����9:13:55
*
 */
public class DropDragDemo {

	 public static void main(String[] args){  
	        JFrame frame = new JFrame("�ļ���קDemo");  
	        frame.setSize(500, 400);  
	        frame.setResizable(false);  
	        frame.setLocationRelativeTo(null);  
	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	  
	        JPanel panel = new JPanel();  
	        panel.setBorder(BorderFactory.createTitledBorder("�ļ���ק��ʾ"));  
	  
	        JTextArea textArea = new DropDragSupportTextArea();  
	        JScrollPane jsp = new JScrollPane();  
	        jsp.setViewportView(textArea);  
	        textArea.setColumns(40);  
	        textArea.setRows(20);  
	          
	        panel.add(jsp);  
	        frame.add(panel);  
	        frame.setVisible(true);  
	    }  
}
