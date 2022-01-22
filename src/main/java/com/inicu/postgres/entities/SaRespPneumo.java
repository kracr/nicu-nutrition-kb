package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sa_resp_pneumothorax")
@NamedQuery(name = "SaRespPneumo.findAll", query = "SELECT s FROM SaRespPneumo s")
public class SaRespPneumo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long resppneumothoraxid;

	@Transient
	private Boolean isNewEntry;


	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;


	@Transient
	private RespSupport respSupport;

	@Transient
	private ScoreDownes downesScore;

	@Transient
	private List<BabyPrescription> babyPrescription;

	@Column(name = "medication_str")
	private String medicationStr;

	public SaRespPneumo() {
		super();
		this.episodeNumber = 1;
		this.lefttransillumination = false;
		this.righttransillumination = false;
		this.assessmentMeridiem = true;
		this.isNewEntry = true;
	}

	private String uhid;

	private String pneumoDiagnosisBy;
	
	@Column(name = "clinical_note")
	private String clinicalNote;
	
	private String riskfactor;

	@Transient
	private List<String> riskfactorList;
	
	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	private String statusOfPneumothoraxLeft;

	private String statusOfPneumothoraxRight;

	@Column(columnDefinition = "bool")
	private Boolean needleAspirationLeft;

	@Column(columnDefinition = "bool")
	private Boolean needleAspirationRight;

	private Timestamp creationtime;

	private String loggeduser;
	
	//not in use anymore
	@Column(name = "resp_system_status")
	private String respSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	private String downesscoreid;

	private String treatmentaction;

	private String treatmentSelectedText;

	@Transient
	private List<String> treatmentActionList;

	private String sufactantname;

	@Column(name = "surfactant_dose")
	private String surfactantDose;

	@Column(columnDefinition = "bool")
	private Boolean isinsuredone;

	@Column(name = "action_after_surfactant")
	private String actionAfterSurfactant;

	@Column(columnDefinition = "bool")
	private Boolean transillumination;

	@Column(columnDefinition = "bool")
	private Boolean lefttransillumination;

	@Column(columnDefinition = "bool")
	private Boolean righttransillumination;

	@Column(columnDefinition = "bool", name = "needle_aspiration")
	private Boolean needleAspiration;

	@Column(name = "aspiration_plan_time")
	private Integer aspirationPlanTime;

	@Column(name = "aspiration_minhrsdays")
	private String aspirationMinhrsdays;

	private String treatmentplan;

	@Transient
	private List<String> treatmentplanList;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	private String silvermanscoreid;

	private String causeofpneumothorax;

	@Transient
	private List<String> pneumothoraxCauseList;

	private String progressnotes;

	private String associatedevent;

	@Transient
	private List<String> orderInvestigationList;

	@Transient
	private List<String> orderInvestigationStringList;

	@Column(name = "needle_aspiration_date")
	private Date needleAspirationDate;

	@Column(name = "needle_aspiration_time")
	private String needleAspirationTime;

	@Column(name = "causeofpneumo_other")
	private String causeofpneumoOther;
	

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String causeOther;

	@Column(columnDefinition = "int8")
	private Integer ageatassesment;

	@Column(columnDefinition = "Float4")
	private Float surfactantTotalDose;

	@Column(columnDefinition = "bool")
	private Boolean ageAtAssessmentInhoursDays;

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

	private String icdCauseofPneumo;

	@Transient
	private List icdCauseofPneumoList;

	private String episodeid;

	public String getIcdCauseofPneumo() {
		return icdCauseofPneumo;
	}

	public void setIcdCauseofPneumo(String icdCauseofPneumo) {
		this.icdCauseofPneumo = icdCauseofPneumo;
	}

	public List getIcdCauseofPneumoList() {
		return icdCauseofPneumoList;
	}

	public void setIcdCauseofPneumoList(List icdCauseofPneumoList) {
		this.icdCauseofPneumoList = icdCauseofPneumoList;
	}

	public Long getResppneumothoraxid() {
		return resppneumothoraxid;
	}

	public void setResppneumothoraxid(Long resppneumothoraxid) {
		this.resppneumothoraxid = resppneumothoraxid;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getRespSystemStatus() {
		return respSystemStatus;
	}

	public void setRespSystemStatus(String respSystemStatus) {
		this.respSystemStatus = respSystemStatus;
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
	
	public String getDownesscoreid() {
		return downesscoreid;
	}

	public void setDownesscoreid(String downesscoreid) {
		this.downesscoreid = downesscoreid;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}

	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}

	public String getSufactantname() {
		return sufactantname;
	}

	public void setSufactantname(String sufactantname) {
		this.sufactantname = sufactantname;
	}

	public String getSurfactantDose() {
		return surfactantDose;
	}

	public void setSurfactantDose(String surfactantDose) {
		this.surfactantDose = surfactantDose;
	}

	public Boolean getIsinsuredone() {
		return isinsuredone;
	}

	public void setIsinsuredone(Boolean isinsuredone) {
		this.isinsuredone = isinsuredone;
	}

	public String getActionAfterSurfactant() {
		return actionAfterSurfactant;
	}

	public void setActionAfterSurfactant(String actionAfterSurfactant) {
		this.actionAfterSurfactant = actionAfterSurfactant;
	}

	public Boolean getTransillumination() {
		return transillumination;
	}

	public void setTransillumination(Boolean transillumination) {
		this.transillumination = transillumination;
	}

	public Boolean getLefttransillumination() {
		return lefttransillumination;
	}

	public void setLefttransillumination(Boolean lefttransillumination) {
		this.lefttransillumination = lefttransillumination;
	}

	public Boolean getRighttransillumination() {
		return righttransillumination;
	}

	public void setRighttransillumination(Boolean righttransillumination) {
		this.righttransillumination = righttransillumination;
	}

	public String getClinicalNote() {
		return clinicalNote;
	}

	public void setClinicalNote(String clinicalNote) {
		this.clinicalNote = clinicalNote;
	}

	public Boolean getNeedleAspiration() {
		return needleAspiration;
	}

	public void setNeedleAspiration(Boolean needleAspiration) {
		this.needleAspiration = needleAspiration;
	}

	public Integer getAspirationPlanTime() {
		return aspirationPlanTime;
	}

	public void setAspirationPlanTime(Integer aspirationPlanTime) {
		this.aspirationPlanTime = aspirationPlanTime;
	}

	public String getAspirationMinhrsdays() {
		return aspirationMinhrsdays;
	}

	public void setAspirationMinhrsdays(String aspirationMinhrsdays) {
		this.aspirationMinhrsdays = aspirationMinhrsdays;
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

	public String getSilvermanscoreid() {
		return silvermanscoreid;
	}

	public void setSilvermanscoreid(String silvermanscoreid) {
		this.silvermanscoreid = silvermanscoreid;
	}

	public String getCauseofpneumothorax() {
		return causeofpneumothorax;
	}

	public void setCauseofpneumothorax(String causeofpneumothorax) {
		this.causeofpneumothorax = causeofpneumothorax;
	}

	public List<String> getPneumothoraxCauseList() {
		return pneumothoraxCauseList;
	}

	public void setPneumothoraxCauseList(List<String> pneumothoraxCauseList) {
		this.pneumothoraxCauseList = pneumothoraxCauseList;
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

	public List<String> getOrderInvestigationList() {
		return orderInvestigationList;
	}

	public void setOrderInvestigationList(List<String> orderInvestigationList) {
		this.orderInvestigationList = orderInvestigationList;
	}

	public List<String> getOrderInvestigationStringList() {
		return orderInvestigationStringList;
	}

	public void setOrderInvestigationStringList(List<String> orderInvestigationStringList) {
		this.orderInvestigationStringList = orderInvestigationStringList;
	}

	public Date getNeedleAspirationDate() {
		return needleAspirationDate;
	}

	public void setNeedleAspirationDate(Date needleAspirationDate) {
		this.needleAspirationDate = needleAspirationDate;
	}

	public String getNeedleAspirationTime() {
		return needleAspirationTime;
	}

	public void setNeedleAspirationTime(String needleAspirationTime) {
		this.needleAspirationTime = needleAspirationTime;
	}

	public String getCauseofpneumoOther() {
		return causeofpneumoOther;
	}

	public void setCauseofpneumoOther(String causeofpneumoOther) {
		this.causeofpneumoOther = causeofpneumoOther;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public List<String> getTreatmentplanList() {
		return treatmentplanList;
	}

	public void setTreatmentplanList(List<String> treatmentplanList) {
		this.treatmentplanList = treatmentplanList;
	}

	public String getCauseOther() {
		return causeOther;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public Float getSurfactantTotalDose() {
		return surfactantTotalDose;
	}

	public void setCauseOther(String causeOther) {
		this.causeOther = causeOther;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public void setSurfactantTotalDose(Float surfactantTotalDose) {
		this.surfactantTotalDose = surfactantTotalDose;
	}

	public Boolean getAgeAtAssessmentInhoursDays() {
		return ageAtAssessmentInhoursDays;
	}

	public void setAgeAtAssessmentInhoursDays(Boolean ageAtAssessmentInhoursDays) {
		this.ageAtAssessmentInhoursDays = ageAtAssessmentInhoursDays;
	}

	public Boolean getNeedleAspirationLeft() {
		return needleAspirationLeft;
	}

	public Boolean getNeedleAspirationRight() {
		return needleAspirationRight;
	}

	public void setNeedleAspirationLeft(Boolean needleAspirationLeft) {
		this.needleAspirationLeft = needleAspirationLeft;
	}

	public void setNeedleAspirationRight(Boolean needleAspirationRight) {
		this.needleAspirationRight = needleAspirationRight;
	}

	public String getTreatmentSelectedText() {
		return treatmentSelectedText;
	}

	public void setTreatmentSelectedText(String treatmentSelectedText) {
		this.treatmentSelectedText = treatmentSelectedText;
	}

	public String getPneumoDiagnosisBy() {
		return pneumoDiagnosisBy;
	}

	public void setPneumoDiagnosisBy(String pneumoDiagnosisBy) {
		this.pneumoDiagnosisBy = pneumoDiagnosisBy;
	}

	public String getStatusOfPneumothoraxLeft() {
		return statusOfPneumothoraxLeft;
	}

	public String getStatusOfPneumothoraxRight() {
		return statusOfPneumothoraxRight;
	}

	public void setStatusOfPneumothoraxLeft(String statusOfPneumothoraxLeft) {
		this.statusOfPneumothoraxLeft = statusOfPneumothoraxLeft;
	}

	public void setStatusOfPneumothoraxRight(String statusOfPneumothoraxRight) {
		this.statusOfPneumothoraxRight = statusOfPneumothoraxRight;
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

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public ScoreDownes getDownesScore() {
		return downesScore;
	}

	public List<BabyPrescription> getBabyPrescription() {
		return babyPrescription;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public void setDownesScore(ScoreDownes downesScore) {
		this.downesScore = downesScore;
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
