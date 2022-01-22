package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the discharge_aminophylline database table.
 * 
 */
@Entity
@Table(name="discharge_aminophylline")
@NamedQuery(name="DischargeAminophylline.findAll", query="SELECT d FROM DischargeAminophylline d")
public class DischargeAminophylline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long aminophyllineid;

	
	private Long metabolicid;
	
	@Transient
	private Timestamp aminophylline;

	@Temporal(TemporalType.DATE)
	@Column(name="aminophylline_enddate")
	private Date aminophyllineEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="aminophylline_startdate")
	private Date aminophyllineStartdate;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	public DischargeAminophylline() {
	}

	public Long getAminophyllineid() {
		return this.aminophyllineid;
	}

	public void setAminophyllineid(Long aminophyllineid) {
		this.aminophyllineid = aminophyllineid;
	}

	public Timestamp getAminophylline() {
		return aminophylline;
	}

	public void setAminophylline(Timestamp aminophylline) {
		this.aminophylline = aminophylline;
	}

	

	

	public Date getAminophyllineEnddate() {
		return aminophyllineEnddate;
	}

	public void setAminophyllineEnddate(Date aminophyllineEnddate) {
		this.aminophyllineEnddate = aminophyllineEnddate;
	}

	public Date getAminophyllineStartdate() {
		return aminophyllineStartdate;
	}

	public void setAminophyllineStartdate(Date aminophyllineStartdate) {
		this.aminophyllineStartdate = aminophyllineStartdate;
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

	public Long getMetabolicid() {
		return metabolicid;
	}

	public void setMetabolicid(Long metabolicid) {
		this.metabolicid = metabolicid;
	}

}