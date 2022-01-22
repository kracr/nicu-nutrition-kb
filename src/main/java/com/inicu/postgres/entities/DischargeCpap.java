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
 * The persistent class for the discharge_cpap database table.
 * 
 */
@Entity
@Table(name="discharge_cpap")
@NamedQuery(name="DischargeCpap.findAll", query="SELECT d FROM DischargeCpap d")
public class DischargeCpap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long cpapid;

	private Long metabolicid;
	
	private Timestamp cpap;

	@Temporal(TemporalType.DATE)
	@Column(name="cpap_enddate")
	private Date cpapEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="cpap_startdate")
	private Date cpapStartdate;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	public DischargeCpap() {
	}

	public Long getCpapid() {
		return this.cpapid;
	}

	public void setCpapid(Long cpapid) {
		this.cpapid = cpapid;
	}

	public Timestamp getCpap() {
		return this.cpap;
	}

	public void setCpap(Timestamp cpap) {
		this.cpap = cpap;
	}

	

	
	public Date getCpapEnddate() {
		return cpapEnddate;
	}

	public void setCpapEnddate(Date cpapEnddate) {
		this.cpapEnddate = cpapEnddate;
	}

	public Date getCpapStartdate() {
		return cpapStartdate;
	}

	public void setCpapStartdate(Date cpapStartdate) {
		this.cpapStartdate = cpapStartdate;
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