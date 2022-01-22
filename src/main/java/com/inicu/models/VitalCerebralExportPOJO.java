package com.inicu.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

public class VitalCerebralExportPOJO {
	private static final long serialVersionUID = 1L;

	private String uhid;
	
	private String deviceTime;
	
	private String heartRate;
	
	private String pulseRate;

	private String spo2;
	
	private String respiratoryRate;

	private String systolicBp;

	private String diastolicBp;

	private String meanBp;

	private String systolicNbp;
	
	private String diastolicNbp;

	private String meanNbp;

	private String systolicIbp;
	
	private String diastolicIbp;

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

	public String getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(String deviceTime) {
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
