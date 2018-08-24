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
* @Description: 文件拖拽的demo
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午9:13:55
*
 */
public class DropDragDemo {

	 public static void main(String[] args){  
	        JFrame frame = new JFrame("文件拖拽Demo");  
	        frame.setSize(500, 400);  
	        frame.setResizable(false);  
	        frame.setLocationRelativeTo(null);  
	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	  
	        JPanel panel = new JPanel();  
	        panel.setBorder(BorderFactory.createTitledBorder("文件拖拽演示"));  
	  
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
