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

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the discharge_ventcourse database table.
 * 
 */
@Entity
@Table(name="discharge_ventcourse")
@NamedQuery(name="DischargeVentcourse.findAll", query="SELECT d FROM DischargeVentcourse d")
public class DischargeVentcourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long ventcourseid;
	
	private Long nicuventillationid;

	

	@Column(name="initial_fio2")
	private String initialFio2;

	@Column(name="initial_map")
	private String initialMap;

	@Column(name="initial_peep")
	private String initialPeep;

	@Column(name="initial_pip")
	private String initialPip;

	@Column(name="initial_rate")
	private String initialRate;

	@Column(name="initial_tv")
	private String initialTv;

	@Column(name="initial_ventmode")
	private String initialVentmode;

	@Column(name="maximum_fio2")
	private String maximumFio2;

	@Column(name="maximum_map")
	private String maximumMap;

	@Column(name="maximum_peep")
	private String maximumPeep;

	@Column(name="maximum_pip")
	private String maximumPip;

	@Column(name="maximum_rate")
	private String maximumRate;

	@Column(name="maximum_tv")
	private String maximumTv;

	@Column(name="maximum_ventmode")
	private String maximumVentmode;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Temporal(TemporalType.DATE)
	@Column(name="vent_enddate")
	private Date ventEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="vent_startdate")
	private Date ventStartdate;
	
	@Transient
	private String ventStartDateStr;
	

	public Date getVentStartdate() {
		return ventStartdate;
	}

	public void setVentStartdate(Date ventStartdate) {
		this.ventStartdate = ventStartdate;
	}

	public DischargeVentcourse() {
	}

	public Long getVentcourseid() {
		return this.ventcourseid;
	}

	public void setVentcourseid(Long ventcourseid) {
		this.ventcourseid = ventcourseid;
	}

	
	public String getInitialFio2() {
		return this.initialFio2;
	}

	public void setInitialFio2(String initialFio2) {
		this.initialFio2 = initialFio2;
	}

	public String getInitialMap() {
		return this.initialMap;
	}

	public void setInitialMap(String initialMap) {
		this.initialMap = initialMap;
	}

	public String getInitialPeep() {
		return this.initialPeep;
	}

	public void setInitialPeep(String initialPeep) {
		this.initialPeep = initialPeep;
	}

	public String getInitialPip() {
		return this.initialPip;
	}

	public void setInitialPip(String initialPip) {
		this.initialPip = initialPip;
	}

	public String getInitialRate() {
		return this.initialRate;
	}

	public void setInitialRate(String initialRate) {
		this.initialRate = initialRate;
	}

	public String getInitialTv() {
		return this.initialTv;
	}

	public void setInitialTv(String initialTv) {
		this.initialTv = initialTv;
	}

	public String getInitialVentmode() {
		return this.initialVentmode;
	}

	public void setInitialVentmode(String initialVentmode) {
		this.initialVentmode = initialVentmode;
	}

	public String getMaximumFio2() {
		return this.maximumFio2;
	}

	public void setMaximumFio2(String maximumFio2) {
		this.maximumFio2 = maximumFio2;
	}

	public String getMaximumMap() {
		return this.maximumMap;
	}

	public void setMaximumMap(String maximumMap) {
		this.maximumMap = maximumMap;
	}

	public String getMaximumPeep() {
		return this.maximumPeep;
	}

	public void setMaximumPeep(String maximumPeep) {
		this.maximumPeep = maximumPeep;
	}

	public String getMaximumPip() {
		return this.maximumPip;
	}

	public void setMaximumPip(String maximumPip) {
		this.maximumPip = maximumPip;
	}

	public String getMaximumRate() {
		return this.maximumRate;
	}

	public void setMaximumRate(String maximumRate) {
		this.maximumRate = maximumRate;
	}

	public String getMaximumTv() {
		return this.maximumTv;
	}

	public void setMaximumTv(String maximumTv) {
		this.maximumTv = maximumTv;
	}

	public String getMaximumVentmode() {
		return this.maximumVentmode;
	}

	public void setMaximumVentmode(String maximumVentmode) {
		this.maximumVentmode = maximumVentmode;
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

	public Date getVentEnddate() {
		return this.ventEnddate;
	}

	public void setVentEnddate(Date ventEnddate) {
		this.ventEnddate = ventEnddate;
	}



	
	public Long getNicuventillationid() {
		return nicuventillationid;
	}

	public void setNicuventillationid(Long nicuventillationid) {
		this.nicuventillationid = nicuventillationid;
	}

	public String getVentStartDateStr() {
		return ventStartDateStr;
	}

	public void setVentStartDateStr(String ventStartDateStr) {
		this.ventStartDateStr = ventStartDateStr;
	}
	

}