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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="sa_feed_intolerance")
@NamedQuery(name="SaFeedIntolerance.findAll", query="SELECT s FROM SaFeedIntolerance s")
public class SaFeedIntolerance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long feedIntoleranceId;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;
	
	@Transient
	private String comments;


	private String comment;
	
	@Column(name = "age_of_onset")
	private Integer ageOfOnset;
	
	@Column(name = "ageofonset_inhours",columnDefinition="bool")
	private Boolean ageOfOnsetInHours;
	
	@Column(name ="age_at_assessment")
	private Integer ageAtAssessment;
	
	@Column(name="ageatassessment_inhours" , columnDefinition="bool")
	private Boolean isAgeAtAssessmentInHours;
	
	@Column(name="episode_number")
	private Integer episodeNumber;
	
	private String riskfactors;
	
	@Transient
	private List riskFactorList;
	
	@Transient
	private List abdominalDistinctionList;
	
	@Transient
	private List feedReasonList;
	
	@Column(name="baseline_abdominal_girth" , columnDefinition="float4")
	private Float baselineAbdominalGirth;
	
	@Column(name="systemic_signs")
	private String systemicSigns;
	
	@Column(name="abdominal_signs")
	private String abdominalSigns;
	
	@Column(name="tachycardia" , columnDefinition="bool")
	private Boolean tachycardia;
	
	@Column(name="shock" , columnDefinition="bool")
	private Boolean shock;
	
	@Column(name="apnea" , columnDefinition="bool")
	private Boolean apnea;
	
	@Column(columnDefinition="bool")
	private Boolean edema;
	
	@Column(columnDefinition="bool")
	private Boolean bradycardia; 
	
	@Column(columnDefinition="bool")
	private Boolean desaturation;
	
	@Column(columnDefinition="bool")
	private Boolean sepsis;
	
	@Column(columnDefinition="bool")
	private Boolean pallor;
	
	@Column(columnDefinition="bool")
	private Boolean vomit;
	
	@Column(name="visible_veins" , columnDefinition="bool")
	private Boolean visibleVeins;
	
	@Column(name="abdominal_distinction" , columnDefinition="bool")
	private Boolean abdominalDistinction; 
	
	@Column(columnDefinition="bool")
	private Boolean asythema;
	
	@Column(name="gastric_aspirate" , columnDefinition="bool")
	private Boolean gastricAspirate;
	
	@Column(name="abdominal_distinction_value")
	private String abdominalDistinctionValue; 
	
	@Column(name="vomit_time")
	private Timestamp vomitTime;
	
	@Column(name="vomit_color")
	private String vomitColor;
	
	@Column(name="gastric_time")
	private Timestamp gastricTime;
	
	@Column(name="gastric_volume_out")
	private String gastricVolumeOut;
	
	@Column(name="gastric_percentile")
	private String gastricPercentile;
	
	@Column(name="medication_text")
	private String medicationText;
	
	@Column(name="order_investigation")
	private String orderInvestigation;
	
	@Transient
	private List orderinvestigationList;

	@Column(name="investigation_ordered_boolean" ,columnDefinition = "bool")
	private Boolean investigationOrderedBooleanValue;
	
	// for multiple treatment plans(reassess,investigation,other)
	@Transient
	private List<String> treatmentactionplanlist;
	
	@Transient
	private String pastTreatmentActionStr;
	
	@Transient
	private String treatmentActionSelected;
	
	@Transient
	private String pastOrderInvestigationStr;
	
	@Column(name="associated_event")
	private String associatedEvent;
	
	@Transient
	private List actiontypeList;
	
	@Column(name="action_type")
	private String actionType;
	
	@Transient
	private List causeOfFeedIntoleranceList;
	
	@Column(name="cause_of_feed")
	private String causeOfFeed;
	
	@Column(columnDefinition="bool")
	private Boolean feeding;
	
	@Column(name="feeding_reason")
	private String feedingReason;
	
	@Column(name="feeding_continue")
	private String feedingContinue;
	
	@Column(name = "assessment_time")
	private Timestamp assessmentTime;
	
	@Column(name = "feed_intolerance_status")
	private String feedIntoleranceStatus;						
		
	@Column(name = "treatment_other")
	private String treatmentOther;
	
	@Transient
	private Boolean isNewEntry;
	
	@Transient
	private boolean edit;
	
	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateofbirth;
	
	private Integer actionduration;

	private String isactiondurationinhours;
	
	private String planOther;
	
	@Column(name = "babyfeedid")
	private Long babyfeedid;

	private String episodeid;
	
	@Transient
	private String orderlist;

    @Column(name="cause_of_feed_other")
    private String causeOfFeedOther;

	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;

	public SaFeedIntolerance() {
		this.isNewEntry = true;
		this.ageOfOnsetInHours = true;
		this.isAgeAtAssessmentInHours = true;
		this.feeding = false;
	}

	public Long getFeedIntoleranceId() {
		return feedIntoleranceId;
	}

	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}

	public void setFeedIntoleranceId(Long feedIntoleranceId) {
		this.feedIntoleranceId = feedIntoleranceId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getAgeOfOnset() {
		return ageOfOnset;
	}

	public void setAgeOfOnset(Integer ageOfOnset) {
		this.ageOfOnset = ageOfOnset;
	}

	public Boolean getAgeOfOnsetInHours() {
		return ageOfOnsetInHours;
	}

	public void setAgeOfOnsetInHours(Boolean ageOfOnsetInHours) {
		this.ageOfOnsetInHours = ageOfOnsetInHours;
	}

	public Integer getAgeAtAssessment() {
		return ageAtAssessment;
	}

	public void setAgeAtAssessment(Integer ageAtAssessment) {
		this.ageAtAssessment = ageAtAssessment;
	}

	public Boolean getIsAgeAtAssessmentInHours() {
		return isAgeAtAssessmentInHours;
	}

	public void setIsAgeAtAssessmentInHours(Boolean isAgeAtAssessmentInHours) {
		this.isAgeAtAssessmentInHours = isAgeAtAssessmentInHours;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getRiskfactors() {
		return riskfactors;
	}

	public void setRiskfactors(String riskfactors) {
		this.riskfactors = riskfactors;
	}

	public List getRiskFactorList() {
		return riskFactorList;
	}

	public void setRiskFactorList(List riskFactorList) {
		this.riskFactorList = riskFactorList;
	}

	public List getAbdominalDistinctionList() {
		return abdominalDistinctionList;
	}

	public void setAbdominalDistinctionList(List abdominalDistinctionList) {
		this.abdominalDistinctionList = abdominalDistinctionList;
	}

	public List getFeedReasonList() {
		return feedReasonList;
	}

	public void setFeedReasonList(List feedReasonList) {
		this.feedReasonList = feedReasonList;
	}

	public Float getBaselineAbdominalGirth() {
		return baselineAbdominalGirth;
	}

	public void setBaselineAbdominalGirth(Float baselineAbdominalGirth) {
		this.baselineAbdominalGirth = baselineAbdominalGirth;
	}

	public String getSystemicSigns() {
		return systemicSigns;
	}

	public void setSystemicSigns(String systemicSigns) {
		this.systemicSigns = systemicSigns;
	}

	public String getAbdominalSigns() {
		return abdominalSigns;
	}

	public void setAbdominalSigns(String abdominalSigns) {
		this.abdominalSigns = abdominalSigns;
	}

	public Boolean getTachycardia() {
		return tachycardia;
	}

	public void setTachycardia(Boolean tachycardia) {
		this.tachycardia = tachycardia;
	}

	public Boolean getShock() {
		return shock;
	}

	public void setShock(Boolean shock) {
		this.shock = shock;
	}

	public Boolean getApnea() {
		return apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Boolean getEdema() {
		return edema;
	}

	public void setEdema(Boolean edema) {
		this.edema = edema;
	}

	public Boolean getBradycardia() {
		return bradycardia;
	}

	public void setBradycardia(Boolean bradycardia) {
		this.bradycardia = bradycardia;
	}

	public Boolean getDesaturation() {
		return desaturation;
	}

	public void setDesaturation(Boolean desaturation) {
		this.desaturation = desaturation;
	}

	public Boolean getSepsis() {
		return sepsis;
	}

	public void setSepsis(Boolean sepsis) {
		this.sepsis = sepsis;
	}

	public Boolean getPallor() {
		return pallor;
	}

	public void setPallor(Boolean pallor) {
		this.pallor = pallor;
	}

	public Boolean getVomit() {
		return vomit;
	}

	public void setVomit(Boolean vomit) {
		this.vomit = vomit;
	}

	public Boolean getVisibleVeins() {
		return visibleVeins;
	}

	public void setVisibleVeins(Boolean visibleVeins) {
		this.visibleVeins = visibleVeins;
	}

	public Boolean getAbdominalDistinction() {
		return abdominalDistinction;
	}

	public void setAbdominalDistinction(Boolean abdominalDistinction) {
		this.abdominalDistinction = abdominalDistinction;
	}

	public Boolean getAsythema() {
		return asythema;
	}

	public void setAsythema(Boolean asythema) {
		this.asythema = asythema;
	}

	public Boolean getGastricAspirate() {
		return gastricAspirate;
	}

	public void setGastricAspirate(Boolean gastricAspirate) {
		this.gastricAspirate = gastricAspirate;
	}

	public String getAbdominalDistinctionValue() {
		return abdominalDistinctionValue;
	}

	public void setAbdominalDistinctionValue(String abdominalDistinctionValue) {
		this.abdominalDistinctionValue = abdominalDistinctionValue;
	}

	public Timestamp getVomitTime() {
		return vomitTime;
	}

	public void setVomitTime(Timestamp vomitTime) {
		this.vomitTime = vomitTime;
	}

	public String getVomitColor() {
		return vomitColor;
	}

	public void setVomitColor(String vomitColor) {
		this.vomitColor = vomitColor;
	}

	public Timestamp getGastricTime() {
		return gastricTime;
	}

	public void setGastricTime(Timestamp gastricTime) {
		this.gastricTime = gastricTime;
	}

	public String getGastricVolumeOut() {
		return gastricVolumeOut;
	}

	public void setGastricVolumeOut(String gastricVolumeOut) {
		this.gastricVolumeOut = gastricVolumeOut;
	}

	public String getGastricPercentile() {
		return gastricPercentile;
	}

	public void setGastricPercentile(String gastricPercentile) {
		this.gastricPercentile = gastricPercentile;
	}

	public String getMedicationText() {
		return medicationText;
	}

	public void setMedicationText(String medicationText) {
		this.medicationText = medicationText;
	}

	public String getOrderInvestigation() {
		return orderInvestigation;
	}

	public void setOrderInvestigation(String orderInvestigation) {
		this.orderInvestigation = orderInvestigation;
	}

	public List getOrderinvestigationList() {
		return orderinvestigationList;
	}

	public void setOrderinvestigationList(List orderinvestigationList) {
		this.orderinvestigationList = orderinvestigationList;
	}

	public Boolean getInvestigationOrderedBooleanValue() {
		return investigationOrderedBooleanValue;
	}

	public void setInvestigationOrderedBooleanValue(Boolean investigationOrderedBooleanValue) {
		this.investigationOrderedBooleanValue = investigationOrderedBooleanValue;
	}

	public List<String> getTreatmentactionplanlist() {
		return treatmentactionplanlist;
	}

	public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
		this.treatmentactionplanlist = treatmentactionplanlist;
	}

	public String getPastTreatmentActionStr() {
		return pastTreatmentActionStr;
	}

	public void setPastTreatmentActionStr(String pastTreatmentActionStr) {
		this.pastTreatmentActionStr = pastTreatmentActionStr;
	}

	public String getTreatmentActionSelected() {
		return treatmentActionSelected;
	}

	public void setTreatmentActionSelected(String treatmentActionSelected) {
		this.treatmentActionSelected = treatmentActionSelected;
	}

	public String getPastOrderInvestigationStr() {
		return pastOrderInvestigationStr;
	}

	public void setPastOrderInvestigationStr(String pastOrderInvestigationStr) {
		this.pastOrderInvestigationStr = pastOrderInvestigationStr;
	}

	public String getAssociatedEvent() {
		return associatedEvent;
	}

	public void setAssociatedEvent(String associatedEvent) {
		this.associatedEvent = associatedEvent;
	}

	public List getActiontypeList() {
		return actiontypeList;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public List getCauseOfFeedIntoleranceList() {
		return causeOfFeedIntoleranceList;
	}

	public void setCauseOfFeedIntoleranceList(List causeOfFeedIntoleranceList) {
		this.causeOfFeedIntoleranceList = causeOfFeedIntoleranceList;
	}

	public String getCauseOfFeed() {
		return causeOfFeed;
	}

	public void setCauseOfFeed(String causeOfFeed) {
		this.causeOfFeed = causeOfFeed;
	}

	

	public String getFeedingReason() {
		return feedingReason;
	}

	public void setFeedingReason(String feedingReason) {
		this.feedingReason = feedingReason;
	}

	public String getFeedingContinue() {
		return feedingContinue;
	}

	public void setFeedingContinue(String feedingContinue) {
		this.feedingContinue = feedingContinue;
	}

	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public String getFeedIntoleranceStatus() {
		return feedIntoleranceStatus;
	}

	public void setFeedIntoleranceStatus(String feedIntoleranceStatus) {
		this.feedIntoleranceStatus = feedIntoleranceStatus;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	@Override
	public String toString() {
		return "SaFeedIntolerance [feedIntoleranceId=" + feedIntoleranceId + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", loggeduser=" + loggeduser
				+ ", comments=" + comments + ", comment=" + comment + ", ageOfOnset=" + ageOfOnset
				+ ", ageOfOnsetInHours=" + ageOfOnsetInHours + ", ageAtAssessment=" + ageAtAssessment
				+ ", isAgeAtAssessmentInHours=" + isAgeAtAssessmentInHours + ", episodeNumber=" + episodeNumber
				+ ", riskfactors=" + riskfactors + ", riskFactorList=" + riskFactorList + ", abdominalDistinctionList="
				+ abdominalDistinctionList + ", feedReasonList=" + feedReasonList + ", baselineAbdominalGirth="
				+ baselineAbdominalGirth + ", systemicSigns=" + systemicSigns + ", abdominalSigns=" + abdominalSigns
				+ ", tachycardia=" + tachycardia + ", shock=" + shock + ", apnea=" + apnea + ", edema=" + edema
				+ ", bradycardia=" + bradycardia + ", desaturation=" + desaturation + ", sepsis=" + sepsis + ", pallor="
				+ pallor + ", vomit=" + vomit + ", visibleVeins=" + visibleVeins + ", abdominalDistinction="
				+ abdominalDistinction + ", asythema=" + asythema + ", gastricAspirate=" + gastricAspirate
				+ ", abdominalDistinctionValue=" + abdominalDistinctionValue + ", vomitTime=" + vomitTime
				+ ", vomitColor=" + vomitColor + ", gastricTime=" + gastricTime + ", gastricVolumeOut="
				+ gastricVolumeOut + ", gastricPercentile=" + gastricPercentile + ", medicationText=" + medicationText
				+ ", orderInvestigation=" + orderInvestigation + ", orderinvestigationList=" + orderinvestigationList
				+ ", investigationOrderedBooleanValue=" + investigationOrderedBooleanValue
				+ ", treatmentactionplanlist=" + treatmentactionplanlist + ", pastTreatmentActionStr="
				+ pastTreatmentActionStr + ", treatmentActionSelected=" + treatmentActionSelected
				+ ", pastOrderInvestigationStr=" + pastOrderInvestigationStr + ", associatedEvent=" + associatedEvent
				+ ", actiontypeList=" + actiontypeList + ", actionType=" + actionType + ", causeOfFeedIntoleranceList="
				+ causeOfFeedIntoleranceList + ", causeOfFeed=" + causeOfFeed + ", feeding=" + feeding
				+ ", feedingReason=" + feedingReason + ", feedingContinue=" + feedingContinue + ", assessmentTime="
				+ assessmentTime + ", feedIntoleranceStatus=" + feedIntoleranceStatus + ", treatmentOther="
				+ treatmentOther + ", isNewEntry=" + isNewEntry + ", edit=" + edit + ", dateofbirth=" + dateofbirth
				+ "]";
	}

	public Boolean getFeeding() {
		return feeding;
	}

	public void setFeeding(Boolean feeding) {
		this.feeding = feeding;
	}

	public Integer getActionduration() {
		return actionduration;
	}

	public void setActionduration(Integer actionduration) {
		this.actionduration = actionduration;
	}

	public String getIsactiondurationinhours() {
		return isactiondurationinhours;
	}

	public void setIsactiondurationinhours(String isactiondurationinhours) {
		this.isactiondurationinhours = isactiondurationinhours;
	}

	public String getPlanOther() {
		return planOther;
	}

	public void setPlanOther(String planOther) {
		this.planOther = planOther;
	}

	public Long getBabyfeedid() {
		return babyfeedid;
	}

	public void setBabyfeedid(Long babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

    public String getCauseOfFeedOther() {
        return causeOfFeedOther;
    }

    public void setCauseOfFeedOther(String causeOfFeedOther) {
        this.causeOfFeedOther = causeOfFeedOther;
    }

	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}
}
