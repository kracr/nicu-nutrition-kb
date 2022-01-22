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
@Table(name = "sa_jaundice")
@NamedQuery(name = "SaJaundice.findAll", query = "SELECT s FROM SaJaundice s")
public class SaJaundice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sajaundiceid;

	@Column(columnDefinition = "bool")
	private Boolean tcbortsb;


	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;


	@Column(columnDefinition = "float4")
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Integer ageofonset;

	private String causeofjaundice;
	@Transient
	private List causeofjaundiceList;

	private Timestamp creationtime;

	@Transient
	private String creationTimeStr;

	private Integer durationofjaundice;

	private String durphoto;

	@Column(columnDefinition = "bool")
	private Boolean exchangetrans;

	@Column(columnDefinition = "bool")
	private Boolean ivig;

	private String ivigvalue;

	private Timestamp modificationtime;

	// private String nicubedno;

	private String noofexchange;

	@Column(columnDefinition = "bool")
	private Boolean phototherapy;

	private String uhid;

	private String loggeduser;

	private String comment;

	// jaudice changes...
	@Transient
	private Boolean isNewEntry;

	@Column(columnDefinition = "bool")
	private Boolean isageofonsetinhours;

	@Column(columnDefinition = "float4")
	private Float tbcvalue;

	private String isphotobelowthreshold;

	private String observationtype;

	private String actiontype;

	@Column(columnDefinition = "float4")
	private Float maxTcb;

	@Column(columnDefinition = "float4")
	private Float maxTsb;

	private String riskfactor;

	@Transient
	private List riskFactorList;

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

	private String jaundicestatus;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateofbirth;

	private String nursingcomment;

	@Column(columnDefinition = "int8")
	private Integer bindscoreid;

	private String otherCause;

	private String associatedevent;

	@Transient
	private Integer gestation;

	private String phototherapyvalue;
	
	@Column(name = "phototherapy_type")
	private String phototherapyType;

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
	private Integer durationOfPhototherapy;

	@Transient
	private Integer numberOfExchangeTransfusion;

	@Transient
	private Integer totalDoseOfIVIG;

	@Transient
	private String prescriptionBackMessage;

	@Transient
	private Integer durationOfOtherTreatment;

	private String planOther;

	private String jaundiceDiagnosisBy;

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
	
	private String icdCauseofjaundice;
	
	@Transient
	private List icdCauseofjaundiceList;
	
	private String weightloss;
	
	@Transient
	private List testResultList;
	
	@Transient
	private Timestamp labTestDateTime;

	private String episodeid;
	
	private String reason;

	public SaJaundice() {
		this.isNewEntry = true;
		this.gestation = 29;
		this.tcbortsb = true;
		this.isageofonsetinhours = true;
		this.isageofassesmentinhours = true;
		this.maxTcb = Float.valueOf(0);
		this.assessmentMeridiem = true;
	}

	
	
	public String getWeightloss() {
		return weightloss;
	}



	public void setWeightloss(String weightloss) {
		this.weightloss = weightloss;
	}



	public Long getSajaundiceid() {
		return this.sajaundiceid;
	}

	public void setSajaundiceid(Long sajaundiceid) {
		this.sajaundiceid = sajaundiceid;
	}

	public Integer getAgeofonset() {
		return this.ageofonset;
	}

	public void setAgeofonset(Integer ageofonset) {
		this.ageofonset = ageofonset;
	}

	public String getCauseofjaundice() {
		return this.causeofjaundice;
	}

	public void setCauseofjaundice(String causeofjaundice) {
		this.causeofjaundice = causeofjaundice;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Integer getDurationofjaundice() {
		return this.durationofjaundice;
	}

	public void setDurationofjaundice(Integer durationofjaundice) {
		this.durationofjaundice = durationofjaundice;
	}

	public String getDurphoto() {
		return this.durphoto;
	}

	public void setDurphoto(String durphoto) {
		this.durphoto = durphoto;
	}

	public Boolean getExchangetrans() {
		return this.exchangetrans;
	}

	public void setExchangetrans(Boolean exchangetrans) {
		this.exchangetrans = exchangetrans;
	}

	public Boolean getIvig() {
		return this.ivig;
	}

	public void setIvig(Boolean ivig) {
		this.ivig = ivig;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	// public String getNicubedno() {
	// return this.nicubedno;
	// }
	//
	// public void setNicubedno(String nicubedno) {
	// this.nicubedno = nicubedno;
	// }

	public String getNoofexchange() {
		return this.noofexchange;
	}

	public void setNoofexchange(String noofexchange) {
		this.noofexchange = noofexchange;
	}

	public Boolean getPhototherapy() {
		return this.phototherapy;
	}

	public void setPhototherapy(Boolean phototherapy) {
		this.phototherapy = phototherapy;
	}

	public String getUhid() {
		return this.uhid;
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

	public Boolean getIsageofonsetinhours() {
		return isageofonsetinhours;
	}

	public void setIsageofonsetinhours(Boolean isageofonsetinhours) {
		this.isageofonsetinhours = isageofonsetinhours;
	}

	public Float getTbcvalue() {
		return tbcvalue;
	}

	public void setTbcvalue(Float tbcvalue) {
		this.tbcvalue = tbcvalue;
	}

	public String getIsphotobelowthreshold() {
		return isphotobelowthreshold;
	}

	public void setIsphotobelowthreshold(String isphotobelowthreshold) {
		this.isphotobelowthreshold = isphotobelowthreshold;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
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

	public List getCauseofjaundiceList() {
		return causeofjaundiceList;
	}

	public List getActiontypeList() {
		return actiontypeList;
	}

	public String getOrderinvestigation() {
		return orderinvestigation;
	}

	public List getOrderinvestigationList() {
		return orderinvestigationList;
	}

	public void setCauseofjaundiceList(List causeofjaundiceList) {
		this.causeofjaundiceList = causeofjaundiceList;
	}

	public void setActiontypeList(List actiontypeList) {
		this.actiontypeList = actiontypeList;
	}

	public void setOrderinvestigation(String orderinvestigation) {
		this.orderinvestigation = orderinvestigation;
	}

	public void setOrderinvestigationList(List orderinvestigationList) {
		this.orderinvestigationList = orderinvestigationList;
	}

	public String getOtherCause() {
		return otherCause;
	}

	public void setOtherCause(String otherCause) {
		this.otherCause = otherCause;
	}

	public Boolean getIsinvestigationodered() {
		return isinvestigationodered;
	}

	public void setIsinvestigationodered(Boolean isinvestigationodered) {
		this.isinvestigationodered = isinvestigationodered;
	}

	public String getJaundicestatus() {
		return jaundicestatus;
	}

	public void setJaundicestatus(String jaundicestatus) {
		this.jaundicestatus = jaundicestatus;
	}

	public String getNursingcomment() {
		return nursingcomment;
	}

	public void setNursingcomment(String nursingcomment) {
		this.nursingcomment = nursingcomment;
	}

	public String getIvigvalue() {
		return ivigvalue;
	}

	public void setIvigvalue(String ivigvalue) {
		this.ivigvalue = ivigvalue;
	}

	public String getPhototherapyvalue() {
		return phototherapyvalue;
	}

	public void setPhototherapyvalue(String phototherapyvalue) {
		this.phototherapyvalue = phototherapyvalue;
	}

	public String getCreationTimeStr() {
		return creationTimeStr;
	}

	public void setCreationTimeStr(String creationTimeStr) {
		this.creationTimeStr = creationTimeStr;
	}

	public Integer getGestation() {
		return gestation;
	}

	public void setGestation(Integer gestation) {
		this.gestation = gestation;
	}

	public Integer getBindscoreid() {
		return bindscoreid;
	}

	public void setBindscoreid(Integer bindscoreid) {
		this.bindscoreid = bindscoreid;
	}

	public String getObservationtype() {
		return observationtype;
	}

	public void setObservationtype(String observationtype) {
		this.observationtype = observationtype;
	}

	public Boolean isEdit() {
		return isEdit;
	}

	public void setEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getRiskfactor() {
		return riskfactor;
	}

	public List getRiskFactorList() {
		return riskFactorList;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public void setRiskFactorList(List riskFactorList) {
		this.riskFactorList = riskFactorList;
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

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public Boolean getTcbortsb() {
		return tcbortsb;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public Boolean getIsageofassesmentinhours() {
		return isageofassesmentinhours;
	}

	public void setTcbortsb(Boolean tcbortsb) {
		this.tcbortsb = tcbortsb;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	public void setIsageofassesmentinhours(Boolean isageofassesmentinhours) {
		this.isageofassesmentinhours = isageofassesmentinhours;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Integer getDurationOfEpisode() {
		return durationOfEpisode;
	}

	public Integer getDurationOfPhototherapy() {
		return durationOfPhototherapy;
	}

	public Integer getNumberOfExchangeTransfusion() {
		return numberOfExchangeTransfusion;
	}

	public Integer getTotalDoseOfIVIG() {
		return totalDoseOfIVIG;
	}

	public void setDurationOfEpisode(Integer durationOfEpisode) {
		this.durationOfEpisode = durationOfEpisode;
	}

	public void setDurationOfPhototherapy(Integer durationOfPhototherapy) {
		this.durationOfPhototherapy = durationOfPhototherapy;
	}

	public void setNumberOfExchangeTransfusion(Integer numberOfExchangeTransfusion) {
		this.numberOfExchangeTransfusion = numberOfExchangeTransfusion;
	}

	public void setTotalDoseOfIVIG(Integer totalDoseOfIVIG) {
		this.totalDoseOfIVIG = totalDoseOfIVIG;
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

	public Float getMaxTcb() {
		return maxTcb;
	}

	public void setMaxTcb(Float maxTcb) {
		this.maxTcb = maxTcb;
	}

	public Float getMaxTsb() {
		return maxTsb;
	}

	public void setMaxTsb(Float maxTsb) {
		this.maxTsb = maxTsb;
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

	public String getJaundiceDiagnosisBy() {
		return this.jaundiceDiagnosisBy;
	}

	public void setJaundiceDiagnosisBy(String jaundiceDiagnosisBy) {
		this.jaundiceDiagnosisBy = jaundiceDiagnosisBy;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public List<String> getTreatmentactionplanlist() {
		return treatmentactionplanlist;
	}

	public void setTreatmentactionplanlist(List<String> treatmentactionplanlist) {
		this.treatmentactionplanlist = treatmentactionplanlist;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getIcdCauseofjaundice() {
		return icdCauseofjaundice;
	}

	public void setIcdCauseofjaundice(String icdCauseofjaundice) {
		this.icdCauseofjaundice = icdCauseofjaundice;
	}

	public List getIcdCauseofjaundiceList() {
		return icdCauseofjaundiceList;
	}

	public void setIcdCauseofjaundiceList(List icdCauseofjaundiceList) {
		this.icdCauseofjaundiceList = icdCauseofjaundiceList;
	}

	public String getPhototherapyType() {
		return phototherapyType;
	}

	public void setPhototherapyType(String phototherapyType) {
		this.phototherapyType = phototherapyType;
	}
	public List getTestResultList() {
		return testResultList;
	}
	public void setTestResultList(List testResultList) {
		this.testResultList = testResultList;
	}
	public Timestamp getLabTestDateTime() {
		return labTestDateTime;
	}
	public void setLabTestDateTime(Timestamp labTestDateTime) {
		this.labTestDateTime = labTestDateTime;
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


	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}

}