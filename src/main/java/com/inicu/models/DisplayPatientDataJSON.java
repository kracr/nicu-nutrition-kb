package com.inicu.models;

import java.util.List;

public class DisplayPatientDataJSON {
	private String bedNumber;
	private String uhid;
	private boolean bedStatus;
	private String roomNumber;
	private boolean admissionstatus;
	private String message;
	public String getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public boolean isBedStatus() {
		return bedStatus;
	}
	public void setBedStatus(boolean bedStatus) {
		this.bedStatus = bedStatus;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public boolean isAdmissionstatus() {
		return admissionstatus;
	}
	public void setAdmissionstatus(boolean admissionstatus) {
		this.admissionstatus = admissionstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
