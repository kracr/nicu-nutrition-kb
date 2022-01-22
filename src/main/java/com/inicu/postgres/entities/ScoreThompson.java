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
 * The persistent class for the score_downes database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_thompson")
@NamedQuery(name = "ScoreThompson.findAll", query = "SELECT s FROM ScoreThompson s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreThompson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long thompsonscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Integer tone;

	private Integer loc;

	private Integer fits;

	private Integer posture;

	private Integer moro;

	private Integer grasp;

	private Integer suck;

	private Integer respiration;

	private Integer frontanelle;

	@Column(name = "thompson_score")
	private Integer thompsonScore;

	public ScoreThompson() {
		super();
	}

	public Long getThompsonscoreid() {
		return thompsonscoreid;
	}

	public void setThompsonscoreid(Long thompsonscoreid) {
		this.thompsonscoreid = thompsonscoreid;
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

	public Integer getTone() {
		return tone;
	}

	public void setTone(Integer tone) {
		this.tone = tone;
	}

	public Integer getLoc() {
		return loc;
	}

	public void setLoc(Integer loc) {
		this.loc = loc;
	}

	public Integer getFits() {
		return fits;
	}

	public void setFits(Integer fits) {
		this.fits = fits;
	}

	public Integer getPosture() {
		return posture;
	}

	public void setPosture(Integer posture) {
		this.posture = posture;
	}

	public Integer getMoro() {
		return moro;
	}

	public void setMoro(Integer moro) {
		this.moro = moro;
	}

	public Integer getGrasp() {
		return grasp;
	}

	public void setGrasp(Integer grasp) {
		this.grasp = grasp;
	}

	public Integer getSuck() {
		return suck;
	}

	public void setSuck(Integer suck) {
		this.suck = suck;
	}

	public Integer getRespiration() {
		return respiration;
	}

	public void setRespiration(Integer respiration) {
		this.respiration = respiration;
	}

	public Integer getFrontanelle() {
		return frontanelle;
	}

	public void setFrontanelle(Integer frontanelle) {
		this.frontanelle = frontanelle;
	}

	public Integer getThompsonScore() {
		return thompsonScore;
	}

	public void setThompsonScore(Integer thompsonScore) {
		this.thompsonScore = thompsonScore;
	}

	@Override
	public String toString() {
		return "ScoreThompson [thompsonscoreid=" + thompsonscoreid + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", tone=" + tone + ", loc=" + loc
				+ ", fits=" + fits + ", posture=" + posture + ", moro=" + moro + ", grasp=" + grasp + ", suck=" + suck
				+ ", respiration=" + respiration + ", frontanelle=" + frontanelle + ", thompsonScore=" + thompsonScore
				+ "]";
	}

}
