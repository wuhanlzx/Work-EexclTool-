package com.tansun.casetransfertool.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 
 * @ClassName: BaseService
 * @Description: service公共方法的抽取
 * @company com.tansun
 * @author lzx
 * @date 2017-12-8 上午9:21:43
 * 
 */
public class BaseService {

	public static String getCellValue(Row Row, int colIndex) {
		String value = "";
		Cell cell = Row.getCell(colIndex);
		if (cell != null) {
			value = getExcelCellValue(cell);
		}
		return value;
	}

	/**
	 * 
	 * @Title: getExcelCellValue
	 * @Description: 根据不同的excel格的数据类型来获取单元格的值
	 * @param @param cell
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getExcelCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			// 注意：一定要设成这个，否则可能会出现乱码
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			switch (cell.getCellType()) {
			// 如果是文本类型
			case Cell.CELL_TYPE_STRING:

				value = cell.getStringCellValue();

				break;
			// 如果是数字类型
			case Cell.CELL_TYPE_NUMERIC:
				// 日期类型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {

					Date date = cell.getDateCellValue();

					if (date != null) {

						value = new SimpleDateFormat("yyyy-MM-dd")

						.format(date);

					} else {

						value = "";

					}
					// 其他的数字类型
				} else {

					value = new DecimalFormat("0").format(cell.getNumericCellValue());
					// value = cell.getNumericCellValue()+"";

				}

				break;

			case Cell.CELL_TYPE_FORMULA:

				// 导入时如果为公式生成的数据则无值

				if (!cell.getStringCellValue().equals("")) {

					value = cell.getStringCellValue();

				} else {

					value = cell.getNumericCellValue() + "";

				}

				break;

			case Cell.CELL_TYPE_BLANK:

				break;

			case Cell.CELL_TYPE_ERROR:

				value = "";

				break;

			case Cell.CELL_TYPE_BOOLEAN:

				value = (cell.getBooleanCellValue() == true ? "Y"

				: "N");

				break;

			default:

				value = "";

			}

		}

		return value;

	}

	/**
	 * 该方法用来将具体的数据转换成Excel中的ABCD列
	 * 
	 * @param int：需要转换成字母的数字
	 * @return column:ABCD列名称
	 * **/
	public static String excelColIndexToStr(int columnIndex) {
		if (columnIndex <= 0) {
			return null;
		}
		String columnStr = "";
		columnIndex--;
		do {
			if (columnStr.length() > 0) {
				columnIndex--;
			}
			columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
			columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
		} while (columnIndex > 0);
		return columnStr;
	}

	/**
	 * 该方法用来将Excel中的ABCD列转换成具体的数据
	 * 
	 * @param column
	 *            :ABCD列名称
	 * @return integer：将字母列名称转换成数字
	 * **/
	public static int excelColStrToNum(String column) {
		int num = 0;
		int result = 0;
		int length = column.length();
		for (int i = 0; i < length; i++) {
			char ch = column.charAt(length - i - 1);
			num = (int) (ch - 'A' + 1);
			num *= Math.pow(26, i);
			result += num;
		}
		return result;
	}

}
