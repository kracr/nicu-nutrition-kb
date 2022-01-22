package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the vw_doctornotes_list_final database table.
 * 
 */
@Entity
@Table(name="vw_doctornotes_list_final")
@NamedQuery(name="VwDoctornotesListFinal.findAll", query="SELECT v FROM VwDoctornotesListFinal v")
public class VwDoctornotesListFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long assesmentid;
	

	private Timestamp creationtime;

	private String plan;

	private String progressnotes;

	@Column(name="sa_event")
	private String saEvent;
	
	@Transient private String systemName;

	private String testname;

	private String treatment;

	private String uhid;

	private String diseaseName;
	
	@Transient private String vitalInfo;

	public VwDoctornotesListFinal() {
	}

	public Long getAssesmentid() {
		return this.assesmentid;
	}

	public void setAssesmentid(Long assesmentid) {
		this.assesmentid = assesmentid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getProgressnotes() {
		return this.progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getSaEvent() {
		return this.saEvent;
	}

	public void setSaEvent(String saEvent) {
		this.saEvent = saEvent;
	}

	public String getTestname() {
		return this.testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public String getTreatment() {
		return this.treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getVitalInfo() {
		return vitalInfo;
	}

	public void setVitalInfo(String vitalInfo) {
		this.vitalInfo = vitalInfo;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
}
