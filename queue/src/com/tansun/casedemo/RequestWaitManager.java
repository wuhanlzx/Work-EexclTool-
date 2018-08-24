package com.tansun.casedemo;
import java.util.concurrent.ConcurrentLinkedQueue;




/**
 * 
 * @ClassName: RequestWaitManger 
 * @Description: �����������
 * @author: lzx
 * @date: 2018��6��14�� ����4:05:54 
 *
 */
public class RequestWaitManager  {
	
	private long SLEEP_TIME = 1000;
	
	private static RequestWaitManager instance =new RequestWaitManager();
	
	private String response;
	
	//˽�й��췽��
	private  RequestWaitManager() {
		
	}
	
	public synchronized static RequestWaitManager getInstance(){
		return instance;
	}
	
	
	
	
	
	/**
	 * 
	* @Title: addRequest 
	* @Description: ���豸��������ӵ�������
	* @param device_id
	* @param thread void
	* @author lzx
	 * @throws InterruptedException 
	* @date 2018��6��14������4:11:51
	 */
	public  String addRequest(String device_id,String request,String case_id,String subNodeId) {
		//�����ȴ����������
		RequestWait requestWait = new RequestWait(request, case_id, subNodeId);
		//���������
		MoblieRequestQueueContainer.getInstance().addDeviceQueue(device_id, requestWait);
		
		while(true){
			/*Map<String, Message> responseMap = ResponseQueueContainer.getInstance().getResponseMap();
			 Message message = responseMap.get(case_id+","+subNodeId);*/
			String message = ResponseQueueContainer.getInstance().getResponse(case_id+","+subNodeId+","+device_id);
			 //�ҵ�����Ӧ����
			if(message!=null){
				ResponseQueueContainer.getInstance().removeHandledRequest(case_id+","+subNodeId);
				return message;
			}else{
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	private String researchResponse(String case_id, String subNodeId, RequestWait requestWait) {
		while(true){
			/*Map<String, Message> responseMap = ResponseQueueContainer.getInstance().getResponseMap();
			 Message message = responseMap.get(case_id+","+subNodeId);*/
			String message = ResponseQueueContainer.getInstance().getResponse(case_id+","+subNodeId);
			 //�ҵ�����Ӧ����
			if(message!=null){
				 ResponseQueueContainer.getInstance().removeHandledRequest(case_id+","+subNodeId);
				//ͬʱ����Ӧ���󴫸����߳�
//				 //System.err.println(requestWait.getThread());
//				
//					 requestWait.getThread().notify();
//				
				return message;
			}else{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	/**
	 * 
	* @Title: getRequestByDeviceId 
	* @Description: �����л�ȡ��������
	* @param device_id
	* @return RequestWait
	* @author lzx
	* @date 2018��6��15������10:54:44
	 */
	public synchronized  ConcurrentLinkedQueue<RequestWait> getRequestByDeviceId(String device_id){
		ConcurrentLinkedQueue<RequestWait> requestWait = MoblieRequestQueueContainer.getInstance().getRequestWait(device_id);
		return requestWait;
	}

	

	public synchronized void setResponseMessage(String response){
		this.response= response;
	}
	
	
	
}
