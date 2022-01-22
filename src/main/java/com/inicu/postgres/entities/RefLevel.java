package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_level database table.
 * 
 */
@Entity
@Table(name="ref_level")
@NamedQuery(name="RefLevel.findAll", query="SELECT r FROM RefLevel r")
public class RefLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String levelid;

	private String description;

	private String levelname;

	public RefLevel() {
	}

	public String getLevelid() {
		return this.levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
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