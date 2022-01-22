package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the nursing_intake database table.
 * 
 */
@Entity
@Table(name="nursing_intake")
@NamedQuery(name="NursingIntake.findAll", query="SELECT n FROM NursingIntake n")
public class NursingIntake implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nn_intakeid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nnIntakeid;

	@Column(columnDefinition="float4")
	private Float aaperhr;

	private Timestamp creationtime;

	private String feedmethod;

	private String feedtype;

	@Column(columnDefinition="float4")
	private Float feedvolume;

	private String hmfvalue;

	@Column(columnDefinition="float4")
	private Float ivperhr;

	@Column(columnDefinition="float4")
	private Float ivtotal;

	private String ivtype;

	@Column(columnDefinition="float4")
	private Float lipidperhr;

	private Timestamp modificationtime;

	@Column(name="nn_intake_time")
	private String nnIntakeTime;

	private String pnrate;

	private String uhid;

	private String loggeduser;
	
	public NursingIntake() {
		this.loggeduser = "";
	}

	public Long getNnIntakeid() {
		return this.nnIntakeid;
	}

	public void setNnIntakeid(Long nnIntakeid) {
		this.nnIntakeid = nnIntakeid;
	}

	public Float getAaperhr() {
		return this.aaperhr;
	}

	public void setAaperhr(Float aaperhr) {
		this.aaperhr = aaperhr;
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

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnIntakeTime() {
		return this.nnIntakeTime;
	}

	public void setNnIntakeTime(String nnIntakeTime) {
		this.nnIntakeTime = nnIntakeTime;
	}

	public String getPnrate() {
		return this.pnrate;
	}

	public void setPnrate(String pnrate) {
		this.pnrate = pnrate;
	}

	public String getUhid() {
		return this.uhid;
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

	
}