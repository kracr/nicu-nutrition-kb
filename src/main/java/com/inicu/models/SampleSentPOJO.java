package com.inicu.models;

import com.inicu.postgres.entities.InvestigationOrdered;

import java.sql.Timestamp;
import java.util.List;


public class SampleSentPOJO {
	
	private List<InvestigationOrdered> ordersList;
	private String comments;
	private String samplevolume;
	private String sampletype;
	private Timestamp sentdate;
	private String uhid;
	private String loggeduser;
	private Timestamp orderdate;
	
	public List<InvestigationOrdered> getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(List<InvestigationOrdered> ordersList) {
		this.ordersList = ordersList;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSamplevolume() {
		return samplevolume;
	}
	public void setSamplevolume(String samplevolume) {
		this.samplevolume = samplevolume;
	}
	public String getSampletype() {
		return sampletype;
	}
	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}
	public Timestamp getSentdate() {
		return sentdate;
	}
	public void setSentdate(Timestamp sentdate) {
		this.sentdate = sentdate;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	public Timestamp getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Timestamp orderdate) {
		this.orderdate = orderdate;
	}
	
}
