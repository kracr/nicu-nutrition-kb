package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the score_pain database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_pain")
@NamedQuery(name = "ScorePain.findAll", query = "SELECT s FROM ScorePain s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScorePain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long painscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String gestationalage;
	private Integer gestationalagescore;

	private String behaviouralstate;
	private Integer behaviouralstatescore;

	private String heartrate;
	private Integer heartratescore;

	private String oxygensat;
	private Integer oxygensatscore;

	private String browbulge;
	private Integer browbulgescore;

	private String eyesqueeze;
	private Integer eyesqueezescore;

	private Integer painscore;

	public ScorePain() {
		super();
	}

	public Long getPainscoreid() {
		return painscoreid;
	}

	public void setPainscoreid(Long painscoreid) {
		this.painscoreid = painscoreid;
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

	public String getGestationalage() {
		return gestationalage;
	}

	public void setGestationalage(String gestationalage) {
		this.gestationalage = gestationalage;
	}

	public Integer getGestationalagescore() {
		return gestationalagescore;
	}

	public void setGestationalagescore(Integer gestationalagescore) {
		this.gestationalagescore = gestationalagescore;
	}

	public String getBehaviouralstate() {
		return behaviouralstate;
	}

	public void setBehaviouralstate(String behaviouralstate) {
		this.behaviouralstate = behaviouralstate;
	}

	public Integer getBehaviouralstatescore() {
		return behaviouralstatescore;
	}

	public void setBehaviouralstatescore(Integer behaviouralstatescore) {
		this.behaviouralstatescore = behaviouralstatescore;
	}

	public String getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}

	public Integer getHeartratescore() {
		return heartratescore;
	}

	public void setHeartratescore(Integer heartratescore) {
		this.heartratescore = heartratescore;
	}

	public String getOxygensat() {
		return oxygensat;
	}

	public void setOxygensat(String oxygensat) {
		this.oxygensat = oxygensat;
	}

	public Integer getOxygensatscore() {
		return oxygensatscore;
	}

	public void setOxygensatscore(Integer oxygensatscore) {
		this.oxygensatscore = oxygensatscore;
	}

	public String getBrowbulge() {
		return browbulge;
	}

	public void setBrowbulge(String browbulge) {
		this.browbulge = browbulge;
	}

	public Integer getBrowbulgescore() {
		return browbulgescore;
	}

	public void setBrowbulgescore(Integer browbulgescore) {
		this.browbulgescore = browbulgescore;
	}

	public String getEyesqueeze() {
		return eyesqueeze;
	}

	public void setEyesqueeze(String eyesqueeze) {
		this.eyesqueeze = eyesqueeze;
	}

	public Integer getEyesqueezescore() {
		return eyesqueezescore;
	}

	public void setEyesqueezescore(Integer eyesqueezescore) {
		this.eyesqueezescore = eyesqueezescore;
	}

	public Integer getPainscore() {
		return painscore;
	}

	public void setPainscore(Integer painscore) {
		this.painscore = painscore;
	}

}
