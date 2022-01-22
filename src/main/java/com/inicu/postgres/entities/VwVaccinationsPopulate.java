package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the vw_vaccinations_populate database View.
 * 
 * @author Sourabh Verma
 */

@Entity
@Table(name = "vw_vaccinations_populate")
@NamedQuery(name = "VwVaccinationsPopulate.findAll", query = "SELECT v FROM VwVaccinationsPopulate v")
public class VwVaccinationsPopulate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dueage;

	@Id
	private Long vaccineinfoid;

	public VwVaccinationsPopulate() {
		super();
	}

	public String getDueage() {
		return dueage;
	}

	public void setDueage(String dueage) {
		this.dueage = dueage;
	}

	public Long getVaccineinfoid() {
		return vaccineinfoid;
	}

	public void setVaccineinfoid(Long vaccineinfoid) {
		this.vaccineinfoid = vaccineinfoid;
	}

	@Override
	public String toString() {
		return "VwVaccinationsPopulate [dueage=" + dueage + ", vaccineinfoid=" + vaccineinfoid + "]";
	}
}
