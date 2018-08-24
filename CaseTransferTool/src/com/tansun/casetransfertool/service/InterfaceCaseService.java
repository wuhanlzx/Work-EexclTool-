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

/**
 * 
 * @ClassName: InterfaceCaseService
 * @Description: 接口案例业务逻辑处理类
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 上午10:50:34
 * 
 */
public class InterfaceCaseService extends BaseService {

	public static List<Case> importCaseToObject(JFrame frame, String fileName) throws Exception {

		// -------------------------------支持2003和2007板的excel------------------------
		FileInputStream in = new FileInputStream(fileName);// 获取上传文件流
		DataInputStream fins = new DataInputStream(in); // 包含数据的数据流

		// excel的类型，支持2003和2007板的excel
		boolean excelType = fileName.endsWith(".xls") == true ? true : false;

		List<Case> cases = getInfo(frame, fins, excelType);

		return cases;
	}

	public static List<Case> getInfo(JFrame frame, DataInputStream fins, boolean excelType) throws Exception {
		Workbook wb = null;
		List<Case> cases = new ArrayList<Case>();
		if (excelType) {
			wb = new HSSFWorkbook(fins);

		} else {
			wb = new XSSFWorkbook(fins);
		}
		// 便利sheet页
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			Sheet st = wb.getSheetAt(sheetIndex); // 获取当前的sheet页
			Row titleRow = st.getRow(1);
			boolean b = validateExcel(titleRow, frame); // 校验首行
			if (b) {
				throw new RuntimeException("接口案例Excel文件校验失败!");
			}
			// 直接从第二行开始 第一行的数据是表头不需要
			for (int rowIndex = 2; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				Case interCase = null; // 一行数据就是一个案例
				Trade trade = null;
				boolean isMerge = isMergedRegionFirstRow(st, rowIndex, 0);
				boolean isMergeForTrade = isMergedRegionFirstRow(st, rowIndex, 3);
				if (isMerge) {
					interCase = new Case();
					interCase.setCaseCode(getCellValue(row, 0)); // 案例编号
					interCase.setCaseName(getCellValue(row, 1)); // 案例名称
					interCase.setCaseNature(getCellValue(row, 2)); // 案例性质
					cases.add(interCase);
				} else {
					interCase = cases.get(cases.size() - 1);
				}

				// 在合并的行的第一行
				if (isMergeForTrade) {
					trade = new Trade();
					trade.setBusinessName(getCellValue(row, 3)); // 业务名称
					interCase.addTrade(trade);
					// 在合并的但不是第一列
				} else {
					List<Trade> trades = interCase.getTrades();
					trade = trades.get(trades.size() - 1);
				}

				InterfaceReport report = new InterfaceReport();
				report.setWorkName(getCellValue(row, 4)); // 任务名称
				report.setWorkType(getCellValue(row, 5)); // 任务类型

				// 请求数据
				String value = getCellValue(row, 6);
				if (value != null && !value.equals("")) {
					String datas[] = value.split("\\r?\\n"); // 对于多行的数据使用换行符进行分割
					List<InputItem> requestData = new ArrayList<InputItem>();
					for (String input : datas) {
						InputItem inputItem = new InputItem();
						String[] inputData = input.split(":");
						String names = inputData[0];
						int enNameIndex = names.indexOf("(");
						inputItem.setEnName(names.substring(0, enNameIndex));
						inputItem.setChName(names.substring(enNameIndex + 1, names.length() - 1));
						String values = inputData[1];
						int valueIndex = values.indexOf("|");
						inputItem.setValueType(values.substring(0, valueIndex));
						inputItem.setInputItemValue(values.substring(valueIndex + 1, values.length()));
						requestData.add(inputItem);
					}
					report.setRequestData(requestData);
				}
				// 预期结果类型
				if (getCellValue(row, 7) != null && !getCellValue(row, 7).equals("")) {
					report.setExceptResultType(getCellValue(row, 7)); // 预期结果类型
				}
				// 预期结果
				String value2 = getCellValue(row, 8);
				if (value2 != null && !value2.equals("")) {
					List<InputItem> exceptResult = new ArrayList<InputItem>();
					String results[] = value2.split("\\r?\\n"); // 对于多行的数据使用换行符进行分割
					for (String input : results) {
						InputItem inputItem = new InputItem();
						String[] inputData = input.split(":");
						String names = inputData[0];
						int enNameIndex = names.indexOf("(");
						inputItem.setEnName(names.substring(0, enNameIndex));
						inputItem.setChName(names.substring(enNameIndex + 1, names.length() - 1));
						String values = inputData[1];
						int valueIndex = values.indexOf("|");
						inputItem.setValueType(values.substring(0, valueIndex));
						inputItem.setInputItemValue(values.substring(valueIndex + 1, values.length()));
						exceptResult.add(inputItem);
					}
					report.setExceptResult(exceptResult);
				}
				trade.addReport(report);
			}

		}
		return cases;
	}

	public static boolean validateExcel(Row firstRow, JFrame frame) {

		boolean b = false;
		String code = getCellValue(firstRow, 0); // 第一行的第一格数据
		if (!code.equals("案例编号")) {
			b = true;
			String seq = excelColIndexToStr(1);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为案例编号");
		}

		String system = getCellValue(firstRow, 1); // 第一行的第二格数据
		if (!system.equals("案例名称")) {
			b = true;
			String seq = excelColIndexToStr(2);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为案例名称");
		}

		String function = getCellValue(firstRow, 2); // 第一行的第三格数据
		if (!function.equals("案例性质")) {
			b = true;
			String seq = excelColIndexToStr(3);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为案例性质");
		}

		String functionName = getCellValue(firstRow, 3); // 第一行的第四格数据
		if (!functionName.equals("业务名称")) {
			b = true;
			String seq = excelColIndexToStr(4);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为业务名称");
		}

		String tradeCode = getCellValue(firstRow, 4); // 第一行的第四格数据
		if (!tradeCode.equals("任务名称")) {
			b = true;
			String seq = excelColIndexToStr(5);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为任务名称");
		}

		String input = getCellValue(firstRow, 5); // 第一行的第八格数据
		if (!input.equals("任务类型")) {
			b = true;
			String seq = excelColIndexToStr(6);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为任务类型");
		}

		String step = getCellValue(firstRow, 6); // 第一行的第四格数据
		if (!step.equals("请求数据(格式:数据来源|数据值)")) {
			b = true;
			String seq = excelColIndexToStr(7);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为请求数据(格式:数据来源|数据值)");
		}

		String exceptType = getCellValue(firstRow, 7); // 第一行的第四格数据
		if (!exceptType.equals("预期结果类型")) {
			b = true;
			String seq = excelColIndexToStr(8);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为预期结果类型");
		}

		String exceptResult = getCellValue(firstRow, 8); // 第一行的第四格数据
		if (!exceptResult.equals("预期结果(格式:数据来源|数据值)")) {
			b = true;
			String seq = excelColIndexToStr(9);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为预期结果(格式:数据来源|数据值)");
		}

		String sql = getCellValue(firstRow, 9); // 第一行的第四格数据
		if (!sql.equals("SQL语句")) {
			b = true;
			String seq = excelColIndexToStr(10);
			JOptionPane.showMessageDialog(frame, "接口案例表第" + seq + "列应为SQL语句");
		}

		return b;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
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

	public static void createNewInterExcel(List<Case> uICases, List<Case> interCases,
			Map<String, TransferRelation> transferMap, File file) throws IOException, ParseException {
		// 获取UI案例的个数 并将 接口案例按照ui案例的个数进行复制
		FileOutputStream fos = null;

		fos = new FileOutputStream(file);
		// 创建Excel对象
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 创建sheet页
		XSSFSheet sheet = workBook.createSheet();

		createTableFirstLine(workBook, sheet);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));// 创建第一行的合并的列

		createTableContent(workBook, uICases, interCases, transferMap, sheet);

		workBook.write(fos);
		fos.close();

	}

	@SuppressWarnings("deprecation")
	public static void createTableContent(XSSFWorkbook workBook, List<Case> uICases, List<Case> interCases,
			Map<String, TransferRelation> transferMap, XSSFSheet sheet) throws ParseException {
		XSSFCellStyle xssfCellStyle = setCellsFormateV(workBook, sheet);
		XSSFCellStyle xssfCaseStyle = setCaseFormateV(workBook, sheet);
		XSSFRow row = null;
		int curRow = 2;
		int firstRow = 2;
		int caseRow = 2;
		for (int i = 0; i < uICases.size(); i++) {
			Case interCase = interCases.get(0);
			List<Trade> trades = interCase.getTrades();
			for (int j = 0; j < trades.size(); j++) {
				List<InterfaceReport> reports = trades.get(j).getReports();
				for (InterfaceReport ip : reports) {
					// 创建行
					row = sheet.createRow(curRow);
					row.setHeightInPoints(30);
					// 任务名称
					XSSFCell workName = row.createCell(4);
					workName.setCellValue(ip.getWorkName() == null ? "" : ip.getWorkName().trim());
					workName.setCellStyle(xssfCellStyle);

					// 任务类型
					XSSFCell workType = row.createCell(5);
					workType.setCellValue(ip.getWorkType() == null ? "" : ip.getWorkType().trim());
					workType.setCellStyle(xssfCellStyle);

					// 请求数据
					XSSFCell reqData = row.createCell(6);
					List<InputItem> requestData = ip.getRequestData();
					StringBuilder sb = new StringBuilder();
					if (requestData != null && requestData.size() > 0) {
						for (InputItem inputItem : requestData) {
							String enName = inputItem.getEnName();
							String chName = inputItem.getChName();
							String valueType = inputItem.getValueType();
							String inputItemValue = inputItem.getInputItemValue();
							if (valueType.equals("常量")) {
								// 这里来处理业务逻辑
								String tradeName = trades.get(j).getBusinessName();
								String key = tradeName + "," + chName;
								TransferRelation tr = transferMap.get(key);
								// 获取到交易名和 输入项的
								if (tr != null) {
									Case uiCase = uICases.get(i);
									inputItemValue = getInputItemValue(inputItemValue, j, tradeName, tr, uiCase);
								}
							}
							sb.append(enName).append("(").append(chName).append(")").append(":").append(valueType)
									.append("|").append(inputItemValue).append("\r\n");
						}
					}
					reqData.setCellValue(sb.toString() == null ? "" : sb.toString().trim());
					reqData.setCellStyle(xssfCellStyle);

					// 预期结果类型
					XSSFCell exceptType = row.createCell(7);
					exceptType.setCellValue(ip.getExceptResultType() == null ? "" : ip.getExceptResultType().trim());
					exceptType.setCellStyle(xssfCellStyle);

					// 预期结果
					XSSFCell exceptResult = row.createCell(8);
					List<InputItem> results = ip.getExceptResult();
					StringBuilder zsb = new StringBuilder();
					if (results != null && results.size() > 0) {
						for (InputItem inputItem : results) {
							String enName = inputItem.getEnName();
							String chName = inputItem.getChName();
							String valueType = inputItem.getValueType();
							String inputItemValue = inputItem.getInputItemValue();
							zsb.append(enName).append("(").append(chName).append(")").append(":").append(valueType)
									.append("|").append(inputItemValue).append("\r\n");
						}
					}
					exceptResult.setCellValue(zsb.toString() == null ? "" : zsb.toString().trim());
					exceptResult.setCellStyle(xssfCellStyle);
					// SQL语句
					XSSFCell sql = row.createCell(9);
					sql.setCellValue("");
					sql.setCellStyle(xssfCellStyle);

					curRow++;
				}
				setTradeCellStyle(curRow, firstRow, sheet, xssfCellStyle);
				XSSFRow tradeRow = sheet.getRow(firstRow);
				XSSFCell tradeName = tradeRow.getCell(3);
				tradeName.setCellValue(trades.get(j).getBusinessName() == null ? "" : trades.get(j).getBusinessName()
						.trim());
				// 业务名称
				// sheet.addMergedRegion(new CellRangeAddress(firstRow, curRow -
				// 1, 3, 3));
				firstRow = curRow;
			}
			setCaseCellStyle(curRow, caseRow, sheet, xssfCaseStyle);
			// 这里处理案例的合并
			XSSFRow firtCaseRow = sheet.getRow(caseRow);
			XSSFCell caseCode = firtCaseRow.getCell(0);
			// 生成四位的序列号
			String seq = "0000" + String.valueOf(i + 1);
			seq = seq.substring(seq.length() - 4);
			Case uiCase = uICases.get(i);
			if (interCase.getCaseCode() != null && interCase.getCaseCode() != "") {
				String intercode = interCase.getCaseCode();
				String BranchName = intercode.substring(0, intercode.length() - 12);

				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String time = format.format(new Date());
				// 案例编号
				intercode = BranchName + time + seq;
				caseCode.setCellValue(intercode == null ? "" : intercode);
				// caseCode.setCellStyle(xssfCellStyle);
				// 重新生成案例名称
				XSSFCell caseName = firtCaseRow.getCell(1);
				// caseName.setCellStyle(xssfCellStyle);
				String newCaseName = BranchName + "_" + uiCase.getCaseNature() + seq;
				caseName.setCellValue(newCaseName == null ? "" : newCaseName);
			}
			// 案例性质
			XSSFCell caseNature = firtCaseRow.getCell(2);
			// caseNature.setCellStyle(xssfCellStyle);
			caseNature.setCellValue(uiCase.getCaseNature() == null ? "" : uiCase.getCaseNature());
			caseRow = curRow;
		}

	}

	public static String getInputItemValue(String inputItemValue, int j, String tradeName, TransferRelation tr,
			Case uiCase) throws ParseException {
		List<Trade> UItrades = uiCase.getTrades();
		if (UItrades.size() <= j) {
			JOptionPane.showMessageDialog(new JFrame(), "ui案例交易与接口案例交易个数不一致");
			throw new RuntimeException();
		}
		Trade UItrade = UItrades.get(j);
		if (tradeName.equals(UItrade.getOperationStep())) {
			Map<String, String> inputMap = UItrade.getInputItems();
			String value = inputMap.get(tr.getU_name());// 获取到ui案例数据项的值
			if (value != null) {
				if (tr.getIsTranfer().equals("是")) { // 这个是要将ui的数据进行处理的成相应的接口的
					if (value.equals("")) {
						inputItemValue = "";
					} else {
						// 然后根据转换关系进行转换
						if (tr.getTransferDependency().equals("数据格式转换")) {
							if (tr.getI_dataType().equals("金额类")) {
								float parseFloat = Float.parseFloat(value);
								DecimalFormat df = new DecimalFormat("0.00");// 转换格式
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
									throw new RuntimeException("金额数据超过" + length + "位!");
								}
								inputItemValue = newValue;

								// 金额高于12位
							} else if (tr.getI_dataType().equals("日期")) {
								String uiDate = dealDateFormat(tr.getU_dateFormat());
								SimpleDateFormat uiDF = new SimpleDateFormat(uiDate);
								String interDate = dealDateFormat(tr.getI_dateFormat());
								SimpleDateFormat interDF = new SimpleDateFormat(interDate);
								Date date = uiDF.parse(value);
								inputItemValue = interDF.format(date);
							}
						} else if (tr.getTransferDependency().equals("数据映射")) {
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
			JOptionPane.showMessageDialog(new JFrame(), "ui案例交易与接口案例交易名称不一致");
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
		// 第一行表头的设置
		// 灰背景色，横向纵向居中，四周边框
		XSSFCellStyle titleStyle = workBook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 设置单元格的居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index); // 设置填充色
		// workBook.getC
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		// 灰背景色，横向纵向居中，四周边框
		XSSFCellStyle firstStyle = workBook.createCellStyle();
		firstStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 设置单元格的居中
		firstStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// firstStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// firstStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// // 设置填充色
		firstStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		firstStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFCellStyle specialStyle = workBook.createCellStyle();
		specialStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 设置单元格的居中
		specialStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		specialStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		specialStyle.setFillForegroundColor(HSSFColor.RED.index); // 设置填充色
		specialStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		specialStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		// 黑色加粗字体
		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // 字体颜色
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("宋体");
		titleFont.setFontHeightInPoints((short) 10); // 设置字体的大小
		firstStyle.setFont(titleFont);
		titleStyle.setFont(titleFont);
		specialStyle.setFont(titleFont);

		XSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(15);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("注：淡蓝色列可编辑数据,红色列请勿修改");
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
		cell0.setCellValue("案例编号");
		cell0.setCellStyle(titleStyle);

		XSSFCell cell1 = titleRow.createCell(1);
		cell1.setCellValue("案例名称");
		cell1.setCellStyle(titleStyle);

		XSSFCell cell2 = titleRow.createCell(2);
		cell2.setCellValue("案例性质");
		cell2.setCellStyle(titleStyle);

		XSSFCell cell3 = titleRow.createCell(3);
		cell3.setCellValue("业务名称");
		cell3.setCellStyle(specialStyle);

		XSSFCell cell4 = titleRow.createCell(4);
		cell4.setCellValue("任务名称");
		cell4.setCellStyle(specialStyle);

		XSSFCell cell5 = titleRow.createCell(5);
		cell5.setCellValue("任务类型");
		cell5.setCellStyle(specialStyle);

		XSSFCell cell6 = titleRow.createCell(6);
		cell6.setCellValue("请求数据(格式:数据来源|数据值)");
		cell6.setCellStyle(titleStyle);

		XSSFCell cell7 = titleRow.createCell(7);
		cell7.setCellValue("预期结果类型");
		cell7.setCellStyle(titleStyle);

		XSSFCell cell8 = titleRow.createCell(8);
		cell8.setCellValue("预期结果(格式:数据来源|数据值)");
		cell8.setCellStyle(titleStyle);

		XSSFCell cell9 = titleRow.createCell(9);
		cell9.setCellValue("SQL语句");
		cell9.setCellStyle(titleStyle);
	}

	public static XSSFCellStyle setCellsFormateV(XSSFWorkbook workBook, XSSFSheet sheet) {
		// 普通单元格的样式，横向、纵向居中，文字可换行，四周边框
		XSSFCellStyle mergStyle = workBook.createCellStyle();
		mergStyle.setWrapText(true);
		mergStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		mergStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		mergStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // 字体颜色
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("宋体");
		titleFont.setFontHeightInPoints((short) 10); // 设置字体的大小
		mergStyle.setFont(titleFont);
		// titleStyle.setFont(titleFont);
		// specialStyle.setFont(titleFont);
		// 设置单元格数据格式 为文本
		mergStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

		return mergStyle;
	}

	public static XSSFCellStyle setCaseFormateV(XSSFWorkbook workBook, XSSFSheet sheet) {
		// 普通单元格的样式，横向、纵向居中，文字可换行，四周边框
		XSSFCellStyle mergStyle = workBook.createCellStyle();
		mergStyle.setWrapText(true);
		mergStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		mergStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		mergStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		mergStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		XSSFFont titleFont = workBook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index); // 字体颜色
		// titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("宋体");
		titleFont.setFontHeightInPoints((short) 10); // 设置字体的大小
		mergStyle.setFont(titleFont);
		// titleStyle.setFont(titleFont);
		// specialStyle.setFont(titleFont);
		// 设置单元格数据格式 为文本
		mergStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

		return mergStyle;
	}

	public static void setTradeCellStyle(int crow, int firstRow, XSSFSheet sheet, XSSFCellStyle xssfCellStyle) {
		for (int c = firstRow; c < crow; c++) {
			XSSFRow row = sheet.getRow(c);
			XSSFCell cell = row.createCell(3);
			cell.setCellStyle(xssfCellStyle);
		}
		// 规则
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
