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
@Table(name = "sa_resp_rds")
@NamedQuery(name = "SaRespRds.findAll", query = "SELECT s FROM SaRespRds s")
public class SaRespRds implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long resprdsid;

	private String uhid;

	private Timestamp creationtime;
	
	//not in use anymore
	@Column(name = "resp_system_status")
	private String respSystemStatus;

	private String eventstatus;


	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;


	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	private String downesscoreid;

	private String treatmentaction;

	@Transient
	private List<String> treatmentActionList;

	private String sufactantname;

	@Column(name = "surfactant_dose")
	private String surfactantDose;

	@Column(columnDefinition = "bool")
	private Boolean isinsuredone;

	@Column(name = "action_after_surfactant")
	private String actionAfterSurfactant;

	private String rdsplan;

	@Transient
	private List<String> rdsplanList;

	private String reassestime;

	private String reasseshoursdays;

	private String othercomments;

	private String silvermanscoreid;

	private String causeofrds;

	@Transient
	private List<String> rdsCauseList;

	private String progressnotes;

	private String associatedevent;

	private String loggeduser;
	
	@Column(name = "clinical_note")
	private String clinicalNote;

	@Transient
	private List<String> orderInvestigationList;

	@Transient
	private List<String> orderInvestigationStringList;

	@Column(name = "causeofrds_other")
	private String causeofrdsOther;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private Timestamp timeofassessment;

	@Column(name = "surfactant_dose_ml")
	private String surfactantDoseMl;

	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private String riskfactor;

	@Transient
	private List<String> riskfactorList;

	@Column(name = "riskfactor_other")
	private String riskfactorOther;

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

	@Transient
	private RespSupport respSupport;

	@Transient
	private ScoreDownes downesScore;

	@Transient
	private List<BabyPrescription> babyPrescription;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Transient
	private Boolean isNewEntry;

	@Column(name = "medication_str")
	private String medicationStr;

	private String icdCauseofRds;

	@Transient
	private List icdCauseofrdsList;

    private String episodeid;

	public SaRespRds() {
		super();
		this.actionAfterSurfactant = "";
		this.assessmentMeridiem = true;
		this.isNewEntry = true;
		this.episodeNumber = 1;
	}

	public String getIcdCauseofRds() {
		return icdCauseofRds;
	}

	public void setIcdCauseofRds(String icdCauseofRds) {
		this.icdCauseofRds = icdCauseofRds;
	}

	public List getIcdCauseofrdsList() {
		return icdCauseofrdsList;
	}

	public void setIcdCauseofrdsList(List icdCauseofrdsList) {
		this.icdCauseofrdsList = icdCauseofrdsList;
	}

	public Long getResprdsid() {
		return resprdsid;
	}

	public void setResprdsid(Long resprdsid) {
		this.resprdsid = resprdsid;
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

	public String getRdsplan() {
		return rdsplan;
	}

	public void setRdsplan(String rdsplan) {
		this.rdsplan = rdsplan;
	}

	public String getReassestime() {
		return reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReasseshoursdays() {
		return reasseshoursdays;
	}

	public void setReasseshoursdays(String reasseshoursdays) {
		this.reasseshoursdays = reasseshoursdays;
	}

	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

	public String getSilvermanscoreid() {
		return silvermanscoreid;
	}

	public void setSilvermanscoreid(String silvermanscoreid) {
		this.silvermanscoreid = silvermanscoreid;
	}

	public String getCauseofrds() {
		return causeofrds;
	}

	public void setCauseofrds(String causeofrds) {
		this.causeofrds = causeofrds;
	}

	public List<String> getRdsCauseList() {
		return rdsCauseList;
	}

	public void setRdsCauseList(List<String> rdsCauseList) {
		this.rdsCauseList = rdsCauseList;
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

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public List<String> getRdsplanList() {
		return rdsplanList;
	}

	public void setRdsplanList(List<String> rdsplanList) {
		this.rdsplanList = rdsplanList;
	}

	public String getCauseofrdsOther() {
		return causeofrdsOther;
	}

	public void setCauseofrdsOther(String causeofrdsOther) {
		this.causeofrdsOther = causeofrdsOther;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public Timestamp getTimeofassessment() {
		return timeofassessment;
	}

	public void setTimeofassessment(Timestamp timeofassessment) {
		this.timeofassessment = timeofassessment;
	}

	public String getSurfactantDoseMl() {
		return surfactantDoseMl;
	}

	public void setSurfactantDoseMl(String surfactantDoseMl) {
		this.surfactantDoseMl = surfactantDoseMl;
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

	public Boolean isAssessmentMeridiem() {
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

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public ScoreDownes getDownesScore() {
		return downesScore;
	}

	public void setDownesScore(ScoreDownes downesScore) {
		this.downesScore = downesScore;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Boolean getAssessmentMeridiem() {
		return assessmentMeridiem;
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
