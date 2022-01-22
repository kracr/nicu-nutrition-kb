package com.inicu.cassandra.models;

import java.sql.Timestamp;
import java.util.List;


public class AddDeviceTemplateJson {

	private String uhid;
	private String date;
	private List<Integer> deviceList;
	private Integer deviceName;
	private String brandName;
	private String patiendDeviceId;
	private String portNo;
	private String ipAddress;
	private String domainId;
	private String bedId;
	private String deviceType;
    private Timestamp hisDischargeDate;
	private List<Integer> tinyDeviceList;
	private String admissionDate;
	
	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getPatiendDeviceId() {
		return patiendDeviceId;
	}
	public void setPatiendDeviceId(String patiendDeviceId) {
		this.patiendDeviceId = patiendDeviceId;
	}
	public String getPortNo() {
		return portNo;
	}
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public List<Integer> getDeviceList() {
		return deviceList;
	}
	public Integer getDeviceName() {
		return deviceName;
	}
	public void setDeviceList(List<Integer> deviceList) {
		this.deviceList = deviceList;
	}
	public void setDeviceName(Integer deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

    public Timestamp getHisDischargeDate() {
        return hisDischargeDate;
    }

    public void setHisDischargeDate(Timestamp hisDischargeDate) {
        this.hisDischargeDate = hisDischargeDate;
    }

	public List<Integer> getTinyDeviceList() {
		return tinyDeviceList;
	}

	public void setTinyDeviceList(List<Integer> tinyDeviceList) {
		this.tinyDeviceList = tinyDeviceList;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
}
