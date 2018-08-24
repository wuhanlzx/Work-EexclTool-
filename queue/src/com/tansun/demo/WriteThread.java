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
			sop("W:开始将数据写入:但等个5秒让我们观察...");
			Thread.sleep(5000); // 释放cpu执行权5秒
			pout.write("W: writePiped 数据...".getBytes()); // 管道输出流
			pout.close();
		} catch (Exception e) {
			throw new RuntimeException("W:WriteThread写入失败...");
		}
	}

	public static void sop(Object obj) // 打印
	{
		System.out.println(obj);
	}
}
