package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_painscore database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "ref_painscore")
@NamedQuery(name = "RefPainScore.findAll", query = "SELECT r FROM RefPainScore r")
public class RefPainScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long ref_painscoreid;

	@Column(name = "clinical_status")
	private String clinicalStatus;

	@Column(name = "clinical_sign")
	private String clinicalSign;

	private Integer painscore;

	public Long getRef_painscoreid() {
		return ref_painscoreid;
	}

	public void setRef_painscoreid(Long ref_painscoreid) {
		this.ref_painscoreid = ref_painscoreid;
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

	public Integer getPainscore() {
		return painscore;
	}

	public void setPainscore(Integer painscore) {
		this.painscore = painscore;
	}

}
