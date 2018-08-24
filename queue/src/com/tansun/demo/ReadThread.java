package com.tansun.demo;

import java.io.PipedInputStream;

public class ReadThread implements Runnable {

	private PipedInputStream pin;

	ReadThread(PipedInputStream pin){
		this.pin = pin;
	}
	@Override
	public void run() // 由于必须要覆盖run方法,所以这里不能抛,只能try
	{
		try {
			sop("R:读取前没有数据,阻塞中...等待数据传过来再输出到控制台...");
			byte[] buf = new byte[1024];
			int len = pin.read(buf); // read阻塞
			sop("R:读取数据成功,阻塞解除...");

			String s = new String(buf, 0, len);
			sop(s); // 将读取的数据流用字符串以字符串打印出来
			pin.close();
		} catch (Exception e) {
			throw new RuntimeException("R:管道读取流失败!");
		}
	}

	public static void sop(Object obj) // 打印
	{
		System.out.println(obj);
	}
}
