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
				// �豸�����д�������
				if (rws != null && rws.size() > 0) {
					RequestWait rw = rws.poll();
					//postRequestMessage(reqeustURL, timeOut, rw);
					try {
						System.out.println("�豸:"+device_id+"���ڷ���:"+rw.getRequest()+"����·��:"+reqeustURL+"��ʱʱ��:"+timeOut);
						Thread.sleep(3000);
						ResponseQueueContainer.getInstance().add(rw.getCase_id() + "," + rw.getSubNodeId()+","+device_id, "�豸:"+device_id+rw.getRequest()+"���");
						Thread.sleep(2000);//�߳��������ڽ���ش�
					} catch (Exception e) {
						e.printStackTrace();
					}
				//�豸������û����������	
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
