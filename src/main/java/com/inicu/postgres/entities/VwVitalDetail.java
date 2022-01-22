package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "vw_vital_detail")
@NamedQuery(name = "VwVitalDetail.findAll", query = "SELECT v FROM VwVitalDetail v")
public class VwVitalDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String uhid;

	private String pulserate;

	private String spo2;

	private String heartrate;

	@Column(name = "ecg_resprate")
	private String ecgResprate;

	@Column(name = "co2_resprate")
	private String co2Resprate;

	private String etco2;

	@Column(name = "sys_bp")
	private String sysBp;

	@Column(name = "dia_bp")
	private String diaBp;

	@Column(name = "mean_bp")
	private String meanBp;

	@Column(name = "nbp_s")
	private String nbpS;

	@Column(name = "nbp_d")
	private String nbpD;

	@Column(name = "nbp_m")
	private String nbpM;

	@Transient
	private Timestamp creationtime;

	private Timestamp starttime;

	private String fio2;

	private String pip;

	private String peep;

	private Timestamp start_time;

	private Timestamp entrydate;
	
	private Timestamp monitorlatesttime;

	private Timestamp ventilatorlatesttime;

	@Column(columnDefinition = "float4")
	private Float hr_rate;

	@Column(columnDefinition = "float4")
	private Float skintemp;

	private String spo21;

	private String mean_bp1;
	
	private String branchname;
	
	@Column(name = "bp_type")
	private String monitorException;
	
	@Column(name = "admission_bp")
	private String ventilatorException;

	public VwVitalDetail() {
		super();
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getPulserate() {
		return pulserate;
	}

	public void setPulserate(String pulserate) {
		this.pulserate = pulserate;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}

	public String getEcgResprate() {
		return ecgResprate;
	}

	public void setEcgResprate(String ecgResprate) {
		this.ecgResprate = ecgResprate;
	}

	public String getCo2Resprate() {
		return co2Resprate;
	}

	public void setCo2Resprate(String co2Resprate) {
		this.co2Resprate = co2Resprate;
	}

	public String getEtco2() {
		return etco2;
	}

	public void setEtco2(String etco2) {
		this.etco2 = etco2;
	}

	public String getSysBp() {
		return sysBp;
	}

	public void setSysBp(String sysBp) {
		this.sysBp = sysBp;
	}

	public String getDiaBp() {
		return diaBp;
	}

	public void setDiaBp(String diaBp) {
		this.diaBp = diaBp;
	}

	public String getMeanBp() {
		return meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public String getNbpS() {
		return nbpS;
	}

	public void setNbpS(String nbpS) {
		this.nbpS = nbpS;
	}

	public String getNbpD() {
		return nbpD;
	}

	public void setNbpD(String nbpD) {
		this.nbpD = nbpD;
	}

	public String getNbpM() {
		return nbpM;
	}

	public void setNbpM(String nbpM) {
		this.nbpM = nbpM;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getFio2() {
		return fio2;
	}

	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}

	public String getPip() {
		return pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public String getPeep() {
		return peep;
	}

	public void setPeep(String peep) {
		this.peep = peep;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Timestamp entrydate) {
		this.entrydate = entrydate;
	}

	public Float getHr_rate() {
		return hr_rate;
	}

	public void setHr_rate(Float hr_rate) {
		this.hr_rate = hr_rate;
	}

	public Float getSkintemp() {
		return skintemp;
	}

	public void setSkintemp(Float skintemp) {
		this.skintemp = skintemp;
	}

	public String getSpo21() {
		return spo21;
	}

	public void setSpo21(String spo21) {
		this.spo21 = spo21;
	}

	public String getMean_bp1() {
		return mean_bp1;
	}

	public void setMean_bp1(String mean_bp1) {
		this.mean_bp1 = mean_bp1;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getMonitorException() {
		return monitorException;
	}

	public void setMonitorException(String monitorException) {
		this.monitorException = monitorException;
	}

	public String getVentilatorException() {
		return ventilatorException;
	}

	public void setVentilatorException(String ventilatorException) {
		this.ventilatorException = ventilatorException;
	}

	public Timestamp getMonitorlatesttime() {
		return monitorlatesttime;
	}

	public void setMonitorlatesttime(Timestamp monitorlatesttime) {
		this.monitorlatesttime = monitorlatesttime;
	}

	public Timestamp getVentilatorlatesttime() {
		return ventilatorlatesttime;
	}

	public void setVentilatorlatesttime(Timestamp ventilatorlatesttime) {
		this.ventilatorlatesttime = ventilatorlatesttime;
	}

}
