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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the admission_notes database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "admission_notes")
@NamedQuery(name = "AdmissionNotes.findAll", query = "SELECT b FROM AdmissionNotes b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdmissionNotes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long admission_notes_id;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String episodeid;

	private String reason_admission;

	private String antenatal_history;

	private String birth_to_inicu;

	private String status_at_admission;

	private String gen_phy_exam;

	private String systemic_exam;

	private String diagnosis;

	private String loggeduser;

	private String course_before_admission;

	public Long getAdmission_notes_id() {
		return admission_notes_id;
	}

	public void setAdmission_notes_id(Long admission_notes_id) {
		this.admission_notes_id = admission_notes_id;
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

	public String getReason_admission() {
		return reason_admission;
	}

	public void setReason_admission(String reason_admission) {
		this.reason_admission = reason_admission;
	}

	public String getAntenatal_history() {
		return antenatal_history;
	}

	public void setAntenatal_history(String antenatal_history) {
		this.antenatal_history = antenatal_history;
	}

	public String getBirth_to_inicu() {
		return birth_to_inicu;
	}

	public void setBirth_to_inicu(String birth_to_inicu) {
		this.birth_to_inicu = birth_to_inicu;
	}

	public String getStatus_at_admission() {
		return status_at_admission;
	}

	public void setStatus_at_admission(String status_at_admission) {
		this.status_at_admission = status_at_admission;
	}

	public String getGen_phy_exam() {
		return gen_phy_exam;
	}

	public void setGen_phy_exam(String gen_phy_exam) {
		this.gen_phy_exam = gen_phy_exam;
	}

	public String getSystemic_exam() {
		return systemic_exam;
	}

	public void setSystemic_exam(String systemic_exam) {
		this.systemic_exam = systemic_exam;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getCourse_before_admission() {
		return course_before_admission;
	}

	public void setCourse_before_admission(String course_before_admission) {
		this.course_before_admission = course_before_admission;
	}

	@Override
	public String toString() {
		return "AdmissionNotes [admission_notes_id=" + admission_notes_id + ", creationtime=" + creationtime + ", uhid="
				+ uhid + ", episodeid=" + episodeid + ", reason_admission=" + reason_admission + ", antenatal_history="
				+ antenatal_history + ", birth_to_inicu=" + birth_to_inicu + ", status_at_admission="
				+ status_at_admission + ", gen_phy_exam=" + gen_phy_exam + ", systemic_exam=" + systemic_exam
				+ ", diagnosis=" + diagnosis + ", loggeduser=" + loggeduser + ", course_before_admission="
				+ course_before_admission + "]";
	}

}
