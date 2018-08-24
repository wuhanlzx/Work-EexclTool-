package com.tansun.casetransfertool.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: InterfaceReport
* @Description: 接口报文实体类
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午10:49:42
*
 */
public class InterfaceReport implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2974773030363110164L;

	private String workName; // 任务名称

	private String workType; // 任务类型

	private List<InputItem> requestData; // 请求数据(相当于是输入项)

	private List<InputItem> exceptResult;// 预期结果相关的

	private String exceptResultType;// 预期结果类型

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public List<InputItem> getRequestData() {
		return requestData;
	}

	public void setRequestData(List<InputItem> requestData) {
		this.requestData = requestData;
	}

	public List<InputItem> getExceptResult() {
		return exceptResult;
	}

	public void setExceptResult(List<InputItem> exceptResult) {
		this.exceptResult = exceptResult;
	}

	public String getExceptResultType() {
		return exceptResultType;
	}

	public void setExceptResultType(String exceptResultType) {
		this.exceptResultType = exceptResultType;
	}

}
