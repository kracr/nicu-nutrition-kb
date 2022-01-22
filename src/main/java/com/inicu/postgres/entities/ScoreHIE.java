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
 * The persistent class for the score_hie database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_hie")
@NamedQuery(name = "ScoreHIE.findAll", query = "SELECT s FROM ScoreHIE s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreHIE implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hiescoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	@Column(name = "consiousness_level")
	private Integer consiousnessLevel;

	@Column(name = "muscle_tone")
	private Integer muscleTone;

	private Integer posture;

	@Column(name = "tendon_reflex")
	private Integer tendonReflex;

	private Integer myoclonus;

	@Column(name = "more_reflex")
	private Integer moreReflex;

	private Integer pupils;

	private Integer seizures;

	private Integer eeg;

	private Integer duration;

	private Integer outcome;

	private Integer hiescore;

	public ScoreHIE() {
		super();
	}

	public Long getHiescoreid() {
		return hiescoreid;
	}

	public void setHiescoreid(Long hiescoreid) {
		this.hiescoreid = hiescoreid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Integer getConsiousnessLevel() {
		return consiousnessLevel;
	}

	public void setConsiousnessLevel(Integer consiousnessLevel) {
		this.consiousnessLevel = consiousnessLevel;
	}

	public Integer getMuscleTone() {
		return muscleTone;
	}

	public void setMuscleTone(Integer muscleTone) {
		this.muscleTone = muscleTone;
	}

	public Integer getPosture() {
		return posture;
	}

	public void setPosture(Integer posture) {
		this.posture = posture;
	}

	public Integer getTendonReflex() {
		return tendonReflex;
	}

	public void setTendonReflex(Integer tendonReflex) {
		this.tendonReflex = tendonReflex;
	}

	public Integer getMyoclonus() {
		return myoclonus;
	}

	public void setMyoclonus(Integer myoclonus) {
		this.myoclonus = myoclonus;
	}

	public Integer getMoreReflex() {
		return moreReflex;
	}

	public void setMoreReflex(Integer moreReflex) {
		this.moreReflex = moreReflex;
	}

	public Integer getPupils() {
		return pupils;
	}

	public void setPupils(Integer pupils) {
		this.pupils = pupils;
	}

	public Integer getSeizures() {
		return seizures;
	}

	public void setSeizures(Integer seizures) {
		this.seizures = seizures;
	}

	public Integer getEeg() {
		return eeg;
	}

	public void setEeg(Integer eeg) {
		this.eeg = eeg;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getOutcome() {
		return outcome;
	}

	public void setOutcome(Integer outcome) {
		this.outcome = outcome;
	}

	public Integer getHiescore() {
		return hiescore;
	}

	public void setHiescore(Integer hiescore) {
		this.hiescore = hiescore;
	}

}
