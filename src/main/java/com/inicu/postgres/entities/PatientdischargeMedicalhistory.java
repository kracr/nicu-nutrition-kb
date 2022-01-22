package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the patientdischarge_medicalhistory database table.
 * 
 */
@Entity
@Table(name="patientdischarge_medicalhistory")
@NamedQuery(name="PatientdischargeMedicalhistory.findAll", query="SELECT p FROM PatientdischargeMedicalhistory p")
public class PatientdischargeMedicalhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String comments;

	private Long dischargepatientid;

	@Column(columnDefinition="float4")
	private Float dose;

	private String enddate;

	private String frequency;

	private String medicationtype;

	private String medicinename;

	private String route;

	private String startdate;

	@Id
	private String uhid;

	public PatientdischargeMedicalhistory() {
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getDischargepatientid() {
		return this.dischargepatientid;
	}

	public void setDischargepatientid(Long dischargepatientid) {
		this.dischargepatientid = dischargepatientid;
	}

	public Float getDose() {
		return this.dose;
	}

	public void setDose(Float dose) {
		this.dose = dose;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getMedicationtype() {
		return this.medicationtype;
	}

	public void setMedicationtype(String medicationtype) {
		this.medicationtype = medicationtype;
	}

	public String getMedicinename() {
		return this.medicinename;
	}

	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}