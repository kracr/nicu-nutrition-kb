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


/**
 * The persistent class for the discharge_caffeine database table.
 * 
 */
@Entity
@Table(name="discharge_caffeine")
@NamedQuery(name="DischargeCaffeine.findAll", query="SELECT d FROM DischargeCaffeine d")
public class DischargeCaffeine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long caffeineid;

	private Long metabolicid;	
	
	@Temporal(TemporalType.DATE)
	@Column(name="caffeine_enddate")
	private Date caffeineEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="caffeine_startdate")
	private Date caffeineStartdate;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	public DischargeCaffeine() {
	}

	public Long getCaffeineid() {
		return this.caffeineid;
	}

	public void setCaffeineid(Long caffeineid) {
		this.caffeineid = caffeineid;
	}

		

	public Date getCaffeineEnddate() {
		return caffeineEnddate;
	}

	public void setCaffeineEnddate(Date caffeineEnddate) {
		this.caffeineEnddate = caffeineEnddate;
	}

	public Date getCaffeineStartdate() {
		return caffeineStartdate;
	}

	public void setCaffeineStartdate(Date caffeineStartdate) {
		this.caffeineStartdate = caffeineStartdate;
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