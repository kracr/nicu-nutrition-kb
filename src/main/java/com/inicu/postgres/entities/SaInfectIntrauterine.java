package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
*
*
*Purpose: adding required columns in Renal assessment 

*@Updated on: June 29 2019
*@author: Shweta Nichani Mohanani
*/
@Entity
@Table(name = "sa_infection_intrauterine")
@NamedQuery(name = "SaInfectIntrauterine.findAll", query = "SELECT s FROM SaInfectIntrauterine s")
public class SaInfectIntrauterine implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long saintrauterineid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;
	
	@Column(name = "infection_system_status")
	private String infectionSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;
	
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Timestamp timeofassessment;

	@Column(columnDefinition = "bool")
	private Boolean isintrauterineavailable;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentplan;
	
	@Transient private List<String> treatmentplanList;
	  
	private String reassestime;

	private String reasseshoursdays;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	private String progressnotes;

	@Transient
	private String entryType;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Column(name = "cause_of_intrauterine")
	private String cause;

	@Transient
	private List<String> causeList;


	// Column added Later

	private String riskfactor;

	@Transient
	private List<String> riskfactorList;

	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	@Column(name = "medication_str")
	private String medicationStr;

	@Transient
	private Boolean isNewEntry;

	private String associatedevent;

	@Column(name = "assessment_date")
	private Timestamp assessmentDate;

	private String orderSelectedText;
	
	@Column(name = "othercomments")
	private String othercomments;

	private String causeOther;

	private String episodeid;

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

	public String getCauseOther() {
		return causeOther;
	}

	public void setCauseOther(String causeOther) {
		this.causeOther = causeOther;
	}

	public String getReasseshoursdays() {
		return reasseshoursdays;
	}

	public void setReasseshoursdays(String reasseshoursdays) {
		this.reasseshoursdays = reasseshoursdays;
	}

	public String getRiskfactor() {
		return riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public List<String> getRiskfactorList() {
		return riskfactorList;
	}

	public void setRiskfactorList(List<String> riskfactorList) {
		this.riskfactorList = riskfactorList;
	}

	public String getRiskfactorOther() {
		return riskfactorOther;
	}

	public void setRiskfactorOther(String riskfactorOther) {
		this.riskfactorOther = riskfactorOther;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Boolean getNewEntry() {
		return isNewEntry;
	}

	public void setNewEntry(Boolean newEntry) {
		isNewEntry = newEntry;
	}

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public Timestamp getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Timestamp assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public List<String> getCauseList() {
		return causeList;
	}

	public void setCauseList(List<String> causeList) {
		this.causeList = causeList;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public void setSaintrauterineid(Long saintrauterineid) {
		this.saintrauterineid = saintrauterineid;
	}

	public void setIsintrauterineavailable(Boolean isintrauterineavailable) {
		this.isintrauterineavailable = isintrauterineavailable;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public Long getSaintrauterineid() {
		return saintrauterineid;
	}

	public void setSaclabsiid(Long saintrauterineid) {
		this.saintrauterineid = saintrauterineid;
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

	public String getInfectionSystemStatus() {
		return infectionSystemStatus;
	}

	public void setInfectionSystemStatus(String infectionSystemStatus) {
		this.infectionSystemStatus = infectionSystemStatus;
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

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}

	public Timestamp getTimeofassessment() {
		return timeofassessment;
	}

	public void setTimeofassessment(Timestamp timeofassessment) {
		this.timeofassessment = timeofassessment;
	}

	public Boolean getIsintrauterineavailable() {
		return isintrauterineavailable;
	}

	public void setIsclabsiavailable(Boolean isintrauterineavailable) {
		this.isintrauterineavailable = isintrauterineavailable;
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

	public List<String> getTreatmentplanList() {
		return treatmentplanList;
	}

	public void setTreatmentplanList(List<String> treatmentplanList) {
		this.treatmentplanList = treatmentplanList;
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

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
}
