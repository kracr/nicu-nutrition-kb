package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the discharge_phototherapy database table.
 * 
 */
@Entity
@Table(name="discharge_phototherapy")
@NamedQuery(name="DischargePhototherapy.findAll", query="SELECT d FROM DischargePhototherapy d")
public class DischargePhototherapy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long phototherapyid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Temporal(TemporalType.DATE)
	@Column(name="phototherapy_enddate")
	private Date phototherapyEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="phototherapy_startdate")
	private Date phototherapyStartdate;

	@Column(name="phototherapy_type",columnDefinition="bool")
	private Boolean phototherapyType;
	
	private Long metabolicid;

	public DischargePhototherapy() {
	}

	public Long getPhototherapyid() {
		return this.phototherapyid;
	}

	public void setPhototherapyid(Long phototherapyid) {
		this.phototherapyid = phototherapyid;
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

	

	public Date getPhototherapyEnddate() {
		return phototherapyEnddate;
	}

	public void setPhototherapyEnddate(Date phototherapyEnddate) {
		this.phototherapyEnddate = phototherapyEnddate;
	}

	public Date getPhototherapyStartdate() {
		return phototherapyStartdate;
	}

	public void setPhototherapyStartdate(Date phototherapyStartdate) {
		this.phototherapyStartdate = phototherapyStartdate;
	}

	

	public Boolean getPhototherapyType() {
		return phototherapyType;
	}

	public void setPhototherapyType(Boolean phototherapyType) {
		this.phototherapyType = phototherapyType;
	}

	public Long getMetabolicid() {
		return metabolicid;
	}

	public void setMetabolicid(Long metabolicid) {
		this.metabolicid = metabolicid;
	}

}