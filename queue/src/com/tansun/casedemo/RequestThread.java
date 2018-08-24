package com.tansun.casedemo;

public class RequestThread implements Runnable {

	private String request_id;
	
	private String device_id;
	
	private String case_id;
	
	private String subNodeId;
	
	public RequestThread(String request_id, String device_id, String case_id, String subNodeId) {
		this.request_id = request_id;
		this.device_id = device_id;
		this.case_id = case_id;
		this.subNodeId = subNodeId;
	}



	@Override
	public synchronized void run() {
		String message = RequestWaitManager.getInstance().addRequest(device_id, request_id, case_id, subNodeId);
		System.out.println(message);
	}

}
