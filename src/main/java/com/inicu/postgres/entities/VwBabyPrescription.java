package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vw_baby_prescription database table.
 * 
 */
@Entity
@Table(name="vw_baby_prescription")
@NamedQuery(name="VwBabyPrescription.findAll", query="SELECT v FROM VwBabyPrescription v")
public class VwBabyPrescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="baby_presid")
	private Long babyPresid;
	
	private String uhid;

	private String medicationtype;
	
	private String medicinename;

	private String route;
	
	@Column(columnDefinition="float4")
	private Float dose;

	private String frequency;
	
	private Timestamp startdate;

	private String starttime;

	private Timestamp enddate;

	private String comments;
	private String loggeduser;
	
	
	@Column(columnDefinition="bool")
	private Boolean isactive;

	private String calculateddose;
	
	@Transient
	private String medicationTypeStr;
	
	@Transient
	private String medicationFreqStr;
	
	public String getMedicationTypeStr() {
		return medicationTypeStr;
	}

	public void setMedicationTypeStr(String medicationTypeStr) {
		this.medicationTypeStr = medicationTypeStr;
	}

	public String getMedicationFreqStr() {
		return medicationFreqStr;
	}

	public void setMedicationFreqStr(String medicationFreqStr) {
		this.medicationFreqStr = medicationFreqStr;
	}

	public String getCalculateddose() {
		return calculateddose;
	}

	public void setCalculateddose(String calculateddose) {
		this.calculateddose = calculateddose;
	}

	public VwBabyPrescription() {
	}

	public Long getBabyPresid() {
		return this.babyPresid;
	}

	public void setBabyPresid(Long babyPresid) {
		this.babyPresid = babyPresid;
	}

	public Float getDose() {
		return this.dose;
	}

	public void setDose(Float dose) {
		this.dose = dose;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getMedicinename() {
		return this.medicinename;
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

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	
	
}