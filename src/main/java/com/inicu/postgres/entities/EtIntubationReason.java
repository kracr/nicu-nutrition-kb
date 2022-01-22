package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the et_intubation_reason database table.
 * 
 * @author Krishna
 */
@Entity
@Table(name = "ref_et_intubation_reason")
public class EtIntubationReason {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "etintubationreasonid")
	private String etIntubationReasonId;

	@Column(name = "etintubationreason")
	private String etIntubationReason;
	
	private String description;

	@Column(name = "etintubationreasontype")
	private String etIntubationReasonType;

	public String getEtIntubationReasonId() {
		return etIntubationReasonId;
	}

	public void setEtIntubationReasonId(String etIntubationReasonId) {
		this.etIntubationReasonId = etIntubationReasonId;
	}

	public String getEtIntubationReason() {
		return etIntubationReason;
	}

	public void setEtIntubationReason(String etIntubationReason) {
		this.etIntubationReason = etIntubationReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtIntubationReasonType() {
		return etIntubationReasonType;
	}

	public void setEtIntubationReasonType(String etIntubationReasonType) {
		this.etIntubationReasonType = etIntubationReasonType;
	}
}
