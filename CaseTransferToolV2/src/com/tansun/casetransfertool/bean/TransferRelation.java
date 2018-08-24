package com.tansun.casetransfertool.bean;

import java.io.Serializable;
/**
 * 
* @ClassName: TransferRelation
* @Description: 转换关系实体类
* @company com.tansun
* @author lzx
* @date 2017-12-6 上午10:50:11
*
 */
public class TransferRelation implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2123615718020580486L;

	private String i_reqReport; // i 接口请求报文

	private String i_repReport; // 应答报文

	private String i_dataType; // 数据类型

	private String i_filedlength; // 字段长度

	private String i_dateFormat;// 日期格式

	private String i_endPoint;// 小数点后几位数

	private String u_name;// 数据项名称

	private String u_dataType; // 数据类型

	private String u_filedlength; // 字段长度

	private String u_dateFormat;// 日期格式

	private String u_endPoint;// 小数点后几位数

	private String isTranfer; // 是否转换

	private String transferDependency; // 转换依据

	private String MappingRelation;// 映射关系

	public String getI_reqReport() {
		return i_reqReport;
	}

	public void setI_reqReport(String i_reqReport) {
		this.i_reqReport = i_reqReport;
	}

	public String getI_repReport() {
		return i_repReport;
	}

	public void setI_repReport(String i_repReport) {
		this.i_repReport = i_repReport;
	}

	public String getI_dataType() {
		return i_dataType;
	}

	public void setI_dataType(String i_dataType) {
		this.i_dataType = i_dataType;
	}

	public String getI_filedlength() {
		return i_filedlength;
	}

	public void setI_filedlength(String i_filedlength) {
		this.i_filedlength = i_filedlength;
	}

	public String getI_dateFormat() {
		return i_dateFormat;
	}

	public void setI_dateFormat(String i_dateFormat) {
		this.i_dateFormat = i_dateFormat;
	}

	public String getI_endPoint() {
		return i_endPoint;
	}

	public void setI_endPoint(String i_endPoint) {
		this.i_endPoint = i_endPoint;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_dataType() {
		return u_dataType;
	}

	public void setU_dataType(String u_dataType) {
		this.u_dataType = u_dataType;
	}

	public String getU_filedlength() {
		return u_filedlength;
	}

	public void setU_filedlength(String u_filedlength) {
		this.u_filedlength = u_filedlength;
	}

	public String getU_dateFormat() {
		return u_dateFormat;
	}

	public void setU_dateFormat(String u_dateFormat) {
		this.u_dateFormat = u_dateFormat;
	}

	public String getU_endPoint() {
		return u_endPoint;
	}

	public void setU_endPoint(String u_endPoint) {
		this.u_endPoint = u_endPoint;
	}

	public String getIsTranfer() {
		return isTranfer;
	}

	public void setIsTranfer(String isTranfer) {
		this.isTranfer = isTranfer;
	}

	public String getTransferDependency() {
		return transferDependency;
	}

	public void setTransferDependency(String transferDependency) {
		this.transferDependency = transferDependency;
	}

	public String getMappingRelation() {
		return MappingRelation;
	}

	public void setMappingRelation(String mappingRelation) {
		MappingRelation = mappingRelation;
	}

}
