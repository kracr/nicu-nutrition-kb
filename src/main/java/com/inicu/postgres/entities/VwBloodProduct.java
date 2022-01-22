package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * The persistent class for the vw_blood_product database table.
 * 
 */
@Entity
@Table(name="vw_blood_product")
@NamedQuery(name="VwBloodProduct.findAll", query="SELECT v FROM VwBloodProduct v")

public class VwBloodProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bloodgroup;

	private String bloodproduct;
	
	@Id
	private Long bloodproductsid;

	private Timestamp creationtime;

	private String dose;

	private String duration;

	private Timestamp modificationtime;

	@NotNull @NotEmpty
	private String uhid;

	public VwBloodProduct() {
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getBloodproduct() {
		return this.bloodproduct;
	}

	public void setBloodproduct(String bloodproduct) {
		this.bloodproduct = bloodproduct;
	}

	public Long getBloodproductsid() {
		return this.bloodproductsid;
	}

	public void setBloodproductsid(Long bloodproductsid) {
		this.bloodproductsid = bloodproductsid;
	}

	

	public String getDose() {
		return this.dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

}