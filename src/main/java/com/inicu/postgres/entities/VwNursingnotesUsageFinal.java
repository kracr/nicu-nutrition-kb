package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the vw_nursingnotes_usage_final database table.
 * 
 */
@Entity
@Table(name="vw_nursingnotes_usage_final")
@NamedQuery(name="VwNursingnotesUsageFinal.findAll", query="SELECT v FROM VwNursingnotesUsageFinal v")
public class VwNursingnotesUsageFinal implements Serializable {
	private static final long serialVersionUID = 1L;

	private String babyname;

	private String bloodgas;

	private String bloodproducts;

	private String catheters;

	@Temporal(TemporalType.DATE)
	private Date creationtime;

	private String criticality;

	private String dailyassesment;

	private String intake;

	private String loggeduser;

	private String neurovitals;

	private String niculevel;

	private String nursingmisc;

	private String output;

	@Id
	private String uhid;

	private String ventilaor;

	private String vitalparameters;

	@Column(name="weight_mesaurement")
	private String weightMesaurement;

	

	public VwNursingnotesUsageFinal() {
		super();
		this.babyname = babyname;
		this.bloodgas = "0";
		this.bloodproducts = "0";
		this.catheters = "0";
		this.creationtime = creationtime;
		this.criticality = criticality;
		this.dailyassesment = "0";
		this.intake = "0";
		this.loggeduser = loggeduser;
		this.neurovitals = "0";
		this.niculevel = niculevel;
		this.nursingmisc = "0";
		this.output = "0";
		this.uhid = uhid;
		this.ventilaor = "0";
		this.vitalparameters = "0";
		this.weightMesaurement = "0";
	}

	public String getBabyname() {
		return this.babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getBloodgas() {
		return this.bloodgas;
	}

	public void setBloodgas(String bloodgas) {
		this.bloodgas = bloodgas;
	}

	public String getBloodproducts() {
		return this.bloodproducts;
	}

	public void setBloodproducts(String bloodproducts) {
		this.bloodproducts = bloodproducts;
	}

	public String getCatheters() {
		return this.catheters;
	}

	public void setCatheters(String catheters) {
		this.catheters = catheters;
	}

	public Date getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}

	public String getCriticality() {
		return this.criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	public String getDailyassesment() {
		return this.dailyassesment;
	}

	public void setDailyassesment(String dailyassesment) {
		this.dailyassesment = dailyassesment;
	}

	public String getIntake() {
		return this.intake;
	}

	public void setIntake(String intake) {
		this.intake = intake;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getNeurovitals() {
		return this.neurovitals;
	}

	public void setNeurovitals(String neurovitals) {
		this.neurovitals = neurovitals;
	}

	public String getNiculevel() {
		return this.niculevel;
	}

	public void setNiculevel(String niculevel) {
		this.niculevel = niculevel;
	}

	public String getNursingmisc() {
		return this.nursingmisc;
	}

	public void setNursingmisc(String nursingmisc) {
		this.nursingmisc = nursingmisc;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVentilaor() {
		return this.ventilaor;
	}

	public void setVentilaor(String ventilaor) {
		this.ventilaor = ventilaor;
	}

	public String getVitalparameters() {
		return this.vitalparameters;
	}

	public void setVitalparameters(String vitalparameters) {
		this.vitalparameters = vitalparameters;
	}

	public String getWeightMesaurement() {
		return this.weightMesaurement;
	}

	public void setWeightMesaurement(String weightMesaurement) {
		this.weightMesaurement = weightMesaurement;
	}

}