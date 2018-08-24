package com.tansun.casedemo2;

import java.io.Serializable;
/**
 * 
 * @ClassName: RequestWait 
 * @Description: һ�������
 * @author: lzx
 * @date: 2018��6��15�� ����9:18:22 
 *
 */
public class RequestWait implements Serializable {
	
	
	private static final long serialVersionUID = 5469214243467841246L;
	
	private String device_id;

	//��ǰ��������
	private String request;
	
	//����İ���id
	private String case_id;
	
	//������ӽڵ�id
	private String subNodeId;
	
	
	private Thread thread;
	
	//�������һ���ڵ�
	private String nextSubNodeId; //����һ������Ľڵ���ӽڵ�id�����ô����ͬһ�������µĽڵ�
	

	
	
	public String getDevice_id() {
		return device_id;
	}


	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}


	public String getNextSubNodeId() {
		return nextSubNodeId;
	}


	public void setNextSubNodeId(String nextSubNodeId) {
		this.nextSubNodeId = nextSubNodeId;
	}


	public Thread getThread() {
		return thread;
	}


	public void setThread(Thread thread) {
		this.thread = thread;
	}


	public RequestWait( String request, String case_id, String subNodeId ,String nextSubNodeId,String device_id) {
	
		this.request = request;
		this.case_id = case_id;
		this.subNodeId = subNodeId;
		this.nextSubNodeId = nextSubNodeId;
		this.device_id = device_id;
				
	}


	public String getRequest() {
		return request;
	}


	public void setRequest(String request) {
		this.request = request;
	}




	public String getCase_id() {
		return case_id;
	}


	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}


	public String getSubNodeId() {
		return subNodeId;
	}


	public void setSubNodeId(String subNodeId) {
		this.subNodeId = subNodeId;
	}



}
