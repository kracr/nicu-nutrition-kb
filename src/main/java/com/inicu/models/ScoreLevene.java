package com.inicu.models;

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
 * The persistent class for the score_levene database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "score_levene")
@NamedQuery(name = "ScoreLevene.findAll", query = "SELECT s FROM ScoreLevene s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreLevene implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long levenescoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String consciousness;
	private String consciousnessscore;

	private String tone;
	private String tonescore;

	private String seizures;
	private String seizuresscore;

	private String suckingrespiration;
	private String suckingrespirationscore;

	private String levenescore;

	@Column(columnDefinition = "bool")
	private Boolean admission_entry;

	public ScoreLevene() {
		super();
	}

	public Long getLevenescoreid() {
		return levenescoreid;
	}

	public void setLevenescoreid(Long levenescoreid) {
		this.levenescoreid = levenescoreid;
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

	public String getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(String consciousness) {
		this.consciousness = consciousness;
	}

	public String getConsciousnessscore() {
		return consciousnessscore;
	}

	public void setConsciousnessscore(String consciousnessscore) {
		this.consciousnessscore = consciousnessscore;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public String getTonescore() {
		return tonescore;
	}

	public void setTonescore(String tonescore) {
		this.tonescore = tonescore;
	}

	public String getSeizures() {
		return seizures;
	}

	public void setSeizures(String seizures) {
		this.seizures = seizures;
	}

	public String getSeizuresscore() {
		return seizuresscore;
	}

	public void setSeizuresscore(String seizuresscore) {
		this.seizuresscore = seizuresscore;
	}

	public String getSuckingrespiration() {
		return suckingrespiration;
	}

	public void setSuckingrespiration(String suckingrespiration) {
		this.suckingrespiration = suckingrespiration;
	}

	public String getSuckingrespirationscore() {
		return suckingrespirationscore;
	}

	public void setSuckingrespirationscore(String suckingrespirationscore) {
		this.suckingrespirationscore = suckingrespirationscore;
	}

	public String getLevenescore() {
		return levenescore;
	}

	public void setLevenescore(String levenescore) {
		this.levenescore = levenescore;
	}

	public Boolean getAdmission_entry() {
		return admission_entry;
	}

	public void setAdmission_entry(Boolean admission_entry) {
		this.admission_entry = admission_entry;
	}

	@Override
	public String toString() {
		return "ScoreLevene [levenescoreid=" + levenescoreid + ", creationtime=" + creationtime + ", modificationtime="
				+ modificationtime + ", uhid=" + uhid + ", consciousness=" + consciousness + ", consciousnessscore="
				+ consciousnessscore + ", tone=" + tone + ", tonescore=" + tonescore + ", seizures=" + seizures
				+ ", seizuresscore=" + seizuresscore + ", suckingrespiration=" + suckingrespiration
				+ ", suckingrespirationscore=" + suckingrespirationscore + ", levenescore=" + levenescore + "]";
	}

}
