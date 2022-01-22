package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "hm_observable_status")
@NamedQuery(name = "HMObservableStatus.findAll", query = "SELECT b FROM HMObservableStatus b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HMObservableStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hmobservablestatusid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String uhid;
	
	private String babyname;
	
	@Column(columnDefinition = "float4")
	private Float birthweight;
	
	private String gender;
	
	private String inout_patient_status;

	private Integer gestationdaysbylmp;

	private Integer gestationweekbylmp;
	
	private Timestamp dateofbirth;
	
	private Timestamp dateofadmission;
	
	private String status;

	private Timestamp hmDate;
	
	private String hmReason;
	
	private String branchname;
	
	private String diagnosis;

	public Long getHmobservablestatusid() {
		return hmobservablestatusid;
	}

	public void setHmobservablestatusid(Long hmobservablestatusid) {
		this.hmobservablestatusid = hmobservablestatusid;
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

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public Float getBirthweight() {
		return birthweight;
	}

	public void setBirthweight(Float birthweight) {
		this.birthweight = birthweight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getGestationdaysbylmp() {
		return gestationdaysbylmp;
	}

	public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}

	public Integer getGestationweekbylmp() {
		return gestationweekbylmp;
	}

	public void setGestationweekbylmp(Integer gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}

	public Timestamp getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Timestamp dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Timestamp getDateofadmission() {
		return dateofadmission;
	}

	public void setDateofadmission(Timestamp dateofadmission) {
		this.dateofadmission = dateofadmission;
	}
	
	public Timestamp getHmDate() {
		return hmDate;
	}

	public void setHmDate(Timestamp hmDate) {
		this.hmDate = hmDate;
	}

	public String getHmReason() {
		return hmReason;
	}

	public void setHmReason(String hmReason) {
		this.hmReason = hmReason;
	}

	public String getInout_patient_status() {
		return inout_patient_status;
	}

	public void setInout_patient_status(String inout_patient_status) {
		this.inout_patient_status = inout_patient_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	

}
