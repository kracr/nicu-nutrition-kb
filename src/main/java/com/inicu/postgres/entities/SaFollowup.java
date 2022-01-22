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
 * The persistent class for the sa_followup database table.
 * 
 */
@Entity
@Table(name="sa_followup")
@NamedQuery(name="SaFollowup.findAll", query="SELECT s FROM SaFollowup s")
public class SaFollowup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long followupid;

	private Timestamp creationtime;

	@Column(name="date_1yr_coa")
	private String date1yrCoa;

	@Column(name="date_3mo_coa")
	private String date3moCoa;

	@Column(name="date_6mo_coa")
	private String date6moCoa;

	@Column(name="len_1yr_coa")
	private String len1yrCoa;

	@Column(name="len_3mo_coa")
	private String len3moCoa;

	@Column(name="len_40weeks_gms")
	private String len40weeksGms;

	@Column(name="len_6mo_coa")
	private String len6moCoa;

	@Column(name="mdi_1yr")
	private String mdi1yr;

	@Column(name="mdi_3mo")
	private String mdi3mo;

	@Column(name="mdi_6mo")
	private String mdi6mo;

	private Timestamp modificationtime;

	@Column(name="ofc_1yr_coa")
	private String ofc1yrCoa;

	@Column(name="ofc_3mo_coa")
	private String ofc3moCoa;

	@Column(name="ofc_40weeks_gms")
	private String ofc40weeksGms;

	@Column(name="ofc_6mo_coa")
	private String ofc6moCoa;

	@Column(name="pca_at_discharge_wks")
	private String pcaAtDischargeWks;

	@Column(name="pdi_1yr")
	private String pdi1yr;

	@Column(name="pdi_3mo")
	private String pdi3mo;

	@Column(name="pdi_6mo")
	private String pdi6mo;

	private String uhid;

	@Column(name="wt_1yr_coa")
	private String wt1yrCoa;

	@Column(name="wt_3mo_coa")
	private String wt3moCoa;

	@Column(name="wt_40weeks_gms")
	private String wt40weeksGms;

	@Column(name="wt_6mo_coa")
	private String wt6moCoa;
	
	private String loggeduser;
	
	@Transient
	private boolean edit;
	
	@Transient
	private String creationDate;

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public SaFollowup() {
		this.date1yrCoa="";
		this.date3moCoa="";
		this.date6moCoa="";
		this.followupid = null;
		this.len1yrCoa = "";
		this.len3moCoa="";
		this.len40weeksGms="";
		this.len6moCoa="";
		this.mdi1yr="";
		this.mdi3mo="";
		this.mdi6mo="";
		this.ofc1yrCoa="";
		this.ofc3moCoa="";
		this.ofc40weeksGms="";
		this.ofc6moCoa="";
		this.pcaAtDischargeWks="";
		this.pdi1yr="";
		this.pdi3mo="";
		this.pdi6mo ="";
		this.wt1yrCoa="";
		this.wt3moCoa="";
		this.wt40weeksGms="";
		this.wt6moCoa="";
	}

	public Long getFollowupid() {
		return this.followupid;
	}

	public void setFollowupid(Long followupid) {
		this.followupid = followupid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDate1yrCoa() {
		return this.date1yrCoa;
	}

	public void setDate1yrCoa(String date1yrCoa) {
		this.date1yrCoa = date1yrCoa;
	}

	public String getDate3moCoa() {
		return this.date3moCoa;
	}

	public void setDate3moCoa(String date3moCoa) {
		this.date3moCoa = date3moCoa;
	}

	public String getDate6moCoa() {
		return this.date6moCoa;
	}

	public void setDate6moCoa(String date6moCoa) {
		this.date6moCoa = date6moCoa;
	}

	public String getLen1yrCoa() {
		return this.len1yrCoa;
	}

	public void setLen1yrCoa(String len1yrCoa) {
		this.len1yrCoa = len1yrCoa;
	}

	public String getLen3moCoa() {
		return this.len3moCoa;
	}

	public void setLen3moCoa(String len3moCoa) {
		this.len3moCoa = len3moCoa;
	}

	public String getLen40weeksGms() {
		return this.len40weeksGms;
	}

	public void setLen40weeksGms(String len40weeksGms) {
		this.len40weeksGms = len40weeksGms;
	}

	public String getLen6moCoa() {
		return this.len6moCoa;
	}

	public void setLen6moCoa(String len6moCoa) {
		this.len6moCoa = len6moCoa;
	}

	public String getMdi1yr() {
		return this.mdi1yr;
	}

	public void setMdi1yr(String mdi1yr) {
		this.mdi1yr = mdi1yr;
	}

	public String getMdi3mo() {
		return this.mdi3mo;
	}

	public void setMdi3mo(String mdi3mo) {
		this.mdi3mo = mdi3mo;
	}

	public String getMdi6mo() {
		return this.mdi6mo;
	}

	public void setMdi6mo(String mdi6mo) {
		this.mdi6mo = mdi6mo;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOfc1yrCoa() {
		return this.ofc1yrCoa;
	}

	public void setOfc1yrCoa(String ofc1yrCoa) {
		this.ofc1yrCoa = ofc1yrCoa;
	}

	public String getOfc3moCoa() {
		return this.ofc3moCoa;
	}

	public void setOfc3moCoa(String ofc3moCoa) {
		this.ofc3moCoa = ofc3moCoa;
	}

	public String getOfc40weeksGms() {
		return this.ofc40weeksGms;
	}

	public void setOfc40weeksGms(String ofc40weeksGms) {
		this.ofc40weeksGms = ofc40weeksGms;
	}

	public String getOfc6moCoa() {
		return this.ofc6moCoa;
	}

	public void setOfc6moCoa(String ofc6moCoa) {
		this.ofc6moCoa = ofc6moCoa;
	}

	public String getPcaAtDischargeWks() {
		return this.pcaAtDischargeWks;
	}

	public void setPcaAtDischargeWks(String pcaAtDischargeWks) {
		this.pcaAtDischargeWks = pcaAtDischargeWks;
	}

	public String getPdi1yr() {
		return this.pdi1yr;
	}

	public void setPdi1yr(String pdi1yr) {
		this.pdi1yr = pdi1yr;
	}

	public String getPdi3mo() {
		return this.pdi3mo;
	}

	public void setPdi3mo(String pdi3mo) {
		this.pdi3mo = pdi3mo;
	}

	public String getPdi6mo() {
		return this.pdi6mo;
	}

	public void setPdi6mo(String pdi6mo) {
		this.pdi6mo = pdi6mo;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getWt1yrCoa() {
		return this.wt1yrCoa;
	}

	public void setWt1yrCoa(String wt1yrCoa) {
		this.wt1yrCoa = wt1yrCoa;
	}

	public String getWt3moCoa() {
		return this.wt3moCoa;
	}

	public void setWt3moCoa(String wt3moCoa) {
		this.wt3moCoa = wt3moCoa;
	}

	public String getWt40weeksGms() {
		return this.wt40weeksGms;
	}

	public void setWt40weeksGms(String wt40weeksGms) {
		this.wt40weeksGms = wt40weeksGms;
	}

	public String getWt6moCoa() {
		return this.wt6moCoa;
	}

	public void setWt6moCoa(String wt6moCoa) {
		this.wt6moCoa = wt6moCoa;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
}
