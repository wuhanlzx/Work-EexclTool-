package com.tansun.casetransfertool.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
* @ClassName: Case
* @Description: 案例实体类接口ui可公用
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午10:48:29
*
 */
public class Case implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5934950789827205779L;

	private String caseCode; // 案例编号

	private String systemNameAndVersion; // 系统名称版本

	private String functionModule; // 功能模块

	private String functionName; // 功能名称

	private String caseType; // 案例类型

	private String caseNature; // 案例性质

	private String priorityLevel; // 优先级

	private String creater; // 编写人

	private String createDate;// 编写日期

	private List<Trade> trades; // 包含的交易集合

	private String caseName; // 案例名称
	
	private  Map<String,String> oppItems ;  //反案例的反数据项
	

	public Map<String, String> getOppItems() {
		return oppItems;
	}

	public void setOppItems(Map<String, String> oppItems) {
		this.oppItems = oppItems;
	}
	
	public void addTrade(Trade trade) {
		if (trades == null) {
			trades = new ArrayList<Trade>();
		}
		trades.add(trade);
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getSystemNameAndVersion() {
		return systemNameAndVersion;
	}

	public void setSystemNameAndVersion(String systemNameAndVersion) {
		this.systemNameAndVersion = systemNameAndVersion;
	}

	public String getFunctionModule() {
		return functionModule;
	}

	public void setFunctionModule(String functionModule) {
		this.functionModule = functionModule;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseNature() {
		return caseNature;
	}

	public void setCaseNature(String caseNature) {
		this.caseNature = caseNature;
	}

	public String getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

}
