package com.inicu.models;


/*
 * author: Vedaant
 * Object to store each test item shown in a test 
 */


public class TestItemObject {
	
	private String itemid;
	private String itemname;
	private String itemvalue; // high,low grouping to be done
	private String normalrange;
	private String itemunit;
	private String resultstatus; //Normal/Abnormal
	private String textualHtml;
	
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getItemvalue() {
		return itemvalue;
	}
	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}
	public String getNormalrange() {
		return normalrange;
	}
	public void setNormalrange(String normalrange) {
		this.normalrange = normalrange;
	}
	public String getItemunit() {
		return itemunit;
	}
	public void setItemunit(String itemunit) {
		this.itemunit = itemunit;
	}
	public String getResultstatus() {
		return resultstatus;
	}
	public void setResultstatus(String resultstatus) {
		this.resultstatus = resultstatus;
	}

	public String getTextualHtml() {
		return textualHtml;
	}

	public void setTextualHtml(String textualHtml) {
		this.textualHtml = textualHtml;
	}
}