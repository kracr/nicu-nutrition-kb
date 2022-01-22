package com.inicu.cassandra.models;

import java.util.List;

import com.inicu.models.BoxJSON;
import com.inicu.models.RegisterDeviceDropdownJSon;
import com.inicu.models.RegisteredDevicesJson;
import com.inicu.models.ResponseMessageObject;

public class NewRegisteredDevicesJSON {
	/*public BoxJSON getBox() {
		return box;
	}
	public void setBox(BoxJSON box) {
		this.box = box;
	}*/
	public List<BoxJSON> getRegisteredDevices() {
		return registeredDevices;
	}
	public void setRegisteredDevices(List<BoxJSON> registeredDevices) {
		this.registeredDevices = registeredDevices;
	}
	public RegisterDeviceDropdownJSon getDropDowns() {
		return dropDowns;
	}
	public void setDropDowns(RegisterDeviceDropdownJSon dropDowns) {
		this.dropDowns = dropDowns;
	}
	public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	//BoxJSON box;
	List<BoxJSON> registeredDevices;
	RegisterDeviceDropdownJSon dropDowns;
	ResponseMessageObject response;
}
