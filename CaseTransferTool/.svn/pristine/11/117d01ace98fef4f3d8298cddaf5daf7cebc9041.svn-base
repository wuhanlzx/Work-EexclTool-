package com.tansun.casetransfertool.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * 
* @ClassName: TransferWindow
* @Description: 这种布局无法去调整每一格的大小  不推荐使用
* @company com.tansun
* @author lzx
* @date 2017-12-4 上午10:00:21
*
 */
public class TransferWindow extends JFrame {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	JButton transferButton = new JButton("转换");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;

	public TransferWindow() {

		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		add(transferButton, BorderLayout.SOUTH);
		setSize(750, 250);
		GridLayout gridLayout = new GridLayout(3, 3, 10, 10); // 3行3列垂直水平的都为10
		panel.setLayout(gridLayout);
		panel.setBounds(20, 20, 700, 140);
		
		JLabel label = new JLabel("UI案例:");
		label.setSize(80, 20);
		panel.add(label);

		UICaseInput = new JTextField();
		UICaseInput.setColumns(51);
		panel.add(UICaseInput);
		
		JButton selectListBtn = new JButton("浏览");
		panel.add(selectListBtn);
		
		JLabel label2 = new JLabel("接口案例模板:");
		panel.add(label2);

		interfaceCaseInput = new JTextField();
		interfaceCaseInput.setColumns(47);
		panel.add(interfaceCaseInput);

		JButton selectListBtn2 = new JButton("浏览");
		panel.add(selectListBtn2);
		
		JLabel label3 = new JLabel("字段对应关系:");
		panel.add(label3);

		fieldRelationInput = new JTextField();
		fieldRelationInput.setColumns(47);
		panel.add(fieldRelationInput);

		JButton selectListBtn3 = new JButton("浏览");
		panel.add(selectListBtn3);
	
		transferButton.setBounds(300, 170, 60, 30);
		transferButton.setHorizontalAlignment(SwingConstants.CENTER);
		transferButton.setEnabled(false);   
		
		pack();
        setTitle("UI/接口案例转换工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		
	}
	
	public static void main(String[] args) {
		TransferWindow window = new TransferWindow();
		window.setVisible(true);
	}
}
