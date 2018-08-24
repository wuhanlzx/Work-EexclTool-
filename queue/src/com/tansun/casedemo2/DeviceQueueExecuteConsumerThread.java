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
			// ��ȡ����һ������
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
	 * ѭ��������һ���ڵ������
	 * @param reqeustURL
	 * @param timeOut
	 * @param rw
	 */
	private void postRequestMessage(String reqeustURL, int timeOut, RequestWait rw) {

		int count = SEARCH_COUNT;
		try {
			System.out
					.println("�豸:" + device_id + "���ڷ���:" + rw.getRequest() + "����·��:" + reqeustURL + "��ʱʱ��:" + timeOut);
			Thread.sleep(3000);
			ResponseQueueContainer.getInstance().add(rw.getCase_id() + "," + rw.getSubNodeId() + "," + device_id,
					"�豸:" + device_id + rw.getRequest() + "���");
			Thread.sleep(2000);// �߳��������ڽ���ش�
			String nextSubNodeId = rw.getNextSubNodeId();
			// ˵��������һ���ӽڵ���ô��Ҫ�ȴ���һ���˽ڵ������������ȴ���
			if (nextSubNodeId != null && !"".equals(nextSubNodeId)) {
				boolean flag = true;
				while (flag) {
					if (count == 0) {
						System.out.println(
								"�豸:" + device_id + "�涨ʱ����δ�ҵ�����" + rw.getCase_id() + "����һ���ӽڵ�" + nextSubNodeId);
						return;
					}
					Map<String, RequestWait> deviceRequestMap = RequestWaitManager.getInstance()
							.getRequestByDeviceId(device_id);
					// ��ѯ��һ�����ڵ������
					RequestWait requestWait = deviceRequestMap.get(rw.getCase_id() + "," + rw.getNextSubNodeId());
					// �ҵ���
					if (requestWait != null) {
						flag = false;
						// ɾ��mao�е�����
						deviceRequestMap.remove(rw.getCase_id() + "," + rw.getNextSubNodeId());
						postRequestMessage(reqeustURL, timeOut, requestWait);
						ResponseQueueContainer.getInstance().add(
								requestWait.getCase_id() + "," + requestWait.getSubNodeId() + "," + device_id,
								"�豸:" + device_id + requestWait.getRequest() + "���");
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
	 * ���UI�����ַ
	 * @return	UI�����ַ
	 */
	/*public String getUIServiceAddress(){
		Configuration sysconfig = ConfigurationRegistery.getInstance().getConfiguration(ConfigType.SYS);
		String serviceUri = sysconfig.getConfigValue("System", "UIExcutorConnect", "UIHttpService");
		Assert.notNull(serviceUri, "����UI�����ַδ���ã�����sys-config.xml�ļ�.");
//		Assert.is(serviceUri, "����UI�����ַδ���ã�����sys-config.xml�ļ�.");
		return serviceUri;
	}*/
	
	/**
	 * ���UI����ʱʱ��
	 * @return	UI����ʱʱ��
	 */
	/*public int getUIServiceTimeOut(){
		Configuration sysconfig = ConfigurationRegistery.getInstance().getConfiguration(ConfigType.SYS);
		String timeOut = sysconfig.getConfigValue("System", "UIExcutorConnect", "Connect_TimeOut");
		Assert.notNull(timeOut, "����UI����ʱʱ��δ���ã�����sys-config.xml�ļ�.");
		return Integer.parseInt(timeOut);
	}*/
	
	
}
