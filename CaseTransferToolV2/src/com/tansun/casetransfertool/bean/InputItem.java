package com.tansun.casetransfertool.bean;

import java.io.Serializable;
/**
 * 
* @ClassName: InputItem
* @Description: 输入项实体类
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午10:49:08
*
 */
public class InputItem implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -307496531277448035L;

	private String inputItemValue;// 交易输入项值(输入项名称和值)

	private String chName;// 中文名

	private String enName; // 英文名

	private String valueType;// 取值类型

	public String getInputItemValue() {
		return inputItemValue;
	}

	public void setInputItemValue(String inputItemValue) {
		this.inputItemValue = inputItemValue;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
