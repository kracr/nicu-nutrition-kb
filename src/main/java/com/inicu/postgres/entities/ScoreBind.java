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
 * The persistent class for the score_bind database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_bind")
@NamedQuery(name = "ScoreBind.findAll", query = "SELECT s FROM ScoreBind s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreBind implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bindscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String mentalstatus;
	private Integer mentalstatusscore;

	private String muscletone;
	private Integer muscletonescore;

	private String crypattern;
	private Integer crypatternscore;

	private String oculomotor;
	private Integer oculomotorscore;

	private Integer bindscore;

	public ScoreBind() {
		super();
	}

	public Long getBindscoreid() {
		return bindscoreid;
	}

	public void setBindscoreid(Long bindscoreid) {
		this.bindscoreid = bindscoreid;
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

	public String getMentalstatus() {
		return mentalstatus;
	}

	public void setMentalstatus(String mentalstatus) {
		this.mentalstatus = mentalstatus;
	}

	public Integer getMentalstatusscore() {
		return mentalstatusscore;
	}

	public void setMentalstatusscore(Integer mentalstatusscore) {
		this.mentalstatusscore = mentalstatusscore;
	}

	public String getMuscletone() {
		return muscletone;
	}

	public void setMuscletone(String muscletone) {
		this.muscletone = muscletone;
	}

	public Integer getMuscletonescore() {
		return muscletonescore;
	}

	public void setMuscletonescore(Integer muscletonescore) {
		this.muscletonescore = muscletonescore;
	}

	public String getCrypattern() {
		return crypattern;
	}

	public void setCrypattern(String crypattern) {
		this.crypattern = crypattern;
	}

	public Integer getCrypatternscore() {
		return crypatternscore;
	}

	public void setCrypatternscore(Integer crypatternscore) {
		this.crypatternscore = crypatternscore;
	}

	public String getOculomotor() {
		return oculomotor;
	}

	public void setOculomotor(String oculomotor) {
		this.oculomotor = oculomotor;
	}

	public Integer getOculomotorscore() {
		return oculomotorscore;
	}

	public void setOculomotorscore(Integer oculomotorscore) {
		this.oculomotorscore = oculomotorscore;
	}

	public Integer getBindscore() {
		return bindscore;
	}

	public void setBindscore(Integer bindscore) {
		this.bindscore = bindscore;
	}

}
