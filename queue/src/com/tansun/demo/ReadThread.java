package com.tansun.demo;

import java.io.PipedInputStream;

public class ReadThread implements Runnable {

	private PipedInputStream pin;

	ReadThread(PipedInputStream pin){
		this.pin = pin;
	}
	@Override
	public void run() // ���ڱ���Ҫ����run����,�������ﲻ����,ֻ��try
	{
		try {
			sop("R:��ȡǰû������,������...�ȴ����ݴ����������������̨...");
			byte[] buf = new byte[1024];
			int len = pin.read(buf); // read����
			sop("R:��ȡ���ݳɹ�,�������...");

			String s = new String(buf, 0, len);
			sop(s); // ����ȡ�����������ַ������ַ�����ӡ����
			pin.close();
		} catch (Exception e) {
			throw new RuntimeException("R:�ܵ���ȡ��ʧ��!");
		}
	}

	public static void sop(Object obj) // ��ӡ
	{
		System.out.println(obj);
	}
}
