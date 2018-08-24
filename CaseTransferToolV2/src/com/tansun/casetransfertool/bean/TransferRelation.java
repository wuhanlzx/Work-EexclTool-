package com.tansun.casetransfertool.bean;

import java.io.Serializable;
/**
 * 
* @ClassName: TransferRelation
* @Description: ת����ϵʵ����
* @company com.tansun
* @author lzx
* @date 2017-12-6 ����10:50:11
*
 */
public class TransferRelation implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2123615718020580486L;

	private String i_reqReport; // i �ӿ�������

	private String i_repReport; // Ӧ����

	private String i_dataType; // ��������

	private String i_filedlength; // �ֶγ���

	private String i_dateFormat;// ���ڸ�ʽ

	private String i_endPoint;// С�����λ��

	private String u_name;// ����������

	private String u_dataType; // ��������

	private String u_filedlength; // �ֶγ���

	private String u_dateFormat;// ���ڸ�ʽ

	private String u_endPoint;// С�����λ��

	private String isTranfer; // �Ƿ�ת��

	private String transferDependency; // ת������

	private String MappingRelation;// ӳ���ϵ

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
