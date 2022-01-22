package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the discharge_doctornotes database table.
 * 
 */
@Entity
@Table(name="discharge_doctornotes")
@NamedQuery(name="DischargeDoctornote.findAll", query="SELECT d FROM DischargeDoctornote d")
public class DischargeDoctornote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long dischargenotesid;

	private Timestamp creationtime;

	private String diagnosisid;

	private String dischargepatientid;

	private String doctornotesid;

	private String followupnotesid;

	private String issuesid;

	private String loggeduser;

	private Timestamp modificationtime;

	private String planid;

	public DischargeDoctornote() {
	}

	public Long getDischargenotesid() {
		return this.dischargenotesid;
	}

	public void setDischargenotesid(Long dischargenotesid) {
		this.dischargenotesid = dischargenotesid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDiagnosisid() {
		return this.diagnosisid;
	}

	public void setDiagnosisid(String diagnosisid) {
		this.diagnosisid = diagnosisid;
	}

	public String getDischargepatientid() {
		return this.dischargepatientid;
	}

	public void setDischargepatientid(String dischargepatientid) {
		this.dischargepatientid = dischargepatientid;
	}

	public String getDoctornotesid() {
		return this.doctornotesid;
	}

	public void setDoctornotesid(String doctornotesid) {
		this.doctornotesid = doctornotesid;
	}

	public String getFollowupnotesid() {
		return this.followupnotesid;
	}

	public void setFollowupnotesid(String followupnotesid) {
		this.followupnotesid = followupnotesid;
	}

	public String getIssuesid() {
		return this.issuesid;
	}

	public void setIssuesid(String issuesid) {
		this.issuesid = issuesid;
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

	public String getPlanid() {
		return this.planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

}