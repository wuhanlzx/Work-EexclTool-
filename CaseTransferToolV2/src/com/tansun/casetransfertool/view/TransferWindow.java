package com.tansun.casetransfertool.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.Workbook;

import com.tansun.casetransfertool.bean.Case;
import com.tansun.casetransfertool.bean.TransferRelation;
import com.tansun.casetransfertool.service.InterfaceCaseService;
import com.tansun.casetransfertool.service.TransferRelationService;
import com.tansun.casetransfertool.service.UICaseService;
import com.tansun.casetransfertool.utils.MyTransferHandler;

/**
 * 
 * @ClassName: TransferWindow
 * @Description: 转换文件选择窗口
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 上午10:52:01
 * 
 */
public class TransferWindow {

	JButton transferButton = new JButton("转换");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;
	private JFrame frame;
	private JFileChooser chooser = new JFileChooser();
	public void open() {
		frame = new JFrame();
		frame.setTitle("UI/接口案例转换工具");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		// setLayout(null);
		frame.setPreferredSize(new Dimension(750, 250));
		frame.setVisible(true);
		frame.pack();
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = frame.getHeight(); // 获取窗口高度
		int width = frame.getWidth(); // 获取窗口宽度
		frame.setLocation(screenWidth - width / 2, screenHeight - height / 2);// 将窗口设置到屏幕的中部
	}

	public void init() {

		panel.setBorder(BorderFactory.createTitledBorder("输入"));
		panel.setLayout(new GridBagLayout());
		panel.setBounds(20, 20, 700, 140);

		GridBagConstraints c = new GridBagConstraints();
		JLabel label = new JLabel("UI案例:");
		c.gridx = 0; // 组件的横向坐标； 0行0列
		c.gridy = 0; // 组件的纵向坐标；

		c.gridwidth = 2; // 该方法是设置组件水平所占用的格子数占用的列数，如果为0，就说明该组件是该行的最后一个
		// c.gridheight = 1;//组件的横向宽度，也就是指组件占用的列数
		// c.fill = GridBagConstraints.HORIZONTAL;
		// //加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
		// NONE：不调整组件大小。
		// HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
		// VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
		// BOTH：使组件完全填满其显示区域。
		c.weightx = 0;// 该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
		c.weighty = 0;// 该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
		panel.add(label, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		UICaseInput = new JTextField();
		UICaseInput.setTransferHandler(new MyTransferHandler(UICaseInput));

		panel.add(UICaseInput, c);

		c.gridx = 9;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn = new JButton("浏览");
		panel.add(selectListBtn, c);
		selectListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("选择文件");
				// 设置是否可以多选
				jfc.setMultiSelectionEnabled(false);
				// 设置隐藏文件可见
				jfc.setFileHidingEnabled(true);
				// 适合所有的文件过滤器
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
				jfc.setFileFilter(new FileNameExtensionFilter("Excel文件(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
				if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(frame)) {
					File file = jfc.getSelectedFile();
					UICaseInput.setText(file.getAbsolutePath());
				}
			}
		});

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label2 = new JLabel("接口案例模板:");
		panel.add(label2, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 7;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		interfaceCaseInput = new JTextField();
		// 支持文件的拖拽功能
		interfaceCaseInput.setTransferHandler(new MyTransferHandler(interfaceCaseInput));
		panel.add(interfaceCaseInput, c);

		c.gridx = 9;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn2 = new JButton("浏览");
		panel.add(selectListBtn2, c);
		selectListBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("选择文件");
				// 设置是否可以多选
				jfc.setMultiSelectionEnabled(false);
				// 设置隐藏文件可见
				jfc.setFileHidingEnabled(true);
				// 适合所有的文件过滤器
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
				jfc.setFileFilter(new FileNameExtensionFilter("Excel文件(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
				if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(frame)) {
					File file = jfc.getSelectedFile();
					interfaceCaseInput.setText(file.getAbsolutePath());
				}
			}
		});

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("字段对应关系:");
		panel.add(label3, c);

		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 7;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		fieldRelationInput = new JTextField();
		fieldRelationInput.setTransferHandler(new MyTransferHandler(fieldRelationInput));
		panel.add(fieldRelationInput, c);

		c.gridx = 9;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn3 = new JButton("浏览");
		panel.add(selectListBtn3, c);
		selectListBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("选择文件");
				// 设置是否可以多选
				jfc.setMultiSelectionEnabled(false);
				// 设置隐藏文件可见
				jfc.setFileHidingEnabled(true);
				// 适合所有的文件过滤器
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
				jfc.setFileFilter(new FileNameExtensionFilter("Excel文件(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
				if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(frame)) {
					File file = jfc.getSelectedFile();
					fieldRelationInput.setText(file.getAbsolutePath());
				}
			}
		});

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		c.weightx = 1;
		c.weighty = 0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		JPanel jp1 = new JPanel();
		panel.add(jp1, c);

		c.gridx = 4;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.insets = new Insets(20, 0, 0, 0);
		// c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(transferButton, c);
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					transferUICaseToInterfaceCase();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		c.gridx = 5;
		c.gridy = 3;
		c.gridwidth = 4;
		c.weightx = 1;
		c.weighty = 0;
		// c.fill = GridBagConstraints.HORIZONTAL;
		JPanel jp2 = new JPanel();
		panel.add(jp2, c);

		frame.getContentPane().add(panel);
	}

	public void transferUICaseToInterfaceCase() throws Exception {
		// 先去读取三个excel文件的内容
		String UICasePath = UICaseInput.getText();
		String interCasePath = interfaceCaseInput.getText();
		String relationPath = fieldRelationInput.getText();

		if ("".equals(UICasePath)) {
			JOptionPane.showMessageDialog(frame, "请选择UI案例");
			return;
		}

		if ("".equals(interCasePath)) {
			JOptionPane.showMessageDialog(frame, "请选择接口案例");
			return;
		}

		if ("".equals(relationPath)) {
			JOptionPane.showMessageDialog(frame, "请选择字段对应关系");
			return;
		}
		List<Case> UICases = null;
		Workbook wb = null;
		Map<String, TransferRelation> transferMap = null;
		// 获取数据
		try {
			UICases = UICaseService.importCaseToObject(frame, UICasePath);
			transferMap = TransferRelationService.importCaseToObject(frame, relationPath);
			wb = InterfaceCaseService.importCaseToObject(frame, interCasePath);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "数据读取失败!");
			return;
		}
		// System.out.println(transferMap);
		File file = getFile(); // 这个是文件保存的路径
		if(file==null){
			return;
		}
		try {
			InterfaceCaseService.createNewInterExcel(UICases, wb, transferMap, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "转换文件被占用!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "转换失败!");
			return;
		}
		JOptionPane.showMessageDialog(frame, "转换成功!");
		return;

	}

	/**
	 * 弹出文件保存窗口并且获取文路径
	 */
	public File getFile() {
		File file = null;
		// 构造文件保存对话框
		chooser.setCurrentDirectory(chooser.getSelectedFile()); //文件选择器默认打开上次选择的位置
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("另存为:");
		//chooser.setCurrentDirectory(new File(""))
		chooser.setFileFilter(new FileNameExtensionFilter("Excel文件(*.xls)", "xls"));
		/*
		 * try { UIManager.setLookAndFeel(
		 * "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		// SwingUtilities.updateComponentTreeUI(chooser);
		int option = chooser.showSaveDialog(null);

		if (option == JFileChooser.APPROVE_OPTION) {
			// 假如用户选择了保存
			file = chooser.getSelectedFile();

			String fname = chooser.getName(file); // 从文件名输入框中获取文件名
			if (fname.indexOf(".xls") == -1) {
				file = new File(chooser.getCurrentDirectory(), fname + ".xls");
				// System.out.println(file);
			}

		}
		return file;
	}

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { //TransferWindow window = new
	 * TransferWindow(); } }); }
	 */
}
