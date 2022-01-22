package com.inicu.cassandra.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.models.KeyValueObj;

public class AddDeviceDropDowns {

	List<KeyValueObj> deviceType;
	HashMap<String,List<String>> brandName;
	List<KeyValueObj> deviceName;
	List<KeyValueObj> deviceName2;
	List<KeyValueObj> tinyDeviceNames;
	
	public List<KeyValueObj> getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(List<KeyValueObj> deviceName) {
		this.deviceName = deviceName;
	}
	public List<KeyValueObj> getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(List<KeyValueObj> deviceType) {
		this.deviceType = deviceType;
	}
	public HashMap<String, List<String>> getBrandName() {
		return brandName;
	}
	public void setBrandName(HashMap<String, List<String>> brandName) {
		this.brandName = brandName;
	}
	public List<KeyValueObj> getDeviceName2() {
		return deviceName2;
	}
	public void setDeviceName2(List<KeyValueObj> deviceName2) {
		this.deviceName2 = deviceName2;
	}

	public List<KeyValueObj> getTinyDeviceNames() {
		return tinyDeviceNames;
	}

	public void setTinyDeviceNames(List<KeyValueObj> tinyDeviceNames) {
		this.tinyDeviceNames = tinyDeviceNames;
	}
}
