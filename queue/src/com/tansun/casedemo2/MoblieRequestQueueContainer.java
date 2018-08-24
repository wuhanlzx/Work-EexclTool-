package com.tansun.casedemo2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @ClassName: MoblieRequestQueueContainer 
 * @Description: 设备队列容器
 * @author: lzx
 * @date: 2018年6月15日 上午9:41:14 
 *
 */
public class MoblieRequestQueueContainer {

	private static MoblieRequestQueueContainer mobileQueueContainer = new MoblieRequestQueueContainer();
	
	//线程安全的map
	private Map<String,Map<String, RequestWait>> moblieDeviceQueue = Collections.synchronizedMap(new HashMap<String, Map<String, RequestWait>>());
	
	
	//私有构造方法
	private  MoblieRequestQueueContainer(){
		
	}

	//获取实例
	public static MoblieRequestQueueContainer getInstance(){
		return mobileQueueContainer;
	}
	
	//将数据放入到设备队列中
	public synchronized void putDeviceInfoToQueue(String device_id, Map <String, RequestWait>deviceRequestMap){
		moblieDeviceQueue.put(device_id, deviceRequestMap);
	}
	
	
	public synchronized Map<String, RequestWait>  getDeviceQueue(String device_id){
		return moblieDeviceQueue.get(device_id);
	}
	
	/**
	 * 
	* @Title: addDeviceQueue 
	* @Description:将请求添加到队列任务中 
	* @param device_id
	* @param rw void
	* @author lzx
	* @date 2018年6月15日上午9:41:43
	 */
	public synchronized void addDeviceQueue(RequestWait rw){
		
		boolean hasDevice= moblieDeviceQueue.containsKey(rw.getDevice_id());
		
		if(!hasDevice){
			System.err.println("创建队列"+rw.getDevice_id()+"案例id:"+rw.getCase_id()+"请求是:"+rw.getRequest());
			//LinkedList<RequestWait> singleDeviceQueue = new LinkedList<RequestWait>();
			Map<String ,RequestWait> deviceRequestMap =  Collections.synchronizedMap(new LinkedHashMap<String ,RequestWait>());
			deviceRequestMap.put(rw.getCase_id()+","+rw.getSubNodeId(),rw);
			putDeviceInfoToQueue(rw.getDevice_id(), deviceRequestMap);
			DeviceQueueExecuteConsumerThread consumerThread = new DeviceQueueExecuteConsumerThread(rw.getDevice_id());
			Thread t= new Thread(consumerThread);
			t.start();
		}else{
			System.err.println("加入设备队列"+rw.getDevice_id()+"案例id:"+rw.getCase_id()+"请求是:"+rw.getRequest());
			Map<String ,RequestWait> deviceRequestMap = getDeviceQueue(rw.getDevice_id());
			deviceRequestMap.put(rw.getCase_id()+","+rw.getSubNodeId(),rw);
		}
	}
	
}
