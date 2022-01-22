package com.inicu.postgres.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vw_vital_cerebral_export")
@NamedQuery(name="VwVitalCerebralExport.findAll", query="SELECT d FROM VwVitalCerebralExport d")
public class VwVitalCerebralExport {
	private static final long serialVersionUID = 1L;

	private String uhid;
	
	@Id
	@Column(name="devicetime")
	private Timestamp deviceTime;
	
	@Column(name="heartrate")
	private String heartRate;
	
	@Column(name="pulserate")
	private String pulseRate;

	private String spo2;
	
	@Column(name="ecg_resprate")
	private String respiratoryRate;

	@Column(name="sys_bp")
	private String systolicBp;

	@Column(name="dia_bp")
	private String diastolicBp;

	@Column(name="mean_bp")
	private String meanBp;

	@Column(name="nbp_s")
	private String systolicNbp;
	
	@Column(name="nbp_d")
	private String diastolicNbp;

	@Column(name="nbp_m")
	private String meanNbp;

	@Column(name="ibp_s")
	private String systolicIbp;
	
	@Column(name="ibp_d")
	private String diastolicIbp;

	@Column(name="ibp_m")
	private String meanIbp;
	
	private String pi;
	
	private String fio2;
	
	private String pip;
	
	private String peep;

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Timestamp getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(Timestamp deviceTime) {
		this.deviceTime = deviceTime;
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

	public String getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(String respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getSystolicBp() {
		return systolicBp;
	}

	public void setSystolicBp(String systolicBp) {
		this.systolicBp = systolicBp;
	}

	public String getDiastolicBp() {
		return diastolicBp;
	}

	public void setDiastolicBp(String diastolicBp) {
		this.diastolicBp = diastolicBp;
	}

	public String getMeanBp() {
		return meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public String getSystolicNbp() {
		return systolicNbp;
	}

	public void setSystolicNbp(String systolicNbp) {
		this.systolicNbp = systolicNbp;
	}

	public String getDiastolicNbp() {
		return diastolicNbp;
	}

	public void setDiastolicNbp(String diastolicNbp) {
		this.diastolicNbp = diastolicNbp;
	}

	public String getMeanNbp() {
		return meanNbp;
	}

	public void setMeanNbp(String meanNbp) {
		this.meanNbp = meanNbp;
	}

	public String getSystolicIbp() {
		return systolicIbp;
	}

	public void setSystolicIbp(String systolicIbp) {
		this.systolicIbp = systolicIbp;
	}

	public String getDiastolicIbp() {
		return diastolicIbp;
	}

	public void setDiastolicIbp(String diastolicIbp) {
		this.diastolicIbp = diastolicIbp;
	}

	public String getMeanIbp() {
		return meanIbp;
	}

	public void setMeanIbp(String meanIbp) {
		this.meanIbp = meanIbp;
	}

	public String getPi() {
		return pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

}
