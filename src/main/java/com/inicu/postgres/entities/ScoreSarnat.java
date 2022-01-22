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

@Entity
@Table(name = "score_sarnat")
@NamedQuery(name = "ScoreSarnat.findAll", query = "SELECT s FROM ScoreSarnat s")
public class ScoreSarnat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sarnatscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;
	
	@Column(name="level_of_consiousness")
	private Integer levelOfConsiousness;
	
	@Column(name="muscle_tone")
	private Integer muscleTone;
	
	private Integer posture;
	
	@Column(name="tendon_reflexes")
	private Integer tendonReflexes;
	
	private Integer myoclonus;
	
	@Column(name="more_reflex")
	private Integer moreReflex;
	
	private Integer pupils;
	
	private Integer seizures;
	
	private Integer ecg;
	
	private Integer duration;
	
	private Integer outcome;
	
	@Column(name="sarnat_score")
	private Integer sarnatScore;

	public Long getSarnatscoreid() {
		return sarnatscoreid;
	}

	public void setSarnatscoreid(Long sarnatscoreid) {
		this.sarnatscoreid = sarnatscoreid;
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

	public Integer getLevelOfConsiousness() {
		return levelOfConsiousness;
	}

	public void setLevelOfConsiousness(Integer levelOfConsiousness) {
		this.levelOfConsiousness = levelOfConsiousness;
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

	public Integer getTendonReflexes() {
		return tendonReflexes;
	}

	public void setTendonReflexes(Integer tendonReflexes) {
		this.tendonReflexes = tendonReflexes;
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

	public Integer getEcg() {
		return ecg;
	}

	public void setEcg(Integer ecg) {
		this.ecg = ecg;
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

	public Integer getSarnatScore() {
		return sarnatScore;
	}

	public void setSarnatScore(Integer sarnatScore) {
		this.sarnatScore = sarnatScore;
	}
}
