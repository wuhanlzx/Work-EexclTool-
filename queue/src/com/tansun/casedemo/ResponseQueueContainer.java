package com.tansun.casedemo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ResponseQueueContainer 
 * @Description: 请求返还的结果
 * @author: lzx
 * @date: 2018年6月15日 上午11:12:23 
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
	* @Description:  添加元素到responseMap中
	* @param caseInfo
	* @param response void
	* @author lzx
	* @date 2018年6月15日下午1:33:48
	 */
	public synchronized void add(String caseInfo,String response){
		responseMap.put(caseInfo, response);
	}
	/**
	 * 
	* @Title: get 
	* @Description:获取response对象 
	* @param caseInfo
	* @return Message
	* @author lzx
	* @date 2018年6月15日下午1:42:09
	 */
	public synchronized String getResponse(String key){
		return responseMap.get(key);
	}
	
	public synchronized void removeHandledRequest(String id){
		responseMap.remove(id);
	}
	
	
	
	
	
}	
