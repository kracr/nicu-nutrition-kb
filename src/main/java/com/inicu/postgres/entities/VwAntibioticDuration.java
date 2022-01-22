package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_antibiotic_duration")
@NamedQuery(name="VwAntibioticDuration.findAll", query="SELECT v FROM VwAntibioticDuration v")
public class VwAntibioticDuration implements Serializable{
		
	@Id
	private String uhid;
	
	private String medicinename;
	
	private String medicationtype;
	
	private Double antibiotic_duration;
	
	private Timestamp startdate;
	
	private Timestamp enddate;

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getMedicinename() {
		return medicinename;
	}

	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}

	public String getMedicationtype() {
		return medicationtype;
	}

	public void setMedicationtype(String medicationtype) {
		this.medicationtype = medicationtype;
	}

	public Double getAntibiotic_duration() {
		return antibiotic_duration;
	}

	public void setAntibiotic_duration(Double antibiotic_duration) {
		this.antibiotic_duration = antibiotic_duration;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}
	
	
	
	
	

}
