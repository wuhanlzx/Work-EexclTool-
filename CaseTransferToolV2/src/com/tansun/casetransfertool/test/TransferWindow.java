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
* @Description: ���ֲ����޷�ȥ����ÿһ��Ĵ�С  ���Ƽ�ʹ��
* @company com.tansun
* @author lzx
* @date 2017-12-4 ����10:00:21
*
 */
public class TransferWindow extends JFrame {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	JButton transferButton = new JButton("ת��");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;

	public TransferWindow() {

		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		add(transferButton, BorderLayout.SOUTH);
		setSize(750, 250);
		GridLayout gridLayout = new GridLayout(3, 3, 10, 10); // 3��3�д�ֱˮƽ�Ķ�Ϊ10
		panel.setLayout(gridLayout);
		panel.setBounds(20, 20, 700, 140);
		
		JLabel label = new JLabel("UI����:");
		label.setSize(80, 20);
		panel.add(label);

		UICaseInput = new JTextField();
		UICaseInput.setColumns(51);
		panel.add(UICaseInput);
		
		JButton selectListBtn = new JButton("���");
		panel.add(selectListBtn);
		
		JLabel label2 = new JLabel("�ӿڰ���ģ��:");
		panel.add(label2);

		interfaceCaseInput = new JTextField();
		interfaceCaseInput.setColumns(47);
		panel.add(interfaceCaseInput);

		JButton selectListBtn2 = new JButton("���");
		panel.add(selectListBtn2);
		
		JLabel label3 = new JLabel("�ֶζ�Ӧ��ϵ:");
		panel.add(label3);

		fieldRelationInput = new JTextField();
		fieldRelationInput.setColumns(47);
		panel.add(fieldRelationInput);

		JButton selectListBtn3 = new JButton("���");
		panel.add(selectListBtn3);
	
		transferButton.setBounds(300, 170, 60, 30);
		transferButton.setHorizontalAlignment(SwingConstants.CENTER);
		transferButton.setEnabled(false);   
		
		pack();
        setTitle("UI/�ӿڰ���ת������");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		
	}
	
	public static void main(String[] args) {
		TransferWindow window = new TransferWindow();
		window.setVisible(true);
	}
}
