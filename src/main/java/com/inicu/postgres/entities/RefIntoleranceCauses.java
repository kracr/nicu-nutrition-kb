package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_feedintolerance_causes")
@NamedQuery(name="RefIntoleranceCauses.findAll", query="SELECT r FROM RefIntoleranceCauses r")
public class RefIntoleranceCauses implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fic_id")
	private String ficId;

	@Column(name="description")
	private String description;

	@Column(name="cause")
	private String cause;

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFicId() {
		return ficId;
	}

	public void setFicId(String ficId) {
		this.ficId = ficId;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	

	
}
