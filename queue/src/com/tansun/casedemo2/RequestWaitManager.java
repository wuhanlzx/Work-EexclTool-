package com.tansun.casedemo2;
import java.util.Map;

/**
 * 
 * @ClassName: RequestWaitManger 
 * @Description: 请求控制中心
 * @author: lzx
 * @date: 2018年6月14日 下午4:05:54 
 *
 */
public class RequestWaitManager  {
	
	private long SLEEP_TIME = 1000;
	
	private static RequestWaitManager instance =new RequestWaitManager();
	
	//私有构造方法
	private  RequestWaitManager() {
		
	}
	
	public synchronized static RequestWaitManager getInstance(){
		return instance;
	}
	
	/**
	 * 
	* @Title: addRequest 
	* @Description: 将设备和请求添加到队列中
	* @param device_id
	* @param thread void
	* @author lzx
	 * @throws InterruptedException 
	* @date 2018年6月14日下午4:11:51
	 */
	public  String addRequest(String device_id,String request,String case_id,String subNodeId,String nextSubNodeId) {
		//创建等待的请求对象
		RequestWait requestWait = new RequestWait(request, case_id, subNodeId,nextSubNodeId,device_id);
		//加入队列中
		MoblieRequestQueueContainer.getInstance().addDeviceQueue( requestWait);
		
		while(true){
			/*Map<String, Message> responseMap = ResponseQueueContainer.getInstance().getResponseMap();
			 Message message = responseMap.get(case_id+","+subNodeId);*/
			String message = ResponseQueueContainer.getInstance().getResponse(case_id+","+subNodeId+","+device_id);
			 //找到了响应对象
			if(message!=null){
				ResponseQueueContainer.getInstance().removeHandledRequest(case_id+","+subNodeId+","+device_id);
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

	/**
	 * 
	* @Title: getRequestByDeviceId 
	* @Description: 队列中获取请求数据
	* @param device_id
	* @return RequestWait
	* @author lzx
	* @date 2018年6月15日上午10:54:44
	 */
	public synchronized  Map<String, RequestWait> getRequestByDeviceId(String device_id){
		Map<String, RequestWait> deviceRequestMap = MoblieRequestQueueContainer.getInstance().getDeviceQueue(device_id);
		return deviceRequestMap;
	}

	
}
