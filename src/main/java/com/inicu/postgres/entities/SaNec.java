package com.inicu.postgres.entities;

import javax.persistence.*;
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
@Table(name="sa_infection_nec")
@NamedQuery(name="SaNec.findAll",query="SELECT s FROM SaNec s")
public class SaNec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sanec_id;

    private String eventstatus;

    private Timestamp creationtime;

    private Timestamp modificationtime;

    private String uhid;

    private String loggeduser;
    private String ageatonset;

    @Column(columnDefinition = "bool")
    private Boolean ageinhoursdays;

    private Integer ageatassesment;

    @Column(columnDefinition = "bool")
    private Boolean isageofassesmentinhours;

    @Column(name = "assessment_time")
    private Timestamp assessmentTime;

    @Column(name = "assessment_date")
    private Timestamp assessmentDate;
    
    @Column(name = "otherplancomments")
    private String otherplanComments;

    private String riskfactor;

    @Transient
    private List<String> riskfactorList;

    private String abdominalsign;

    @Transient
    private List<String> abdominalsignList;

//    private String bellscoreid;

    @Column(name = "infection_system_status")
    private String infectionSystemStatus;


    @Column(name = "riskfactor_other")
    private String riskfactorOther;

    @Column(name="systematic_symptoms")
    private String systematicSymptoms;

    @Transient
    private List<String> systematicSymptomsList;

    @Column(name = "systematic_symptoms_other")
    private String systematicSymptomsOther;

    @Column(name="base_line_adb")
    private Integer baseLineAdb;

    @Column(name="base_line_adb_time")
    private Date baseLineAdbTime;

    @Column(name="current_adb")
    private String currentAdb;

    @Column(name="current_adb_time")
    private Date currentAdbTime;

    @Column(name="adb_difference")
    private Integer adbDifference;

    @Column(name="no_of_feed_intolerance")
    private Integer noOfFeedIntolerance;

    @Column(name="no_of_npo_days")
    private Integer noOfNpoDays;

    @Column(name="npo_count_due_to_feed_intolerance")
    private Integer npoCountDueToFeedIntolerance;

    @Transient private List<String> orderInvestigationList;

    @Transient private List<String> orderInvestigationStringList;

    @Column(name="x_ray_inference")
    private String xRayInference;

    @Column(name="bell_staging_value")
    private String bellStagingValue;

    @Column(name="surgical_management")
    private String surgicalManagement;

    @Column(name = "medication_str")
    private String medicationStr;

    @Column(name="other_treatment_comments")
    private String otherTreatmentComment;

    @Column(name="skip_one_feed",columnDefinition = "bool")
    private Boolean skipOneFeed;

    @Column(name="npo_clicked",columnDefinition = "bool")
    private Boolean npoClicked;

    private String reassestime;

    private String reasseshoursdays;

    private String abdGirthtime;

    private String abdGirthhoursdays;

    private String npotime;

    private String npohoursdays;

    @Column(name="gastric_aspiratetime")
    private String gastricAspiratetime;

    @Column(name="gastric_aspirate_hoursdays")
    private String gastricAspirateHoursDays;

    private String causeofnec;

    @Transient
    private List<String> causeListofnec;

    @Transient
    private List<String> treatmentactionList;

    private String causeofnecother;

    private String associatedevent;

    private String progressnotes;

    @Column(name = "episode_number")
    private Integer episodeNumber;

    private String treatmentaction;

    // @Column(name="other_plan_comments")
    // private String otherplanComments;

    @Transient
    private boolean isNewEntry;

    private String episodeid;

    @Column(columnDefinition = "bool")
    private Boolean iseditednotes;

    public String getTreatmentaction() {
        return treatmentaction;
    }

    public void setTreatmentaction(String treatmentaction) {
        this.treatmentaction = treatmentaction;
    }



    public SaNec() {
        super();
        this.isNewEntry=true;
        this.episodeNumber = 1;
        this.ageinhoursdays = true;
        this.isageofassesmentinhours = true;
    }

    // public String getOtherplanComments() {
    //     return otherplanComments;
    // }

    // public void setOtherplanComments(String otherplanComments) {
    //     this.otherplanComments = otherplanComments;
    // }

    public Long getSanec_id() {
        return sanec_id;
    }

    public void setSanec_id(Long sanec_id) {
        this.sanec_id = sanec_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getInfectionSystemStatus() {
        return infectionSystemStatus;
    }

    public void setInfectionSystemStatus(String infectionSystemStatus) {
        this.infectionSystemStatus = infectionSystemStatus;
    }

    public String getAbdominalsign() {
        return abdominalsign;
    }

    public String getBellStagingValue() {
        return bellStagingValue;
    }

    public void setBellStagingValue(String bellStagingValue) {
        this.bellStagingValue = bellStagingValue;
    }

    public void setAbdominalsign(String abdominalsign) {
        this.abdominalsign = abdominalsign;
    }

    public List<String> getAbdominalsignList() {
        return abdominalsignList;
    }

    public void setAbdominalsignList(List<String> abdominalsignList) {
        this.abdominalsignList = abdominalsignList;
    }

    public boolean isNewEntry() {
        return isNewEntry;
    }

    public void setNewEntry(boolean newEntry) {
        isNewEntry = newEntry;
    }

    public Timestamp getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Timestamp assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public List<String> getTreatmentactionList() {
        return treatmentactionList;
    }

    public void setTreatmentactionList(List<String> treatmentactionList) {
        this.treatmentactionList = treatmentactionList;
    }

    public String getLoggeduser() {
        return loggeduser;
    }

    public void setLoggeduser(String loggeduser) {
        this.loggeduser = loggeduser;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
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

    public String getSystematicSymptoms() {
        return systematicSymptoms;
    }

    public void setSystematicSymptoms(String systematicSymptoms) {
        this.systematicSymptoms = systematicSymptoms;
    }

    public List<String> getSystematicSymptomsList() {
        return systematicSymptomsList;
    }

    public void setSystematicSymptomsList(List<String> systematicSymptomsList) {
        this.systematicSymptomsList = systematicSymptomsList;
    }

    public String getSystematicSymptomsOther() {
        return systematicSymptomsOther;
    }

    public void setSystematicSymptomsOther(String systematicSymptomsOther) {
        this.systematicSymptomsOther = systematicSymptomsOther;
    }

    public Integer getBaseLineAdb() {
        return baseLineAdb;
    }

    public void setBaseLineAdb(Integer baseLineAdb) {
        this.baseLineAdb = baseLineAdb;
    }

    public Date getBaseLineAdbTime() {
        return baseLineAdbTime;
    }

    public void setBaseLineAdbTime(Date baseLineAdbTime) {
        this.baseLineAdbTime = baseLineAdbTime;
    }

    public String getCurrentAdb() {
        return currentAdb;
    }

    public void setCurrentAdb(String currentAdb) {
        this.currentAdb = currentAdb;
    }

    public Date getCurrentAdbTime() {
        return currentAdbTime;
    }

    public void setCurrentAdbTime(Date currentAdbTime) {
        this.currentAdbTime = currentAdbTime;
    }

    public Integer getAdbDifference() {
        return adbDifference;
    }

    public void setAdbDifference(Integer adbDifference) {
        this.adbDifference = adbDifference;
    }

    public Integer getNoOfFeedIntolerance() {
        return noOfFeedIntolerance;
    }

    public void setNoOfFeedIntolerance(Integer noOfFeedIntolerance) {
        this.noOfFeedIntolerance = noOfFeedIntolerance;
    }

    public Integer getNoOfNpoDays() {
        return noOfNpoDays;
    }

    public void setNoOfNpoDays(Integer noOfNpoDays) {
        this.noOfNpoDays = noOfNpoDays;
    }

    public Integer getNpoCountDueToFeedIntolerance() {
        return npoCountDueToFeedIntolerance;
    }

    public void setNpoCountDueToFeedIntolerance(Integer npoCountDueToFeedIntolerance) {
        this.npoCountDueToFeedIntolerance = npoCountDueToFeedIntolerance;
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

    public String getxRayInference() {
        return xRayInference;
    }

    public void setxRayInference(String xRayInference) {
        this.xRayInference = xRayInference;
    }

    public String getSurgicalManagement() {
        return surgicalManagement;
    }

    public void setSurgicalManagement(String surgicalManagement) {
        this.surgicalManagement = surgicalManagement;
    }

    public String getMedicationStr() {
        return medicationStr;
    }

    public void setMedicationStr(String medicationStr) {
        this.medicationStr = medicationStr;
    }

    public String getOtherTreatmentComment() {
        return otherTreatmentComment;
    }

    public void setOtherTreatmentComment(String otherTreatmentComment) {
        this.otherTreatmentComment = otherTreatmentComment;
    }

    public Boolean getSkipOneFeed() {
        return skipOneFeed;
    }

    public void setSkipOneFeed(Boolean skipOneFeed) {
        this.skipOneFeed = skipOneFeed;
    }

    public Boolean getNpoClicked() {
        return npoClicked;
    }

    public void setNpoClicked(Boolean npoClicked) {
        this.npoClicked = npoClicked;
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

    public String getAbdGirthtime() {
        return abdGirthtime;
    }

    public void setAbdGirthtime(String abdGirthtime) {
        this.abdGirthtime = abdGirthtime;
    }

    public String getAbdGirthhoursdays() {
        return abdGirthhoursdays;
    }

    public void setAbdGirthhoursdays(String abdGirthhoursdays) {
        this.abdGirthhoursdays = abdGirthhoursdays;
    }

    public String getNpotime() {
        return npotime;
    }

    public void setNpotime(String npotime) {
        this.npotime = npotime;
    }

    public String getNpohoursdays() {
        return npohoursdays;
    }

    public void setNpohoursdays(String npohoursdays) {
        this.npohoursdays = npohoursdays;
    }

    public String getGastricAspiratetime() {
        return gastricAspiratetime;
    }

    public void setGastricAspiratetime(String gastricAspiratetime) {
        this.gastricAspiratetime = gastricAspiratetime;
    }

    public String getGastricAspirateHoursDays() {
        return gastricAspirateHoursDays;
    }

    public void setGastricAspirateHoursDays(String gastricAspirateHoursDays) {
        this.gastricAspirateHoursDays = gastricAspirateHoursDays;
    }

    public String getCauseofnec() {
        return causeofnec;
    }

    public void setCauseofnec(String causeofnec) {
        this.causeofnec = causeofnec;
    }

    public List<String> getCauseListofnec() {
        return causeListofnec;
    }

    public void setCauseListofnec(List<String> causeListofnec) {
        this.causeListofnec = causeListofnec;
    }

    public String getCauseofnecother() {
        return causeofnecother;
    }

    public void setCauseofnecother(String causeofnecother) {
        this.causeofnecother = causeofnecother;
    }

    public String getAssociatedevent() {
        return associatedevent;
    }

    public void setAssociatedevent(String associatedevent) {
        this.associatedevent = associatedevent;
    }

    public String getProgressnotes() {
        return progressnotes;
    }

    public void setProgressnotes(String progressnotes) {
        this.progressnotes = progressnotes;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

	public String getOtherplanComments() {
		return otherplanComments;
	}

	public void setOtherplanComments(String otherplanComments) {
		this.otherplanComments = otherplanComments;
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
