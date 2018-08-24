package com.tansun.casetransfertool.utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class MyTransferHandler extends TransferHandler {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField textField;
	
	public MyTransferHandler(JTextField textField) {
		this.textField=textField;
	}

	@Override
	public boolean importData(JComponent comp, Transferable t) {
		try {
			Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

			if (o instanceof List) {
				List fileList = (List) o;
				if (fileList.size() > 1) {
					JOptionPane.showMessageDialog(new JFrame(), "不要选择多个文件!");

				} else {
					String filepath = o.toString();
					if (filepath.startsWith("[")) {
						filepath = filepath.substring(1);
					}
					if (filepath.endsWith("]")) {
						filepath = filepath.substring(0, filepath.length() - 1);
					}
					if (filepath.endsWith(".xls") || filepath.endsWith(".xlsx")) {
						textField.setText(filepath);
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "请选择excel文件!");
					}
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] flavors) {
		for (int i = 0; i < flavors.length; i++) {
			if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}



}
