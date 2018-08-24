package com.tansun.demo;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Demo {
	public static void main(String[] args) throws Exception {
		PipedInputStream pin = new PipedInputStream();
		PipedOutputStream pout = new PipedOutputStream();
		pin.connect(pout); // �����������������

		ReadThread readTh = new ReadThread(pin);
		WriteThread writeTh = new WriteThread(pout);
		new Thread(readTh).start();
		new Thread(writeTh).start();
	}

}