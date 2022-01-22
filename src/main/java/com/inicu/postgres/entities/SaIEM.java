package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sa_iem")
@NamedQuery(name = "SaIEM.findAll", query = "SELECT s FROM SaIEM s")
public class SaIEM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long iemid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;
	
	private String uhid;

	private String loggeduser;
	
	@Column(name = "metabolic_system_status")
	private String metabolicSystemStatus;
	
	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;
	
	@Column(columnDefinition = "bool", name = "symptomatic_status")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;
	
	private String pasthistory;
	
	private String ph;
	private String hco3;
	private String be;
	private String pco2;
	private String lactate;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;
	
	@Column(name = "treatment_other")
	private String treatmentOther;
	
	@Column(name = "bolus_type")
	private String bolusType;
	
	@Column(name = "bolus_feedmethod")
	private String bolusFeedmethod;

	@Column(name = "bolus_volume")
	private String bolusVolume;

	@Column(name = "bolus_frequency")
	private String bolusFrequency;
	
	@Column(name = "total_bolusfeed")
	private String totalBolusfeed;
	
	private String treatmentplan;
	  
	private String reassestime;
	
	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;
	
	private String causeofiem;
	  
	@Transient private List<String> causeofiemList;

	@Column(name = "causeofiem_other")
	private String causeofiemOther;

	private String progressnotes;
	
	private String associatedevent;
	
	private String associatedeventid;
	
	@Column(columnDefinition = "bool")
	private Boolean exchangetrans;

	public Long getIemid() {
		return iemid;
	}

	public void setIemid(Long iemid) {
		this.iemid = iemid;
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

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMetabolicSystemStatus() {
		return metabolicSystemStatus;
	}

	public void setMetabolicSystemStatus(String metabolicSystemStatus) {
		this.metabolicSystemStatus = metabolicSystemStatus;
	}

	public String getEventstatus() {
		return eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getAgeatonset() {
		return ageatonset;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	public Boolean getAgeinhoursdays() {
		return ageinhoursdays;
	}

	public void setAgeinhoursdays(Boolean ageinhoursdays) {
		this.ageinhoursdays = ageinhoursdays;
	}

	public Boolean getSymptomaticStatus() {
		return symptomaticStatus;
	}

	public void setSymptomaticStatus(Boolean symptomaticStatus) {
		this.symptomaticStatus = symptomaticStatus;
	}

	public String getSymptomaticValue() {
		return symptomaticValue;
	}

	public void setSymptomaticValue(String symptomaticValue) {
		this.symptomaticValue = symptomaticValue;
	}

	public String getPasthistory() {
		return pasthistory;
	}

	public void setPasthistory(String pasthistory) {
		this.pasthistory = pasthistory;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getHco3() {
		return hco3;
	}

	public void setHco3(String hco3) {
		this.hco3 = hco3;
	}

	public String getBe() {
		return be;
	}

	public void setBe(String be) {
		this.be = be;
	}

	public String getPco2() {
		return pco2;
	}

	public void setPco2(String pco2) {
		this.pco2 = pco2;
	}

	public String getLactate() {
		return lactate;
	}

	public void setLactate(String lactate) {
		this.lactate = lactate;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getBolusType() {
		return bolusType;
	}

	public void setBolusType(String bolusType) {
		this.bolusType = bolusType;
	}

	public String getBolusFeedmethod() {
		return bolusFeedmethod;
	}

	public void setBolusFeedmethod(String bolusFeedmethod) {
		this.bolusFeedmethod = bolusFeedmethod;
	}

	public String getBolusVolume() {
		return bolusVolume;
	}

	public void setBolusVolume(String bolusVolume) {
		this.bolusVolume = bolusVolume;
	}

	public String getBolusFrequency() {
		return bolusFrequency;
	}

	public void setBolusFrequency(String bolusFrequency) {
		this.bolusFrequency = bolusFrequency;
	}

	public String getTotalBolusfeed() {
		return totalBolusfeed;
	}

	public void setTotalBolusfeed(String totalBolusfeed) {
		this.totalBolusfeed = totalBolusfeed;
	}

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public String getReassestime() {
		return reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReassestimeType() {
		return reassestimeType;
	}

	public void setReassestimeType(String reassestimeType) {
		this.reassestimeType = reassestimeType;
	}

	public String getOtherplanComments() {
		return otherplanComments;
	}

	public void setOtherplanComments(String otherplanComments) {
		this.otherplanComments = otherplanComments;
	}

	public String getCauseofiem() {
		return causeofiem;
	}

	public void setCauseofiem(String causeofiem) {
		this.causeofiem = causeofiem;
	}

	public List<String> getCauseofiemList() {
		return causeofiemList;
	}

	public void setCauseofiemList(List<String> causeofiemList) {
		this.causeofiemList = causeofiemList;
	}

	public String getCauseofiemOther() {
		return causeofiemOther;
	}

	public void setCauseofiemOther(String causeofiemOther) {
		this.causeofiemOther = causeofiemOther;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public String getAssociatedeventid() {
		return associatedeventid;
	}

	public void setAssociatedeventid(String associatedeventid) {
		this.associatedeventid = associatedeventid;
	}

	public Boolean getExchangetrans() {
		return exchangetrans;
	}

	public void setExchangetrans(Boolean exchangetrans) {
		this.exchangetrans = exchangetrans;
	}
}
