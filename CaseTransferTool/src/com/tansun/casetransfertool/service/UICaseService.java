package com.tansun.casetransfertool.service;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tansun.casetransfertool.bean.Case;
import com.tansun.casetransfertool.bean.Trade;

/**
 * 
 * @ClassName: UICaseService
 * @Description: UI����ҵ���߼�������
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 ����10:51:33
 * 
 */
public class UICaseService extends BaseService {

	public static List<Case> importCaseToObject(JFrame frame, String fileName) throws IOException {

		// -------------------------------֧��2003��2007���excel------------------------
		FileInputStream in = new FileInputStream(fileName);// ��ȡ�ϴ��ļ���
		DataInputStream fins = new DataInputStream(in); // �������ݵ�������

		// excel�����ͣ�֧��2003��2007���excel
		boolean excelType = fileName.endsWith(".xls") == true ? true : false;

		List<Case> cases = getInfo(frame, fins, excelType);

		return cases;
	}

	public static List<Case> getInfo(JFrame frame, DataInputStream fins, boolean excelType) throws IOException {
		Workbook wb = null;
		// Cell cell = null;
		List<Case> cases = new ArrayList<Case>();
		if (excelType) {
			wb = new HSSFWorkbook(fins);

		} else {
			wb = new XSSFWorkbook(fins);
		}
		// ����sheetҳ
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			Sheet st = wb.getSheetAt(sheetIndex); // ��ȡ��ǰ��sheetҳ
			Row firstRow = st.getRow(0); // ��ȡ����
			boolean b = validateExcel(firstRow, frame); // У������
			if (b) {
				throw new RuntimeException("UI����Excel�ļ�У��ʧ��!");
			}
			// ֱ�Ӵӵڶ��п�ʼ ��һ�е������Ǳ�ͷ����Ҫ
			// �������е���
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {

				Row row = st.getRow(rowIndex);
				// һ�����ݾ���һ������
				if (row == null) {
					continue;
				}

				boolean isMerge = isMergedRegionFirstRow(st, rowIndex, 0);
				// �ж��Ƿ���кϲ���Ԫ��
				Case UICase = null;
				if (isMerge) {
					UICase = new Case();
					UICase.setCaseCode(getExcelCellValue(row.getCell(0))); // �������
					UICase.setSystemNameAndVersion(getExcelCellValue(row.getCell(1))); // ϵͳ���ư汾
					UICase.setFunctionModule(getExcelCellValue(row.getCell(2))); // ����ģ��
					UICase.setFunctionName(getExcelCellValue(row.getCell(3))); // ��������
					UICase.setCaseType(getExcelCellValue(row.getCell(11))); // ��������
					UICase.setCaseNature(getExcelCellValue(row.getCell(12))); // ��������
					UICase.setPriorityLevel(getExcelCellValue(row.getCell(13))); // ���ȼ�
					UICase.setCreater(getExcelCellValue(row.getCell(14))); // ������
					UICase.setCreateDate(getExcelCellValue(row.getCell(15))); // ��������
					cases.add(UICase);
				} else {
					UICase = cases.get(cases.size() - 1);
				}
				// ������
				Trade trade = new Trade();
				trade.setTradeCode(getExcelCellValue(row.getCell(4)));

				// ������
				String value = getExcelCellValue(row.getCell(7));
				Map<String, String> inputItems = new HashMap<String, String>();
				String itemExcels[] = value.split("\\r?\\n"); // ���ڶ��е�����ʹ�û��з����зָ�
				for (String inputDate : itemExcels) {
					if (inputDate.endsWith("��")) {
						inputDate = inputDate.substring(0, inputDate.indexOf("��"));
						inputItems.put(inputDate, "");
					} else {
						String[] input = inputDate.split("��"); // ʹ��: ���зָ�
						inputItems.put(input[0], input[1]);
					}
				}
				trade.setInputItems(inputItems);
				// ��������
				trade.setOperationStep(getExcelCellValue(row.getCell(8)));
				UICase.addTrade(trade);// ���������ӵ�������
			}

		}
		return cases;
	}

	public static boolean validateExcel(Row firstRow, JFrame frame) {
		boolean b = false;
		String code = getCellValue(firstRow, 0); // ��һ�еĵ�һ������
		if (!code.equals("�������*")) {
			String seq = excelColIndexToStr(1);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ�������*");
			b = true;
		}

		String system = getCellValue(firstRow, 1); // ��һ�еĵڶ�������
		if (!system.equals("ϵͳ����+�汾")) {
			String seq = excelColIndexToStr(2);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪϵͳ����+�汾");
			b = true;
			;
		}

		String function = getCellValue(firstRow, 2); // ��һ�еĵ���������
		if (!function.equals("����ģ��")) {
			String seq = excelColIndexToStr(3);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ����ģ��");
			b = true;
			;
		}

		String functionName = getCellValue(firstRow, 3); // ��һ�еĵ��ĸ�����
		if (!functionName.equals("��������*")) {
			String seq = excelColIndexToStr(4);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ��������*");
			b = true;
			;
		}

		String tradeCode = getCellValue(firstRow, 4); // ��һ�еĵ��ĸ�����
		if (!tradeCode.equals("������")) {
			String seq = excelColIndexToStr(5);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ������");
			b = true;
			;
		}

		String input = getCellValue(firstRow, 7); // ��һ�еĵڰ˸�����
		if (!input.equals("������ֵ")) {
			String seq = excelColIndexToStr(8);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ������ֵ");
			b = true;
			;
		}

		String step = getCellValue(firstRow, 8); // ��һ�еĵ��ĸ�����
		if (!step.equals("��������*")) {
			String seq = excelColIndexToStr(9);
			JOptionPane.showMessageDialog(frame, "UI��������" + seq + "��ӦΪ��������*");
			b = true;
			;
		}

		return b;
	}

	/**
	 * �ж�ָ���ĵ�Ԫ���Ƿ��Ǻϲ���Ԫ��
	 * 
	 * @param sheet
	 * @param row
	 *            ���±�
	 * @param column
	 *            ���±�
	 * @return
	 */
	private static boolean isMergedRegionFirstRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			// int lastRow = range.getLastRow();
			if (row == firstRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ��ȡ�ϲ���Ԫ���ֵ
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getExcelCellValue(fCell);
				}
			}
		}

		return null;
	}

}