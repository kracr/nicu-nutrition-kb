package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the dashboard_nursing_vitalparameters database
 * table.
 * 
 */
@Entity
@Table(name = "dashboard_nursing_vitalparameters")
@NamedQuery(name = "DashboardNursingVitalparameters.findAll", query = "SELECT d FROM DashboardNursingVitalparameters d")
public class DashboardNursingVitalparameters implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uhid;

	private Timestamp entrydate;

	@Column(columnDefinition = "float4")
	private Float hr_rate;

	@Column(columnDefinition = "float4")
	private Float skintemp;

	private String spo21;

	private String mean_bp1;

	public DashboardNursingVitalparameters() {
		super();
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
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

	@Override
	public String toString() {
		return "DashboardNursingVitalparameters [uhid=" + uhid + ", entrydate=" + entrydate + ", hr_rate=" + hr_rate
				+ ", skintemp=" + skintemp + ", spo21=" + spo21 + ", mean_bp1=" + mean_bp1 + "]";
	}

}
