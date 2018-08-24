package com.tansun.casedemo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

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
	private static Map<String, ConcurrentLinkedQueue<RequestWait>> moblieDeviceQueue = Collections.synchronizedMap(new HashMap<String, ConcurrentLinkedQueue<RequestWait>>());
	
	
	//私有构造方法
	private  MoblieRequestQueueContainer(){
		
	}

	//获取实例
	public static MoblieRequestQueueContainer getInstance(){
		return mobileQueueContainer;
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
	public synchronized void addDeviceQueue(String device_id,RequestWait rw){
		
		boolean hasDevice= moblieDeviceQueue.containsKey(device_id);
		
		if(!hasDevice){
			System.err.println("创建队列"+device_id+"请求是:"+rw.getRequest());
			ConcurrentLinkedQueue<RequestWait> singleDeviceQueue = new ConcurrentLinkedQueue<RequestWait>();	
			singleDeviceQueue.add(rw);
			moblieDeviceQueue.put(device_id, singleDeviceQueue);
			DeviceQueueExecuteConsumerThread consumerThread = new DeviceQueueExecuteConsumerThread(device_id);
			Thread t= new Thread(consumerThread);
			t.start();
		}else{
			System.err.println("加入设备队列"+device_id+"请求是:"+rw.getRequest());
			ConcurrentLinkedQueue<RequestWait> singleDeviceQueue = moblieDeviceQueue.get(device_id);
			singleDeviceQueue.add(rw);
			moblieDeviceQueue.put(device_id, singleDeviceQueue);
		}
	}
	/**
	 * 
	* @Title: getRequestWait 
	* @Description:根据case_id从对列中获取请求数据
	* @param device_id
	* @return RequestWait
	* @author lzx
	* @date 2018年6月15日上午10:53:09
	 */
	public synchronized ConcurrentLinkedQueue<RequestWait>  getRequestWait(String device_id){
		ConcurrentLinkedQueue<RequestWait> rws = moblieDeviceQueue.get(device_id);
		//RequestWait element = rws.element();
		//rws.contains(o)
		return rws;
	}
	
	
	
	
	
	
}
