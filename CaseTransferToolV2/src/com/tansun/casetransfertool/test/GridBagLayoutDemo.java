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
import javax.swing.SwingConstants;

/**
 * 
* @ClassName: GridBagLayout
* @Description: 最灵活最复杂的布局管理器   
* @company com.tansun
* @author lzx
* @date 2017-12-4 上午10:01:36
*
 */
public class GridBagLayoutDemo extends JFrame {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 3856426818059747260L;
	JButton transferButton = new JButton("转换");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;
	
	public GridBagLayoutDemo() {
		
		
        setTitle("UI/接口案例转换工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		//setLayout(null);
		setPreferredSize(new Dimension(750, 250));
		setVisible(true);
		pack();
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight(); // 获取窗口高度
		int width = this.getWidth(); // 获取窗口宽度
		setLocation(screenWidth - width / 2, screenHeight - height / 2);// 将窗口设置到屏幕的中部
	}

	public void init() {
		
		panel.setBorder(BorderFactory.createTitledBorder("输入"));
		panel.setLayout(new GridBagLayout());
		panel.setBounds(20, 20, 700, 140);
		
		GridBagConstraints c = new GridBagConstraints();
		JLabel label = new JLabel("UI案例:");
		c.gridx=0; //组件的横向坐标；  0行0列
		c.gridy = 0; //组件的纵向坐标；
		
		c.gridwidth = 2; //该方法是设置组件水平所占用的格子数占用的列数，如果为0，就说明该组件是该行的最后一个
		//c.gridheight = 1;//组件的横向宽度，也就是指组件占用的列数
		//c.fill = GridBagConstraints.HORIZONTAL; //加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
		//NONE：不调整组件大小。
		//HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
		//VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
		//BOTH：使组件完全填满其显示区域。
		c.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间 
		c.weighty = 0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间 
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
		JButton selectListBtn = new JButton("浏览");
		panel.add(selectListBtn,c);
		
		c.gridx=0;
		c.gridy=1;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label2 = new JLabel("接口案例模板:");
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
		JButton selectListBtn2 = new JButton("浏览");
		panel.add(selectListBtn2,c);
		
		c.gridx=0;
		c.gridy=2;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("字段对应关系:");
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
		JButton selectListBtn3 = new JButton("浏览");
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
		
		this.getContentPane().add(panel);
	}
		
	
	
	public static void main(String[] args) {
		GridBagLayoutDemo demo = new GridBagLayoutDemo();
	}
	
	
}	
