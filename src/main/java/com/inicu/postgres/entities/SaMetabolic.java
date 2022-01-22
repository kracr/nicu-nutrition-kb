package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the sa_metabolic database table.
 * 
 */
@Entity
@Table(name="sa_metabolic")
@NamedQuery(name="SaMetabolic.findAll", query="SELECT s FROM SaMetabolic s")
public class SaMetabolic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long sametabolicid;

	private String comments;

	private Timestamp creationtime;

	private String dayhypoglycemia;

	private String durationhypoglycemia;

	@Column(columnDefinition="bool")
	private Boolean hypoglycemia;

	private String maxgdr;
    private String loggeduser;
	private String minimumbs;

	private Timestamp modificationtime;

	private String treatmentused;

	private String uhid;
	
	//changes 09 march 2017
	private String maximumbs;
	
	private String insulinlevel;
	
	@Column(columnDefinition="bool")
	private Boolean tms;
	
	@Column(columnDefinition="bool")
	private Boolean gcms;
	
	private String symptoms;

	public SaMetabolic() {
	}

	public Long getSametabolicid() {
		return this.sametabolicid;
	}

	public void setSametabolicid(Long sametabolicid) {
		this.sametabolicid = sametabolicid;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDayhypoglycemia() {
		return this.dayhypoglycemia;
	}

	public void setDayhypoglycemia(String dayhypoglycemia) {
		this.dayhypoglycemia = dayhypoglycemia;
	}

	public String getDurationhypoglycemia() {
		return this.durationhypoglycemia;
	}

	public void setDurationhypoglycemia(String durationhypoglycemia) {
		this.durationhypoglycemia = durationhypoglycemia;
	}

	public Boolean getHypoglycemia() {
		return this.hypoglycemia;
	}

	public void setHypoglycemia(Boolean hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}

	public String getMaxgdr() {
		return this.maxgdr;
	}

	public void setMaxgdr(String maxgdr) {
		this.maxgdr = maxgdr;
	}

	public String getMinimumbs() {
		return this.minimumbs;
	}

	public void setMinimumbs(String minimumbs) {
		this.minimumbs = minimumbs;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getTreatmentused() {
		return this.treatmentused;
	}

	public void setTreatmentused(String treatmentused) {
		this.treatmentused = treatmentused;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMaximumbs() {
		return maximumbs;
	}

	public void setMaximumbs(String maximumbs) {
		this.maximumbs = maximumbs;
	}

	public String getInsulinlevel() {
		return insulinlevel;
	}

	public void setInsulinlevel(String insulinlevel) {
		this.insulinlevel = insulinlevel;
	}

	public Boolean getTms() {
		return tms;
	}

	public void setTms(Boolean tms) {
		this.tms = tms;
	}

	public Boolean getGcms() {
		return gcms;
	}

	public void setGcms(Boolean gcms) {
		this.gcms = gcms;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	
	


}