package com.inicu.models;

import java.util.List;

public class NewDeviceJSON {
	RegisterDeviceDropdownJSon dropdown;
	BoxJSON box;
	List<String> macid;
	
	public List<String> getMacid() {
		return macid;
	}
	public void setMacid(List<String> macid) {
		this.macid = macid;
	}
	public BoxJSON getBox() {
		return box;
	}
	public void setBox(BoxJSON box) {
		this.box = box;
	}
	public RegisterDeviceDropdownJSon getDropdown() {
		return dropdown;
	}
	public void setDropdown(RegisterDeviceDropdownJSon dropdown) {
		this.dropdown = dropdown;
	}


}
