package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the vw_vaccinations_patient database View.
 * 
 * @author Sourabh Verma
 */

@Entity
@Table(name = "vw_vaccinations_patient")
@NamedQuery(name = "VwVaccinationsPatient.findAll", query = "SELECT v FROM VwVaccinationsPatient v")
public class VwVaccinationsPatient implements Serializable {
	private static final long serialVersionUID = 1L;

	public VwVaccinationsPatient() {
		super();
	}

	@Id
	private Long patientduervaccineid;

	private String uid;

	private String dueage;

	private Long vaccineid;

	private Long vaccineinfoid;

	private String vaccinename;

	@Temporal(TemporalType.DATE)
	private Date duedate;

	@Temporal(TemporalType.DATE)
	private Date givendate;

	@Transient
	private Long duedateLong;

	@Transient
	private Long givendateLong;

	private String administratedby;

	private Long brandid;

	private String brandname;

	private Timestamp creationtime;

	public Long getPatientduervaccineid() {
		return patientduervaccineid;
	}

	public void setPatientduervaccineid(Long patientduervaccineid) {
		this.patientduervaccineid = patientduervaccineid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDueage() {
		return dueage;
	}

	public void setDueage(String dueage) {
		this.dueage = dueage;
	}

	public Long getVaccineid() {
		return vaccineid;
	}

	public void setVaccineid(Long vaccineid) {
		this.vaccineid = vaccineid;
	}

	public Long getVaccineinfoid() {
		return vaccineinfoid;
	}

	public void setVaccineinfoid(Long vaccineinfoid) {
		this.vaccineinfoid = vaccineinfoid;
	}

	public String getVaccinename() {
		return vaccinename;
	}

	public void setVaccinename(String vaccinename) {
		this.vaccinename = vaccinename;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getGivendate() {
		return givendate;
	}

	public void setGivendate(Date givendate) {
		this.givendate = givendate;
	}

	public Long getDuedateLong() {
		return duedateLong;
	}

	public void setDuedateLong(Long duedateLong) {
		this.duedateLong = duedateLong;
	}

	public Long getGivendateLong() {
		return givendateLong;
	}

	public void setGivendateLong(Long givendateLong) {
		this.givendateLong = givendateLong;
	}

	public String getAdministratedby() {
		return administratedby;
	}

	public void setAdministratedby(String administratedby) {
		this.administratedby = administratedby;
	}

	public Long getBrandid() {
		return brandid;
	}

	public void setBrandid(Long brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	@Override
	public String toString() {
		return "VwVaccinationsPatient [patientduervaccineid=" + patientduervaccineid + ", uid=" + uid + ", dueage="
				+ dueage + ", vaccineinfoid=" + vaccineinfoid + ", vaccinename=" + vaccinename + ", duedate=" + duedate
				+ ", givendate=" + givendate + ", administratedby=" + administratedby + ", brandid=" + brandid
				+ ", brandname=" + brandname + ", creationtime=" + creationtime + "]";
	}

}
