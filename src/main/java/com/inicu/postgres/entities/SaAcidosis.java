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


/**
 * The persistent class for the sa_acidosis database table.
 * 
 * @author Vedaant
 */
@Entity
@Table(name = "sa_acidosis")
@NamedQuery(name = "SaAcidosis.findAll", query = "SELECT s FROM SaAcidosis s")
public class SaAcidosis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long acidosisid;

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
	
	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;
	
	@Column(name = "ph")
	private String ph;

	@Column(name = "hco3")
	private String hco3;

	@Column(name = "be")
	private String be;
	
	@Column(name = "pco2")
	private String pco2;
	
	@Column(name = "lactate")
	private String lactate;
	
	/*
	 * four more added 
	 * 	sodium
		potassium
		chloride
		aniongap
	 */
	@Column(name = "sodium")
	private String sodium;
	
	@Column(name = "potassium")
	private String potassium;
	
	@Column(name = "chloride")
	private String chloride;
	
	@Column(name = "aniongap")
	private String aniongap;
		
	@Column(name = "urine_output")
	private String urineOutput;
	
	@Column(name = "urine_output_type")
	private String urineOutputType;
	
	private String treatmentaction;
	
	@Column(name = "bolus_type")
	private String bolusType;

	@Column(name = "bolus_feedmethod")
	private String bolusFeedMethod;
	
	@Column(name = "bolus_volume")
	private String bolusVolume;
	
	@Column(name = "bolus_frequency")
	private String bolusFrequency;
	
	@Column(name = "total_bolusfeed")
	private String totalBolusFeed;
	
	
	@Column(name = "treatment_other")
	private String treatmentOther;
		
	private String treatmentplan;
	
	@Transient private List<String> treatmentactionList;
	
	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;
	
	@Column(name = "causeofacidosis")
	private String causeOfAcidosis;
	
	@Column(name = "causeofacidosis_other")
	private String causeOfAcidosisOther;
	
	@Transient private List<String> causeofAcidosisList;

	private String progressnotes;

	private String associatedevent;

	private String associatedeventid;

	public Long getAcidosisid() {
		return acidosisid;
	}

	public void setAcidosisid(Long acidosisid) {
		this.acidosisid = acidosisid;
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

	public String getUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(String urineOutput) {
		this.urineOutput = urineOutput;
	}

	public String getUrineOutputType() {
		return urineOutputType;
	}

	public void setUrineOutputType(String urineOutputType) {
		this.urineOutputType = urineOutputType;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getBolusType() {
		return bolusType;
	}

	public void setBolusType(String bolusType) {
		this.bolusType = bolusType;
	}

	public String getBolusFeedMethod() {
		return bolusFeedMethod;
	}

	public void setBolusFeedMethod(String bolusFeedMethod) {
		this.bolusFeedMethod = bolusFeedMethod;
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

	public String getTotalBolusFeed() {
		return totalBolusFeed;
	}

	public void setTotalBolusFeed(String totalBolusFeed) {
		this.totalBolusFeed = totalBolusFeed;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
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

	public String getCauseOfAcidosis() {
		return causeOfAcidosis;
	}

	public void setCauseOfAcidosis(String causeOfAcidosis) {
		this.causeOfAcidosis = causeOfAcidosis;
	}

	public String getCauseOfAcidosisOther() {
		return causeOfAcidosisOther;
	}

	public void setCauseOfAcidosisOther(String causeOfAcidosisOther) {
		this.causeOfAcidosisOther = causeOfAcidosisOther;
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

	public List<String> getCauseofAcidosisList() {
		return causeofAcidosisList;
	}

	public void setCauseofAcidosisList(List<String> causeofAcidosisList) {
		this.causeofAcidosisList = causeofAcidosisList;
	}

	public String getSodium() {
		return sodium;
	}

	public void setSodium(String sodium) {
		this.sodium = sodium;
	}

	public String getPotassium() {
		return potassium;
	}

	public void setPotassium(String potassium) {
		this.potassium = potassium;
	}

	public String getChloride() {
		return chloride;
	}

	public void setChloride(String chloride) {
		this.chloride = chloride;
	}

	public String getAniongap() {
		return aniongap;
	}

	public void setAniongap(String aniongap) {
		this.aniongap = aniongap;
	}

	
	
}
