package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_bindscore database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "ref_bindscore")
@NamedQuery(name = "RefBindScore.findAll", query = "SELECT r FROM RefBindScore r")
public class RefBindScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long ref_bindscoreid;

	@Column(name = "clinical_status")
	private String clinicalStatus;

	@Column(name = "clinical_sign")
	private String clinicalSign;

	private Integer bindscore;

	public Long getRef_bindscoreid() {
		return ref_bindscoreid;
	}

	public void setRef_bindscoreid(Long ref_bindscoreid) {
		this.ref_bindscoreid = ref_bindscoreid;
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

	public Integer getBindscore() {
		return bindscore;
	}

	public void setBindscore(Integer bindscore) {
		this.bindscore = bindscore;
	}

}
