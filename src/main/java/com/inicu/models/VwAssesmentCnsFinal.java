package com.inicu.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="vw_assesment_cns_final")
@NamedQuery(name="VwAssesmentCnsFinal.findAll", query="SELECT v FROM VwAssesmentCnsFinal v")
public class VwAssesmentCnsFinal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String uniquekey;
	
	private Timestamp creationtime;
	
	private String uhid;
	
	private String category;



	private Long id;

	private String ageatassesment;

	private String loggeduser;

	@Column(name="treatmentaction")
	private String treatment;

	
	private String cns_system_status;
	
	private String event;
	
	private String eventstatus;
	
	private String duration;
		
	private String progressnotes;
	
	@Transient
	private String orderlist;
	
	@Column(name="cause")
	private String CauseList;

	public String getCauseList() {
		return CauseList;
	}

	public void setCauseList(String causeList) {
		CauseList = causeList;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCns_system_status() {
		return cns_system_status;
	}

	public String getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(String ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}

	public void setCns_system_status(String cns_system_status) {
		this.cns_system_status = cns_system_status;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventstatus() {
		return eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

}
