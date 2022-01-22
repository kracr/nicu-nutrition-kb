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
@Table(name = "sa_miscellaneous_2")
@NamedQuery(name = "SaMiscellaneous2.findAll", query = "SELECT s FROM SaMiscellaneous s")
public class SaMiscellaneous2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sa_miscellaneous_2_id")
	private Long saMiscellaneous2Id;

	@Column(columnDefinition = "float4")
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Integer ageofonset;

	private String causeofmiscellaneous;

	private Timestamp creationtime;

	@Transient
	private String creationTimeStr;

	private Timestamp modificationtime;

	// private String nicubedno
	private String uhid;

	private String loggeduser;

	private String description;

	// jaudice changes...
	@Transient
	private Boolean isNewEntry;

	@Column(columnDefinition = "bool")
	private Boolean isageofonsetinhours;

	private String orderinvestigation;
	@Transient
	private List orderinvestigationList;

	@Column(columnDefinition = "bool")
	private Boolean isinvestigationodered;

	private String miscellaneousstatus;

	private String eventstatus;

	@Temporal(TemporalType.DATE)
	@Transient
	private Date dateofbirth;

	//Not in use anymore
	//private String associatedevent;
	
	private String disease;

	@Transient
	private Integer gestation;

	@Transient
	Boolean isEdit;

	@Transient
	private String pastOrderInvestigationStr;

	// other treatment field added, after gangaram meeting
	private String treatment;

	private String planOther;

	private Integer actionduration;

	private String comment;

	private String isactiondurationinhours;

	@Column(name = "assessment_time")
	private Timestamp assessmentTime;

	@Transient
	private String previousMiscStatus;
	
	@Transient
	private List<String> diseaseList;
	
	private String otherDisease;
	
	public List<String> getDiseaseList() {
		return diseaseList;
	}

	public void setDiseaseList(List<String> diseaseList) {
		this.diseaseList = diseaseList;
	}
	public Long getSaMiscellaneous2Id() {
		return saMiscellaneous2Id;
	}

	public void setSaMiscellaneous2Id(Long saMiscellaneous2Id) {
		this.saMiscellaneous2Id = saMiscellaneous2Id;
	}

	public Integer getAgeatassesment() {
		return ageatassesment;
	}

	public void setAgeatassesment(Integer ageatassesment) {
		this.ageatassesment = ageatassesment;
	}

	@Column(name = "medication_str")
	private String medicationStr;

	private String episodeid;

	public SaMiscellaneous2() {
		this.isNewEntry = true;
		this.gestation = 29;
		this.isageofonsetinhours = true;
		this.isageofassesmentinhours = true;

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

	public String getCauseofmiscellaneous() {
		return causeofmiscellaneous;
	}

	public void setCauseofmiscellaneous(String causeofmiscellaneous) {
		this.causeofmiscellaneous = causeofmiscellaneous;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getMiscellaneousstatus() {
		return miscellaneousstatus;
	}

	public void setMiscellaneousstatus(String miscellaneousstatus) {
		this.miscellaneousstatus = miscellaneousstatus;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

//	public String getAssociatedevent() {
//		return associatedevent;
//	}
//
//	public void setAssociatedevent(String associatedevent) {
//		this.associatedevent = associatedevent;
//	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public Integer getGestation() {
		return gestation;
	}

	public void setGestation(Integer gestation) {
		this.gestation = gestation;
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

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getPlanOther() {
		return planOther;
	}

	public void setPlanOther(String planOther) {
		this.planOther = planOther;
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

	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEventstatus() {
		return eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public String getPreviousMiscStatus() {
		return previousMiscStatus;
	}

	public void setPreviousMiscStatus(String previousMiscStatus) {
		this.previousMiscStatus = previousMiscStatus;
	}

	public String getOtherDisease() {
		return otherDisease;
	}

	public void setOtherDisease(String otherDisease) {
		this.otherDisease = otherDisease;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
}
