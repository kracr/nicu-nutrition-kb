package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_enaddtivesbrand")
@NamedQuery(name="RefEnAddtivesBrand.findAll", query="SELECT r FROM RefEnAddtivesBrand r")
public class RefEnAddtivesBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String enaddtivesbrandid;

	private String addtivename;
	
	private String brandname;
	
	private String unit;
	
	@Column(columnDefinition = "Float4")
	private Float strength;

	public String getEnaddtivesbrandid() {
		return enaddtivesbrandid;
	}

	public void setEnaddtivesbrandid(String enaddtivesbrandid) {
		this.enaddtivesbrandid = enaddtivesbrandid;
	}

	public String getAddtivename() {
		return addtivename;
	}

	public void setAddtivename(String addtivename) {
		this.addtivename = addtivename;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getStrength() {
		return strength;
	}

	public void setStrength(Float strength) {
		this.strength = strength;
	}

}
