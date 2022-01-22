package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the nursingorder_assesment database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "nursingorder_assesment")
@NamedQuery(name = "NursingOrderAssesment.findAll", query = "SELECT n FROM NursingOrderAssesment n")
public class NursingOrderAssesment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long nursingorderid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String eventid;

	private String eventname;

	@Column(name = "assessment_type")
	private String assessmentType;

	private String uhid;

	private String actiontype;

	private String actionvalue;

	@Column(name = "actiontaken_time")
	private Timestamp actiontakenTime;

	private String loggeduser;

	@Column(name = "nursing_comments")
	private String nursingComments;

	@Transient
	private boolean status;

	public NursingOrderAssesment() {
		super();
	}

	public Long getNursingorderid() {
		return nursingorderid;
	}

	public void setNursingorderid(Long nursingorderid) {
		this.nursingorderid = nursingorderid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getActionvalue() {
		return actionvalue;
	}

	public void setActionvalue(String actionvalue) {
		this.actionvalue = actionvalue;
	}

	public Timestamp getActiontakenTime() {
		return actiontakenTime;
	}

	public void setActiontakenTime(Timestamp actiontakenTime) {
		this.actiontakenTime = actiontakenTime;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getNursingComments() {
		return nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NursingOrderAssesment [nursingorderid=" + nursingorderid + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", eventid=" + eventid + ", eventname=" + eventname
				+ ", assessmentType=" + assessmentType + ", uhid=" + uhid + ", actiontype=" + actiontype
				+ ", actionvalue=" + actionvalue + ", actiontakenTime=" + actiontakenTime + ", loggeduser=" + loggeduser
				+ ", nursingComments=" + nursingComments + ", status=" + status + "]";
	}

}
