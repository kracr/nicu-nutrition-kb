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

@Entity
@Table(name = "sa_infection_clabsi")
@NamedQuery(name = "SaInfectClabsi.findAll", query = "SELECT s FROM SaInfectClabsi s")
public class SaInfectClabsi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long saclabsiid;
	
	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;
	
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;
	
	private Timestamp timeofassessment;

	@Column(columnDefinition = "bool")
	private Boolean isclabsiavailable;
	
	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentplan;
	
	@Transient private List<String> treatmentplanList;
	  
	private String reassestime;
	
	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	/*private String causeofsepsis;
	  
	@Transient private List<String> causeofsepsisList;
	
	@Column(name = "causeofsepsis_other")
	private String causeofsepsisOther;*/	

	private String progressnotes;
	
	@Column(name = "assessment_date")
	private Timestamp assessmentDate; 
	
	@Column(name = "assessment_time")
	private Timestamp assessmentTime; 
	
	@Column(name = "assessment_hour")
	private String assessmentHour;
	
	@Column(name = "assessment_min")
	private String assessmentMin; 
	
	@Column(name = "assessment_meridiem" , columnDefinition = "bool")
	private Boolean assessmentMeridiem;
	
	@Column(name = "episode_number")
	private Integer episodeNumber;
	/*private String associatedevent;
	
	private String associatedeventid;*/
	
	//not in use anymore
	@Column(name = "infection_system_status")
	private String infectionSystemStatus;

	@Transient
	private Boolean isNewEntry;
	
	@Column(name = "medication_str")
	private String medicationStr;

	@Column(name = "order_investigation")
	private String orderSelectedText;

	private String episodeid;

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}
	public SaInfectClabsi()
	{
		super();
		//this.isNewEntry=true;
		this.episodeNumber = 1;
	}

	public Long getSaclabsiid() {
		return saclabsiid;
	}

	public void setSaclabsiid(Long saclabsiid) {
		this.saclabsiid = saclabsiid;
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

	public String getEventstatus() {
		return eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
	}
	
	public String getInfectionSystemStatus() {
		return infectionSystemStatus;
	}

	public void setInfectionSystemStatus(String infectionSystemStatus) {
		this.infectionSystemStatus = infectionSystemStatus;
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

	public Boolean getIsclabsiavailable() {
		return isclabsiavailable;
	}

	public void setIsclabsiavailable(Boolean isclabsiavailable) {
		this.isclabsiavailable = isclabsiavailable;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public List<String> getTreatmentplanList() {
		return treatmentplanList;
	}

	public void setTreatmentplanList(List<String> treatmentplanList) {
		this.treatmentplanList = treatmentplanList;
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

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public Timestamp getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Timestamp assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public Timestamp getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(Timestamp assessmentTime) {
		this.assessmentTime = assessmentTime;
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

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}
}
