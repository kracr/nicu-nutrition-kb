package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_levenescore database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "ref_levenescore")
@NamedQuery(name = "RefLeveneScore.findAll", query = "SELECT r FROM RefLeveneScore r")
public class RefLeveneScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long ref_levenescoreid;

	@Column(name = "clinical_status")
	private String clinicalStatus;

	@Column(name = "clinical_sign")
	private String clinicalSign;

	private Integer levenescore;

	public Long getRef_levenescoreid() {
		return ref_levenescoreid;
	}

	public void setRef_levenescoreid(Long ref_levenescoreid) {
		this.ref_levenescoreid = ref_levenescoreid;
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

	public Integer getLevenescore() {
		return levenescore;
	}

	public void setLevenescore(Integer levenescore) {
		this.levenescore = levenescore;
	}

	@Override
	public String toString() {
		return "RefLeveneScore [ref_levenescoreid=" + ref_levenescoreid + ", clinicalStatus=" + clinicalStatus
				+ ", clinicalSign=" + clinicalSign + ", levenescore=" + levenescore + "]";
	}

}
