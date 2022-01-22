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

/**
 * The persistent class for the outborn_medicine database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "outborn_medicine")
@NamedQuery(name = "OutbornMedicine.findAll", query = "SELECT s FROM OutbornMedicine s")
public class OutbornMedicine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long outborn_medicine_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String episodeid;

	private String medicine_name;

	private String medicine_dose;
	
	private String medicine_dose_unit;

	private String medicine_cummulative_dose;

	private String loggeduser;

	public Long getOutborn_medicine_id() {
		return outborn_medicine_id;
	}

	public void setOutborn_medicine_id(Long outborn_medicine_id) {
		this.outborn_medicine_id = outborn_medicine_id;
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

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getMedicine_name() {
		return medicine_name;
	}

	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}

	public String getMedicine_dose() {
		return medicine_dose;
	}

	public void setMedicine_dose(String medicine_dose) {
		this.medicine_dose = medicine_dose;
	}
	
	public void setMedicine_dose_unit(String medicine_dose_unit) {
		this.medicine_dose_unit = medicine_dose_unit;
	}
	
	public String getMedicine_dose_unit() {
		return this.medicine_dose_unit;
	}

	public String getMedicine_cummulative_dose() {
		return medicine_cummulative_dose;
	}

	public void setMedicine_cummulative_dose(String medicine_cummulative_dose) {
		this.medicine_cummulative_dose = medicine_cummulative_dose;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	@Override
	public String toString() {
		return "OutbornMedicine [outborn_medicine_id=" + outborn_medicine_id + ", creationtime=" + creationtime
				+ ", uhid=" + uhid + ", episodeid=" + episodeid + ", medicine_name=" + medicine_name
				+ ", medicine_dose=" + medicine_dose + ", medicine_cummulative_dose=" + medicine_cummulative_dose
				+ ", loggeduser=" + loggeduser + "]";
	}

}
