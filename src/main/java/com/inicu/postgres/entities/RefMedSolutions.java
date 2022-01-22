package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_medsolutions")
@NamedQuery(name="RefMedSolutions.findAll", query="SELECT r FROM RefMedSolutions r")
public class RefMedSolutions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long refmedsolutionsid;

	private String medid;
	
	private String medname;
	
	private String diluenttype;

	public Long getRefmedsolutionsid() {
		return refmedsolutionsid;
	}

	public void setRefmedsolutionsid(Long refmedsolutionsid) {
		this.refmedsolutionsid = refmedsolutionsid;
	}

	public String getMedid() {
		return medid;
	}

	public void setMedid(String medid) {
		this.medid = medid;
	}

	public String getMedname() {
		return medname;
	}

	public void setMedname(String medname) {
		this.medname = medname;
	}

	public String getDiluenttype() {
		return diluenttype;
	}

	public void setDiluenttype(String diluenttype) {
		this.diluenttype = diluenttype;
	}

}