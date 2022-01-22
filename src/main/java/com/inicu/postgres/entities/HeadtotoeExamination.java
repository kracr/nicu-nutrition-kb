package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the headtotoe_examination database table.
 * 
 */
@Entity
@Table(name="headtotoe_examination")
@NamedQuery(name="HeadtotoeExamination.findAll", query="SELECT h FROM HeadtotoeExamination h")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadtotoeExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long headtoeid;

	@Column(name="anomaly_comments")
	private String anomalyComments;

	@Column(name="anomaly_value",columnDefinition="bool")
	private Boolean anomalyValue;

	@Column(name="anus_comments")
	private String anusComments;

	@Column(name="anus_value" ,columnDefinition="bool")
	private Boolean anusValue;

	@Column(name="back_comments")
	private String backComments;

	@Column(name="back_value" ,columnDefinition="bool")
	private Boolean backValue;

	@Column(name="ccd_comments")
	private String ccdComments;

	@Column(name="ccd_value" ,columnDefinition="bool")
	private Boolean ccdValue;

	@Column(name="cdh_comments")
	private String cdhComments;

	@Column(name="cdh_value" ,columnDefinition="bool")
	private Boolean cdhValue;

	private Timestamp creationtime;

	@Column(name="finger_comments")
	private String fingerComments;

	@Column(name="finger_value" ,columnDefinition="bool")
	private Boolean fingerValue;

	@Column(name="head_comments")
	private String headComments;

	@Column(name="head_value" ,columnDefinition="bool")
	private Boolean headValue;

	@Column(name="heart_comments")
	private String heartComments;

	@Column(name="heart_value" ,columnDefinition="bool")
	private Boolean heartValue;

	private Timestamp modificationtime;

	@Column(name="mouth_comments")
	private String mouthComments;

	@Column(name="mouth_value" ,columnDefinition="bool")
	private Boolean mouthValue;

	@Column(name="ngpassed_comments")
	private String ngpassedComments;

	@Column(name="ngpassed_value" ,columnDefinition="bool")
	private Boolean ngpassedValue;

	private String uhid;

	

	public HeadtotoeExamination() {
		super();
		this.headtoeid = headtoeid;
		this.anomalyComments = anomalyComments;
		this.anomalyValue = anomalyValue;
		this.anusComments = anusComments;
		this.anusValue = anusValue;
		this.backComments = backComments;
		this.backValue = backValue;
		this.ccdComments = ccdComments;
		this.ccdValue = ccdValue;
		this.cdhComments = cdhComments;
		this.cdhValue = cdhValue;
		this.creationtime = creationtime;
		this.fingerComments = fingerComments;
		this.fingerValue = fingerValue;
		this.headComments = headComments;
		this.headValue = headValue;
		this.heartComments = heartComments;
		this.heartValue = heartValue;
		this.modificationtime = modificationtime;
		this.mouthComments = mouthComments;
		this.mouthValue = mouthValue;
		this.ngpassedComments = ngpassedComments;
		this.ngpassedValue = ngpassedValue;
		this.uhid = uhid;
	}

	public Long getHeadtoeid() {
		return this.headtoeid;
	}

	public void setHeadtoeid(Long headtoeid) {
		this.headtoeid = headtoeid;
	}

	public String getAnomalyComments() {
		return this.anomalyComments;
	}

	public void setAnomalyComments(String anomalyComments) {
		this.anomalyComments = anomalyComments;
	}

	public Boolean getAnomalyValue() {
		return this.anomalyValue;
	}

	public void setAnomalyValue(Boolean anomalyValue) {
		this.anomalyValue = anomalyValue;
	}

	public String getAnusComments() {
		return this.anusComments;
	}

	public void setAnusComments(String anusComments) {
		this.anusComments = anusComments;
	}

	public Boolean getAnusValue() {
		return this.anusValue;
	}

	public void setAnusValue(Boolean anusValue) {
		this.anusValue = anusValue;
	}

	public String getBackComments() {
		return this.backComments;
	}

	public void setBackComments(String backComments) {
		this.backComments = backComments;
	}

	public Boolean getBackValue() {
		return this.backValue;
	}

	public void setBackValue(Boolean backValue) {
		this.backValue = backValue;
	}

	public String getCcdComments() {
		return this.ccdComments;
	}

	public void setCcdComments(String ccdComments) {
		this.ccdComments = ccdComments;
	}

	public Boolean getCcdValue() {
		return this.ccdValue;
	}

	public void setCcdValue(Boolean ccdValue) {
		this.ccdValue = ccdValue;
	}

	public String getCdhComments() {
		return this.cdhComments;
	}

	public void setCdhComments(String cdhComments) {
		this.cdhComments = cdhComments;
	}

	public Boolean getCdhValue() {
		return this.cdhValue;
	}

	public void setCdhValue(Boolean cdhValue) {
		this.cdhValue = cdhValue;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getFingerComments() {
		return this.fingerComments;
	}

	public void setFingerComments(String fingerComments) {
		this.fingerComments = fingerComments;
	}

	public Boolean getFingerValue() {
		return this.fingerValue;
	}

	public void setFingerValue(Boolean fingerValue) {
		this.fingerValue = fingerValue;
	}

	public String getHeadComments() {
		return this.headComments;
	}

	public void setHeadComments(String headComments) {
		this.headComments = headComments;
	}

	public Boolean getHeadValue() {
		return this.headValue;
	}

	public void setHeadValue(Boolean headValue) {
		this.headValue = headValue;
	}

	public String getHeartComments() {
		return this.heartComments;
	}

	public void setHeartComments(String heartComments) {
		this.heartComments = heartComments;
	}

	public Boolean getHeartValue() {
		return this.heartValue;
	}

	public void setHeartValue(Boolean heartValue) {
		this.heartValue = heartValue;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getMouthComments() {
		return this.mouthComments;
	}

	public void setMouthComments(String mouthComments) {
		this.mouthComments = mouthComments;
	}

	public Boolean getMouthValue() {
		return this.mouthValue;
	}

	public void setMouthValue(Boolean mouthValue) {
		this.mouthValue = mouthValue;
	}

	public String getNgpassedComments() {
		return this.ngpassedComments;
	}

	public void setNgpassedComments(String ngpassedComments) {
		this.ngpassedComments = ngpassedComments;
	}

	public Boolean getNgpassedValue() {
		return this.ngpassedValue;
	}

	public void setNgpassedValue(Boolean ngpassedValue) {
		this.ngpassedValue = ngpassedValue;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}