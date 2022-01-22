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
@Table(name = "sa_hypercalcemia")
@NamedQuery(name = "SaHypercalcemia.findAll", query = "SELECT s FROM SaHypercalcemia s")
public class SaHypercalcemia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hypercalcemiaid;
	
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
	
	@Column(name = "serum_calcium")
	private String serumCalcium;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentplan;
	  
	private String reassestime;
	
	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	private String causeofhypercalcemia;
	  
	@Transient private List<String> causeofhypercalcemiaList;
	
	@Column(name = "causeofhypercalcemia_other")
	private String causeofhypercalcemiaOther;	

	private String progressnotes;
	
	private String associatedevent;
	
	private String associatedeventid;


	public Long getHypercalcemiaid() {
		return hypercalcemiaid;
	}

	public void setHypercalcemiaid(Long hypercalcemiaid) {
		this.hypercalcemiaid = hypercalcemiaid;
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

	public String getSerumCalcium() {
		return serumCalcium;
	}

	public void setSerumCalcium(String serumCalcium) {
		this.serumCalcium = serumCalcium;
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

	public String getCauseofhypercalcemia() {
		return causeofhypercalcemia;
	}

	public void setCauseofhypercalcemia(String causeofhypercalcemia) {
		this.causeofhypercalcemia = causeofhypercalcemia;
	}

	public List<String> getCauseofhypercalcemiaList() {
		return causeofhypercalcemiaList;
	}

	public void setCauseofhypercalcemiaList(List<String> causeofhypercalcemiaList) {
		this.causeofhypercalcemiaList = causeofhypercalcemiaList;
	}

	public String getCauseofhypercalcemiaOther() {
		return causeofhypercalcemiaOther;
	}

	public void setCauseofhypercalcemiaOther(String causeofhypercalcemiaOther) {
		this.causeofhypercalcemiaOther = causeofhypercalcemiaOther;
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
}
