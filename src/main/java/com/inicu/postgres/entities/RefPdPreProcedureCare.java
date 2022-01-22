package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_pd_indication database table.
 * 
 */
@Entity
@Table(name="ref_pd_pre_procedure_care")
@NamedQuery(name="RefPdPreProcedureCare.findAll", query="SELECT r FROM RefPdPreProcedureCare r")
public class RefPdPreProcedureCare
 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pre_procedure_care_id")
	private String PreProcedureCareId;
	
	@Column(name="description")
	private String description;

	@Column(name="pre_procedure_care_name")
	private String PreProcedureCareName;

	public RefPdPreProcedureCare() {
	}
	
	public String getPreProcedureCareId() {
		return PreProcedureCareId;
	}


	public void setPreProcedureCareId(String preProcedureCareId) {
		this.PreProcedureCareId = preProcedureCareId;
	}


	public String getPreProcedureCareName() {
		return this.PreProcedureCareName;
	}


	public void setPreProcedureCareName(String preProcedureCareName) {
		this.PreProcedureCareName = preProcedureCareName;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}