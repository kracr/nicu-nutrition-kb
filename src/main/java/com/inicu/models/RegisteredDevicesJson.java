package com.inicu.models;

import com.inicu.device.utility.RegistrationConstants;

public class RegisteredDevicesJson {
	private String addedon;
	private String deviceType;
	private String brandName;
	private String deviceName;
	private String macid;
	private String boardname;
	private String usbport;
	private String baudrate;
	private String parity;
	private String stopbits;
	private String databits;

	public String getBoardname() {
		return boardname;
	}

	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}

	public RegisteredDevicesJson(){
		this.addedon = "";
		this.deviceType = "";
		this.brandName = "";
		this.deviceName = "";
		this.macid = "";
	}
	
	public String getAddedon() {
		return addedon;
	}
	public void setAddedon(String addedon) {
		this.addedon = addedon;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getMacid() {
		return macid;
	}
	public void setMacid(String macid) {
		this.macid = macid;
	}

	public String getUsbport() {
		return usbport;
	}

	public void setUsbport(String usbport) {
		this.usbport = usbport;
	}

	public String getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	public String getStopbits() {
		return stopbits;
	}

	public void setStopbits(String stopbits) {
		this.stopbits = stopbits;
	}

	public String getDatabits() {
		return databits;
	}

	public void setDatabits(String databits) {
		this.databits = databits;
	}
}
