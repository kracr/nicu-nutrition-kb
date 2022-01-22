package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_criticallevel database table.
 * 
 */
@Entity
@Table(name="ref_criticallevel")
@NamedQuery(name="RefCriticallevel.findAll", query="SELECT r FROM RefCriticallevel r")
public class RefCriticallevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String crlevelid;

	private String description;

	private String levelname;

	public RefCriticallevel() {
	}

	public String getCrlevelid() {
		return this.crlevelid;
	}

	public void setCrlevelid(String crlevelid) {
		this.crlevelid = crlevelid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevelname() {
		return this.levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

}