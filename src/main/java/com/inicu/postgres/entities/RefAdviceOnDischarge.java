package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ref_advice_on_discharge")
@NamedQuery(name = "RefAdviceOnDischarge.findAll", query = "SELECT s FROM RefAdviceOnDischarge s")
public class RefAdviceOnDischarge {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ref_advice_on_discharge_id")
	private Long refAdviceOnDischargeId;
	
	private Timestamp creationtime;
	
	private Timestamp modificationtime;
	
	private String description;
	
	@Column(name="description_value")
	private String descriptionValue;
	
	@Column(columnDefinition = "bool")
	private Boolean editable;
	
	@Column(columnDefinition = "bool")
	private Boolean status;
	
	@Column(name="branch_name")
	private String branchName;


	@Column(name="isdateincluded",columnDefinition = "bool")
	private Boolean dateIncluded;


	public Long getRefAdviceOnDischargeId() {
		return refAdviceOnDischargeId;
	}

	public void setRefAdviceOnDischargeId(Long refAdviceOnDischargeId) {
		this.refAdviceOnDischargeId = refAdviceOnDischargeId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}

	public void setDescriptionValue(String descriptionValue) {
		this.descriptionValue = descriptionValue;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Boolean getDateIncluded() {
		return dateIncluded;
	}

	public void setDateIncluded(Boolean dateIncluded) {
		this.dateIncluded = dateIncluded;
	}
}
