package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_pain_procedures_list")
@NamedQuery(name="RefPainProceduresList.findAll", query="SELECT r FROM RefPainProceduresList r")
public class RefPainProceduresList {
	@Id
    @Column(name="pain_procedures_id")
    private String painProcedurelId;

    @Column(name="procedure_name")
    private String procedureName;

	public String getPainProcedurelId() {
		return painProcedurelId;
	}

	public void setPainProcedurelId(String painProcedurelId) {
		this.painProcedurelId = painProcedurelId;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
    
}
