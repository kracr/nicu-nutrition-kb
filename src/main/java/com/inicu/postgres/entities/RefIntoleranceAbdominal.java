package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="ref_feedintolerance_abdominal")
@NamedQuery(name="RefIntoleranceAbdominal.findAll", query="SELECT r FROM RefIntoleranceAbdominal r")
public class RefIntoleranceAbdominal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="abdominal_id")
	private String abdominalId;

	@Column(name="description")
	private String description;

	@Column(name="abdominal_distinction")
	private String abdominalDistinction;

	public String getAbdominalId() {
		return abdominalId;
	}

	public void setAbdominalId(String abdominalId) {
		this.abdominalId = abdominalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAbdominalDistinction() {
		return abdominalDistinction;
	}

	public void setAbdominalDistinction(String abdominalDistinction) {
		this.abdominalDistinction = abdominalDistinction;
	}

}
