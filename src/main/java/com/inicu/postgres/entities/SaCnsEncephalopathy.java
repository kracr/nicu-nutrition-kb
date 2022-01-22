package com.inicu.postgres.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the sa_cns_encephalopathy database table.
 *
 */
/**
*
*
*Purpose: adding required columns in Encephalopathy assessment 

*@Updated on: June 29 2019
*@author: Shweta Nichani Mohanani
*/

@Entity
@Table(name = "sa_cns_encephalopathy")
@NamedQuery(name = "SaCnsEncephalopathy.findAll", query = "SELECT s FROM SaCnsEncephalopathy s")
public class SaCnsEncephalopathy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "saencephalopathyid")
    private Long SaCnsEncephalopathyid;

    private String uhid;

    private String ageatonset;

    private Integer ageatassesment;

    @Column(columnDefinition = "bool")
    private Boolean ageinhoursdays;

    @Column(columnDefinition = "bool")
    private Boolean isageofassesmentinhours;

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

    @Column(name = "episode_number")
    private Integer episodeNumber;

    private String reassestime;

    @Column(name = "reassestime_type")
    private String reassestimeType;

    @Column(name = "medication_str")
    private String medicationStr;

    private String treatmentSelectedText;

    private String treatmentaction;

    @Column(name = "othertreatment_comments")
    private String othertreatmentComments;

    private String progressnotes;

    private String eventstatus;

    @Column(name = "cns_system_status")
    private String cnsSystemStatus;

    private String history;

    private String loggeduser;

    private Timestamp creationtime;
    private Timestamp modificationtime;

    @Transient
    private List<BabyPrescription> babyPrescription;

    @Transient
    private List<String> treatmentactionList;

    @Transient
    private String entryType;

    @Transient
    private RespSupport respSupport;

    // Column added

    @Column(name = "causeof_encephalopathy")
    private String causeofEncephalopathy;

    @Column(name = "causeofencephalopathy_other")
    private String causeofEncephalopathyOther;

    private String associatedevent;

    private String associatedeventid;

    private String riskfactor;

    @Column(name = "riskfactor_other")
    private String riskfactorOther;

    @Transient
    private List<String> treatmentactioncause;

    @Transient
    private List<String> treatmentactionplanlist;

    @Transient
    private List<String> treatmentactionlist;

    @Transient
    private List<String> riskfactorList;
    
    @Column(name = "othercomments")
    private String othercomments;

    private String episodeid;

    public SaCnsEncephalopathy() {
        super();
        this.ageinhoursdays = true;
        this.episodeNumber = 1;
    }

    public List<String> getTreatmentactionlist() {
        return treatmentactionlist;
    }

    public void setTreatmentactionlist(List<String> treatmentactionlist) {
        this.treatmentactionlist = treatmentactionlist;
    }

    public String getRiskfactor() {
        return riskfactor;
    }

    public void setRiskfactor(String riskfactor) {
        this.riskfactor = riskfactor;
    }

    public String getCauseofEncephalopathy() {
        return causeofEncephalopathy;
    }

    public void setCauseofEncephalopathy(String causeofEncephalopathy) {
        this.causeofEncephalopathy = causeofEncephalopathy;
    }

    public String getCauseofEncephalopathyOther() {
        return causeofEncephalopathyOther;
    }

    public void setCauseofEncephalopathyOther(String causeofEncephalopathyOther) {
        this.causeofEncephalopathyOther = causeofEncephalopathyOther;
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

    public String getRiskfactorOther() {
        return riskfactorOther;
    }

    public void setRiskfactorOther(String riskfactorOther) {
        this.riskfactorOther = riskfactorOther;
    }

    public List<String> getTreatmentactioncause() {
        return treatmentactioncause;
    }

    public void setTreatmentactioncause(List<String> treatmentactioncause) {
        this.treatmentactioncause = treatmentactioncause;
    }

    public List<String> getTreatmentactionplanlist() {
        return treatmentactionplanlist;
    }

    public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
        this.treatmentactionplanlist = treatmentactionplanlist;
    }

    public List<String> getRiskfactorList() {
        return riskfactorList;
    }

    public void setRiskfactorList(List<String> riskfactorList) {
        this.riskfactorList = riskfactorList;
    }

    public RespSupport getRespSupport() {
        return respSupport;
    }

    public void setRespSupport(RespSupport respSupport) {
        this.respSupport = respSupport;
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

    public List<String> getTreatmentactionList() {
        return treatmentactionList;
    }

    public void setTreatmentactionList(List<String> treatmentactionList) {
        this.treatmentactionList = treatmentactionList;
    }

    public String getCnsSystemStatus() {
        return cnsSystemStatus;
    }

    public void setCnsSystemStatus(String cnsSystemStatus) {
        this.cnsSystemStatus = cnsSystemStatus;
    }

    public Long getSaCnsEncephalopathyid() {
        return SaCnsEncephalopathyid;
    }

    public void setSaCnsEncephalopathyid(Long saCnsEncephalopathyid) {
        SaCnsEncephalopathyid = saCnsEncephalopathyid;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getAgeatonset() {
        return ageatonset;
    }

    public void setAgeatonset(String ageatonset) {
        this.ageatonset = ageatonset;
    }

    public Integer getAgeatassesment() {
        return ageatassesment;
    }

    public void setAgeatassesment(Integer ageatassesment) {
        this.ageatassesment = ageatassesment;
    }

    public Boolean getAgeinhoursdays() {
        return ageinhoursdays;
    }

    public void setAgeinhoursdays(Boolean ageinhoursdays) {
        this.ageinhoursdays = ageinhoursdays;
    }

    public Boolean getIsageofassesmentinhours() {
        return isageofassesmentinhours;
    }

    public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
        this.isageofassesmentinhours = isageofassesmentinhours;
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

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
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

    public String getProgressnotes() {
        return progressnotes;
    }

    public void setProgressnotes(String progressnotes) {
        this.progressnotes = progressnotes;
    }

    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getLoggeduser() {
        return loggeduser;
    }

    public void setLoggeduser(String loggeduser) {
        this.loggeduser = loggeduser;
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

	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

    public String getEpisodeid() {
        return episodeid;
    }

    public void setEpisodeid(String episodeid) {
        this.episodeid = episodeid;
    }
}