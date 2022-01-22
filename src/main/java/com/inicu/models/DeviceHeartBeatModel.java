package com.inicu.models;

import java.sql.Timestamp;

public class DeviceHeartBeatModel {
	String boxSerialNo;
	Timestamp heartBeatFrom;
	Timestamp heartBeatTo;
	
	public String getBoxSerialNo() {
		return boxSerialNo;
	}
	public void setBoxSerialNo(String boxSerialNo) {
		this.boxSerialNo = boxSerialNo;
	}
	public Timestamp getHeartBeatFrom() {
		return heartBeatFrom;
	}
	public void setHeartBeatFrom(Timestamp heartBeatFrom) {
		this.heartBeatFrom = heartBeatFrom;
	}
	public Timestamp getHeartBeatTo() {
		return heartBeatTo;
	}
	public void setHeartBeatTo(Timestamp heartBeatTo) {
		this.heartBeatTo = heartBeatTo;
	}
	
	

}
