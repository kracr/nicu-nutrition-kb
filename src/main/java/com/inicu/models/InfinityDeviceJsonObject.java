package com.inicu.models;

import java.util.HashMap;
import java.util.List;

import com.inicu.postgres.entities.Hl7DeviceMapping;

public class InfinityDeviceJsonObject {

	private String icu;
	private String hl7Bed;
	private String bedid;
	private String uhid;
	List<KeyValueObj> icuDropDown;
	List<KeyValueObj> bedDropDown;
	ResponseMessageObject response;	
	HashMap<String,List<String>> bedlist;
	List<Hl7DeviceMapping> previousBedList;
	
	public List<Hl7DeviceMapping> getPreviousBedList() {
		return previousBedList;
	}
	public void setPreviousBedList(List<Hl7DeviceMapping> previousBedList) {
		this.previousBedList = previousBedList;
	}
	public HashMap<String, List<String>> getBedlist() {
		return bedlist;
	}
	public void setBedlist(HashMap<String, List<String>> bedlist) {
		this.bedlist = bedlist;
	}
	public ResponseMessageObject getResponse() {
		return response;
	}
	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	public String getIcu() {
		return icu;
	}
	public void setIcu(String icu) {
		this.icu = icu;
	}
	public String getHl7Bed() {
		return hl7Bed;
	}
	public void setHl7Bed(String hl7Bed) {
		this.hl7Bed = hl7Bed;
	}
	public String getBedid() {
		return bedid;
	}
	public void setBedid(String bedid) {
		this.bedid = bedid;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public List<KeyValueObj> getIcuDropDown() {
		return icuDropDown;
	}
	public void setIcuDropDown(List<KeyValueObj> icuDropDown) {
		this.icuDropDown = icuDropDown;
	}
	public List<KeyValueObj> getBedDropDown() {
		return bedDropDown;
	}
	public void setBedDropDown(List<KeyValueObj> bedDropDown) {
		this.bedDropDown = bedDropDown;
	}
}
