package com.inicu.kafkacassandra.connector.datamodel;

public class CustomHardwareDataModel {

	private String deviceName;
	private String deviceId;
	private String data;
	private String deviceTime;
	private double fi02;
	private double meanbp;
	private double minairwaypress;
	private double occpressure;
	private double peakbp;
	private double peepbp;
	private double platpressure;
	private double spontrr;
	private String uhid;
	private String deviceType;
	
	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public CustomHardwareDataModel(String deviceName, String deviceId, String data, String deviceTime, String uhid) {
		super();
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.data = data;
		this.deviceTime = deviceTime;
		this.uhid = uhid;
	}

	public CustomHardwareDataModel(String deviceName, String deviceId, String data, String deviceTime, double fi02,
			double meanbp, double minairwaypress, double occpressure, double peakbp, double peepbp, double platpressure,
			double spontrr) {
		super();
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.data = data;
		this.deviceTime = deviceTime;
		this.fi02 = fi02;
		this.meanbp = meanbp;
		this.minairwaypress = minairwaypress;
		this.occpressure = occpressure;
		this.peakbp = peakbp;
		this.peepbp = peepbp;
		this.platpressure = platpressure;
		this.spontrr = spontrr;
	}

	public double getFi02() {
		return fi02;
	}

	public void setFi02(double fi02) {
		this.fi02 = fi02;
	}

	public double getMeanbp() {
		return meanbp;
	}

	public void setMeanbp(double meanbp) {
		this.meanbp = meanbp;
	}

	public double getMinairwaypress() {
		return minairwaypress;
	}

	public void setMinairwaypress(double minairwaypress) {
		this.minairwaypress = minairwaypress;
	}

	public double getOccpressure() {
		return occpressure;
	}

	public void setOccpressure(double occpressure) {
		this.occpressure = occpressure;
	}

	public double getPeakbp() {
		return peakbp;
	}

	public void setPeakbp(double peakbp) {
		this.peakbp = peakbp;
	}

	public double getPeepbp() {
		return peepbp;
	}

	public void setPeepbp(double peepbp) {
		this.peepbp = peepbp;
	}

	public double getPlatpressure() {
		return platpressure;
	}

	public void setPlatpressure(double platpressure) {
		this.platpressure = platpressure;
	}

	public double getSpontrr() {
		return spontrr;
	}

	public void setSpontrr(double spontrr) {
		this.spontrr = spontrr;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(String deviceTime) {
		this.deviceTime = deviceTime;
	}

}
