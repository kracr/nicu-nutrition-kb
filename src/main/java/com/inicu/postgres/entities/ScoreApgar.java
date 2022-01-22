package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;


/**
 * The persistent class for the score_apgar database table.
 * 
 */
@Entity
@Table(name="score_apgar")
@NamedQuery(name="ScoreApgar.findAll", query="SELECT s FROM ScoreApgar s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreApgar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long apgarscoreid;

	private Integer activity;

	@Column(name="apgar_total_score")
	private Integer apgarTotalScore;

	private Integer appearance;

	private Timestamp creationtime;

	private Integer grimace;

	private Timestamp modificationtime;

	private Integer pulse;

	private Integer respiration;

	private String uhid;
	
	@Column(name="apgar_time")
	private Integer apgarTime;
	
	public ScoreApgar() {
		super();
		this.apgarscoreid = apgarscoreid;
		this.activity = activity;
		this.appearance = appearance;
		this.creationtime = creationtime;
		this.grimace = grimace;
		this.modificationtime = modificationtime;
		this.pulse = pulse;
		this.respiration = respiration;
		this.uhid = uhid;
		this.apgarTime = apgarTime;
	}


	public Long getApgarscoreid() {
		return this.apgarscoreid;
	}

	public void setApgarscoreid(Long apgarscoreid) {
		this.apgarscoreid = apgarscoreid;
	}

	public Integer getActivity() {
		return this.activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Integer getAppearance() {
		return this.appearance;
	}

	public void setAppearance(Integer appearance) {
		this.appearance = appearance;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Integer getGrimace() {
		return this.grimace;
	}

	public void setGrimace(Integer grimace) {
		this.grimace = grimace;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Integer getPulse() {
		return this.pulse;
	}

	public void setPulse(Integer pulse) {
		this.pulse = pulse;
	}

	public Integer getRespiration() {
		return this.respiration;
	}

	public void setRespiration(Integer respiration) {
		this.respiration = respiration;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}


	public Integer getApgarTotalScore() {
		return apgarTotalScore;
	}


	public void setApgarTotalScore(Integer apgarTotalScore) {
		this.apgarTotalScore = apgarTotalScore;
	}


	public Integer getApgarTime() {
		return apgarTime;
	}


	public void setApgarTime(Integer apgarTime) {
		this.apgarTime = apgarTime;
	}

	
	
}