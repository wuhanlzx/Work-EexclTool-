package com.tansun.casetransfertool.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
* @ClassName: Trade
* @Description: ����ʵ����
* @company com.tansun
* @author lzx
* @date 2017-12-6 ����10:49:58
*
 */
public class Trade implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -7856277081226218114L;

	private String tradeCode;// ������

	private String businessName;// ҵ������

	private String operationStep;// ��������

	private Map<String, String> inputItems;// ֱ��������������ݺ�ֵ����һ��map

	// private Map<String,InterfaceReport> reports; //�����еı���

	private List<InterfaceReport> reports;// �����еı���

	public void addReport(InterfaceReport report) {
		if (reports == null) {
			reports = new ArrayList<InterfaceReport>();
		}
		reports.add(report);
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getOperationStep() {
		return operationStep;
	}

	public void setOperationStep(String operationStep) {
		this.operationStep = operationStep;
	}

	public Map<String, String> getInputItems() {
		return inputItems;
	}

	public void setInputItems(Map<String, String> inputItems) {
		this.inputItems = inputItems;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<InterfaceReport> getReports() {
		return reports;
	}

	public void setReports(List<InterfaceReport> reports) {
		this.reports = reports;
	}

}
