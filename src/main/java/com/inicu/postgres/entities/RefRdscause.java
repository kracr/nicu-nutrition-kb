package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_rdscause database table.
 * 
 */
@Entity
@Table(name="ref_rdscause")
@NamedQuery(name="RefRdscause.findAll", query="SELECT r FROM RefRdscause r")
public class RefRdscause implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String rdscauseid;

	private String description;

	private String rdscause;

	public RefRdscause() {
	}

	public String getRdscauseid() {
		return this.rdscauseid;
	}

	public void setRdscauseid(String rdscauseid) {
		this.rdscauseid = rdscauseid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRdscause() {
		return this.rdscause;
	}

	public void setRdscause(String rdscause) {
		this.rdscause = rdscause;
	}

}