package com.tansun.casedemo;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.security.auth.login.Configuration;


public class DeviceQueueExecuteConsumerThread implements Runnable {
	
	private long SLEEP_TIME = 1000;
	
	private String device_id; 
	
	
	public DeviceQueueExecuteConsumerThread(String device_id) {
		this.device_id = device_id;
	}

	@Override
	public synchronized void run() {
		/*synchronized ("") {*/
			String reqeustURL ="http://localhost/127.0.0.1";
			int timeOut = 6000;
			while (true) {
				ConcurrentLinkedQueue<RequestWait> rws = RequestWaitManager.getInstance()
						.getRequestByDeviceId(device_id);
				// 设备队列中存在请求
				if (rws != null && rws.size() > 0) {
					RequestWait rw = rws.poll();
					//postRequestMessage(reqeustURL, timeOut, rw);
					try {
						System.out.println("设备:"+device_id+"正在发送:"+rw.getRequest()+"请求路径:"+reqeustURL+"超时时间:"+timeOut);
						Thread.sleep(3000);
						ResponseQueueContainer.getInstance().add(rw.getCase_id() + "," + rw.getSubNodeId()+","+device_id, "设备:"+device_id+rw.getRequest()+"完成");
						Thread.sleep(2000);//线程休眠用于结果回传
					} catch (Exception e) {
						e.printStackTrace();
					}
				//设备队列中没有请求休眠	
				} else {
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			//}
		}
	}


	
	/**
	 * 获得UI服务地址
	 * @return	UI服务地址
	 */
	/*public String getUIServiceAddress(){
		Configuration sysconfig = ConfigurationRegistery.getInstance().getConfiguration(ConfigType.SYS);
		String serviceUri = sysconfig.getConfigValue("System", "UIExcutorConnect", "UIHttpService");
		Assert.notNull(serviceUri, "请求UI服务地址未配置，请检查sys-config.xml文件.");
//		Assert.is(serviceUri, "请求UI服务地址未配置，请检查sys-config.xml文件.");
		return serviceUri;
	}*/
	
	/**
	 * 获得UI服务超时时间
	 * @return	UI服务超时时间
	 */
	/*public int getUIServiceTimeOut(){
		Configuration sysconfig = ConfigurationRegistery.getInstance().getConfiguration(ConfigType.SYS);
		String timeOut = sysconfig.getConfigValue("System", "UIExcutorConnect", "Connect_TimeOut");
		Assert.notNull(timeOut, "请求UI服务超时时间未配置，请检查sys-config.xml文件.");
		return Integer.parseInt(timeOut);
	}*/
	
	
}
