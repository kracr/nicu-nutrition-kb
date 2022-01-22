package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the patientdischarge_page2 database table.
 * 
 */
@Entity
@Table(name="patientdischarge_page2")
@NamedQuery(name="PatientdischargePage2.findAll", query="SELECT p FROM PatientdischargePage2 p")
public class PatientdischargePage2 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String diagnosis;

	private String diagnosisid;

	private Long dischargepatientid;

	private String doctornotes;

	private String doctornotesid;

	private String followupnotes;

	private String followupnotesid;

	private String issues;

	private String issuesid;

	private String plan;

	private String planid;

	@Id
	private String uhid;

	public PatientdischargePage2() {
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDiagnosisid() {
		return this.diagnosisid;
	}

	public void setDiagnosisid(String diagnosisid) {
		this.diagnosisid = diagnosisid;
	}

	public Long getDischargepatientid() {
		return this.dischargepatientid;
	}

	public void setDischargepatientid(Long dischargepatientid) {
		this.dischargepatientid = dischargepatientid;
	}

	public String getDoctornotes() {
		return this.doctornotes;
	}

	public void setDoctornotes(String doctornotes) {
		this.doctornotes = doctornotes;
	}

	public String getDoctornotesid() {
		return this.doctornotesid;
	}

	public void setDoctornotesid(String doctornotesid) {
		this.doctornotesid = doctornotesid;
	}

	public String getFollowupnotes() {
		return this.followupnotes;
	}

	public void setFollowupnotes(String followupnotes) {
		this.followupnotes = followupnotes;
	}

	public String getFollowupnotesid() {
		return this.followupnotesid;
	}

	public void setFollowupnotesid(String followupnotesid) {
		this.followupnotesid = followupnotesid;
	}

	public String getIssues() {
		return this.issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}

	public String getIssuesid() {
		return this.issuesid;
	}

	public void setIssuesid(String issuesid) {
		this.issuesid = issuesid;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getPlanid() {
		return this.planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}