package com.inicu.models;

import org.json.JSONException;
import org.json.JSONObject;

public class BoardDetailJson {
	private String boardName;
	private String macId;
	private String deviceType;
	private String brandName;
	private String deviceName;
	private String communicationtype;
	private String usbport;
	private String baudrate;
	private String parity;
	private String stopbits;
	private String databits;
	
	public BoardDetailJson(String string) {
		// TODO Auto-generated constructor stub
		try {
			JSONObject json= new JSONObject(string);
			this.boardName=json.getString("boardName");
			this.brandName=json.getString("brandName");
			this.deviceName=json.getString("deviceName");
			this.deviceType=json.getString("deviceType");
			if(json.has("macId")) {
				this.macId=json.getString("macId");
			}
			this.communicationtype=json.getString("communicationtype");
			this.usbport=json.getString("usbport");
			this.baudrate=json.getString("baudrate");
			this.parity=json.getString("parity");
			this.databits=json.getString("databits");
			this.stopbits=json.getString("stopbits");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public BoardDetailJson() {
		// TODO Auto-generated constructor stub
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
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getCommunicationtype() {
		return communicationtype;
	}
	public void setCommunicationtype(String communicationtype) {
		this.communicationtype = communicationtype;
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
