package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_medtype")
@NamedQuery(name="RefMedType.findAll", query="SELECT r FROM RefMedType r")
public class RefMedType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String typeid;

	private String typevalue;

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypevalue() {
		return typevalue;
	}

	public void setTypevalue(String typevalue) {
		this.typevalue = typevalue;
	}
}
