package com.tansun.casedemo2;

public class RequestThread implements Runnable {

	private String request_id;
	
	private String device_id;
	
	private String case_id;
	
	private String subNodeId;
	
	private String nextSubNodeId; //����һ������Ľڵ���ӽڵ�id�����ô����ͬһ�������µĽڵ�
	
	public RequestThread(String request_id, String device_id, String case_id, String subNodeId,String nextSubNodeId) {
		this.request_id = request_id;
		this.device_id = device_id;
		this.case_id = case_id;
		this.subNodeId = subNodeId;
		this.nextSubNodeId = nextSubNodeId;
	}



	@Override
	public synchronized void run() {
		String message = RequestWaitManager.getInstance().addRequest(device_id, request_id, case_id, subNodeId,nextSubNodeId);
		System.out.println(message);
	}

}
