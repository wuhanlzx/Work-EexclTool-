package com.tansun.casedemo2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	private Map<String,Map<String, RequestWait>> moblieDeviceQueue = Collections.synchronizedMap(new HashMap<String, Map<String, RequestWait>>());
	
	
	//˽�й��췽��
	private  MoblieRequestQueueContainer(){
		
	}

	//��ȡʵ��
	public static MoblieRequestQueueContainer getInstance(){
		return mobileQueueContainer;
	}
	
	//�����ݷ��뵽�豸������
	public synchronized void putDeviceInfoToQueue(String device_id, Map <String, RequestWait>deviceRequestMap){
		moblieDeviceQueue.put(device_id, deviceRequestMap);
	}
	
	
	public synchronized Map<String, RequestWait>  getDeviceQueue(String device_id){
		return moblieDeviceQueue.get(device_id);
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
	public synchronized void addDeviceQueue(RequestWait rw){
		
		boolean hasDevice= moblieDeviceQueue.containsKey(rw.getDevice_id());
		
		if(!hasDevice){
			System.err.println("��������"+rw.getDevice_id()+"����id:"+rw.getCase_id()+"������:"+rw.getRequest());
			//LinkedList<RequestWait> singleDeviceQueue = new LinkedList<RequestWait>();
			Map<String ,RequestWait> deviceRequestMap =  Collections.synchronizedMap(new LinkedHashMap<String ,RequestWait>());
			deviceRequestMap.put(rw.getCase_id()+","+rw.getSubNodeId(),rw);
			putDeviceInfoToQueue(rw.getDevice_id(), deviceRequestMap);
			DeviceQueueExecuteConsumerThread consumerThread = new DeviceQueueExecuteConsumerThread(rw.getDevice_id());
			Thread t= new Thread(consumerThread);
			t.start();
		}else{
			System.err.println("�����豸����"+rw.getDevice_id()+"����id:"+rw.getCase_id()+"������:"+rw.getRequest());
			Map<String ,RequestWait> deviceRequestMap = getDeviceQueue(rw.getDevice_id());
			deviceRequestMap.put(rw.getCase_id()+","+rw.getSubNodeId(),rw);
		}
	}
	
}
