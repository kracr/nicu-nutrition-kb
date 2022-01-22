package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * The persistent class for the nursing_bloodgas database table.
 *
 */

@Entity
@Table(name = "nursing_bloodgas")
@NamedQuery(name = "NursingBloodGas.findAll", query = "SELECT n FROM NursingBloodGas n")
public class NursingBloodGas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nn_bloodgasid")
	private Long nnBloodgasid;

	private String be;

	private Timestamp creationtime;

	private String hco2;

	private Timestamp modificationtime;

	@Column(name = "nn_bloodgas_time")
	private String nnBloodgasTime;

	private String pco2;

	private String ph;
	private String loggeduser;

	private String po2;

	private String uhid;

	private String lactate;

	private String spo2;

	private String na;

	private String k;

	private String cl;

	private String glucose;

	private String ionized_calcium;

	private String regular_hco3;

	private String be_ecf;
	
	private String thbc;
	
	private String hct;
	
	@Column(name = "sample_type")
	private String sampleType;

	@Column(name = "sample_method")
	private String sampleMethod;
	
	private String osmolarity;
	
	@Column(name = "anion_gap")
	private String anionGap;
	
	private String userDate;

	@Transient
	private String orderId;
	
	private Timestamp entryDate;

	public NursingBloodGas() {
	}

	public Long getNnBloodgasid() {
		return this.nnBloodgasid;
	}

	public void setNnBloodgasid(Long nnBloodgasid) {
		this.nnBloodgasid = nnBloodgasid;
	}

	public String getBe() {
		return this.be;
	}

	public void setBe(String be) {
		this.be = be;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getHco2() {
		return this.hco2;
	}

	public void setHco2(String hco2) {
		this.hco2 = hco2;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnBloodgasTime() {
		return this.nnBloodgasTime;
	}

	public void setNnBloodgasTime(String nnBloodgasTime) {
		this.nnBloodgasTime = nnBloodgasTime;
	}

	public String getPco2() {
		return this.pco2;
	}

	public void setPco2(String pco2) {
		this.pco2 = pco2;
	}

	public String getPh() {
		return this.ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getPo2() {
		return this.po2;
	}

	public void setPo2(String po2) {
		this.po2 = po2;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLactate(String lactate) {
		this.lactate = lactate;
	}

	public String getLactate() {
		return this.lactate;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getNa() {
		return na;
	}

	public void setNa(String na) {
		this.na = na;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getGlucose() {
		return glucose;
	}

	public void setGlucose(String glucose) {
		this.glucose = glucose;
	}

	public String getIonized_calcium() {
		return ionized_calcium;
	}

	public void setIonized_calcium(String ionized_calcium) {
		this.ionized_calcium = ionized_calcium;
	}

	public String getRegular_hco3() {
		return regular_hco3;
	}

	public void setRegular_hco3(String regular_hco3) {
		this.regular_hco3 = regular_hco3;
	}

	public String getBe_ecf() {
		return be_ecf;
	}

	public void setBe_ecf(String be_ecf) {
		this.be_ecf = be_ecf;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public String getThbc() {
		return thbc;
	}

	public String getHct() {
		return hct;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setThbc(String thbc) {
		this.thbc = thbc;
	}

	public void setHct(String hct) {
		this.hct = hct;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSampleMethod() {
		return sampleMethod;
	}

	public String getOsmolarity() {
		return osmolarity;
	}

	public String getAnionGap() {
		return anionGap;
	}

	public void setSampleMethod(String sampleMethod) {
		this.sampleMethod = sampleMethod;
	}

	public void setOsmolarity(String osmolarity) {
		this.osmolarity = osmolarity;
	}

	public void setAnionGap(String anionGap) {
		this.anionGap = anionGap;
	}
	
}
