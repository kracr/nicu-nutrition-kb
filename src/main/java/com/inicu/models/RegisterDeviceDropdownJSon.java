package com.inicu.models;

import java.util.HashMap;
import java.util.List;

public class RegisterDeviceDropdownJSon {

	HashMap<String,List<String>> deviceTypeBrand;
	HashMap<String,List<String>> deviceBrandName;
	List<String> deviceCommunicationType;
	List<String> deviceUsbPort;
	List<String> deviceBaudRate;
	List<String> deviceParity;
	List<String> deviceDataBits;
	List<String> deviceStopBits;

	
	public HashMap<String, List<String>> getDeviceTypeBrand() {
		return deviceTypeBrand;
	}
	public void setDeviceTypeBrand(HashMap<String, List<String>> deviceTypeBrand) {
		this.deviceTypeBrand = deviceTypeBrand;
	}
	public HashMap<String, List<String>> getDeviceBrandName() {
		return deviceBrandName;
	}
	public void setDeviceBrandName(HashMap<String, List<String>> deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}
	public List<String> getDeviceCommunicationType() {
		return deviceCommunicationType;
	}
	public void setDeviceCommunicationType(List<String> deviceCommunicationType) {
		this.deviceCommunicationType = deviceCommunicationType;
	}
	public List<String> getDeviceUsbPort() {
		return deviceUsbPort;
	}
	public void setDeviceUsbPort(List<String> deviceUsbPort) {
		this.deviceUsbPort = deviceUsbPort;
	}
	public List<String> getDeviceBaudRate() {
		return deviceBaudRate;
	}
	public void setDeviceBaudRate(List<String> deviceBaudRate) {
		this.deviceBaudRate = deviceBaudRate;
	}
	public List<String> getDeviceParity() {
		return deviceParity;
	}
	public void setDeviceParity(List<String> deviceParity) {
		this.deviceParity = deviceParity;
	}
	public List<String> getDeviceDataBits() {
		return deviceDataBits;
	}
	public void setDeviceDataBits(List<String> deviceDataBits) {
		this.deviceDataBits = deviceDataBits;
	}
	public List<String> getDeviceStopBits() {
		return deviceStopBits;
	}
	public void setDeviceStopBits(List<String> deviceStopBits) {
		this.deviceStopBits = deviceStopBits;
	}
}
