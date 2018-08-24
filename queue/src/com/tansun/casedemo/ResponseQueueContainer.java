package com.tansun.casedemo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ResponseQueueContainer 
 * @Description: ���󷵻��Ľ��
 * @author: lzx
 * @date: 2018��6��15�� ����11:12:23 
 *
 */
public class ResponseQueueContainer {
	
	private static  ResponseQueueContainer instance = new  ResponseQueueContainer();
	
	private static Map<String, String> responseMap = Collections.synchronizedMap(new HashMap<String, String>());
	
	
	public static ResponseQueueContainer getInstance(){
		return instance;
	}
	
	/**
	 *
	* @Title: add 
	* @Description:  ���Ԫ�ص�responseMap��
	* @param caseInfo
	* @param response void
	* @author lzx
	* @date 2018��6��15������1:33:48
	 */
	public synchronized void add(String caseInfo,String response){
		responseMap.put(caseInfo, response);
	}
	/**
	 * 
	* @Title: get 
	* @Description:��ȡresponse���� 
	* @param caseInfo
	* @return Message
	* @author lzx
	* @date 2018��6��15������1:42:09
	 */
	public synchronized String getResponse(String key){
		return responseMap.get(key);
	}
	
	public synchronized void removeHandledRequest(String id){
		responseMap.remove(id);
	}
	
	
	
	
	
}	
