package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="vw_assesment_infection_final")
@NamedQuery(name="VwAssesmentInfectionFinal.findAll", query="SELECT v FROM VwAssesmentInfectionFinal v")
public class VwAssesmentInfectionFinal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String uniquekey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	private Timestamp creationtime;
	
	private String uhid;

	private Integer ageatassesment;

	@Column(name="isageofassesmentinhours",columnDefinition="bool")
	private boolean isageofassesmentinhours;

	private String loggeduser;
	private String category;
	
	@Column(name="infection_system_status")
	private String infection_system_status;
	
	private String event;
	
	@Column(name="eventstatus")
	private String eventstatus;
	
	@Column(name="duration")
	private String duration;
	
	private String progressnotes;
	
	private String comment;
	
	@Transient
	private String orderlist;
	
	@Column(name="cause")
	private String CauseList;
	
	@Column(name="treatmentaction")
	private String treatment;
	
	

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

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public String getInfection_system_status() {
		return infection_system_status;
	}

	public void setInfection_system_status(String infection_system_status) {
		this.infection_system_status = infection_system_status;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setIsageofassesmentinhours(boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}
}
