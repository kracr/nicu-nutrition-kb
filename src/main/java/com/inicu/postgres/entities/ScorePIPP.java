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

@Entity
@Table(name = "score_pipp")
@NamedQuery(name = "ScorePIPP.findAll", query = "SELECT s FROM ScorePIPP s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScorePIPP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long PIPPscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Integer gestational;

	private Integer behavioural;

	private Integer heartRate;

	private Integer saturation;

	private Integer brow;

	private Integer eye;
	
	private Integer nasal;

	private Integer pippscore;
	
	private Timestamp entrydate;

	@Column(columnDefinition = "bool")
	private Boolean admission_entry;

	public ScorePIPP() {
		super();
	}

	public Long getPIPPscoreid() {
		return PIPPscoreid;
	}

	public void setPIPPscoreid(Long pIPPscoreid) {
		PIPPscoreid = pIPPscoreid;
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

	public Integer getGestational() {
		return gestational;
	}

	public void setGestational(Integer gestational) {
		this.gestational = gestational;
	}

	public Integer getBehavioural() {
		return behavioural;
	}

	public void setBehavioural(Integer behavioural) {
		this.behavioural = behavioural;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Integer getSaturation() {
		return saturation;
	}

	public void setSaturation(Integer saturation) {
		this.saturation = saturation;
	}

	public Integer getBrow() {
		return brow;
	}

	public void setBrow(Integer brow) {
		this.brow = brow;
	}

	public Integer getEye() {
		return eye;
	}

	public void setEye(Integer eye) {
		this.eye = eye;
	}

	public Integer getNasal() {
		return nasal;
	}

	public void setNasal(Integer nasal) {
		this.nasal = nasal;
	}
	
	public Integer getPippscore() {
		return pippscore;
	}

	public void setPippscore(Integer pippscore) {
		this.pippscore = pippscore;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public Boolean getAdmission_entry() {
		return admission_entry;
	}

	public void setAdmission_entry(Boolean admission_entry) {
		this.admission_entry = admission_entry;
	}

}
