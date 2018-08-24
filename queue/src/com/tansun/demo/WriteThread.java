package com.tansun.demo;

import java.io.PipedOutputStream;

public class WriteThread implements Runnable {

	private PipedOutputStream pout;

	WriteThread(PipedOutputStream pout) {
		this.pout = pout;
	}
	
	@Override
	public void run() {
		try {
			sop("W:��ʼ������д��:���ȸ�5�������ǹ۲�...");
			Thread.sleep(5000); // �ͷ�cpuִ��Ȩ5��
			pout.write("W: writePiped ����...".getBytes()); // �ܵ������
			pout.close();
		} catch (Exception e) {
			throw new RuntimeException("W:WriteThreadд��ʧ��...");
		}
	}

	public static void sop(Object obj) // ��ӡ
	{
		System.out.println(obj);
	}
}
