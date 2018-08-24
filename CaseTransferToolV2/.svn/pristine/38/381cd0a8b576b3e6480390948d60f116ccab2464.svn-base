package com.tansun.casetransfertool.service;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tansun.casetransfertool.bean.TransferRelation;
import com.tansun.casetransfertool.utils.PinYin4JUtils;

/**
 * 
 * @ClassName: TransferRelationService
 * @Description: 转换关系业务逻辑处理类
 * @company com.tansun
 * @author lzx
 * @date 2017-12-6 上午10:51:10
 * 
 */
public class TransferRelationService extends BaseService {

	private static String tradeName;

	public static Map<String, TransferRelation> importCaseToObject(JFrame frame, String fileName) throws IOException {

		// -------------------------------支持2003和2007板的excel------------------------
		FileInputStream in = new FileInputStream(fileName);// 获取上传文件流
		DataInputStream fins = new DataInputStream(in); // 包含数据的数据流

		// excel的类型，支持2003和2007板的excel
		boolean excelType = fileName.endsWith(".xls") == true ? true : false;

		Map<String, TransferRelation> relationMap = getInfo(frame, fins, excelType);

		return relationMap;
	}

	public static Map<String, TransferRelation> getInfo(JFrame frame, DataInputStream fins, boolean excelType)
			throws IOException {
		Workbook wb = null;
		// int rowSize = 0;
		Map<String, TransferRelation> relationMap = new HashMap<String, TransferRelation>();
		;
		if (excelType) {
			wb = new HSSFWorkbook(fins);

		} else {
			wb = new XSSFWorkbook(fins);
		}
		// 便利sheet页
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			Sheet st = wb.getSheetAt(sheetIndex); // 获取当前的sheet页
			Row firstRow = st.getRow(0); // 获取首行
			boolean b = false;
			if (firstRow != null) {
				b = validateExcel(firstRow, frame); // 校验首行
			}

			Row titleRow = st.getRow(1);
			boolean c = false;
			if (titleRow != null) {
				c = validateTitleRow(titleRow, frame);// 校验标题行
			}
			if (b || c) {
				throw new RuntimeException("转换关系Excel文件校验失败!");
			}
			// 直接从第二行开始 第一行的数据是表头不需要
			for (int rowIndex = 2; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				/*boolean isMerge = isMergedRegionFirstRow(st, rowIndex, 0);
				if (isMerge) {

					tradeName = getCellValue(row, 0);
				}*/
				if (!"".equals(getCellValue(row, 0))) {

					tradeName = getCellValue(row, 0);
				}

				TransferRelation tr = new TransferRelation();
				// 数据项名字
				tr.setU_name(getCellValue(row, 1));
				// 数据类型
				tr.setU_dataType(getCellValue(row, 2));
				// 日期格式
				tr.setU_dateFormat(getCellValue(row, 3));
				// 小数点后几位
				tr.setU_filedlength(getCellValue(row, 4));
				// 请求报文
				tr.setI_reqReport(getCellValue(row, 5));
				// 数据类型
				tr.setI_dataType(getCellValue(row, 6));
				// 字段长度
				tr.setI_filedlength(getCellValue(row, 7));
				// 日期格式
				tr.setI_dateFormat(getCellValue(row, 8));
				// 小数点后面几位
				tr.setI_endPoint(getCellValue(row, 9));
				// 是否需要转换
				tr.setIsTranfer(getCellValue(row, 10));
				// 转换依据
				tr.setTransferDependency(getCellValue(row, 11));
				// 映射关系
				tr.setMappingRelation(getCellValue(row, 12));

				 //tradeName = PinYin4JUtils.ToPinyin(tradeName);
				relationMap.put(tradeName + "," + tr.getI_reqReport(), tr);

			}
		}
		return relationMap;
	}

	public static boolean validateExcel(Row firstRow, JFrame frame) {

		boolean b = false;
		String code = getCellValue(firstRow, 0).trim(); // 第一行的第一格数据
		if (!code.equals("交易名称")) {
			String seq = excelColIndexToStr(1);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为交易名称");
			b = true;
		}

		String system = getCellValue(firstRow, 1).trim(); // 第一行的第二格数据
		if (!system.equals("UI")) {
			String seq = excelColIndexToStr(2);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为UI");
			b = true;
		}

		String function = getCellValue(firstRow, 5).trim(); // 第一行的第三格数据
		if (!function.equals("接口")) {
			String seq = excelColIndexToStr(6);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为接口");
			b = true;
		}

		String functionName = getCellValue(firstRow, 10).trim(); // 第一行的第四格数据
		if (!functionName.equals("是否需要数据转换")) {
			String seq = excelColIndexToStr(11);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为是否需要数据转换");
			b = true;
		}

		String tradeCode = getCellValue(firstRow, 11).trim(); // 第一行的第四格数据
		if (!tradeCode.equals("转换依据")) {
			String seq = excelColIndexToStr(12);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为转换依据");
			b = true;
		}

		String input = getCellValue(firstRow, 12).trim(); // 第一行的第八格数据
		if (!input.equals("映射关系")) {
			String seq = excelColIndexToStr(13);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为映射关系");
			b = true;
		}
		return b;

	}

	public static boolean validateTitleRow(Row titleRow, JFrame frame) {

		boolean b = false;

		String dataItem = getCellValue(titleRow, 1).trim(); // 第一行的第一格数据
		if (!dataItem.equals("数据项")) {
			String seq = excelColIndexToStr(2);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为数据项");
			b = true;
		}

		String code = getCellValue(titleRow, 2).trim(); // 第一行的第一格数据
		if (!code.equals("数据类型")) {
			String seq = excelColIndexToStr(3);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为数据类型");
			b = true;
		}

		String function = getCellValue(titleRow, 3).trim(); // 第一行的第三格数据
		if (!function.equals("日期格式")) {
			String seq = excelColIndexToStr(4);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为日期格式");
			b = true;
		}

		String functionName = getCellValue(titleRow, 4).trim(); // 第一行的第四格数据
		if (!functionName.equals("小数点后几位数")) {
			String seq = excelColIndexToStr(5);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为小数点后几位数");
			b = true;
		}

		String tradeCode = getCellValue(titleRow, 5).trim(); // 第一行的第四格数据
		if (!tradeCode.equals("请求报文字段")) {
			String seq = excelColIndexToStr(6);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为请求报文");
			b = true;
		}

		String dataType = getCellValue(titleRow, 6).trim(); // 第一行的第八格数据
		if (!dataType.equals("数据类型")) {
			String seq = excelColIndexToStr(7);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为数据类型");
			b = true;
		}

		String length = getCellValue(titleRow, 7).trim(); // 第一行的第八格数据
		if (!length.equals("字段长度")) {
			String seq = excelColIndexToStr(8);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为字段长度");
			b = true;
		}

		String dateFormat = getCellValue(titleRow, 8).trim(); // 第一行的第八格数据
		if (!dateFormat.equals("日期格式")) {
			String seq = excelColIndexToStr(9);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为日期格式");
			b = true;
		}

		String endPoint = getCellValue(titleRow, 9).trim(); // 第一行的第八格数据
		if (!endPoint.equals("小数点后几位数")) {
			String seq = excelColIndexToStr(10);
			JOptionPane.showMessageDialog(frame, "转换关系表第" + seq + "列应为小数点后几位数");
			b = true;
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

}
