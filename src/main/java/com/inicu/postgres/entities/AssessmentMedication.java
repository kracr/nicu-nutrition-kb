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
 * The persistent class for the assessment_medication database table.
 * 
 */
@Entity
@Table(name = "assessment_medication")
@NamedQuery(name = "AssessmentMedication.findAll", query = "SELECT s FROM AssessmentMedication s")
public class AssessmentMedication implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long assessment_medication_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp modificationtime;

	private Long assessmentid;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp assessmenttime;

	private String med_name;

	private String route;

	private String dose;

	private String maintainence_dose;

	@Column(columnDefinition = "bool")
	private Boolean nursing_action;

	private String uhid;

	public AssessmentMedication() {
		super();
		this.nursing_action = false;
	}

	public Long getAssessment_medication_id() {
		return assessment_medication_id;
	}

	public void setAssessment_medication_id(Long assessment_medication_id) {
		this.assessment_medication_id = assessment_medication_id;
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

	public Long getAssessmentid() {
		return assessmentid;
	}

	public void setAssessmentid(Long assessmentid) {
		this.assessmentid = assessmentid;
	}

	public Timestamp getAssessmenttime() {
		return assessmenttime;
	}

	public void setAssessmenttime(Timestamp assessmenttime) {
		this.assessmenttime = assessmenttime;
	}

	public String getMed_name() {
		return med_name;
	}

	public void setMed_name(String med_name) {
		this.med_name = med_name;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getMaintainence_dose() {
		return maintainence_dose;
	}

	public void setMaintainence_dose(String maintainence_dose) {
		this.maintainence_dose = maintainence_dose;
	}

	public Boolean getNursing_action() {
		return nursing_action;
	}

	public void setNursing_action(Boolean nursing_action) {
		this.nursing_action = nursing_action;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	@Override
	public String toString() {
		return "AssessmentMedication [assessment_medication_id=" + assessment_medication_id + ", creationtime="
				+ creationtime + ", modificationtime=" + modificationtime + ", assessmentid=" + assessmentid
				+ ", assessmenttime=" + assessmenttime + ", med_name=" + med_name + ", route=" + route + ", dose="
				+ dose + ", maintainence_dose=" + maintainence_dose + ", nursing_action=" + nursing_action + ", uhid="
				+ uhid + "]";
	}

}
