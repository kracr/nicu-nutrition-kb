package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_pd_indication database table.
 * 
 */

@Entity
@Table(name="ref_pd_indication")
@NamedQuery(name="RefPdIndication.findAll", query="SELECT r FROM RefPdIndication r")
public class RefPdIndication implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="indication_id")
	private String indicationId;
	
	@Column(name="description")
	private String description;

	@Column(name="indication_name")
	private String indicationName;

	public RefPdIndication() {
	}
	
	public String getIndicationId() {
		return indicationId;
	}

	public void setIndicationId(String indicationId) {
		this.indicationId = indicationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndicationName() {
		return indicationName;
	}

	public void setIndicationName(String indicationName) {
		this.indicationName = indicationName;
	}

	

}