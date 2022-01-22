package com.inicu.postgres.entities;

import scala.math.BigInt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the vw_assesment_respsystem_final database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "vw_assesment_respsystem_final")
@NamedQuery(name = "VwRespsystem.findAll", query = "SELECT v FROM VwRespsystem v")
public class VwRespsystem implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	private String uniquekey;
	
	@Column(name = "id")
	private Long ide;
	
	
	private Timestamp creationtime;

	private String uhid;

	private String category;

	@Column(name = "resp_system_status")
	private String respSystemStatus;

	@Id
	private String event;

	private String eventstatus;

	@Column(name = "ageatassesment")
	private String ageatassesment;

	private String loggeduser;

	private String cause;

	@Transient
	private List<String> causeList;


	private String duration;

	private String progressnotes;
	
	private String comment;
	
	@Transient
	private String orderlist;

	private String treatment;

	@Transient
	private List treatmentList;

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public List getTreatmentList() {
		return treatmentList;
	}

	public void setTreatmentList(List treatmentList) {
		this.treatmentList = treatmentList;
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

	public String getRespSystemStatus() {
		return respSystemStatus;
	}

	public void setRespSystemStatus(String respSystemStatus) {
		this.respSystemStatus = respSystemStatus;
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

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
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



	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}


	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	@Override
	public String toString() {
		return "VwRespsystem [creationtime=" + creationtime + ", uhid=" + uhid + ", category=" + category
				+ ", respSystemStatus=" + respSystemStatus + ", event=" + event + ", eventstatus=" + eventstatus
				+ ", cause=" + cause + ", causeList=" + causeList + ", duration=" + duration + ", progressnotes="
				+ progressnotes + "]";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return ide;
	}

	public void setId(Long id) {
		this.ide = id;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	

	
	
}
