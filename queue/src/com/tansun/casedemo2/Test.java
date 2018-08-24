package com.tansun.casedemo2;

public class Test {
	public static void main(String[] args) {
		
		String device_id = "d001";
		
		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 3; j++) {
				device_id = "d00" + String.valueOf(j + 1);
				String case_id = "c00" + String.valueOf(i + 1);
				String subNodeId = "c00_" + String.valueOf(i + 1);
				String request_id = "ÇëÇó" + String.valueOf(i + 1);
				RequestThread rt = new RequestThread(request_id, device_id, case_id, subNodeId,
						"c00_" + String.valueOf(i + 11));
				Thread thread = new Thread(rt);
				thread.start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		RequestThread rt = new RequestThread("ÇëÇó11", "d001", "c001", "c00_11",
				"c00_21");
		Thread thread = new Thread(rt);
		thread.start();
		
		rt = new RequestThread("ÇëÇó21", "d001", "c001", "c00_21",
				"");
		thread = new Thread(rt);
		thread.start();
		
		
	}
}
