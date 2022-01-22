/*package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="device_monitor_detail_monthly")
@NamedQuery(name="DeviceMonitorDetailMonthly.findAll", query="SELECT d FROM DeviceMonitorDetailMonthly d")
public class DeviceMonitorDetailMonthly implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long devicemoniterid;

	private String beddeviceid;

	@Column(name="co2_resprate")
	private String co2Resprate;

	private Timestamp creationtime;

	@Column(name="dia_bp")
	private String diaBp;

	@Column(name="ecg_resprate")
	private String ecgResprate;

	private String etco2;

	private String heartrate;

	@Column(name="mean_bp")
	private String meanBp;

	private Timestamp modificationtime;

	private String pulserate;

	private String spo2;

	@Column(name="sys_bp")
	private String sysBp;

	private String uhid;
	
	private String nbp_s;
	
	private String nbp_m;
	
	private String nbp_d;
	
	private Timestamp starttime;
	
	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getNbp_s() {
		return nbp_s;
	}

	public void setNbp_s(String nbp_s) {
		this.nbp_s = nbp_s;
	}

	public String getNbp_m() {
		return nbp_m;
	}

	public void setNbp_m(String nbp_m) {
		this.nbp_m = nbp_m;
	}

	public String getNbp_d() {
		return nbp_d;
	}

	public void setNbp_d(String nbp_d) {
		this.nbp_d = nbp_d;
	}

	public DeviceMonitorDetailMonthly() {
	}

	public Long getDevicemoniterid() {
		return this.devicemoniterid;
	}

	public void setDevicemoniterid(Long devicemoniterid) {
		this.devicemoniterid = devicemoniterid;
	}

	public String getBeddeviceid() {
		return this.beddeviceid;
	}

	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
	}

	public String getCo2Resprate() {
		return this.co2Resprate;
	}

	public void setCo2Resprate(String co2Resprate) {
		this.co2Resprate = co2Resprate;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDiaBp() {
		return this.diaBp;
	}

	public void setDiaBp(String diaBp) {
		this.diaBp = diaBp;
	}

	public String getEcgResprate() {
		return this.ecgResprate;
	}

	public void setEcgResprate(String ecgResprate) {
		this.ecgResprate = ecgResprate;
	}

	public String getEtco2() {
		return this.etco2;
	}

	public void setEtco2(String etco2) {
		this.etco2 = etco2;
	}

	public String getHeartrate() {
		return this.heartrate;
	}

	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}

	public String getMeanBp() {
		return this.meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getPulserate() {
		return this.pulserate;
	}

	public void setPulserate(String pulserate) {
		this.pulserate = pulserate;
	}

	public String getSpo2() {
		return this.spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getSysBp() {
		return this.sysBp;
	}

	public void setSysBp(String sysBp) {
		this.sysBp = sysBp;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}
*/