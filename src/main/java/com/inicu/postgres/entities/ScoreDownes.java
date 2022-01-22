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
@Table(name = "score_downes")
@NamedQuery(name = "ScoreDownes.findAll", query = "SELECT s FROM ScoreDownes s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDownes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long downesscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Integer cynosis;

	private Integer retractions;

	private Integer grunting;

	private Integer airentry;

	private Integer respiratoryrate;

	private Integer downesscore;
	
	private Timestamp entrydate;

	@Column(columnDefinition = "bool")
	private Boolean admission_entry;

	public ScoreDownes() {
		super();
	}

	public Long getDownesscoreid() {
		return downesscoreid;
	}

	public void setDownesscoreid(Long downesscoreid) {
		this.downesscoreid = downesscoreid;
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

	public Integer getCynosis() {
		return cynosis;
	}

	public void setCynosis(Integer cynosis) {
		this.cynosis = cynosis;
	}

	public Integer getRetractions() {
		return retractions;
	}

	public void setRetractions(Integer retractions) {
		this.retractions = retractions;
	}

	public Integer getGrunting() {
		return grunting;
	}

	public void setGrunting(Integer grunting) {
		this.grunting = grunting;
	}

	public Integer getAirentry() {
		return airentry;
	}

	public void setAirentry(Integer airentry) {
		this.airentry = airentry;
	}

	public Integer getRespiratoryrate() {
		return respiratoryrate;
	}

	public void setRespiratoryrate(Integer respiratoryrate) {
		this.respiratoryrate = respiratoryrate;
	}

	public Integer getDownesscore() {
		return downesscore;
	}

	public void setDownesscore(Integer downesscore) {
		this.downesscore = downesscore;
	}

	public Boolean getAdmission_entry() {
		return admission_entry;
	}

	public void setAdmission_entry(Boolean admission_entry) {
		this.admission_entry = admission_entry;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

}
