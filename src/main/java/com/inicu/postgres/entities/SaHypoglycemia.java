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

import com.inicu.postgres.utility.BasicConstants;

/**
 * The persistent class for the sa_hypoglycemia database table.
 * 
 */
@Entity
@Table(name = "sa_hypoglycemia")
@NamedQuery(name = "SaHypoglycemia.findAll", query = "SELECT s FROM SaHypoglycemia s")
public class SaHypoglycemia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hypoglycemiaid;

	private String ageatonset;

	@Column(columnDefinition = "bool") // not used
	private Boolean ageinhoursdays;

	@Column(name = "ageofonset_inhours", columnDefinition = "bool")
	private Boolean ageOfOnsetInHours;

	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "a_symptomatic_status", columnDefinition = "bool")
	private Boolean aSymptomaticStatus;

	
	private String lethargy;

	
	private String apnea;

	
	private String seizures;

	
	private String rds;

	
	private String hypothermia;
	
	private String symptomatic;

	@Column(name = "lethargy_continue", columnDefinition = "bool")
	private Boolean lethargyContinue;

	@Column(name = "lethargy_resolved", columnDefinition = "bool")
	private Boolean lethargyResolved;

	@Column(name = "apnea_continue", columnDefinition = "bool")
	private Boolean apneaContinue;

	@Column(name = "apnea_resolved", columnDefinition = "bool")
	private Boolean apneaResolved;

	@Column(name = "seizure_continue", columnDefinition = "bool")
	private Boolean seizureContinue;

	@Column(name = "seizure_resolved", columnDefinition = "bool")
	private Boolean seizureResolved;

	@Column(name = "rds_continue", columnDefinition = "bool")
	private Boolean rdsContinue;

	@Column(name = "rds_resolved", columnDefinition = "bool")
	private Boolean rdsResolved;

	@Column(name = "hypothermia_continue", columnDefinition = "bool")
	private Boolean hypothermiaContinue;

	@Column(name = "hypothermia_resolved", columnDefinition = "bool")
	private Boolean hypothermiaResolved;

	@Column(name = "count_hypoglycaemic_events")
	private Integer countHypoglycaemicEvents;// used
	
	@Column(name = "cumulative_hypoglycaemic_events")
	private Integer cumulativeHypoEvents;

	private String actionTaken;

	private Timestamp creationtime;

	@Column(name = "assessment_time")
	private Timestamp assessmentTime;

	private String loggeduser;

	@Column(name = "episode_number")
	private Integer episodeNumber;

	@Column(name = "blood_sugar", columnDefinition = "Float4")
	private Float bloodSugar;

	private Timestamp modificationtime;
	// progress notes
	private String progressnotes;

	// plan
	@Column(name = "otherplan_comments")
	private String otherPlanComments;//used

	@Column(name = "plan_reassestime")
	private Integer planReassesTime;

	@Column(name = "plan_reassestime_type")
	private String planReassesTimeType;

	@Column(name = "blood_reassestime")
	private Integer bloodReassesTime;

	@Column(name = "blood_reassestime_type")
	private String bloodReassesTimeType;

	// treatment
	@Transient
	private String pastOrderInvestigationStr;

	@Transient
	private String pastTreatmentActionStr;

	@Column(name = "order_investigation")
	private String orderInvestigation;

	@Transient
	private List orderinvestigationList;

	// for multiple treatment plans(reassess,investigation,other)

	private String uhid;
	private String treatmentaction;

	private String treatmentactionOtherText;

	@Transient
	private List<String> treatmentactionList;

	private String treatmentplan;

	@Column(name = "medication_str")
	private String medicationStr;

	@Column(name = "max_gir", columnDefinition = "float4")
	private Float maxGir;

	@Column(name = "min_gir", columnDefinition = "float4")
	private Float minGir;

	@Column(name = "gir_increased", columnDefinition = "bool")
	private Boolean girIncreased;

	@Column(name = "gir_decreased", columnDefinition = "bool")
	private Boolean girDecreased;

	@Column(columnDefinition = "float4")
	private Float girValue;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	private String associatedevent;

	private String causeofhypoglycemia;

	private String causeOtherText;

	@Transient
	private List<String> causeofhypoglycemiaList;

	private String bolusType;

	private String bolusVolume;

	private String bolusTotalFeed;

	// 2nd visit entry...................
	private Integer countHypoglemicEvents;// no use till now

	private Integer countSymptomaticEvents;

	private Integer countAsymptomaticEvents;

	@Column(name = "totalfeed_ml_day", columnDefinition = "Float4")
	private Float totalfeedMlDay;

	private String eventstatus; // no use

	@Column(name = "hypoglycemia_event")
	private String hypoglycemiaEventStatus;

	@Column(name = "metabolic_system_status")
	private String metabolicSystemStatus;

	@Column(name = "min_bloodsugar")
	private String minBloodsugar;

	@Column(name = "age_at_assessment")
	private Integer ageAtAssessment;

	private String comment;

	@Transient
	private String treatmentActionSelected;

	@Column(name = "ageatassessment_inhours", columnDefinition = "bool")
	private Boolean isAgeAtAssessmentInHours;

	@Transient
	private Boolean isNewEntry;

	@Transient
	private Boolean isEdit;

	@Transient
	private List actiontypeList;

	@Column(name = "action_type")
	private String actionType;
	
	@Transient
	private float minGIR;
	
	@Transient
	private float maxGIR;
	
	@Column(name = "causes_of_hypoglycemia")
	private String causesOfHypoglycemia;
	
	
	@Column(name = "risk_factors")
	private String riskFactors;
	
	@Column(name = "nutrition_id")
	private String nutritionId;

	private String episodeid;
	
	@Transient
	private String orderlist;

	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;

	public SaHypoglycemia() {
		super();
		this.isNewEntry = true;
		this.ageOfOnsetInHours = true;
		this.isAgeAtAssessmentInHours = true;

	}


	

	public float getMinGIR() {
		return minGIR;
	}



	public void setMinGIR(float minGIR) {
		this.minGIR = minGIR;
	}



	public float getMaxGIR() {
		return maxGIR;
	}



	public void setMaxGIR(float maxGIR) {
		this.maxGIR = maxGIR;
	}



	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public List getActiontypeList() {
		return actiontypeList;
	}
	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Boolean getIsAgeAtAssessmentInHours() {
		return isAgeAtAssessmentInHours;
	}

	public void setIsAgeAtAssessmentInHours(Boolean isAgeAtAssessmentInHours) {
		this.isAgeAtAssessmentInHours = isAgeAtAssessmentInHours;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public String getTreatmentActionSelected() {
		return treatmentActionSelected;
	}

	public void setTreatmentActionSelected(String treatmentActionSelected) {
		this.treatmentActionSelected = treatmentActionSelected;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getAgeAtAssessment() {
		return ageAtAssessment;
	}

	public void setAgeAtAssessment(Integer ageAtAssessment) {
		this.ageAtAssessment = ageAtAssessment;
	}

	public Boolean getAgeOfOnsetInHours() {
		return ageOfOnsetInHours;
	}

	public void setAgeOfOnsetInHours(Boolean ageOfOnsetInHours) {
		this.ageOfOnsetInHours = ageOfOnsetInHours;
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

	public Long getHypoglycemiaid() {
		return this.hypoglycemiaid;
	}

	public void setHypoglycemiaid(Long hypoglycemiaid) {
		this.hypoglycemiaid = hypoglycemiaid;
	}

	public String getAgeatonset() {
		return this.ageatonset;
	}

	public void setAgeatonset(String ageatonset) {
		this.ageatonset = ageatonset;
	}

	public Boolean getAgeinhoursdays() {
		return this.ageinhoursdays;
	}

	public void setAgeinhoursdays(Boolean ageinhoursdays) {
		this.ageinhoursdays = ageinhoursdays;
	}

	public Boolean getaSymptomaticStatus() {
		return aSymptomaticStatus;
	}

	public void setaSymptomaticStatus(Boolean aSymptomaticStatus) {
		this.aSymptomaticStatus = aSymptomaticStatus;
	}

	

	

	public Boolean getLethargyContinue() {
		return lethargyContinue;
	}

	public void setLethargyContinue(Boolean lethargyContinue) {
		this.lethargyContinue = lethargyContinue;
	}

	public Boolean getLethargyResolved() {
		return lethargyResolved;
	}

	public void setLethargyResolved(Boolean lethargyResolved) {
		this.lethargyResolved = lethargyResolved;
	}

	public Boolean getApneaContinue() {
		return apneaContinue;
	}

	public void setApneaContinue(Boolean apneaContinue) {
		this.apneaContinue = apneaContinue;
	}

	public Boolean getApneaResolved() {
		return apneaResolved;
	}

	public void setApneaResolved(Boolean apneaResolved) {
		this.apneaResolved = apneaResolved;
	}

	public Boolean getSeizureContinue() {
		return seizureContinue;
	}

	public void setSeizureContinue(Boolean seizureContinue) {
		this.seizureContinue = seizureContinue;
	}

	public Boolean getSeizureResolved() {
		return seizureResolved;
	}

	public void setSeizureResolved(Boolean seizureResolved) {
		this.seizureResolved = seizureResolved;
	}

	public Boolean getRdsContinue() {
		return rdsContinue;
	}

	public void setRdsContinue(Boolean rdsContinue) {
		this.rdsContinue = rdsContinue;
	}

	public Boolean getRdsResolved() {
		return rdsResolved;
	}

	public void setRdsResolved(Boolean rdsResolved) {
		this.rdsResolved = rdsResolved;
	}

	public Boolean getHypothermiaContinue() {
		return hypothermiaContinue;
	}

	public void setHypothermiaContinue(Boolean hypothermiaContinue) {
		this.hypothermiaContinue = hypothermiaContinue;
	}

	public Boolean getHypothermiaResolved() {
		return hypothermiaResolved;
	}

	public void setHypothermiaResolved(Boolean hypothermiaResolved) {
		this.hypothermiaResolved = hypothermiaResolved;
	}

	public Integer getCountHypoglycaemicEvents() {
		return countHypoglycaemicEvents;
	}

	public void setCountHypoglycaemicEvents(Integer countHypoglycaemicEvents) {
		this.countHypoglycaemicEvents = countHypoglycaemicEvents;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
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

	public Float getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(Float bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public String getOtherPlanComments() {
		return otherPlanComments;
	}

	public void setOtherPlanComments(String otherPlanComments) {
		this.otherPlanComments = otherPlanComments;
	}

	public String getPlanReassesTimeType() {
		return planReassesTimeType;
	}

	public void setPlanReassesTimeType(String planReassesTimeType) {
		this.planReassesTimeType = planReassesTimeType;
	}

	public Integer getPlanReassesTime() {
		return planReassesTime;
	}

	public void setPlanReassesTime(Integer planReassesTime) {
		this.planReassesTime = planReassesTime;
	}

	public Integer getBloodReassesTime() {
		return bloodReassesTime;
	}

	public void setBloodReassesTime(Integer bloodReassesTime) {
		this.bloodReassesTime = bloodReassesTime;
	}

	public String getBloodReassesTimeType() {
		return bloodReassesTimeType;
	}

	public void setBloodReassesTimeType(String bloodReassesTimeType) {
		this.bloodReassesTimeType = bloodReassesTimeType;
	}

	public String getPastOrderInvestigationStr() {
		return pastOrderInvestigationStr;
	}

	public void setPastOrderInvestigationStr(String pastOrderInvestigationStr) {
		this.pastOrderInvestigationStr = pastOrderInvestigationStr;
	}

	public String getPastTreatmentActionStr() {
		return pastTreatmentActionStr;
	}

	public void setPastTreatmentActionStr(String pastTreatmentActionStr) {
		this.pastTreatmentActionStr = pastTreatmentActionStr;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Boolean getGirIncreased() {
		return girIncreased;
	}

	public void setGirIncreased(Boolean girIncreased) {
		this.girIncreased = girIncreased;
	}

	public Boolean getGirDecreased() {
		return girDecreased;
	}

	public void setGirDecreased(Boolean girDecreased) {
		this.girDecreased = girDecreased;
	}

	public Float getGirValue() {
		return girValue;
	}

	public void setGirValue(Float girValue) {
		this.girValue = girValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	public void setMaxGir(Float maxGir) {
//		this.maxGir = maxGir;
//	}
//
//	public void setMinGir(Float minGir) {
//		this.minGir = minGir;
//	}

	public String getAssociatedevent() {
		return this.associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public String getCauseofhypoglycemia() {
		return causeofhypoglycemia;
	}

	public void setCauseofhypoglycemia(String causeofhypoglycemia) {
		this.causeofhypoglycemia = causeofhypoglycemia;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEventstatus() {
		return this.eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getHypoglycemiaEventStatus() {
		return hypoglycemiaEventStatus;
	}

	public void setHypoglycemiaEventStatus(String hypoglycemiaEventStatus) {
		this.hypoglycemiaEventStatus = hypoglycemiaEventStatus;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMetabolicSystemStatus() {
		return this.metabolicSystemStatus;
	}

	public void setMetabolicSystemStatus(String metabolicSystemStatus) {
		this.metabolicSystemStatus = metabolicSystemStatus;
	}

	public String getMinBloodsugar() {
		return this.minBloodsugar;
	}

	public void setMinBloodsugar(String minBloodsugar) {
		this.minBloodsugar = minBloodsugar;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getProgressnotes() {
		return this.progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public Boolean getSymptomaticStatus() {
		return this.symptomaticStatus;
	}

	public void setSymptomaticStatus(Boolean symptomaticStatus) {
		this.symptomaticStatus = symptomaticStatus;
	}

	public String getSymptomaticValue() {
		return this.symptomaticValue;
	}

	public void setSymptomaticValue(String symptomaticValue) {
		this.symptomaticValue = symptomaticValue;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public List<String> getCauseofhypoglycemiaList() {
		return causeofhypoglycemiaList;
	}

	public void setCauseofhypoglycemiaList(List<String> causeofhypoglycemiaList) {
		this.causeofhypoglycemiaList = causeofhypoglycemiaList;
	}

	public Integer getCountHypoglemicEvents() {
		return countHypoglemicEvents;
	}

	public Integer getCountSymptomaticEvents() {
		return countSymptomaticEvents;
	}

	public Integer getCountAsymptomaticEvents() {
		return countAsymptomaticEvents;
	}

	public void setCountHypoglemicEvents(Integer countHypoglemicEvents) {
		this.countHypoglemicEvents = countHypoglemicEvents;
	}

	public void setCountSymptomaticEvents(Integer countSymptomaticEvents) {
		this.countSymptomaticEvents = countSymptomaticEvents;
	}

	public void setCountAsymptomaticEvents(Integer countAsymptomaticEvents) {
		this.countAsymptomaticEvents = countAsymptomaticEvents;
	}

	public Float getTotalfeedMlDay() {
		return totalfeedMlDay;
	}

	public String getCauseOtherText() {
		return causeOtherText;
	}

	public void setCauseOtherText(String causeOtherText) {
		this.causeOtherText = causeOtherText;
	}

	public String getBolusType() {
		return bolusType;
	}

	public String getBolusVolume() {
		return bolusVolume;
	}

	public String getBolusTotalFeed() {
		return bolusTotalFeed;
	}

	public void setBolusType(String bolusType) {
		this.bolusType = bolusType;
	}

	public void setBolusVolume(String bolusVolume) {
		this.bolusVolume = bolusVolume;
	}

	public void setBolusTotalFeed(String bolusTotalFeed) {
		this.bolusTotalFeed = bolusTotalFeed;
	}

//	public Float getMaxGir() {
//		return maxGir;
//	}
//
//	public Float getMinGir() {
//		return minGir;
//	}

	public void setTotalfeedMlDay(Float totalfeedMlDay) {
		this.totalfeedMlDay = totalfeedMlDay;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getTreatmentactionOtherText() {
		return treatmentactionOtherText;
	}

	public void setTreatmentactionOtherText(String treatmentactionOtherText) {
		this.treatmentactionOtherText = treatmentactionOtherText;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}



	public String getCausesOfHypoglycemia() {
		return causesOfHypoglycemia;
	}



	public void setCausesOfHypoglycemia(String causesOfHypoglycemia) {
		this.causesOfHypoglycemia = causesOfHypoglycemia;
	}



	public String getLethargy() {
		return lethargy;
	}



	public void setLethargy(String lethargy) {
		this.lethargy = lethargy;
	}



	public String getApnea() {
		return apnea;
	}



	public void setApnea(String apnea) {
		this.apnea = apnea;
	}



	public String getSeizures() {
		return seizures;
	}



	public void setSeizures(String seizures) {
		this.seizures = seizures;
	}



	public String getRds() {
		return rds;
	}



	public void setRds(String rds) {
		this.rds = rds;
	}



	public String getHypothermia() {
		return hypothermia;
	}



	public void setHypothermia(String hypothermia) {
		this.hypothermia = hypothermia;
	}



	public String getRiskFactors() {
		return riskFactors;
	}



	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}



	public Integer getCumulativeHypoEvents() {
		return cumulativeHypoEvents;
	}



	public void setCumulativeHypoEvents(Integer cumulativeHypoEvents) {
		this.cumulativeHypoEvents = cumulativeHypoEvents;
	}



	public String getNutritionId() {
		return nutritionId;
	}



	public void setNutritionId(String nutritionId) {
		this.nutritionId = nutritionId;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Float getMaxGir() {
		return maxGir;
	}

	public void setMaxGir(Float maxGir) {
		this.maxGir = maxGir;
	}

	public Float getMinGir() {
		return minGir;
	}

	public void setMinGir(Float minGir) {
		this.minGir = minGir;
	}

	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}
}