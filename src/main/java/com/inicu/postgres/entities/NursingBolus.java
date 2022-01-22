package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the nursing_bolus database table.
 * 
 */
@Entity
@Table(name="nursing_bolus")
@NamedQuery(name="NursingBolus.findAll", query="SELECT n FROM NursingBolus n")
public class NursingBolus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nn_bolusid")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nnBolusid;

	
	
	private String bolustype;

	private Timestamp creationtime;

	private String dose;

	private String duration;

	private String loggeduser;

	private Timestamp modificationtime;

	@Column(name="nn_bolus_time")
	private String nnBolusTime;

	private String starttime;

	private String uhid;

	public NursingBolus() {
	}

	public Long getNnBolusid() {
		return this.nnBolusid;
	}

	public void setNnBolusid(Long nnBolusid) {
		this.nnBolusid = nnBolusid;
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

	public String getDose() {
		return this.dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnBolusTime() {
		return this.nnBolusTime;
	}

	public void setNnBolusTime(String nnBolusTime) {
		this.nnBolusTime = nnBolusTime;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	
}