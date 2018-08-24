package com.tansun.casedemo;

public class Test {
	public static void main(String[] args) {
		
		String device_id = "";
		
		//String request_id = "«Î«Û“ª";
		
		//String case_id = "c001";
		
		//String subNodeId = "c001_1";
		
		for (int i = 0; i <10; i++) {
			for (int j = 0; j <3 ; j++) {
				 device_id="d00"+String.valueOf(j+1);
				 String case_id = "c00"+String.valueOf(i+1);
				 String subNodeId = "c00_"+String.valueOf(i+1);
				 String request_id = "«Î«Û"+String.valueOf(i+1);
				 RequestThread rt = new RequestThread(request_id, device_id, case_id, subNodeId);
				 Thread thread = new Thread(rt);
				 thread.start();
				 try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 
			}
			/*String message = RequestWaitManager.getInstance().addRequest(device_id, request_id, case_id, subNodeId);
			System.out.println(message);*/
			
			/*try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		
		
	}
}
