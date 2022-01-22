package com.inicu.cassandra.models;

import java.util.List;

import com.inicu.models.KeyValueObj;
import com.inicu.models.ResponseMessageObject;

public class AddDeviceMasterJson {

	AddDeviceTemplateJson device;
	
	List<AddDeviceListJson> listDevice;
	
	List<AddDeviceListJson> pastDevice;
	
	AddDeviceDropDowns dropDowns;
	
	List<KeyValueObj> currentDevice;
	
	ResponseMessageObject response;

	KeyValueObj tinyCurrentDevice;
	
	public List<AddDeviceListJson> getPastDevice() {
		return pastDevice;
	}

	public void setPastDevice(List<AddDeviceListJson> pastDevice) {
		this.pastDevice = pastDevice;
	}

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}

	public List<AddDeviceListJson> getListDevice() {
		return listDevice;
	}

	public void setListDevice(List<AddDeviceListJson> listDevice) {
		this.listDevice = listDevice;
	}

	public List<KeyValueObj> getCurrentDevice() {
		return currentDevice;
	}

	public void setCurrentDevice(List<KeyValueObj> currentDevice) {
		this.currentDevice = currentDevice;
	}

	public AddDeviceTemplateJson getDevice() {
		return device;
	}

	public void setDevice(AddDeviceTemplateJson device) {
		this.device = device;
	}

	public AddDeviceDropDowns getDropDowns() {
		return dropDowns;
	}

	public void setDropDowns(AddDeviceDropDowns dropDowns) {
		this.dropDowns = dropDowns;
	}

	public KeyValueObj getTinyCurrentDevice() {
		return tinyCurrentDevice;
	}

	public void setTinyCurrentDevice(KeyValueObj tinyCurrentDevice) {
		this.tinyCurrentDevice = tinyCurrentDevice;
	}
}
