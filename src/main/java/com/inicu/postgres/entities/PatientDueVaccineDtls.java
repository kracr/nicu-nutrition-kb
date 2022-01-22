package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the patient_due_vaccine_dtls database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "patient_due_vaccine_dtls")
@NamedQuery(name = "PatientDueVaccineDtls.findAll", query = "SELECT s FROM PatientDueVaccineDtls s")
public class PatientDueVaccineDtls implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long patientduervaccineid;

	private String uid;

	private Long vaccineinfoid;

	@Temporal(TemporalType.DATE)
	private Date duedate;

	@Temporal(TemporalType.DATE)
	private Date givendate;

	private String administratedby;

	private Long brandid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	public PatientDueVaccineDtls() {
		super();
	}

	public Long getPatientduervaccineid() {
		return patientduervaccineid;
	}

	public void setPatientduervaccineid(Long patientduervaccineid) {
		this.patientduervaccineid = patientduervaccineid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getVaccineinfoid() {
		return vaccineinfoid;
	}

	public void setVaccineinfoid(Long vaccineinfoid) {
		this.vaccineinfoid = vaccineinfoid;
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

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

}
