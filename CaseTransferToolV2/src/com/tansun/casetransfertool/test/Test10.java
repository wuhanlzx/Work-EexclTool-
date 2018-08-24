package com.tansun.casetransfertool.test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test10 {
	
	
	JButton transferButton = new JButton("ת��");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;
	private JFrame frame;
	
	public Test10() {
		frame = new JFrame();
		frame.setTitle("UI/�ӿڰ���ת������");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		//setLayout(null);
		frame.setPreferredSize(new Dimension(750, 250));
		frame.setVisible(true);
		frame.pack();
		Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
		Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width / 2; // ��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height / 2; // ��ȡ��Ļ�ĸ�
		int height = frame.getHeight(); // ��ȡ���ڸ߶�
		int width = frame.getWidth(); // ��ȡ���ڿ��
		frame.setLocation(screenWidth - width / 2, screenHeight - height / 2);// ���������õ���Ļ���в�
	}
	
	public void init() {
		
		panel.setBorder(BorderFactory.createTitledBorder("����"));
		panel.setLayout(new GridBagLayout());
		panel.setBounds(20, 20, 700, 140);
		
		GridBagConstraints c = new GridBagConstraints();
		JLabel label = new JLabel("UI����:");
		c.gridx=0; //����ĺ������ꣻ  0��0��
		c.gridy = 0; //������������ꣻ
		
		c.gridwidth = 2; //�÷������������ˮƽ��ռ�õĸ�����ռ�õ����������Ϊ0����˵��������Ǹ��е����һ��
		//c.gridheight = 1;//����ĺ����ȣ�Ҳ����ָ���ռ�õ�����
		//c.fill = GridBagConstraints.HORIZONTAL; //�ӿ������ʹ����ˮƽ��������������ʾ���򣬵��ǲ��ı�߶ȡ�
		//NONE�������������С��
		//HORIZONTAL���ӿ������ʹ����ˮƽ��������������ʾ���򣬵��ǲ��ı�߶ȡ�
		//VERTICAL���Ӹ������ʹ���ڴ�ֱ��������������ʾ���򣬵��ǲ��ı��ȡ�
		//BOTH��ʹ�����ȫ��������ʾ����
		c.weightx = 0;//�÷����������ˮƽ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮�� 
		c.weighty = 0;//�÷������������ֱ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮�� 
		panel.add(label,c);
		
		
		c.gridx=2;
		c.gridy=0;		
		c.gridwidth=7;
		c.gridheight = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL; 
		UICaseInput = new JTextField();
		panel.add(UICaseInput,c);
		
		c.gridx=9;
		c.gridy=0;
		c.gridwidth=1;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn = new JButton("���");
		panel.add(selectListBtn,c);
		
		c.gridx=0;
		c.gridy=1;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label2 = new JLabel("�ӿڰ���ģ��:");
		panel.add(label2,c);
		
		c.gridx=2;
		c.gridy=1;
		c.gridwidth=7;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL; 
		interfaceCaseInput = new JTextField();
		panel.add(interfaceCaseInput,c);
		
		c.gridx=9;
		c.gridy=1;
		c.gridwidth=1;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn2 = new JButton("���");
		panel.add(selectListBtn2,c);
		
		c.gridx=0;
		c.gridy=2;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("�ֶζ�Ӧ��ϵ:");
		panel.add(label3,c);
		
		c.gridx=2;
		c.gridy=2;
		c.gridwidth=7;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL; 
		fieldRelationInput = new JTextField();
		panel.add(fieldRelationInput,c);
		
		c.gridx=9;
		c.gridy=2;
		c.gridwidth=1;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn3 = new JButton("���");
		panel.add(selectListBtn3,c);
	
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=4;
		c.weightx = 1;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JPanel jp1 = new JPanel();
		panel.add(jp1,c);
		
		c.gridx=4;
		c.gridy=3;
		c.gridwidth=1;
		c.weightx = 0;
		c.weighty = 0;
		c.insets=new Insets(20, 0, 0, 0);
		//c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(transferButton,c);
		
		c.gridx=5;
		c.gridy=3;
		c.gridwidth=4;
		c.weightx = 1;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JPanel jp2 = new JPanel();
		panel.add(jp2,c);
		
		frame.getContentPane().add(panel);
	}
		
	public static void main(String[] args) {
		Test10 demo = new Test10();
	}
}
