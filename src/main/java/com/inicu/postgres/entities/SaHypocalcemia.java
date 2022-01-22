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
 * The persistent class for the sa_hypocalcemia database table.
 * 
 * @author Vedaant
 */
@Entity
@Table(name = "sa_hypocalcemia")
@NamedQuery(name = "SaHypocalcemia.findAll", query = "SELECT s FROM SaHypocalcemia s")
public class SaHypocalcemia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hypocalcemiaid;

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
	
	@Column(name = "magnesium_route")
	private String magnesiumRoute;

	@Column(name = "magnesium_dose")
	private String magnesiumDose;
	
	@Column(name = "magnesium_frequency")
	private String magnesiumFrequency;
	
	@Column(name = "vitamind_route")
	private String vitamindRoute;
	
	@Column(name = "vitamind_dose")
	private String vitamindDose;
	
	@Column(name = "vitamind_frequency")
	private String vitamindFrequency;

	private String treatmentaction;
	
	@Column(name = "treatment_other")
	private String treatmentOther;
		
	private String treatmentplan;
	
	@Transient private List<String> treatmentactionList;
	
	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;
	
	@Column(name = "causeofhypocalcemia")
	private String causeofhypocalcemia;
	
	@Column(name = "causeofhypocalcemia_other")
	private String causeOfHypocalcemiaOther;
	
	@Transient private List<String> causeofHypocalcemiaList;

	private String progressnotes;

	private String associatedevent;

	private String associatedeventid;
	
	@Column(name = "serum_calcium")
	private String serumCalcium;

	public Long getHypocalcemiaid() {
		return hypocalcemiaid;
	}

	public void setHypocalcemiaid(Long hypocalcemiaid) {
		this.hypocalcemiaid = hypocalcemiaid;
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

	public String getMagnesiumRoute() {
		return magnesiumRoute;
	}

	public void setMagnesiumRoute(String magnesiumRoute) {
		this.magnesiumRoute = magnesiumRoute;
	}

	public String getMagnesiumDose() {
		return magnesiumDose;
	}

	public void setMagnesiumDose(String magnesiumDose) {
		this.magnesiumDose = magnesiumDose;
	}

	public String getMagnesiumFrequency() {
		return magnesiumFrequency;
	}

	public void setMagnesiumFrequency(String magnesiumFrequency) {
		this.magnesiumFrequency = magnesiumFrequency;
	}

	public String getVitamindRoute() {
		return vitamindRoute;
	}

	public void setVitamindRoute(String vitamindRoute) {
		this.vitamindRoute = vitamindRoute;
	}

	public String getVitamindDose() {
		return vitamindDose;
	}

	public void setVitamindDose(String vitamindDose) {
		this.vitamindDose = vitamindDose;
	}

	public String getVitamindFrequency() {
		return vitamindFrequency;
	}

	public void setVitamindFrequency(String vitamindFrequency) {
		this.vitamindFrequency = vitamindFrequency;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
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

	public String getCauseofhypocalcemia() {
		return causeofhypocalcemia;
	}

	public void setCauseofhypocalcemia(String causeofhypocalcemia) {
		this.causeofhypocalcemia = causeofhypocalcemia;
	}

	public String getCauseOfHypocalcemiaOther() {
		return causeOfHypocalcemiaOther;
	}

	public void setCauseOfHypocalcemiaOther(String causeOfHypocalcemiaOther) {
		this.causeOfHypocalcemiaOther = causeOfHypocalcemiaOther;
	}

	public List<String> getCauseofHypocalcemiaList() {
		return causeofHypocalcemiaList;
	}

	public void setCauseofHypocalcemiaList(List<String> causeofHypocalcemiaList) {
		this.causeofHypocalcemiaList = causeofHypocalcemiaList;
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

	public String getSerumCalcium() {
		return serumCalcium;
	}

	public void setSerumCalcium(String serumCalcium) {
		this.serumCalcium = serumCalcium;
	}
}
