package com.tansun.casedemo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * @ClassName: MoblieRequestQueueContainer 
 * @Description: �豸��������
 * @author: lzx
 * @date: 2018��6��15�� ����9:41:14 
 *
 */
public class MoblieRequestQueueContainer {

	private static MoblieRequestQueueContainer mobileQueueContainer = new MoblieRequestQueueContainer();
	
	//�̰߳�ȫ��map
	private static Map<String, ConcurrentLinkedQueue<RequestWait>> moblieDeviceQueue = Collections.synchronizedMap(new HashMap<String, ConcurrentLinkedQueue<RequestWait>>());
	
	
	//˽�й��췽��
	private  MoblieRequestQueueContainer(){
		
	}

	//��ȡʵ��
	public static MoblieRequestQueueContainer getInstance(){
		return mobileQueueContainer;
	}
	
	/**
	 * 
	* @Title: addDeviceQueue 
	* @Description:��������ӵ����������� 
	* @param device_id
	* @param rw void
	* @author lzx
	* @date 2018��6��15������9:41:43
	 */
	public synchronized void addDeviceQueue(String device_id,RequestWait rw){
		
		boolean hasDevice= moblieDeviceQueue.containsKey(device_id);
		
		if(!hasDevice){
			System.err.println("��������"+device_id+"������:"+rw.getRequest());
			ConcurrentLinkedQueue<RequestWait> singleDeviceQueue = new ConcurrentLinkedQueue<RequestWait>();	
			singleDeviceQueue.add(rw);
			moblieDeviceQueue.put(device_id, singleDeviceQueue);
			DeviceQueueExecuteConsumerThread consumerThread = new DeviceQueueExecuteConsumerThread(device_id);
			Thread t= new Thread(consumerThread);
			t.start();
		}else{
			System.err.println("�����豸����"+device_id+"������:"+rw.getRequest());
			ConcurrentLinkedQueue<RequestWait> singleDeviceQueue = moblieDeviceQueue.get(device_id);
			singleDeviceQueue.add(rw);
			moblieDeviceQueue.put(device_id, singleDeviceQueue);
		}
	}
	/**
	 * 
	* @Title: getRequestWait 
	* @Description:����case_id�Ӷ����л�ȡ��������
	* @param device_id
	* @return RequestWait
	* @author lzx
	* @date 2018��6��15������10:53:09
	 */
	public synchronized ConcurrentLinkedQueue<RequestWait>  getRequestWait(String device_id){
		ConcurrentLinkedQueue<RequestWait> rws = moblieDeviceQueue.get(device_id);
		//RequestWait element = rws.element();
		//rws.contains(o)
		return rws;
	}
	
	
	
	
	
	
}
