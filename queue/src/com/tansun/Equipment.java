package com.tansun;

import java.io.Serializable;

public class Equipment implements Serializable {
	
	
	private String uuid;
	
	private String deviceName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	
	
	 
	
	
}
