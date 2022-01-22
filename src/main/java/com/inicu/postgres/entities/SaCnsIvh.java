package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
@Table(name = "sa_cns_ivh")
@NamedQuery(name = "SaCnsIvh.findAll", query = "SELECT s FROM SaCnsIvh s")
public class SaCnsIvh implements Serializable{

	/**
	*
	*
	*Purpose: adding required columns in Hydrocephalus assessment 

	*@Updated on: June 29 2019
	*@author: Shweta Nichani Mohanani
	*/
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sacnsivhid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;
	
	//not in use anymore
	@Column(name = "cns_system_status")
	private String cnsSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	
	@Column(name = "symptomatic_value")
	private String symptomaticValue;
	
	@Column(name = "papilescore_id")
	private String papilescoreId;
	
	@Column(name = "volpescore_id")
	private String volpescoreId;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;

	@Column(name = "othertreatment_comments")
	private String othertreatmentComments;

	private String treatmentplan;
	
	@Transient private List<String> treatmentplanList;
	  
	private String reassestime;
	
	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "othercomments")
	private String othercomments;

	@Column(name = "causeof_ivh")
	private String causeofIvh;
	  
	@Transient private List<String> causeofIvhList;
	
	@Column(name = "causeofivh_other")
	private String causeofIvhOther;	

	private String progressnotes;
	
	private String associatedevent;
	
	private String associatedeventid;

	@Column(columnDefinition = "bool")
	private Boolean bloodtrans;

	// Column Added

	private String history;

	@Transient
	private String entryType;

	private Integer ageatassesment;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private String treatmentSelectedText;

	@Column(name = "assessment_date")
	private Timestamp assessmentDate;

	@Column(name = "assessment_hour")
	private String assessmentHour;

	@Column(name = "assessment_min")
	private String assessmentMin;

	@Column(name = "assessment_meridiem", columnDefinition = "bool")
	private Boolean assessmentMeridiem;

	@Column(name = "assessment_time")
	private Timestamp assessmentTime;

	private String riskfactor;

	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	@Column(name = "medication_str")
	private String medicationStr;

	@Transient
	private List<BabyPrescription> babyPrescription;
	@Transient
	private List<String> riskfactorList;

	private String episodeid;

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public List<String> getRiskfactorList() {
		return riskfactorList;
	}

	public void setRiskfactorList(List<String> riskfactorList) {
		this.riskfactorList = riskfactorList;
	}

	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}

	public String getTreatmentSelectedText() {
		return treatmentSelectedText;
	}

	public void setTreatmentSelectedText(String treatmentSelectedText) {
		this.treatmentSelectedText = treatmentSelectedText;
	}

	public Timestamp getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Timestamp assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public String getAssessmentHour() {
		return assessmentHour;
	}

	public void setAssessmentHour(String assessmentHour) {
		this.assessmentHour = assessmentHour;
	}

	public String getAssessmentMin() {
		return assessmentMin;
	}

	public void setAssessmentMin(String assessmentMin) {
		this.assessmentMin = assessmentMin;
	}

	public Boolean getAssessmentMeridiem() {
		return assessmentMeridiem;
	}

	public void setAssessmentMeridiem(Boolean assessmentMeridiem) {
		this.assessmentMeridiem = assessmentMeridiem;
	}

	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public String getRiskfactor() {
		return riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
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

	public List<BabyPrescription> getBabyPrescription() {
		return babyPrescription;
	}

	public void setBabyPrescription(List<BabyPrescription> babyPrescription) {
		this.babyPrescription = babyPrescription;
	}

	public Long getSacnsivhid() {
		return sacnsivhid;
	}

	public void setSacnsivhid(Long sacnsivhid) {
		this.sacnsivhid = sacnsivhid;
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

	public String getCnsSystemStatus() {
		return cnsSystemStatus;
	}

	public void setCnsSystemStatus(String cnsSystemStatus) {
		this.cnsSystemStatus = cnsSystemStatus;
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

	public String getSymptomaticValue() {
		return symptomaticValue;
	}

	public void setSymptomaticValue(String symptomaticValue) {
		this.symptomaticValue = symptomaticValue;
	}

	public String getPapilescoreId() {
		return papilescoreId;
	}

	public void setPapilescoreId(String papilescoreId) {
		this.papilescoreId = papilescoreId;
	}

	public String getVolpescoreId() {
		return volpescoreId;
	}

	public void setVolpescoreId(String volpescoreId) {
		this.volpescoreId = volpescoreId;
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

	public String getOthertreatmentComments() {
		return othertreatmentComments;
	}

	public void setOthertreatmentComments(String othertreatmentComments) {
		this.othertreatmentComments = othertreatmentComments;
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
	
	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

	public String getCauseofIvh() {
		return causeofIvh;
	}

	public void setCauseofIvh(String causeofIvh) {
		this.causeofIvh = causeofIvh;
	}

	public List<String> getCauseofIvhList() {
		return causeofIvhList;
	}

	public void setCauseofIvhList(List<String> causeofIvhList) {
		this.causeofIvhList = causeofIvhList;
	}

	public String getCauseofIvhOther() {
		return causeofIvhOther;
	}

	public void setCauseofIvhOther(String causeofIvhOther) {
		this.causeofIvhOther = causeofIvhOther;
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

	public List<String> getTreatmentplanList() {
		return treatmentplanList;
	}

	public void setTreatmentplanList(List<String> treatmentplanList) {
		this.treatmentplanList = treatmentplanList;
	}

	public Boolean getBloodtrans() {
		return bloodtrans;
	}

	public void setBloodtrans(Boolean bloodtrans) {
		this.bloodtrans = bloodtrans;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
}
