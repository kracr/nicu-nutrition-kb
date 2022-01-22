package com.inicu.postgres.entities;
/**
 * The persistent class for the vw_vaccinations_brand database View.
 * 
 * @author Sourabh Verma
 */

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vw_vaccinations_brand")
@NamedQuery(name = "VwVaccinationsBrand.findAll", query = "SELECT v FROM VwVaccinationsBrand v")
public class VwVaccinationsBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	public VwVaccinationsBrand() {
		super();
	}

	private Long vaccineid;

	private String vaccinename;

	@Id
	private Long id;

	private String brandname;

	public Long getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(Long vaccineid) {
		this.vaccineid = vaccineid;
	}

	public String getVaccinename() {
		return vaccinename;
	}

	public void setVaccinename(String vaccinename) {
		this.vaccinename = vaccinename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	@Override
	public String toString() {
		return "VwVaccinationsBrand [vaccineid=" + vaccineid + ", vaccinename=" + vaccinename + ", id=" + id
				+ ", brandname=" + brandname + "]";
	}
}
