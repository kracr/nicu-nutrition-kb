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

/**
 * The persistent class for the sa_cns_seizures database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "sa_cns_seizures")
@NamedQuery(name = "SaCnsSeizures.findAll", query = "SELECT s FROM SaCnsSeizures s")
public class SaCnsSeizures implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sacnsseizuresid;

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

	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Timestamp timeofassessment;

	private String noofseizures;

	private String bloodSugarLevel;

	private String calciumLevel;

	private String treatmentSelectedText;

	private String treatmentaction;
	
	@Column(name = "clinical_note")
	private String clinicalNote;

	@Column(name = "othertreatment_comments")
	private String othertreatmentComments;

	@Column(name = "resuscitation_comments")
	private String resuscitationComments;

	private String treatmentplan;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	@Column(name = "causeof_seizures")
	private String causeofSeizures;

	@Column(name = "causeofseizures_other")
	private String causeofseizuresOther;

	private String progressnotes;

	//Not in use anymore
//	private String associatedevent;
//
//	private String associatedeventid;

	@Transient
	private List<String> treatmentactionList;

	@Transient
	private List<String> causeofSeizuresList;

	@Transient
	private RespSupport respSupport;

	@Transient
	private List<BabyPrescription> babyPrescription;

	// addition of columns to enable manual date/time of assessment
	@Column(name = "assessment_date")
	private Date assessmentDate;

	@Column(name = "assessment_hour")
	private String assessmentHour;

	@Column(name = "assessment_min")
	private String assessmentMin;

	@Column(name = "assessment_meridiem", columnDefinition = "bool")
	private Boolean assessmentMeridiem;

	@Column(name = "assessment_time")
	private Timestamp assessmentTime;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Column(name = "medication_str")
	private String medicationStr;

	@Transient
	private Boolean isNewEntry;

	private String episodeid;

	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;

	public SaCnsSeizures() {
		super();
		this.isNewEntry = true;
		this.episodeNumber = 1;
		this.ageinhoursdays = true;
		this.isageofassesmentinhours = true;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public Long getSacnsseizuresid() {
		return sacnsseizuresid;
	}

	public void setSacnsseizuresid(Long sacnsseizuresid) {
		this.sacnsseizuresid = sacnsseizuresid;
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

	public String getNoofseizures() {
		return noofseizures;
	}

	public void setNoofseizures(String noofseizures) {
		this.noofseizures = noofseizures;
	}

	public String getBloodSugarLevel() {
		return bloodSugarLevel;
	}

	public void setBloodSugarLevel(String bloodSugarLevel) {
		this.bloodSugarLevel = bloodSugarLevel;
	}

	public String getCalciumLevel() {
		return calciumLevel;
	}

	public void setCalciumLevel(String calciumLevel) {
		this.calciumLevel = calciumLevel;
	}

	public String getTreatmentSelectedText() {
		return treatmentSelectedText;
	}

	public void setTreatmentSelectedText(String treatmentSelectedText) {
		this.treatmentSelectedText = treatmentSelectedText;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getOthertreatmentComments() {
		return othertreatmentComments;
	}

	public void setOthertreatmentComments(String othertreatmentComments) {
		this.othertreatmentComments = othertreatmentComments;
	}

	public String getResuscitationComments() {
		return resuscitationComments;
	}

	public void setResuscitationComments(String resuscitationComments) {
		this.resuscitationComments = resuscitationComments;
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

	public String getCauseofSeizures() {
		return causeofSeizures;
	}

	public void setCauseofSeizures(String causeofSeizures) {
		this.causeofSeizures = causeofSeizures;
	}

	public String getCauseofseizuresOther() {
		return causeofseizuresOther;
	}

	public void setCauseofseizuresOther(String causeofseizuresOther) {
		this.causeofseizuresOther = causeofseizuresOther;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

//	public String getAssociatedevent() {
//		return associatedevent;
//	}
//
//	public void setAssociatedevent(String associatedevent) {
//		this.associatedevent = associatedevent;
//	}
//
//	public String getAssociatedeventid() {
//		return associatedeventid;
//	}
//
//	public void setAssociatedeventid(String associatedeventid) {
//		this.associatedeventid = associatedeventid;
//	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public List<String> getCauseofSeizuresList() {
		return causeofSeizuresList;
	}

	public void setCauseofSeizuresList(List<String> causeofSeizuresList) {
		this.causeofSeizuresList = causeofSeizuresList;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
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

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public List<BabyPrescription> getBabyPrescription() {
		return babyPrescription;
	}

	public void setBabyPrescription(List<BabyPrescription> babyPrescription) {
		this.babyPrescription = babyPrescription;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public String getClinicalNote() {
		return clinicalNote;
	}

	public void setClinicalNote(String clinicalNote) {
		this.clinicalNote = clinicalNote;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}
}
