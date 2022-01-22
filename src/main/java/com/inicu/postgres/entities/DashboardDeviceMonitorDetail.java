package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the dashboard_device_monitor_detail database table.
 * 
 */
@Entity
@Table(name = "dashboard_device_monitor_detail")
@NamedQuery(name = "DashboardDeviceMonitorDetail.findAll", query = "SELECT d FROM DashboardDeviceMonitorDetail d")
public class DashboardDeviceMonitorDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uhid;

	private String beddeviceid;

	private String pulserate;

	private String spo2;

	private String heartrate;
	
	private Timestamp creationtime;
	
	private Timestamp modificationtime;

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

	@Column(name = "ibp_s")
	private String ibpS;

	@Column(name = "ibp_d")
	private String ibpD;

	@Column(name = "ibp_m")
	private String ibpM;
	
	@Column(name="temp")
	private String temperature;
	
	private String pi;
	
	private String o3rSO2_1;
	
	private String o3rSO2_2;

	private String pa; // pulse amplitude

	
	private Timestamp starttime;
	
	private String ra;
	
	private String cvp;
	
	private String pls;

	public DashboardDeviceMonitorDetail() {
		super();
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBeddeviceid() {
		return beddeviceid;
	}

	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
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

	public String getIbpS() {
		return ibpS;
	}

	public void setIbpS(String ibpS) {
		this.ibpS = ibpS;
	}

	public String getIbpD() {
		return ibpD;
	}

	public void setIbpD(String ibpD) {
		this.ibpD = ibpD;
	}

	public String getIbpM() {
		return ibpM;
	}

	public void setIbpM(String ibpM) {
		this.ibpM = ibpM;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getPi() {
		return pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

	public String getO3rSO2_1() {
		return o3rSO2_1;
	}

	public void setO3rSO2_1(String o3rSO2_1) {
		this.o3rSO2_1 = o3rSO2_1;
	}

	public String getO3rSO2_2() {
		return o3rSO2_2;
	}

	public void setO3rSO2_2(String o3rSO2_2) {
		this.o3rSO2_2 = o3rSO2_2;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getCvp() {
		return cvp;
	}

	public void setCvp(String cvp) {
		this.cvp = cvp;
	}

	public String getPls() {
		return pls;
	}

	public void setPls(String pls) {
		this.pls = pls;
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

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "DashboardDeviceMonitorDetail [uhid=" + uhid + ", beddeviceid=" + beddeviceid + ", pulserate="
				+ pulserate + ", spo2=" + spo2 + ", heartrate=" + heartrate + ", ecgResprate=" + ecgResprate
				+ ", co2Resprate=" + co2Resprate + ", etco2=" + etco2 + ", sysBp=" + sysBp + ", diaBp=" + diaBp
				+ ", meanBp=" + meanBp + ", nbpS=" + nbpS + ", nbpD=" + nbpD + ", nbpM=" + nbpM + ", pi=" + pi
				+ ", o3rSO2_1=" + o3rSO2_1 + ", o3rSO2_2=" + o3rSO2_2 + ", starttime=" + starttime + ", getUhid()="
				+ getUhid() + ", getBeddeviceid()=" + getBeddeviceid() + ", getPulserate()=" + getPulserate()
				+ ", getSpo2()=" + getSpo2() + ", getHeartrate()=" + getHeartrate() + ", getEcgResprate()="
				+ getEcgResprate() + ", getCo2Resprate()=" + getCo2Resprate() + ", getEtco2()=" + getEtco2()
				+ ", getSysBp()=" + getSysBp() + ", getDiaBp()=" + getDiaBp() + ", getMeanBp()=" + getMeanBp()
				+ ", getNbpS()=" + getNbpS() + ", getNbpD()=" + getNbpD() + ", getNbpM()=" + getNbpM()
				+ ", getStarttime()=" + getStarttime() + ", getPi()=" + getPi() + ", getO3rSO2_1()=" + getO3rSO2_1()
				+ ", getO3rSO2_2()=" + getO3rSO2_2() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
