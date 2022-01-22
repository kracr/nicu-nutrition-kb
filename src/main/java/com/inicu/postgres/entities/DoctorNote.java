package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;


/**
 * The persistent class for the doctor_notes database table.
 * 
 */
@Entity
@Table(name="doctor_notes")
@NamedQuery(name="DoctorNote.findAll", query="SELECT d FROM DoctorNote d")
public class DoctorNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long doctornoteid;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSS")
	private Timestamp creationtime;

	private Timestamp noteentrytime;
	
	private String doctornotes;

	private String diagnosis;

	private String issues;

	private Timestamp modificationtime;

	private String plan;

	private String uhid;
	
	private String followupnotes;
	private String loggeduser;
	

	public DoctorNote() {
	}

	public Long getDoctornoteid() {
		return this.doctornoteid;
	}

	public void setDoctornoteid(Long doctornoteid) {
		this.doctornoteid = doctornoteid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	
	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getIssues() {
		return this.issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getDoctornotes() {
		return doctornotes;
	}

	public void setDoctornotes(String doctornotes) {
		this.doctornotes = doctornotes;
	}

	public String getFollowupnotes() {
		return followupnotes;
	}

	public void setFollowupnotes(String followupnotes) {
		this.followupnotes = followupnotes;
	}

	public Timestamp getNoteentrytime() {
		return noteentrytime;
	}

	public void setNoteentrytime(Timestamp noteentrytime) {
		this.noteentrytime = noteentrytime;
	}

	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
	
}