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
@Table(name = "admission_form_tracker")
@NamedQuery(name = "AdmissionFormTracker.findAll", query = "SELECT b FROM AdmissionFormTracker b")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdmissionFormTracker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long admission_form_tracker_id;

	@Column(name = "inout_status")
	private String inoutStatus;

	private String babyname;

	@Column(columnDefinition = "float4")
	private Float birthweight;

	private Timestamp creationtime;
	
	@Temporal(TemporalType.DATE)
	private Date dateofadmission;

	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	private Integer gestationdays;

	private Integer gestationweek;

	private String loggeduser;

	private Timestamp modificationtime;

	private String uhid;

	public Long getAdmission_form_tracker_id() {
		return admission_form_tracker_id;
	}

	public void setAdmission_form_tracker_id(Long admission_form_tracker_id) {
		this.admission_form_tracker_id = admission_form_tracker_id;
	}

	public String getInoutStatus() {
		return inoutStatus;
	}

	public void setInoutStatus(String inoutStatus) {
		this.inoutStatus = inoutStatus;
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

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Date getDateofadmission() {
		return dateofadmission;
	}

	public void setDateofadmission(Date dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Integer getGestationdays() {
		return gestationdays;
	}

	public void setGestationdays(Integer gestationdays) {
		this.gestationdays = gestationdays;
	}

	public Integer getGestationweek() {
		return gestationweek;
	}

	public void setGestationweek(Integer gestationweek) {
		this.gestationweek = gestationweek;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
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
	
}