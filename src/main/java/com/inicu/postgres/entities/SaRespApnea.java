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

/**
 * The persistent class for the sa_resp_apnea database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "sa_resp_apnea")
@NamedQuery(name = "SaRespApnea.findAll", query = "SELECT s FROM SaRespApnea s")
public class SaRespApnea implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long apneaid;

	@Transient
	private Boolean isNewEntry;


	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;


	@Column(name = "medication_str")
	private String medicationStr;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private String uhid;

	private String loggeduser;

	//not in use anymore
	@Column(name = "resp_system_status")
	private String respSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhours;

	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Integer totalnoofapnea;

	@Column(name = "noofapnea_inaday")
	private Integer noOfApneaInaDay;

	@Column(columnDefinition = "bool")
	private Boolean cyanosis;

	private String actiontype;

	@Transient
	private List<String> treatmentActionList;

	@Column(name = "caffeine_action")
	private String caffeineAction;

	@Column(name = "caffeine_route")
	private String caffeineRoute;

	@Column(name = "caffeine_bolus_dose")
	private String caffeineBolusDose;

	@Column(name = "caffeine_maintenance_dose")
	private String caffeineMaintenanceDose;

	@Column(name = "action_plan")
	private String actionPlan;

	@Column(name = "action_plan_time")
	private String actionPlanTime;

	@Column(name = "action_plan_timetype")
	private String action_plan_timetype;

	@Column(name = "action_plan_comments")
	private String actionPlanComments;

	@Column(name = "apnea_cause")
	private String apneaCause;

	@Transient
	private List<String> apneaCauseList;

	private Integer cummulative_apnea_on_caffeine;
	private Integer continuous_apnea_on_caffeine;
	private Integer apnea_free_days_after_caffeine;
	private Integer cummulative_number_of_episodes;
	private Integer cummulative_days_of_caffeine;

	@Column(name = "apnea_comment")
	private String apneaComment;
	
	@Column(name = "clinical_note")
	private String clinicalNote;

	@Transient
	private List<String> orderInvestigationList;

	@Transient
	private List<String> orderInvestigationStringList;

	@Transient
	private RespSupport respSupport;

	@Transient
	private String nursingComments;

	@Transient
	private List<String> apneaPlanList;

	@Column(name = "causeofapnea_other")
	private String causeofapneaOther;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private Timestamp timeofassessment;

	private String associatedevent;

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

	@Column(name = "is_prophylactic_caffeine_enabled", columnDefinition = "bool")
	private Boolean isProphylacticCaffeineEnabled;

	private String icdCauseofApnea;

	@Transient
	private List icdCauseofApneaList;

	private String episodeid;
	
	public SaRespApnea() {
		super();
		this.episodeNumber = 1;
		this.isNewEntry = true;
	}

	public Long getApneaid() {
		this.assessmentMeridiem = false;
		return apneaid;
	}

	public String getIcdCauseofApnea() {
		return icdCauseofApnea;
	}

	public void setIcdCauseofApnea(String icdCauseofApnea) {
		this.icdCauseofApnea = icdCauseofApnea;
	}

	public List getIcdCauseofApneaList() {
		return icdCauseofApneaList;
	}

	public void setIcdCauseofApneaList(List icdCauseofApneaList) {
		this.icdCauseofApneaList = icdCauseofApneaList;
	}

	public void setApneaid(Long apneaid) {
		this.apneaid = apneaid;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
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

	public Boolean getAgeinhours() {
		return ageinhours;
	}

	public void setAgeinhours(Boolean ageinhours) {
		this.ageinhours = ageinhours;
	}

	public Integer getTotalnoofapnea() {
		return totalnoofapnea;
	}

	public void setTotalnoofapnea(Integer totalnoofapnea) {
		this.totalnoofapnea = totalnoofapnea;
	}

	public Integer getNoOfApneaInaDay() {
		return noOfApneaInaDay;
	}

	public void setNoOfApneaInaDay(Integer noOfApneaInaDay) {
		this.noOfApneaInaDay = noOfApneaInaDay;
	}

	public Boolean getCyanosis() {
		return cyanosis;
	}

	public void setCyanosis(Boolean cyanosis) {
		this.cyanosis = cyanosis;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}

	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}

	public String getCaffeineAction() {
		return caffeineAction;
	}

	public void setCaffeineAction(String caffeineAction) {
		this.caffeineAction = caffeineAction;
	}

	public String getCaffeineRoute() {
		return caffeineRoute;
	}

	public void setCaffeineRoute(String caffeineRoute) {
		this.caffeineRoute = caffeineRoute;
	}

	public String getCaffeineBolusDose() {
		return caffeineBolusDose;
	}

	public void setCaffeineBolusDose(String caffeineBolusDose) {
		this.caffeineBolusDose = caffeineBolusDose;
	}

	public String getCaffeineMaintenanceDose() {
		return caffeineMaintenanceDose;
	}

	public void setCaffeineMaintenanceDose(String caffeineMaintenanceDose) {
		this.caffeineMaintenanceDose = caffeineMaintenanceDose;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public String getActionPlanTime() {
		return actionPlanTime;
	}

	public void setActionPlanTime(String actionPlanTime) {
		this.actionPlanTime = actionPlanTime;
	}

	public String getAction_plan_timetype() {
		return action_plan_timetype;
	}

	public void setAction_plan_timetype(String action_plan_timetype) {
		this.action_plan_timetype = action_plan_timetype;
	}

	public String getActionPlanComments() {
		return actionPlanComments;
	}

	public void setActionPlanComments(String actionPlanComments) {
		this.actionPlanComments = actionPlanComments;
	}

	public String getApneaCause() {
		return apneaCause;
	}

	public void setApneaCause(String apneaCause) {
		this.apneaCause = apneaCause;
	}

	public List<String> getApneaCauseList() {
		return apneaCauseList;
	}

	public void setApneaCauseList(List<String> apneaCauseList) {
		this.apneaCauseList = apneaCauseList;
	}

	public Integer getCummulative_apnea_on_caffeine() {
		return cummulative_apnea_on_caffeine;
	}

	public void setCummulative_apnea_on_caffeine(Integer cummulative_apnea_on_caffeine) {
		this.cummulative_apnea_on_caffeine = cummulative_apnea_on_caffeine;
	}

	public Integer getContinuous_apnea_on_caffeine() {
		return continuous_apnea_on_caffeine;
	}

	public void setContinuous_apnea_on_caffeine(Integer continuous_apnea_on_caffeine) {
		this.continuous_apnea_on_caffeine = continuous_apnea_on_caffeine;
	}

	public Integer getApnea_free_days_after_caffeine() {
		return apnea_free_days_after_caffeine;
	}

	public void setApnea_free_days_after_caffeine(Integer apnea_free_days_after_caffeine) {
		this.apnea_free_days_after_caffeine = apnea_free_days_after_caffeine;
	}

	public Integer getCummulative_number_of_episodes() {
		return cummulative_number_of_episodes;
	}

	public void setCummulative_number_of_episodes(Integer cummulative_number_of_episodes) {
		this.cummulative_number_of_episodes = cummulative_number_of_episodes;
	}

	public Integer getCummulative_days_of_caffeine() {
		return cummulative_days_of_caffeine;
	}

	public void setCummulative_days_of_caffeine(Integer cummulative_days_of_caffeine) {
		this.cummulative_days_of_caffeine = cummulative_days_of_caffeine;
	}

	public String getApneaComment() {
		return apneaComment;
	}

	public void setApneaComment(String apneaComment) {
		this.apneaComment = apneaComment;
	}

	public List<String> getOrderInvestigationList() {
		return orderInvestigationList;
	}

	public void setOrderInvestigationList(List<String> orderInvestigationList) {
		this.orderInvestigationList = orderInvestigationList;
	}

	public String getNursingComments() {
		return nursingComments;
	}

	public void setNursingComments(String nursingComments) {
		this.nursingComments = nursingComments;
	}

	public List<String> getApneaPlanList() {
		return apneaPlanList;
	}

	public void setApneaPlanList(List<String> apneaPlanList) {
		this.apneaPlanList = apneaPlanList;
	}

	public String getCauseofapneaOther() {
		return causeofapneaOther;
	}

	public void setCauseofapneaOther(String causeofapneaOther) {
		this.causeofapneaOther = causeofapneaOther;
	}

	public String getClinicalNote() {
		return clinicalNote;
	}

	public void setClinicalNote(String clinicalNote) {
		this.clinicalNote = clinicalNote;
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

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
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

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public Boolean getAssessmentMeridiem() {
		return assessmentMeridiem;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public List<String> getOrderInvestigationStringList() {
		return orderInvestigationStringList;
	}

	public void setOrderInvestigationStringList(List<String> orderInvestigationStringList) {
		this.orderInvestigationStringList = orderInvestigationStringList;
	}

	public RespSupport getRespSupport() {
		return respSupport;
	}

	public void setRespSupport(RespSupport respSupport) {
		this.respSupport = respSupport;
	}

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public Boolean getIsProphylacticCaffeineEnabled() {
		return isProphylacticCaffeineEnabled;
	}

	public void setIsProphylacticCaffeineEnabled(Boolean isProphylacticCaffeineEnabled) {
		this.isProphylacticCaffeineEnabled = isProphylacticCaffeineEnabled;
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
