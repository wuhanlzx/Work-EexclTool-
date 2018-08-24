package com.tansun.casedemo2;

import java.util.Map;


public class DeviceQueueExecuteConsumerThread implements Runnable {
	
	private static final long SLEEP_TIME = 1000;
	
	private static final int SEARCH_COUNT = 10;
	
	private String device_id; 
	
	
	
	public DeviceQueueExecuteConsumerThread(String device_id) {
		this.device_id = device_id;
	}

	@Override
	public synchronized void run() {
		String reqeustURL = "http://localhost/127.0.0.1";
		int timeOut = 6000;
		while (true) {
			Map<String, RequestWait> deviceRequestMap = RequestWaitManager.getInstance()
					.getRequestByDeviceId(device_id);
			// 获取到第一条数据
			if( deviceRequestMap.keySet().iterator().hasNext()){
				String key = deviceRequestMap.keySet().iterator().next();
				RequestWait rw = deviceRequestMap.get(key);
				deviceRequestMap.remove(key);
				postRequestMessage(reqeustURL, timeOut, rw);
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
	 * 循环调用下一个节点的请求
	 * @param reqeustURL
	 * @param timeOut
	 * @param rw
	 */
	private void postRequestMessage(String reqeustURL, int timeOut, RequestWait rw) {

		int count = SEARCH_COUNT;
		try {
			System.out
					.println("设备:" + device_id + "正在发送:" + rw.getRequest() + "请求路径:" + reqeustURL + "超时时间:" + timeOut);
			Thread.sleep(3000);
			ResponseQueueContainer.getInstance().add(rw.getCase_id() + "," + rw.getSubNodeId() + "," + device_id,
					"设备:" + device_id + rw.getRequest() + "完成");
			Thread.sleep(2000);// 线程休眠用于结果回传
			String nextSubNodeId = rw.getNextSubNodeId();
			// 说明存在下一个子节点那么就要等待下一个此节点的请求过来优先处理
			if (nextSubNodeId != null && !"".equals(nextSubNodeId)) {
				boolean flag = true;
				while (flag) {
					if (count == 0) {
						System.out.println(
								"设备:" + device_id + "规定时间内未找到案例" + rw.getCase_id() + "的下一个子节点" + nextSubNodeId);
						return;
					}
					Map<String, RequestWait> deviceRequestMap = RequestWaitManager.getInstance()
							.getRequestByDeviceId(device_id);
					// 查询下一个个节点的请求
					RequestWait requestWait = deviceRequestMap.get(rw.getCase_id() + "," + rw.getNextSubNodeId());
					// 找到了
					if (requestWait != null) {
						flag = false;
						// 删除mao中的数据
						deviceRequestMap.remove(rw.getCase_id() + "," + rw.getNextSubNodeId());
						postRequestMessage(reqeustURL, timeOut, requestWait);
						ResponseQueueContainer.getInstance().add(
								requestWait.getCase_id() + "," + requestWait.getSubNodeId() + "," + device_id,
								"设备:" + device_id + requestWait.getRequest() + "完成");
					} else {
						Thread.sleep(SLEEP_TIME);
						count--;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
