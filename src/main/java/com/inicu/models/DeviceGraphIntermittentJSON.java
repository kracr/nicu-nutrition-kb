package com.inicu.models;
import java.sql.Timestamp;


public class DeviceGraphIntermittentJSON {
	String itemname;
	Timestamp executionDate;
	Float value;
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Timestamp getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Timestamp executionDate) {
		this.executionDate = executionDate;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}

}





