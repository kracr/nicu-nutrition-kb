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

//import com.microsoft.windowsazure.core.TimeSpan8601Converter;

/**
 * The persistent class for the sa_jaundice database table.
 * 
 */
@Entity
@Table(name = "sa_shock")
@NamedQuery(name = "SaShock.findAll", query = "SELECT s FROM SaShock s")
public class SaShock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sashockid;

	@Column(columnDefinition = "float4")
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Integer ageofonset;

	private String causeofshock;
	@Transient
	private List causeofshockList;

	private Timestamp creationtime;
	
	@Transient
	private String creationTimeStr;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private String comment;

	// jaudice changes...
	@Transient
	private Boolean isNewEntry;

	@Column(columnDefinition = "bool")
	private Boolean isageofonsetinhours;
	private String actiontype;
	@Transient
	private List actiontypeList;

	private String otheractiontype;

	private Integer actionduration;

	private String isactiondurationinhours;

	private String orderinvestigation;
	@Transient
	private List orderinvestigationList;

	@Column(columnDefinition = "bool")
	private Boolean isinvestigationodered;

	private String shockStatus;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateofbirth;

	private String otherCause;

	@Transient
	Boolean isEdit;

	@Transient
	private String pastOrderInvestigationStr;

	@Transient
	private String pastTreatmentActionStr;

	// other treatment field added, after gangaram meeting
	private String treatmentOther;

	// for multiple treatment plans(reassess,investigation,other)
	@Transient
	private List<String> treatmentactionplanlist;

	@Transient
	private Integer durationOfEpisode;

	@Transient
	private String prescriptionBackMessage;

	@Transient
	private Integer durationOfOtherTreatment;

	private String planOther;

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
	private List testResultList;

	private String episodeid;
	
	private String reason;
	
	private Integer heartrate;
	
	private Integer respiratoryrate;
	
	private Integer saturation;
	
	private Integer crt;
	
	private Integer shocksystbp;
	
	private Integer shockdiastbp;
	
	private Integer shockmeanbp;
	
	@Column(name = "temperature_diff", columnDefinition = "Float4")
	private Float temperatureDiff;
	
	@Column(columnDefinition = "Float4")
	private Float lactate;
	
	@Column(columnDefinition = "Float4")
	private Float urine;
	
	private String pulses;
	
	private String peripheries;
	
	@Column(columnDefinition = "bool")
	private Boolean venous;
	
	@Column(columnDefinition = "bool")
	private Boolean arterial;
	
	@Column(columnDefinition = "bool")
	private Boolean metabolic;
	
	@Column(columnDefinition = "bool")
	private Boolean arrhythmia;
	
	@Column(name = "internal_hemorrhage", columnDefinition = "bool")
	private Boolean internalHemorrhage;
	
	@Column(name = "gi_losses", columnDefinition = "bool")
	private Boolean giLosses;
	
	@Column(name = "poor_feeding", columnDefinition = "bool")
	private Boolean poorFeeding;
	
	@Column(name = "other_hypovolemic", columnDefinition = "bool")
	private Boolean otherHypovolemic;
	
	@Column(name = "other_hypovolemic_type")
	private String otherHypovolemicType;
	
	@Column(name = "other_distributive", columnDefinition = "bool")
	private Boolean otherDistributive;
	
	@Column(name = "other_distributive_type")
	private String otherDistributiveType;
	
	@Column(name = "other_obstructive", columnDefinition = "bool")
	private Boolean otherObstructive;
	
	@Column(name = "other_obstructive_type")
	private String otherObstructiveType;
	
	@Column(name = "other_cardiogenic", columnDefinition = "bool")
	private Boolean otherCardiogenic;
	
	@Column(name = "other_cardiogenic_type")
	private String otherCardiogenicType;
	
	@Column(name = "other_shock_type")
	private String otherShockType;
	
	@Column(name = "asphyxia", columnDefinition = "bool")
	private Boolean asphyxia;
	
	@Column(name = "myocarditis", columnDefinition = "bool")
	private Boolean myocarditis;
	
	@Column(columnDefinition = "bool")
	private Boolean chd;
	
	@Column(name = "sepsis_distributive", columnDefinition = "bool")
	private Boolean sepsisDistributive;
	
	@Column(name = "fluid_loss", columnDefinition = "bool")
	private Boolean fluidLoss;
	
	@Column(name = "neurologic_injury", columnDefinition = "bool")
	private Boolean neurologicInjury;
	
	@Column(name = "anaphylactic_shock", columnDefinition = "bool")
	private Boolean anaphylacticShock;
	
	@Column(name = "echo_findings")
	private String echoFindings;
	
	@Column(name = "babyfeedid")
	private Long babyfeedid;
	
	@Column(columnDefinition="bool")
	private Boolean feeding;
	
	@Column(name="feeding_continue")
	private String feedingContinue;

	private String consciousness;
	
	private String etco2;
	
	private String cvp;
	
	@Transient
	private Boolean obstructive;
	
	@Transient
	private Boolean cardiogenic;
	
	@Transient
	private Boolean distributive;
	
	@Transient
	private Boolean hypovolemic;
	
	@Transient
	private String cvpUnit;
	
	@Column(name="pulses_review")
	private String pulsesReview;
	
	@Column(name="peripheries_review")
	private String peripheriesReview;
	
	private String tachycardia;
	
	@Column(name="prolonged_crt")
	private String prolongedcrt;
	
	@Column(name="temp_diff")
	private String tempDiff;
	
	@Column(name="lactate_review")
	private String lactateReview;
	
	@Column(name="urine_review")
	private String urineReview;
	
	public SaShock() {
		this.isNewEntry = true;
		this.isageofonsetinhours = true;
		this.isageofassesmentinhours = true;
		this.assessmentMeridiem = true;
	}

	public Long getSashockid() {
		return sashockid;
	}

	public void setSashockid(Long sashockid) {
		this.sashockid = sashockid;
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

	public Integer getAgeofonset() {
		return ageofonset;
	}

	public void setAgeofonset(Integer ageofonset) {
		this.ageofonset = ageofonset;
	}

	public String getCauseofshock() {
		return causeofshock;
	}

	public void setCauseofshock(String causeofshock) {
		this.causeofshock = causeofshock;
	}

	public List getCauseofshockList() {
		return causeofshockList;
	}

	public void setCauseofshockList(List causeofshockList) {
		this.causeofshockList = causeofshockList;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getCreationTimeStr() {
		return creationTimeStr;
	}

	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public Boolean getIsageofonsetinhours() {
		return isageofonsetinhours;
	}

	public void setIsageofonsetinhours(Boolean isageofonsetinhours) {
		this.isageofonsetinhours = isageofonsetinhours;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public List getActiontypeList() {
		return actiontypeList;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public String getOtheractiontype() {
		return otheractiontype;
	}

	public void setOtheractiontype(String otheractiontype) {
		this.otheractiontype = otheractiontype;
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

	public String getOrderinvestigation() {
		return orderinvestigation;
	}

	public void setOrderinvestigation(String orderinvestigation) {
		this.orderinvestigation = orderinvestigation;
	}

	public List getOrderinvestigationList() {
		return orderinvestigationList;
	}

	public void setOrderinvestigationList(List orderinvestigationList) {
		this.orderinvestigationList = orderinvestigationList;
	}

	public Boolean getIsinvestigationodered() {
		return isinvestigationodered;
	}

	public void setIsinvestigationodered(Boolean isinvestigationodered) {
		this.isinvestigationodered = isinvestigationodered;
	}

	public String getShockStatus() {
		return shockStatus;
	}

	public void setShockStatus(String shockStatus) {
		this.shockStatus = shockStatus;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getOtherCause() {
		return otherCause;
	}

	public void setOtherCause(String otherCause) {
		this.otherCause = otherCause;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
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

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public List<String> getTreatmentactionplanlist() {
		return treatmentactionplanlist;
	}

	public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
		this.treatmentactionplanlist = treatmentactionplanlist;
	}

	public Integer getDurationOfEpisode() {
		return durationOfEpisode;
	}

	public void setDurationOfEpisode(Integer durationOfEpisode) {
		this.durationOfEpisode = durationOfEpisode;
	}

	public String getPrescriptionBackMessage() {
		return prescriptionBackMessage;
	}

	public void setPrescriptionBackMessage(String prescriptionBackMessage) {
		this.prescriptionBackMessage = prescriptionBackMessage;
	}

	public Integer getDurationOfOtherTreatment() {
		return durationOfOtherTreatment;
	}

	public void setDurationOfOtherTreatment(Integer durationOfOtherTreatment) {
		this.durationOfOtherTreatment = durationOfOtherTreatment;
	}

	public String getPlanOther() {
		return planOther;
	}

	public void setPlanOther(String planOther) {
		this.planOther = planOther;
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

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public List getTestResultList() {
		return testResultList;
	}

	public void setTestResultList(List testResultList) {
		this.testResultList = testResultList;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(Integer heartrate) {
		this.heartrate = heartrate;
	}

	public Integer getRespiratoryrate() {
		return respiratoryrate;
	}

	public void setRespiratoryrate(Integer respiratoryrate) {
		this.respiratoryrate = respiratoryrate;
	}

	public Integer getSaturation() {
		return saturation;
	}

	public void setSaturation(Integer saturation) {
		this.saturation = saturation;
	}

	public Integer getCrt() {
		return crt;
	}

	public void setCrt(Integer crt) {
		this.crt = crt;
	}

	public Integer getShocksystbp() {
		return shocksystbp;
	}

	public void setShocksystbp(Integer shocksystbp) {
		this.shocksystbp = shocksystbp;
	}

	public Integer getShockdiastbp() {
		return shockdiastbp;
	}

	public void setShockdiastbp(Integer shockdiastbp) {
		this.shockdiastbp = shockdiastbp;
	}

	public Float getLactate() {
		return lactate;
	}

	public void setLactate(Float lactate) {
		this.lactate = lactate;
	}

	public Float getUrine() {
		return urine;
	}

	public void setUrine(Float urine) {
		this.urine = urine;
	}

	public String getPulses() {
		return pulses;
	}

	public void setPulses(String pulses) {
		this.pulses = pulses;
	}

	public String getPeripheries() {
		return peripheries;
	}

	public void setPeripheries(String peripheries) {
		this.peripheries = peripheries;
	}

	public Float getTemperatureDiff() {
		return temperatureDiff;
	}

	public void setTemperatureDiff(Float temperatureDiff) {
		this.temperatureDiff = temperatureDiff;
	}
	
	public Boolean getInternalHemorrhage() {
		return internalHemorrhage;
	}

	public void setInternalHemorrhage(Boolean internalHemorrhage) {
		this.internalHemorrhage = internalHemorrhage;
	}

	public Boolean getAsphyxia() {
		return asphyxia;
	}

	public void setAsphyxia(Boolean asphyxia) {
		this.asphyxia = asphyxia;
	}

	public Boolean getMyocarditis() {
		return myocarditis;
	}

	public void setMyocarditis(Boolean myocarditis) {
		this.myocarditis = myocarditis;
	}

	public Boolean getChd() {
		return chd;
	}

	public void setChd(Boolean chd) {
		this.chd = chd;
	}

	public Boolean getSepsisDistributive() {
		return sepsisDistributive;
	}

	public void setSepsisDistributive(Boolean sepsisDistributive) {
		this.sepsisDistributive = sepsisDistributive;
	}

	public String getEchoFindings() {
		return echoFindings;
	}

	public void setEchoFindings(String echoFindings) {
		this.echoFindings = echoFindings;
	}

	public Long getBabyfeedid() {
		return babyfeedid;
	}

	public void setBabyfeedid(Long babyfeedid) {
		this.babyfeedid = babyfeedid;
	}

	public Boolean getFeeding() {
		return feeding;
	}

	public void setFeeding(Boolean feeding) {
		this.feeding = feeding;
	}

	public String getFeedingContinue() {
		return feedingContinue;
	}

	public void setFeedingContinue(String feedingContinue) {
		this.feedingContinue = feedingContinue;
	}

	public Boolean getVenous() {
		return venous;
	}

	public void setVenous(Boolean venous) {
		this.venous = venous;
	}

	public Boolean getArterial() {
		return arterial;
	}

	public void setArterial(Boolean arterial) {
		this.arterial = arterial;
	}

	public Boolean getMetabolic() {
		return metabolic;
	}

	public void setMetabolic(Boolean metabolic) {
		this.metabolic = metabolic;
	}

	public Boolean getArrhythmia() {
		return arrhythmia;
	}

	public void setArrhythmia(Boolean arrhythmia) {
		this.arrhythmia = arrhythmia;
	}

	public Boolean getFluidLoss() {
		return fluidLoss;
	}

	public void setFluidLoss(Boolean fluidLoss) {
		this.fluidLoss = fluidLoss;
	}

	public Boolean getNeurologicInjury() {
		return neurologicInjury;
	}

	public void setNeurologicInjury(Boolean neurologicInjury) {
		this.neurologicInjury = neurologicInjury;
	}

	public Boolean getAnaphylacticShock() {
		return anaphylacticShock;
	}

	public void setAnaphylacticShock(Boolean anaphylacticShock) {
		this.anaphylacticShock = anaphylacticShock;
	}

	public String getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(String consciousness) {
		this.consciousness = consciousness;
	}

	public String getEtco2() {
		return etco2;
	}

	public void setEtco2(String etco2) {
		this.etco2 = etco2;
	}

	public String getCvp() {
		return cvp;
	}

	public void setCvp(String cvp) {
		this.cvp = cvp;
	}

	public String getCvpUnit() {
		return cvpUnit;
	}

	public void setCvpUnit(String cvpUnit) {
		this.cvpUnit = cvpUnit;
	}

	public Boolean getGiLosses() {
		return giLosses;
	}

	public void setGiLosses(Boolean giLosses) {
		this.giLosses = giLosses;
	}

	public Boolean getPoorFeeding() {
		return poorFeeding;
	}

	public void setPoorFeeding(Boolean poorFeeding) {
		this.poorFeeding = poorFeeding;
	}

	public Boolean getOtherHypovolemic() {
		return otherHypovolemic;
	}

	public void setOtherHypovolemic(Boolean otherHypovolemic) {
		this.otherHypovolemic = otherHypovolemic;
	}

	public String getOtherHypovolemicType() {
		return otherHypovolemicType;
	}

	public void setOtherHypovolemicType(String otherHypovolemicType) {
		this.otherHypovolemicType = otherHypovolemicType;
	}

	public Boolean getOtherDistributive() {
		return otherDistributive;
	}

	public void setOtherDistributive(Boolean otherDistributive) {
		this.otherDistributive = otherDistributive;
	}

	public String getOtherDistributiveType() {
		return otherDistributiveType;
	}

	public void setOtherDistributiveType(String otherDistributiveType) {
		this.otherDistributiveType = otherDistributiveType;
	}

	public Boolean getOtherObstructive() {
		return otherObstructive;
	}

	public void setOtherObstructive(Boolean otherObstructive) {
		this.otherObstructive = otherObstructive;
	}

	public String getOtherObstructiveType() {
		return otherObstructiveType;
	}

	public void setOtherObstructiveType(String otherObstructiveType) {
		this.otherObstructiveType = otherObstructiveType;
	}

	public Boolean getOtherCardiogenic() {
		return otherCardiogenic;
	}

	public void setOtherCardiogenic(Boolean otherCardiogenic) {
		this.otherCardiogenic = otherCardiogenic;
	}

	public String getOtherCardiogenicType() {
		return otherCardiogenicType;
	}

	public void setOtherCardiogenicType(String otherCardiogenicType) {
		this.otherCardiogenicType = otherCardiogenicType;
	}

	public String getOtherShockType() {
		return otherShockType;
	}

	public void setOtherShockType(String otherShockType) {
		this.otherShockType = otherShockType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getShockmeanbp() {
		return shockmeanbp;
	}

	public void setShockmeanbp(Integer shockmeanbp) {
		this.shockmeanbp = shockmeanbp;
	}

	public Boolean getObstructive() {
		return obstructive;
	}

	public void setObstructive(Boolean obstructive) {
		this.obstructive = obstructive;
	}

	public Boolean getCardiogenic() {
		return cardiogenic;
	}

	public void setCardiogenic(Boolean cardiogenic) {
		this.cardiogenic = cardiogenic;
	}

	public Boolean getDistributive() {
		return distributive;
	}

	public void setDistributive(Boolean distributive) {
		this.distributive = distributive;
	}

	public Boolean getHypovolemic() {
		return hypovolemic;
	}

	public void setHypovolemic(Boolean hypovolemic) {
		this.hypovolemic = hypovolemic;
	}

	public String getPulsesReview() {
		return pulsesReview;
	}

	public void setPulsesReview(String pulsesReview) {
		this.pulsesReview = pulsesReview;
	}

	public String getPeripheriesReview() {
		return peripheriesReview;
	}

	public void setPeripheriesReview(String peripheriesReview) {
		this.peripheriesReview = peripheriesReview;
	}

	public String getTachycardia() {
		return tachycardia;
	}

	public void setTachycardia(String tachycardia) {
		this.tachycardia = tachycardia;
	}

	public String getProlongedcrt() {
		return prolongedcrt;
	}

	public void setProlongedcrt(String prolongedcrt) {
		this.prolongedcrt = prolongedcrt;
	}

	public String getTempDiff() {
		return tempDiff;
	}

	public void setTempDiff(String tempDiff) {
		this.tempDiff = tempDiff;
	}

	public String getLactateReview() {
		return lactateReview;
	}

	public void setLactateReview(String lactateReview) {
		this.lactateReview = lactateReview;
	}

	public String getUrineReview() {
		return urineReview;
	}

	public void setUrineReview(String urineReview) {
		this.urineReview = urineReview;
	}
	
}