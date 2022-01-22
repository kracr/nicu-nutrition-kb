package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_masterbo database table.
 * 
 */
@Entity
@Table(name="ref_masterbo")
@NamedQuery(name="RefMasterbo.findAll", query="SELECT r FROM RefMasterbo r")
public class RefMasterbo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String masterboid;

	private String description;

	private String masterboname;

	public RefMasterbo() {
	}

	public String getMasterboid() {
		return this.masterboid;
	}

	public void setMasterboid(String masterboid) {
		this.masterboid = masterboid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMasterboname() {
		return this.masterboname;
	}

	public void setMasterboname(String masterboname) {
		this.masterboname = masterboname;
	}

}