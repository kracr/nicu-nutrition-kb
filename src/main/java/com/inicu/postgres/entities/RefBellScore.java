package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_bellscore")
@NamedQuery(name = "RefBellScore.findAll", query = "SELECT r FROM RefBellScore r")
public class RefBellScore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long ref_bellscoreid;

	@Column(name = "clinical_status")
	private String clinicalStatus;

	@Column(name = "clinical_sign")
	private String clinicalSign;

	private String bellscore;

	public Long getRef_bellscoreid() {
		return ref_bellscoreid;
	}

	public void setRef_bellscoreid(Long ref_bellscoreid) {
		this.ref_bellscoreid = ref_bellscoreid;
	}

	public String getClinicalStatus() {
		return clinicalStatus;
	}

	public void setClinicalStatus(String clinicalStatus) {
		this.clinicalStatus = clinicalStatus;
	}

	public String getClinicalSign() {
		return clinicalSign;
	}

	public void setClinicalSign(String clinicalSign) {
		this.clinicalSign = clinicalSign;
	}

	public String getBellscore() {
		return bellscore;
	}

	public void setBellscore(String bellscore) {
		this.bellscore = bellscore;
	}
}
