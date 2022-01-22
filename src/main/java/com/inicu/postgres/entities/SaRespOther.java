package com.inicu.postgres.entities;
/**
 * The persistent class for the sa_resp_others database table.
 * 
 * @author Sourabh Verma
 */

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
@Table(name = "sa_resp_others")
@NamedQuery(name = "SaRespOther.findAll", query = "SELECT s FROM SaRespOther s")
public class SaRespOther implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long respotherid;

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

	@Column(columnDefinition = "bool")
	private Boolean pulmonaryhaemorrhage;

	@Column(columnDefinition = "bool")
	private Boolean pulmonaryemphysema;

	@Column(columnDefinition = "bool")
	private Boolean congenitallungsmalformations;

	private String miscellaneous;

	@Column(name = "pulmonaryhaemorrhage_comments")
	private String pulmonaryhaemorrhageComments;

	@Column(name = "pulmonaryemphysema_comments")
	private String pulmonaryemphysemaComments;

	@Column(name = "congenitallungsmalformations_comments")
	private String congenitallungsmalformationsComments;

	@Column(name = "miscellaneous_comments")
	private String miscellaneousComments;

	@Transient
	private List<String> treatmentActionList;

	private String treatmentaction;

	private String sufactantname;

	private String sufactantdose;

	@Column(columnDefinition = "bool")
	private Boolean isinsuredone;

	@Column(name = "action_after_surfactant")
	private String actionAfterSurfactant;

	private String treatmentplan;

	private String reassestime;

	private String reassesstime_type;

	private String othercomments;

	private String causeofdisease;

	private String progressnotes;
	
	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;
	
	private Timestamp timeofassessment;
	
	@Column(name="surfactant_dose_ml")
	private String surfactantDoseMl;
	
	@Column(name="treatment_other")
	private String treatmentOther;
	
	private String associatedevent;

	private String episodeid;

	@Transient
	private List<String> orderInvestigationList;
	
	public List<String> getOrderInvestigationList() {
		return orderInvestigationList;
	}

	public void setOrderInvestigationList(List<String> orderInvestigationList) {
		this.orderInvestigationList = orderInvestigationList;
	}

	public Long getRespotherid() {
		return respotherid;
	}

	public void setRespotherid(Long respotherid) {
		this.respotherid = respotherid;
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

	public Boolean getPulmonaryhaemorrhage() {
		return pulmonaryhaemorrhage;
	}

	public void setPulmonaryhaemorrhage(Boolean pulmonaryhaemorrhage) {
		this.pulmonaryhaemorrhage = pulmonaryhaemorrhage;
	}

	public Boolean getPulmonaryemphysema() {
		return pulmonaryemphysema;
	}

	public void setPulmonaryemphysema(Boolean pulmonaryemphysema) {
		this.pulmonaryemphysema = pulmonaryemphysema;
	}

	public Boolean getCongenitallungsmalformations() {
		return congenitallungsmalformations;
	}

	public void setCongenitallungsmalformations(Boolean congenitallungsmalformations) {
		this.congenitallungsmalformations = congenitallungsmalformations;
	}

	public String getMiscellaneous() {
		return miscellaneous;
	}

	public void setMiscellaneous(String miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public String getPulmonaryhaemorrhageComments() {
		return pulmonaryhaemorrhageComments;
	}

	public void setPulmonaryhaemorrhageComments(String pulmonaryhaemorrhageComments) {
		this.pulmonaryhaemorrhageComments = pulmonaryhaemorrhageComments;
	}

	public String getPulmonaryemphysemaComments() {
		return pulmonaryemphysemaComments;
	}

	public void setPulmonaryemphysemaComments(String pulmonaryemphysemaComments) {
		this.pulmonaryemphysemaComments = pulmonaryemphysemaComments;
	}

	public String getCongenitallungsmalformationsComments() {
		return congenitallungsmalformationsComments;
	}

	public void setCongenitallungsmalformationsComments(String congenitallungsmalformationsComments) {
		this.congenitallungsmalformationsComments = congenitallungsmalformationsComments;
	}

	public String getMiscellaneousComments() {
		return miscellaneousComments;
	}

	public void setMiscellaneousComments(String miscellaneousComments) {
		this.miscellaneousComments = miscellaneousComments;
	}

	public List<String> getTreatmentActionList() {
		return treatmentActionList;
	}

	public void setTreatmentActionList(List<String> treatmentActionList) {
		this.treatmentActionList = treatmentActionList;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getSufactantname() {
		return sufactantname;
	}

	public void setSufactantname(String sufactantname) {
		this.sufactantname = sufactantname;
	}

	public String getSufactantdose() {
		return sufactantdose;
	}

	public void setSufactantdose(String sufactantdose) {
		this.sufactantdose = sufactantdose;
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

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public String getReassestime() {
		return reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReassesstime_type() {
		return reassesstime_type;
	}

	public void setReassesstime_type(String reassesstime_type) {
		this.reassesstime_type = reassesstime_type;
	}

	public String getOthercomments() {
		return othercomments;
	}

	public void setOthercomments(String othercomments) {
		this.othercomments = othercomments;
	}

	public String getCauseofdisease() {
		return causeofdisease;
	}

	public void setCauseofdisease(String causeofdisease) {
		this.causeofdisease = causeofdisease;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
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

	public String getSurfactantDoseMl() {
		return surfactantDoseMl;
	}

	public void setSurfactantDoseMl(String surfactantDoseMl) {
		this.surfactantDoseMl = surfactantDoseMl;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	@Override
	public String toString() {
		return "SaRespOther [respotherid=" + respotherid + ", creationtime=" + creationtime + ", uhid=" + uhid
				+ ", loggeduser=" + loggeduser + ", respSystemStatus=" + respSystemStatus + ", eventstatus="
				+ eventstatus + ", ageatonset=" + ageatonset + ", ageinhours=" + ageinhours + ", pulmonaryhaemorrhage="
				+ pulmonaryhaemorrhage + ", pulmonaryemphysema=" + pulmonaryemphysema
				+ ", congenitallungsmalformations=" + congenitallungsmalformations + ", miscellaneous=" + miscellaneous
				+ ", pulmonaryhaemorrhageComments=" + pulmonaryhaemorrhageComments + ", pulmonaryemphysemaComments="
				+ pulmonaryemphysemaComments + ", congenitallungsmalformationsComments="
				+ congenitallungsmalformationsComments + ", miscellaneousComments=" + miscellaneousComments
				+ ", treatmentActionList=" + treatmentActionList + ", treatmentaction=" + treatmentaction
				+ ", sufactantname=" + sufactantname + ", sufactantdose=" + sufactantdose + ", isinsuredone="
				+ isinsuredone + ", actionAfterSurfactant=" + actionAfterSurfactant + ", treatmentplan=" + treatmentplan
				+ ", reassestime=" + reassestime + ", reassesstime_type=" + reassesstime_type + ", othercomments="
				+ othercomments + ", causeofdisease=" + causeofdisease + ", progressnotes=" + progressnotes
				+ ", orderInvestigationList=" + orderInvestigationList + "]";
	}

}
