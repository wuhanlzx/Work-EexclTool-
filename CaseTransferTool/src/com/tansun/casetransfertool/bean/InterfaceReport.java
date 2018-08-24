package com.tansun.casetransfertool.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: InterfaceReport
* @Description: �ӿڱ���ʵ����
* @company com.tansun
* @author lzx
* @date 2017-12-6 ����10:49:42
*
 */
public class InterfaceReport implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2974773030363110164L;

	private String workName; // ��������

	private String workType; // ��������

	private List<InputItem> requestData; // ��������(�൱����������)

	private List<InputItem> exceptResult;// Ԥ�ڽ����ص�

	private String exceptResultType;// Ԥ�ڽ������

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
