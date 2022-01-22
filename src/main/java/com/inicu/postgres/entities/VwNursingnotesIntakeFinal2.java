package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the vw_nursingnotes_intake_final2 database table.
 * 
 */
@Entity
@Table(name="vw_nursingnotes_intake_final2")
@NamedQuery(name="VwNursingnotesIntakeFinal2.findAll", query="SELECT v FROM VwNursingnotesIntakeFinal2 v")
@JsonIgnoreProperties
public class VwNursingnotesIntakeFinal2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition="float4")
	private Float aaperhr;

	@Column(name="blood_dose")
	private String bloodDose;

	@Column(name="blood_duration")
	private String bloodDuration;

	@Column(name="blood_starttime")
	private String bloodStarttime;

	private String bloodgroup;

	private String bloodproduct;

	@Column(name="bolus_dose")
	private String bolusDose;

	@Column(name="bolus_duration")
	private String bolusDuration;

	@Column(name="bolus_starttime")
	private String bolusStarttime;

	private String bolustype;

	private Timestamp creationtime;

	private String feedmethod;

	
	@Transient
	private List feedmethodList;
	
	private String feedtype;

	@Column(columnDefinition="float4")
	private Float feedvolume;

	private String hmfvalue;

	@Column(name="hourly_intake")
	private String hourlyIntake;

	@Column(columnDefinition="float4")
	private Float ivperhr;

	@Column(columnDefinition="float4")
	private Float ivtotal;

	
	private String ivtype;

	@Column(columnDefinition="float4")
	private Float lipidperhr;

	@Column(name="nn_blood_time")
	private String nnBloodTime;

	@Column(name="nn_bolus_time")
	private String nnBolusTime;

	@Column(name="nn_intake_time")
	private String nnIntakeTime;

	@Id
	@Column(name="nn_intakeid")
	private Long nnIntakeid;

	private String pnrate;

	@Column(name="total_intake")
	private String totalIntake;

	private String uhid;

	public VwNursingnotesIntakeFinal2() {
		this.bloodproduct = "";
		this.bolustype = "";
		this.ivtype = "";
		this.feedmethod = "";
		this.feedtype = "";
		
	}

	public Float getAaperhr() {
		return this.aaperhr;
	}

	public void setAaperhr(Float aaperhr) {
		this.aaperhr = aaperhr;
	}

	public String getBloodDose() {
		return this.bloodDose;
	}

	public void setBloodDose(String bloodDose) {
		this.bloodDose = bloodDose;
	}

	public String getBloodDuration() {
		return this.bloodDuration;
	}

	public void setBloodDuration(String bloodDuration) {
		this.bloodDuration = bloodDuration;
	}

	public String getBloodStarttime() {
		return this.bloodStarttime;
	}

	public void setBloodStarttime(String bloodStarttime) {
		this.bloodStarttime = bloodStarttime;
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getBloodproduct() {
		return this.bloodproduct;
	}

	public void setBloodproduct(String bloodproduct) {
		this.bloodproduct = bloodproduct;
	}

	public String getBolusDose() {
		return this.bolusDose;
	}

	public void setBolusDose(String bolusDose) {
		this.bolusDose = bolusDose;
	}

	public String getBolusDuration() {
		return this.bolusDuration;
	}

	public void setBolusDuration(String bolusDuration) {
		this.bolusDuration = bolusDuration;
	}

	public String getBolusStarttime() {
		return this.bolusStarttime;
	}

	public void setBolusStarttime(String bolusStarttime) {
		this.bolusStarttime = bolusStarttime;
	}

	public String getBolustype() {
		return this.bolustype;
	}

	public void setBolustype(String bolustype) {
		this.bolustype = bolustype;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getFeedmethod() {
		return this.feedmethod;
	}

	public void setFeedmethod(String feedmethod) {
		this.feedmethod = feedmethod;
	}

	public String getFeedtype() {
		return this.feedtype;
	}

	public void setFeedtype(String feedtype) {
		this.feedtype = feedtype;
	}

	public Float getFeedvolume() {
		return this.feedvolume;
	}

	public void setFeedvolume(Float feedvolume) {
		this.feedvolume = feedvolume;
	}

	public String getHmfvalue() {
		return this.hmfvalue;
	}

	public void setHmfvalue(String hmfvalue) {
		this.hmfvalue = hmfvalue;
	}

	
	public Float getIvperhr() {
		return this.ivperhr;
	}

	public void setIvperhr(Float ivperhr) {
		this.ivperhr = ivperhr;
	}

	public Float getIvtotal() {
		return this.ivtotal;
	}

	public void setIvtotal(Float ivtotal) {
		this.ivtotal = ivtotal;
	}

	public String getIvtype() {
		return this.ivtype;
	}

	public void setIvtype(String ivtype) {
		this.ivtype = ivtype;
	}

	public Float getLipidperhr() {
		return this.lipidperhr;
	}

	public void setLipidperhr(Float lipidperhr) {
		this.lipidperhr = lipidperhr;
	}

	public String getNnBloodTime() {
		return this.nnBloodTime;
	}

	public void setNnBloodTime(String nnBloodTime) {
		this.nnBloodTime = nnBloodTime;
	}

	public String getNnBolusTime() {
		return this.nnBolusTime;
	}

	public void setNnBolusTime(String nnBolusTime) {
		this.nnBolusTime = nnBolusTime;
	}

	public String getNnIntakeTime() {
		return this.nnIntakeTime;
	}

	public void setNnIntakeTime(String nnIntakeTime) {
		this.nnIntakeTime = nnIntakeTime;
	}

	public Long getNnIntakeid() {
		return this.nnIntakeid;
	}

	public void setNnIntakeid(Long nnIntakeid) {
		this.nnIntakeid = nnIntakeid;
	}

	public String getPnrate() {
		return this.pnrate;
	}

	public void setPnrate(String pnrate) {
		this.pnrate = pnrate;
	}

	
	public String getHourlyIntake() {
		return hourlyIntake;
	}

	public void setHourlyIntake(String hourlyIntake) {
		this.hourlyIntake = hourlyIntake;
	}

	public String getTotalIntake() {
		return totalIntake;
	}

	public void setTotalIntake(String totalIntake) {
		this.totalIntake = totalIntake;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public List getFeedmethodList() {
		return feedmethodList;
	}

	public void setFeedmethodList(List feedmethodList) {
		this.feedmethodList = feedmethodList;
	}

	
}