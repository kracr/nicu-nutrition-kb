package com.inicu.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="vw_assesment_respsystem_final")
@NamedQuery(name="VwAssesmentRespsystemFinal.findAll", query="SELECT v FROM VwAssesmentRespsystemFinal v")
public class VwAssesmentRespsystemFinal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String uniquekey;
	
	private Timestamp creationtime;
	
	private String uhid;
	
	private String category;
	
	private String resp_system_status;
	
	private String event;
	
	private String eventstatus;
	
	private String duration;
	
	private String cause;
	
	private String progressnotes;
	
	private String comment;

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

	public String getResp_system_status() {
		return resp_system_status;
	}

	public void setResp_system_status(String resp_system_status) {
		this.resp_system_status = resp_system_status;
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

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
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
}
