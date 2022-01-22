package com.inicu.models;

import java.util.List;

public class RegisterDeviceMasterJson {

	RegisteredDevicesJson deviceDetails;
	List<RegisteredDevicesJson> registeredDevices;
	RegisterDeviceDropdownJSon dropDowns;
	ResponseMessageObject response;
		public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	public RegisteredDevicesJson getDeviceDetails() {
		return deviceDetails;
	}
	public void setDeviceDetails(RegisteredDevicesJson deviceDetails) {
		this.deviceDetails = deviceDetails;
	}
	public List<RegisteredDevicesJson> getRegisteredDevices() {
		return registeredDevices;
	}
	public void setRegisteredDevices(List<RegisteredDevicesJson> registeredDevices) {
		this.registeredDevices = registeredDevices;
	}
	public RegisterDeviceDropdownJSon getDropDowns() {
		return dropDowns;
	}
	public void setDropDowns(RegisterDeviceDropdownJSon dropDowns) {
		this.dropDowns = dropDowns;
	}
}
