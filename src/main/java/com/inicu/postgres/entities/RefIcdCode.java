package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_icd_code")
@NamedQuery(name = "RefIcdCode.findAll", query = "SELECT s FROM RefIcdCode s")
public class RefIcdCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String icdcodeid;

	private String code;

	private String condition;

	private String cause;
	
	private String assessment;

	public String getIcdcodeid() {
		return icdcodeid;
	}

	public void setIcdcodeid(String icdcodeid) {
		this.icdcodeid = icdcodeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

}
