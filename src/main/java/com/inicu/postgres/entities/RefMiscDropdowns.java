package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ref_misc_dropdowns database table.
 * 
 */

@Entity
@Table(name="ref_misc_dropdowns")
@NamedQuery(name="RefMiscDropdowns.findAll", query="SELECT r FROM RefMiscDropdowns r")
public class RefMiscDropdowns implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="misc_id")
	private int miscId;
	
	@Column(name="description")
	private String description;

	@Column(name="assessment_name")
	private String assessmentName;

	public int getMiscId() {
		return miscId;
	}

	public void setMiscId(int miscId) {
		this.miscId = miscId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssessmentName() {
		return assessmentName;
	}

	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	

}