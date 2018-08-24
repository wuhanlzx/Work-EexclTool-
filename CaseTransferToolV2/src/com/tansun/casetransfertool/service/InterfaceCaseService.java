package com.tansun.casetransfertool.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tansun.casetransfertool.bean.Case;
import com.tansun.casetransfertool.bean.InputItem;
import com.tansun.casetransfertool.bean.InterfaceReport;
import com.tansun.casetransfertool.bean.Trade;
import com.tansun.casetransfertool.bean.TransferRelation;
import com.tansun.casetransfertool.utils.PinYin4JUtils;

/**
 * 
 * @ClassName: InterfaceCaseService
 * @Description: �ӿڰ���ҵ���߼�������
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 ����10:50:34
 * 
 */
public class InterfaceCaseService extends BaseService {

	private static String tradeSeq = "s";

	private static String baseSeq; // ������ų�ʼ

	private static String baseNature; // �������ʳ�ʼ

	private static String baseName; // �������Ƴ�ʼ

	private static String baseDesc; // ����������ʼ

	private static int reportStart;// ��������ʼ����

	private static int reportEnd;// �����Ľ�������
	
	private static String baseCaseName;//��������

	private static CellStyle cellStyle; 
	
	private static String oppoSeparator = "����_"; //��������������ֵ�Ľӿڷָ���
	
	private static String oppoName = "������"; //���������ַ�
	
	private static List<String> requestReport = new ArrayList<String>(); //��ȡ����ȥҪ�滻ֵ�������ĵ��ֶ���������
	
	
	
	public static Workbook importCaseToObject(JFrame frame, String fileName) throws Exception {
		// -------------------------------֧��2003��2007���excel------------------------
		FileInputStream in = new FileInputStream(fileName);// ��ȡ�ϴ��ļ���
		DataInputStream fins = new DataInputStream(in); // �������ݵ�������

		// excel�����ͣ�֧��2003��2007���excel
		boolean excelType = fileName.endsWith(".xls") == true ? true : false;

		Workbook wb = getInfo(frame, fins, excelType);

		return wb;
	}

	public static Workbook getInfo(JFrame frame, DataInputStream fins, boolean excelType) throws Exception {
		Workbook wb = null;
		List<Case> cases = new ArrayList<Case>();
		if (excelType) {
			wb = new HSSFWorkbook(fins);

		} else {
			wb = new XSSFWorkbook(fins);
		}

		return wb;
	}

	public static boolean validateExcel(Row firstRow, JFrame frame) {

		boolean b = false;
		String code = getCellValue(firstRow, 0); // ��һ�еĵ�һ������
		if (!code.equals("�������")) {
			b = true;
			String seq = excelColIndexToStr(1);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ�������");
		}

		String system = getCellValue(firstRow, 1); // ��һ�еĵڶ�������
		if (!system.equals("��������")) {
			b = true;
			String seq = excelColIndexToStr(2);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ��������");
		}

		String function = getCellValue(firstRow, 2); // ��һ�еĵ���������
		if (!function.equals("��������")) {
			b = true;
			String seq = excelColIndexToStr(3);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ��������");
		}

		String functionName = getCellValue(firstRow, 3); // ��һ�еĵ��ĸ�����
		if (!functionName.equals("ҵ������")) {
			b = true;
			String seq = excelColIndexToStr(4);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪҵ������");
		}

		String tradeCode = getCellValue(firstRow, 4); // ��һ�еĵ��ĸ�����
		if (!tradeCode.equals("��������")) {
			b = true;
			String seq = excelColIndexToStr(5);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ��������");
		}

		String input = getCellValue(firstRow, 5); // ��һ�еĵڰ˸�����
		if (!input.equals("��������")) {
			b = true;
			String seq = excelColIndexToStr(6);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ��������");
		}

		String step = getCellValue(firstRow, 6); // ��һ�еĵ��ĸ�����
		if (!step.equals("��������(��ʽ:������Դ|����ֵ)")) {
			b = true;
			String seq = excelColIndexToStr(7);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪ��������(��ʽ:������Դ|����ֵ)");
		}

		String exceptType = getCellValue(firstRow, 7); // ��һ�еĵ��ĸ�����
		if (!exceptType.equals("Ԥ�ڽ������")) {
			b = true;
			String seq = excelColIndexToStr(8);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪԤ�ڽ������");
		}

		String exceptResult = getCellValue(firstRow, 8); // ��һ�еĵ��ĸ�����
		if (!exceptResult.equals("Ԥ�ڽ��(��ʽ:������Դ|����ֵ)")) {
			b = true;
			String seq = excelColIndexToStr(9);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪԤ�ڽ��(��ʽ:������Դ|����ֵ)");
		}

		String sql = getCellValue(firstRow, 9); // ��һ�еĵ��ĸ�����
		if (!sql.equals("SQL���")) {
			b = true;
			String seq = excelColIndexToStr(10);
			JOptionPane.showMessageDialog(frame, "�ӿڰ�������" + seq + "��ӦΪSQL���");
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

	public static void createNewInterExcel(List<Case> uICases, Workbook wb, Map<String, TransferRelation> transferMap,
			File file) throws IOException, ParseException {
		// ��ȡUI�����ĸ��� ���� �ӿڰ�������ui�����ĸ������и���
		createTableContent(wb, uICases, transferMap);

		FileOutputStream fos = null;
		fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();

	}

	private static void createTableContent(Workbook wb, List<Case> uICases, Map<String, TransferRelation> transferMap)
			throws ParseException {

		// ��ȡ�ӿڰ����Ĳ������
		Sheet lastSheet = wb.getSheetAt(wb.getNumberOfSheets() - 1);
		String lastSName = lastSheet.getSheetName();
		String[] lastSplit = lastSName.split("_");
		String validateSeq = lastSplit[0];

		// int a=0;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			Sheet st = wb.getSheetAt(sheetIndex);
			String sheetName = st.getSheetName();
			boolean validateSheetName = validateSheetName(sheetName,wb.getNumberOfSheets());
			if (validateSheetName) {
				//Ĭ�ϵ���1
				Integer itradeSeq =1 ;
				
				if (wb.getNumberOfSheets() > 1) {
					String[] split = sheetName.split("_");
					String tradeNum = split[0].replace(tradeSeq, "").trim();
					itradeSeq = Integer.valueOf(tradeNum);
				}
				// ������
				// int a=0;
				// �Ƚ��а����еĸ���
				for (int i = 0; i <= st.getLastRowNum(); i++) {
					if (i == 0) {
						Row firstRow = st.getRow(i); // ��һ�а������
						String caseCode = getCellValue(firstRow, 8).trim();
						String[] split2 = caseCode.split(":");
						baseSeq = split2[0] + ":";
						baseCaseName=	caseCode.substring(caseCode.indexOf(":")+1, caseCode.length()-12);
						// baseSeq =
						// Integer.parseInt(caseCode.substring(caseCode.lastIndexOf("��")
						// + 1, caseCode.length()));
						// ������ŵ������߼� ����?��ʼ�ջ�������������ʱ���
					} else if (i == 1) {
						Row secondRow = st.getRow(i); // �ڶ��а���������
						String caseNature = getCellValue(secondRow, 8).trim();
						String[] split2 = caseNature.split(":");
						baseNature = split2[0] + ":";
					} else if (i == 2) {
						Row thirdRow = st.getRow(i); // �����а�������
						String caseName = getCellValue(thirdRow, 8).trim();
						String[] split2 = caseName.split(":");
						baseName = split2[0] + ":";
					} else if (i == 3) {
						Row fourRow = st.getRow(i);
						baseDesc = getCellValue(fourRow, 8).trim();
					}

					Row row = st.getRow(i);
					if (i >= 4) {
						String reportInfo = getCellValue(row, 0).trim(); // ��ȡ����1�е�����
						if (reportInfo.contains("start)������")) {
							int rowNum = row.getRowNum();
							reportStart = rowNum + 2;
						}
						if (reportInfo.contains("end)������")) { // ��ô֮������ݲ���ת��
							int rowNum = row.getRowNum();
							reportEnd = rowNum - 1;
						}
					}
					// ������
					String caseInfo = getCellValue(row, 8); // ��ȡ����9�е�����
					int columnWidth = st.getColumnWidth(8); // ��Ҫ�����е��п�

					Cell cell = row.getCell(8);
					if (cell != null) {
						cellStyle = cell.getCellStyle();
					} else {
						cellStyle = null;
					}
					if (i != 4) { // �����еĺ�����в�����
						for (int j = 1; j < uICases.size(); j++) {

							Cell newCell = row.createCell(j + 8);
							newCell.setCellStyle(cellStyle);
							st.setColumnWidth(j + 8, columnWidth);
							newCell.setCellValue(caseInfo);
						}
					}
				}
				for (int j = 0; j <= st.getLastRowNum(); j++) {
					Row row = st.getRow(j);
					if (j >= reportStart && j <= reportEnd) {
						requestReport.add(getCellValue(row, 2).trim()); // ��ȡ�ӿ�������)
					}
				}
				// �滻ֵ�Ĳ��������滻ֵ
				for (int rowIndex =  st.getLastRowNum(); rowIndex >=0; rowIndex--) {
					Row row = st.getRow(rowIndex);
					// String caseInfo = getCellValue(row, 8); //��ȡ����9�е�����
					if (rowIndex == 0) {
						int seq = 1;
						Row firstRow = st.getRow(rowIndex); // ��һ�а������
						for (int k = 8; k < 8 + uICases.size(); k++) {
							SimpleDateFormat uiDF = new SimpleDateFormat("yyyyMMdd");
							String format = uiDF.format(new Date());
							String valueOf = String.valueOf(seq);
							if (valueOf.length() < 4) {
								StringBuilder sb2 = new StringBuilder();
								for (int l = 0; l < 4; l++) {
									sb2.append("0");
								}
								String str = sb2.toString();
								valueOf = str.substring(0, 4 - valueOf.length()) + valueOf;
							}
							String newCode = baseSeq + baseCaseName + format + valueOf;
							Cell cell = firstRow.getCell(k);
							cell.setCellValue(newCode);
							seq++;
						}
					} else if (rowIndex == 1) {
						Row secondRow = st.getRow(rowIndex); // �ڶ��а���������
						for (int k = 8; k < 8 + uICases.size(); k++) {
							Cell cell = secondRow.getCell(k);
							cell.setCellValue(baseNature + uICases.get(k - 8).getCaseNature());
						}
					} else if (rowIndex == 2) {
						Row thirdRow = st.getRow(rowIndex); // �����а�������
						for (int k = 8; k < 8 + uICases.size(); k++) {
							Cell cell = thirdRow.getCell(k);
							//����ƴ�ӽӿڱ������ж�Ӧ�����������ֺ�������ֵ
							if(uICases.get(k - 8).getOppItems()!=null && uICases.get(k - 8).getOppItems().size()>0){
								StringBuilder sb = new StringBuilder();
								Map<String, String> oppItems = uICases.get(k - 8).getOppItems();
								Set<Entry<String, String>> entrySet = oppItems.entrySet();
								for (Entry<String, String> entry : entrySet) {
									if (requestReport.contains(entry.getKey())) {
										sb.append("��").append(entry.getKey())
												.append("��")
												.append(entry.getValue());
									}
								}
								cell.setCellValue(baseName + baseCaseName + "��"
										+ uICases.get(k - 8).getCaseNature()+sb.toString());
							}else{
								cell.setCellValue(baseName + baseCaseName + "��"
										+ uICases.get(k - 8).getCaseNature());
							}
						}
					}else if (rowIndex == 3) {
						Row fourRow = st.getRow(rowIndex);
						baseDesc = getCellValue(fourRow, 8).trim();
					} else if (rowIndex >= reportStart && rowIndex <= reportEnd) { // ������Ҫ��������ֵ�滻����
						String dataSource = getCellValue(row, 4).trim(); // ��ȡ��������Դ
						String icn_name = getCellValue(row, 2).trim(); // ��ȡ�ӿ�������
						String u_tradeName = getCurrentTradeName(uICases,
								itradeSeq);
						String key = u_tradeName + "," + icn_name;
						TransferRelation tr = transferMap.get(key); // ��ȡ��Ӧ��ϵ���е�����
						if (tr != null) {
							for (int k = 8; k < 8 + uICases.size(); k++) {
								String inputItemValue = getCellValue(row, k)
										.trim(); // ��ȡ����ֵ
								Case uiCase = uICases.get(k - 8);
								String replaceItemValue = replaceItemValue(
										inputItemValue, uiCase, validateSeq,
										tr, itradeSeq, dataSource,icn_name,wb.getNumberOfSheets());
								Cell cell = row.getCell(k);
								cell.setCellValue(replaceItemValue);
							}
						}
					}
				}
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "�ӿڰ���ģ�� "+sheetName+"ҳǩ���Ʋ��Ϸ���Ӧ�ԡ�s����_����ͷ��");
				throw new RuntimeException("�ӿڰ���ģ�� "+sheetName+"ҳǩ���Ʋ��Ϸ���Ӧ�ԡ�s����_����ͷ��");
			}
		}
	}

	private static String getCurrentTradeName(List<Case> uICases, Integer itradeSeq) {
		Case uiCase = uICases.get(0);
		List<Trade> trades = uiCase.getTrades();
		Trade trade = trades.get(itradeSeq-1);
		return trade.getOperationStep();
	}
	


	private static boolean validateSheetName(String sheetName,int sheetNum) {
		if (sheetNum == 1) {
			return true;
		} else {
			String regex = "^" + tradeSeq + "[1-9][0-9]*_[\\s\\S]*";
			Pattern r = Pattern.compile(regex);
			Matcher m = r.matcher(sheetName);
			return m.matches();
		}
	}

	private static String replaceItemValue(String inputItemValue, Case uiCase, String validateSeq, TransferRelation tr,
			Integer itradeSeq ,String dataSource,String icn_name,int tradeNum) throws ParseException {
		List<Trade> trades = uiCase.getTrades();
		//�����ж�����׵����̰�������У��
		if(tradeNum >1){
			if (!validateSeq.equals(tradeSeq + String.valueOf(trades.size()))) {
				JOptionPane.showMessageDialog(new JFrame(), "UI������ӿ����̲��������һ��");
				throw new RuntimeException();
			}
		}
		Trade UItrade = trades.get(itradeSeq - 1);
		Map<String, String> inputMap = UItrade.getInputItems(); // ��ȡ��ǰ���׵�������
		String value = inputMap.get(tr.getU_name().trim());// ��ȡ��ui�����������ֵ
		if (value != null) {
			if(value.trim().equals("��ֵ")||value.trim().equals("��ֵ������")){
				if(value.trim().equals("��ֵ������")){
					//uiCase.getOppItems().remove(tr.getU_name().trim());
					Map<String, String> oppItems = uiCase.getOppItems();
					Set<Entry<String, String>> entrySet = oppItems.entrySet();
					for (Entry<String, String> entry : entrySet) {
						if(entry.getKey().trim().equals(tr.getU_name().trim())){
							oppItems.remove(tr.getU_name().trim());
							oppItems.put(icn_name, "��ֵ");
						}
					}
					return oppoSeparator;
				}else{
					return inputItemValue="";
				}
			}
			if(dataSource.trim().equals("����")||value.trim().contains(oppoName)){
				if(value.trim().contains(oppoName)){
					value = value.replaceAll(oppoName, "").trim();
					value=oppoSeparator+value;
					Map<String, String> oppItems = uiCase.getOppItems();
					if(oppItems!=null && oppItems.size()>0){
					Set<Entry<String, String>> entrySet = oppItems.entrySet();
					for (Entry<String, String> entry : entrySet) {
						if(entry.getKey().trim().equals(tr.getU_name().trim())){
							oppItems.remove(tr.getU_name().trim());
							oppItems.put(icn_name, entry.getValue());
							} 
						}
					}
				}
				if (tr.getIsTranfer().equals("��")) { // �����Ҫ��ui�����ݽ��д����ĳ���Ӧ�Ľӿڵ�
					if (value.equals("")) {
						inputItemValue = "";
					} else {
						// Ȼ�����ת����ϵ����ת��
						if (tr.getTransferDependency().equals("���ݸ�ʽת��")) {
							if (tr.getI_dataType().equals("�����")) {
								String length = tr.getI_filedlength();
								if(value.contains(oppoSeparator)){
									value =value.replace(oppoSeparator, "").trim();
									String newValue = dealMoneyAmount(value, length);
									inputItemValue =oppoSeparator + newValue;
								}else{
									String newValue = dealMoneyAmount(value, length);
									inputItemValue =newValue;
								}

								// ������12λ
							} else if (tr.getI_dataType().equals("����")) {
								String uiDate = dealDateFormat(tr.getU_dateFormat());
								SimpleDateFormat uiDF = new SimpleDateFormat(uiDate);
								String interDate = dealDateFormat(tr.getI_dateFormat());
								SimpleDateFormat interDF = new SimpleDateFormat(interDate);
								
								if(value.contains(oppoSeparator)){
									value =value.replace(oppoSeparator, "").trim();
									Date date = uiDF.parse(value);
									inputItemValue =oppoSeparator +interDF.format(date);
								}else{
									Date date = uiDF.parse(value);
									inputItemValue = interDF.format(date);
								}
							} else {
								inputItemValue = value;
							}

						} else if (tr.getTransferDependency().equals("����ӳ��")) {
							String maping = tr.getMappingRelation();
							if (maping == null || "".equals(maping)) {
								inputItemValue = "";
							} else {
								String[] maps = maping.split(";");
								Map<String, String> moneyType = new HashMap<String, String>();
								for (String map : maps) {
									String[] split = map.split("->");
									moneyType.put(split[0], split[1]);
								}
								
								if(value.contains(oppoSeparator)){
									value =value.replace(oppoSeparator, "").trim();
									if (moneyType.get(value) != null && !"".equals(moneyType.get(value))) {
										inputItemValue = oppoSeparator+moneyType.get(value);
									} else {
										inputItemValue = "";
									}
									
								}else{
									if (moneyType.get(value) != null && !"".equals(moneyType.get(value))) {
										inputItemValue = moneyType.get(value);
									} else {
										inputItemValue = "";
									}
								}
							}
						}
					}
				} else {
					inputItemValue = value;
				}
			}
		}
		return inputItemValue;
	}

	private static String dealMoneyAmount(String value, String length) {
		float parseFloat = Float.parseFloat(value);
		DecimalFormat df = new DecimalFormat("0.00");// ת����ʽ
		String format = df.format(parseFloat);
		String[] split = format.split("\\.");
		String newValue = split[0] + split[1];

		if(length.endsWith("λ")){
			length=length.replace("λ", "").trim();
		}
		if (newValue.length() < Integer.parseInt(length)) {
			StringBuilder sb2 = new StringBuilder();
			for (int k = 0; k < Integer.parseInt(length); k++) {
				sb2.append("0");
			}
			String str = sb2.toString();
			newValue = str.substring(0, Integer.parseInt(length) - newValue.length()) + newValue;
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "������ݳ���" + length + "λ!");
			throw new RuntimeException("������ݳ���" + length + "λ!");
		}
		return newValue;
	}

	public static String getInputItemValue(String inputItemValue, int j, String tradeName, TransferRelation tr,
			Case uiCase) throws ParseException {
		List<Trade> UItrades = uiCase.getTrades();
		if (UItrades.size() <= j) {
			JOptionPane.showMessageDialog(new JFrame(), "ui����������ӿڰ������׸�����һ��");
			throw new RuntimeException();
		}
		Trade UItrade = UItrades.get(j);
		if (tradeName.equals(UItrade.getOperationStep())) {
			Map<String, String> inputMap = UItrade.getInputItems();
			String value = inputMap.get(tr.getU_name());// ��ȡ��ui�����������ֵ
			if (value != null) {
				if (tr.getIsTranfer().equals("��")) { // �����Ҫ��ui�����ݽ��д����ĳ���Ӧ�Ľӿڵ�
					if (value.equals("")) {
						inputItemValue = "";
					} else {
						// Ȼ�����ת����ϵ����ת��
						if (tr.getTransferDependency().equals("���ݸ�ʽת��")) {
							if (tr.getI_dataType().equals("�����")) {
								float parseFloat = Float.parseFloat(value);
								DecimalFormat df = new DecimalFormat("0.00");// ת����ʽ
								String format = df.format(parseFloat);
								String[] split = format.split("\\.");
								String newValue = split[0] + split[1];

								String length = tr.getI_filedlength();
								length = length.substring(0, length.length() - 1);

								if (newValue.length() < Integer.parseInt(length)) {
									StringBuilder sb2 = new StringBuilder();
									for (int k = 0; k < Integer.parseInt(length); k++) {
										sb2.append("0");
									}
									String str = sb2.toString();
									newValue = str.substring(0, Integer.parseInt(length) - newValue.length())
											+ newValue;
								} else {
									throw new RuntimeException("������ݳ���" + length + "λ!");
								}
								inputItemValue = newValue;

								// ������12λ
							} else if (tr.getI_dataType().equals("����")) {
								String uiDate = dealDateFormat(tr.getU_dateFormat());
								SimpleDateFormat uiDF = new SimpleDateFormat(uiDate);
								String interDate = dealDateFormat(tr.getI_dateFormat());
								SimpleDateFormat interDF = new SimpleDateFormat(interDate);
								Date date = uiDF.parse(value);
								inputItemValue = interDF.format(date);
							}
						} else if (tr.getTransferDependency().equals("����ӳ��")) {
							String maping = tr.getMappingRelation();
							String[] maps = maping.split(";");
							Map<String, String> moneyType = new HashMap<String, String>();
							for (String map : maps) {
								String[] split = map.split("->");
								moneyType.put(split[0], split[1]);
							}
							if (moneyType.get(value) != null && !"".equals(moneyType.get(value))) {
								inputItemValue = moneyType.get(value);
							} else {
								inputItemValue = "";
							}
						}
					}
				} else {
					inputItemValue = value;
				}
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "ui����������ӿڰ����������Ʋ�һ��");
			throw new RuntimeException();
		}
		return inputItemValue;
	}

	public static String dealDateFormat(String date) {

		if (date.contains("YYYY")) {
			date = date.replace("YYYY", "yyyy");
		}

		if (date.contains("DD")) {
			date = date.replace("DD", "dd");
		}

		if (date.contains(":MM")) {
			date = date.replace(":MM", ":mm");
		}

		if (date.contains(":SS")) {
			date = date.replace(":SS", ":ss");
		}

		return date;

	}

	public static void createTableFirstLine(XSSFWorkbook workBook, XSSFSheet sheet) {
		// ��һ�б�ͷ������
		// �ұ���ɫ������������У����ܱ߿�
		XSSFCellStyle titleStyle = workBook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ���õ�Ԫ��ľ���
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index); // �������ɫ
		// workBook.getC
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		// �ұ���ɫ������������У����ܱ߿�
		XSSFCellStyle firstStyle = workBook.createCellStyle();
		firstStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ���õ�Ԫ��ľ���
		firstStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// firstStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// firstStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// // �������ɫ
		firstStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFCellStyle specialStyle = workBook.createCellStyle();
		specialStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // ���õ�Ԫ��ľ���
		specialStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		specialStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		specialStyle.setFillForegroundColor(HSSFColor.RED.index); // �������ɫ
		specialStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		// ��ɫ�Ӵ�����
		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // ������ɫ
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("����");
		titleFont.setFontHeightInPoints((short) 10); // ��������Ĵ�С
		firstStyle.setFont(titleFont);
		titleStyle.setFont(titleFont);
		specialStyle.setFont(titleFont);

		XSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(15);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("ע������ɫ�пɱ༭����,��ɫ�������޸�");
		cell.setCellStyle(firstStyle);
		sheet.setColumnWidth(0, 3660);

		cell = row.createCell(1);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(1, 3660);

		cell = row.createCell(2);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(2, 3660);

		cell = row.createCell(3);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(3, 3660);

		cell = row.createCell(4);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(4, 7500);

		cell = row.createCell(5);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(5, 3660);

		cell = row.createCell(6);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(6, 7500);

		cell = row.createCell(7);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(7, 7500);

		cell = row.createCell(8);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(8, 7500);

		cell = row.createCell(9);
		cell.setCellStyle(titleStyle);
		sheet.setColumnWidth(9, 7500);

		XSSFRow titleRow = sheet.createRow(1);
		titleRow.setHeightInPoints(15);
		XSSFCell cell0 = titleRow.createCell(0);
		cell0.setCellValue("�������");
		cell0.setCellStyle(titleStyle);

		XSSFCell cell1 = titleRow.createCell(1);
		cell1.setCellValue("��������");
		cell1.setCellStyle(titleStyle);

		XSSFCell cell2 = titleRow.createCell(2);
		cell2.setCellValue("��������");
		cell2.setCellStyle(titleStyle);

		XSSFCell cell3 = titleRow.createCell(3);
		cell3.setCellValue("ҵ������");
		cell3.setCellStyle(specialStyle);

		XSSFCell cell4 = titleRow.createCell(4);
		cell4.setCellValue("��������");
		cell4.setCellStyle(specialStyle);

		XSSFCell cell5 = titleRow.createCell(5);
		cell5.setCellValue("��������");
		cell5.setCellStyle(specialStyle);

		XSSFCell cell6 = titleRow.createCell(6);
		cell6.setCellValue("��������(��ʽ:������Դ|����ֵ)");
		cell6.setCellStyle(titleStyle);

		XSSFCell cell7 = titleRow.createCell(7);
		cell7.setCellValue("Ԥ�ڽ������");
		cell7.setCellStyle(titleStyle);

		XSSFCell cell8 = titleRow.createCell(8);
		cell8.setCellValue("Ԥ�ڽ��(��ʽ:������Դ|����ֵ)");
		cell8.setCellStyle(titleStyle);

		XSSFCell cell9 = titleRow.createCell(9);
		cell9.setCellValue("SQL���");
		cell9.setCellStyle(titleStyle);
	}

	public static XSSFCellStyle setCellsFormateV(XSSFWorkbook workBook, XSSFSheet sheet) {
		// ��ͨ��Ԫ�����ʽ������������У����ֿɻ��У����ܱ߿�
		XSSFCellStyle mergStyle = workBook.createCellStyle();
		mergStyle.setWrapText(true);
		mergStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		mergStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		mergStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // ������ɫ
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("����");
		titleFont.setFontHeightInPoints((short) 10); // ��������Ĵ�С
		mergStyle.setFont(titleFont);
		// titleStyle.setFont(titleFont);
		// specialStyle.setFont(titleFont);
		// ���õ�Ԫ�����ݸ�ʽ Ϊ�ı�
		mergStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

		return mergStyle;
	}

	public static XSSFCellStyle setCaseFormateV(XSSFWorkbook workBook, XSSFSheet sheet) {
		// ��ͨ��Ԫ�����ʽ������������У����ֿɻ��У����ܱ߿�
		XSSFCellStyle mergStyle = workBook.createCellStyle();
		mergStyle.setWrapText(true);
		mergStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		mergStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		mergStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // ������ɫ
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("����");
		titleFont.setFontHeightInPoints((short) 10); // ��������Ĵ�С
		mergStyle.setFont(titleFont);
		// titleStyle.setFont(titleFont);
		// specialStyle.setFont(titleFont);
		// ���õ�Ԫ�����ݸ�ʽ Ϊ�ı�
		mergStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

		return mergStyle;
	}

	public static void setTradeCellStyle(int crow, int firstRow, XSSFSheet sheet, XSSFCellStyle xssfCellStyle) {
		for (int c = firstRow; c < crow; c++) {
			XSSFRow row = sheet.getRow(c);
			XSSFCell cell = row.createCell(3);
			cell.setCellStyle(xssfCellStyle);
		}
		// ����
		sheet.addMergedRegion(new CellRangeAddress(firstRow, crow - 1, 3, 3));
	}

	public static void setCaseCellStyle(int crow, int firstRow, XSSFSheet sheet, XSSFCellStyle xssfCellStyle) {
		for (int c = firstRow; c < crow; c++) {
			XSSFRow row = sheet.getRow(c);
			row.setHeightInPoints(50);
			XSSFCell cell = row.createCell(0);
			cell.setCellStyle(xssfCellStyle);
			cell = sheet.getRow(c).createCell(1);
			cell.setCellStyle(xssfCellStyle);
			cell = sheet.getRow(c).createCell(2);
			cell.setCellStyle(xssfCellStyle);
		}
		for (int h = 0; h < 3; h++) {
			sheet.addMergedRegion(new CellRangeAddress(firstRow, crow - 1, h, h));
		}
	}

}