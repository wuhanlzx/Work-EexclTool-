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
 * @Description: ת���ļ�ѡ�񴰿�
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 ����10:52:01
 * 
 */
public class TransferWindow {

	JButton transferButton = new JButton("ת��");
	JPanel panel = new JPanel();
	private JTextField UICaseInput;
	private JTextField interfaceCaseInput;
	private JTextField fieldRelationInput;
	private JFrame frame;
	private JFileChooser chooser = new JFileChooser();
	public void open() {
		frame = new JFrame();
		frame.setTitle("UI/�ӿڰ���ת������");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		// setLayout(null);
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
		c.gridx = 0; // ����ĺ������ꣻ 0��0��
		c.gridy = 0; // ������������ꣻ

		c.gridwidth = 2; // �÷������������ˮƽ��ռ�õĸ�����ռ�õ����������Ϊ0����˵��������Ǹ��е����һ��
		// c.gridheight = 1;//����ĺ����ȣ�Ҳ����ָ���ռ�õ�����
		// c.fill = GridBagConstraints.HORIZONTAL;
		// //�ӿ������ʹ����ˮƽ��������������ʾ���򣬵��ǲ��ı�߶ȡ�
		// NONE�������������С��
		// HORIZONTAL���ӿ������ʹ����ˮƽ��������������ʾ���򣬵��ǲ��ı�߶ȡ�
		// VERTICAL���Ӹ������ʹ���ڴ�ֱ��������������ʾ���򣬵��ǲ��ı��ȡ�
		// BOTH��ʹ�����ȫ��������ʾ����
		c.weightx = 0;// �÷����������ˮƽ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮��
		c.weighty = 0;// �÷������������ֱ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮��
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
		JButton selectListBtn = new JButton("���");
		panel.add(selectListBtn, c);
		selectListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("ѡ���ļ�");
				// �����Ƿ���Զ�ѡ
				jfc.setMultiSelectionEnabled(false);
				// ���������ļ��ɼ�
				jfc.setFileHidingEnabled(true);
				// �ʺ����е��ļ�������
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// �趨ֻ��ѡ���ļ�
				jfc.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
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
		JLabel label2 = new JLabel("�ӿڰ���ģ��:");
		panel.add(label2, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 7;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		interfaceCaseInput = new JTextField();
		// ֧���ļ�����ק����
		interfaceCaseInput.setTransferHandler(new MyTransferHandler(interfaceCaseInput));
		panel.add(interfaceCaseInput, c);

		c.gridx = 9;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		// c.fill = GridBagConstraints.BOTH;
		JButton selectListBtn2 = new JButton("���");
		panel.add(selectListBtn2, c);
		selectListBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("ѡ���ļ�");
				// �����Ƿ���Զ�ѡ
				jfc.setMultiSelectionEnabled(false);
				// ���������ļ��ɼ�
				jfc.setFileHidingEnabled(true);
				// �ʺ����е��ļ�������
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// �趨ֻ��ѡ���ļ�
				jfc.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
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
		JLabel label3 = new JLabel("�ֶζ�Ӧ��ϵ:");
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
		JButton selectListBtn3 = new JButton("���");
		panel.add(selectListBtn3, c);
		selectListBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("ѡ���ļ�");
				// �����Ƿ���Զ�ѡ
				jfc.setMultiSelectionEnabled(false);
				// ���������ļ��ɼ�
				jfc.setFileHidingEnabled(true);
				// �ʺ����е��ļ�������
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// �趨ֻ��ѡ���ļ�
				jfc.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls,*.xlsx)", new String[] { "xlsx", "xls" }));
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
		// ��ȥ��ȡ����excel�ļ�������
		String UICasePath = UICaseInput.getText();
		String interCasePath = interfaceCaseInput.getText();
		String relationPath = fieldRelationInput.getText();

		if ("".equals(UICasePath)) {
			JOptionPane.showMessageDialog(frame, "��ѡ��UI����");
			return;
		}

		if ("".equals(interCasePath)) {
			JOptionPane.showMessageDialog(frame, "��ѡ��ӿڰ���");
			return;
		}

		if ("".equals(relationPath)) {
			JOptionPane.showMessageDialog(frame, "��ѡ���ֶζ�Ӧ��ϵ");
			return;
		}
		List<Case> UICases = null;
		Workbook wb = null;
		Map<String, TransferRelation> transferMap = null;
		// ��ȡ����
		try {
			UICases = UICaseService.importCaseToObject(frame, UICasePath);
			transferMap = TransferRelationService.importCaseToObject(frame, relationPath);
			wb = InterfaceCaseService.importCaseToObject(frame, interCasePath);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "���ݶ�ȡʧ��!");
			return;
		}
		// System.out.println(transferMap);
		File file = getFile(); // ������ļ������·��
		if(file==null){
			return;
		}
		try {
			InterfaceCaseService.createNewInterExcel(UICases, wb, transferMap, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "ת���ļ���ռ��!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "ת��ʧ��!");
			return;
		}
		JOptionPane.showMessageDialog(frame, "ת���ɹ�!");
		return;

	}

	/**
	 * �����ļ����洰�ڲ��һ�ȡ��·��
	 */
	public File getFile() {
		File file = null;
		// �����ļ�����Ի���
		chooser.setCurrentDirectory(chooser.getSelectedFile()); //�ļ�ѡ����Ĭ�ϴ��ϴ�ѡ���λ��
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("���Ϊ:");
		//chooser.setCurrentDirectory(new File(""))
		chooser.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls)", "xls"));
		/*
		 * try { UIManager.setLookAndFeel(
		 * "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		// SwingUtilities.updateComponentTreeUI(chooser);
		int option = chooser.showSaveDialog(null);

		if (option == JFileChooser.APPROVE_OPTION) {
			// �����û�ѡ���˱���
			file = chooser.getSelectedFile();

			String fname = chooser.getName(file); // ���ļ���������л�ȡ�ļ���
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
