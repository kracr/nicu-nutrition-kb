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
@Table(name = "score_nips")
@NamedQuery(name = "ScoreNIPS.findAll", query = "SELECT s FROM ScoreNIPS s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreNIPS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long NIPSscoreid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private Integer facial;

	private Integer cry;

	private Integer breathing;

	private Integer arms;

	private Integer legs;

	private Integer arousal;
	
	private Integer nipsscore;
	
	private Timestamp entrydate;

	@Column(columnDefinition = "bool")
	private Boolean admission_entry;

	public ScoreNIPS() {
		super();
	}

	public Long getNIPSscoreid() {
		return NIPSscoreid;
	}

	public void setNIPSscoreid(Long nIPSscoreid) {
		NIPSscoreid = nIPSscoreid;
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

	public Integer getFacial() {
		return facial;
	}

	public void setFacial(Integer facial) {
		this.facial = facial;
	}

	public Integer getCry() {
		return cry;
	}

	public void setCry(Integer cry) {
		this.cry = cry;
	}

	public Integer getBreathing() {
		return breathing;
	}

	public void setBreathing(Integer breathing) {
		this.breathing = breathing;
	}

	public Integer getArms() {
		return arms;
	}

	public void setArms(Integer arms) {
		this.arms = arms;
	}

	public Integer getLegs() {
		return legs;
	}

	public void setLegs(Integer legs) {
		this.legs = legs;
	}

	public Integer getArousal() {
		return arousal;
	}

	public void setArousal(Integer arousal) {
		this.arousal = arousal;
	}

	public Integer getNipsscore() {
		return nipsscore;
	}

	public void setNipsscore(Integer nipsscore) {
		this.nipsscore = nipsscore;
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
