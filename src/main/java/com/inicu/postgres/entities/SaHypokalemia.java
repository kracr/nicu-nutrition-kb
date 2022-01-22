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

/**
 * The persistent class for the sa_hypokalemia database table.
 * 
 */
@Entity
@Table(name = "sa_hypokalemia")
@NamedQuery(name = "SaHypokalemia.findAll", query = "SELECT s FROM SaHypokalemia s")
public class SaHypokalemia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hypokalemiaid;

	@Column(name = "abg_hco3")
	private String abgHco3;

	@Column(name = "abg_ph")
	private String abgPh;

	@Column(name = "abg_pco2")
	private String abgPco2;
	
	@Column(name = "potassium_total", columnDefinition = "float4")
	private Float potassiumTotal;

	@Column(name = "potassium_volume", columnDefinition = "float4")
	private Float potassiumVolume;
	
	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	private String associatedevent;

	private String associatedeventid;

	private String causeofhypokalemia;

	@Column(name = "causeofhypokalemia_other")
	private String causeofhypokalemiaOther;

	private Timestamp creationtime;

	@Column(name = "ecg_uwave", columnDefinition = "bool")
	private Boolean ecgUwave;

	private String serumpotassium;

	private String eventstatus;

	private String loggeduser;

	@Column(name = "metabolic_system_status")
	private String metabolicSystemStatus;

	private Timestamp modificationtime;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	private String progressnotes;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentaction;

	private String treatmentplan;

	private String uhid;

	@Transient
	private List<String> treatmentactionList;

	@Transient
	private List<String> causeofhypokalemiaList;

	public SaHypokalemia() {
		super();
		this.ageinhoursdays = true;
	}

	public Long getHypokalemiaid() {
		return this.hypokalemiaid;
	}

	public void setHypokalemiaid(Long hypokalemiaid) {
		this.hypokalemiaid = hypokalemiaid;
	}

	public String getAbgHco3() {
		return this.abgHco3;
	}

	public void setAbgHco3(String abgHco3) {
		this.abgHco3 = abgHco3;
	}

	public String getAbgPh() {
		return this.abgPh;
	}

	public void setAbgPh(String abgPh) {
		this.abgPh = abgPh;
	}

	public String getAbgPco2() {
		return abgPco2;
	}

	public void setAbgPco2(String abgPco2) {
		this.abgPco2 = abgPco2;
	}

	public String getSerumpotassium() {
		return serumpotassium;
	}

	public void setSerumpotassium(String serumpotassium) {
		this.serumpotassium = serumpotassium;
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

	public String getAssociatedevent() {
		return this.associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public String getAssociatedeventid() {
		return this.associatedeventid;
	}

	public void setAssociatedeventid(String associatedeventid) {
		this.associatedeventid = associatedeventid;
	}

	public String getCauseofhypokalemia() {
		return this.causeofhypokalemia;
	}

	public void setCauseofhypokalemia(String causeofhypokalemia) {
		this.causeofhypokalemia = causeofhypokalemia;
	}

	public String getCauseofhypokalemiaOther() {
		return this.causeofhypokalemiaOther;
	}

	public void setCauseofhypokalemiaOther(String causeofhypokalemiaOther) {
		this.causeofhypokalemiaOther = causeofhypokalemiaOther;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getEcgUwave() {
		return this.ecgUwave;
	}

	public void setEcgUwave(Boolean ecgUwave) {
		this.ecgUwave = ecgUwave;
	}

	public String getEventstatus() {
		return this.eventstatus;
	}

	public void setEventstatus(String eventstatus) {
		this.eventstatus = eventstatus;
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

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOtherplanComments() {
		return this.otherplanComments;
	}

	public void setOtherplanComments(String otherplanComments) {
		this.otherplanComments = otherplanComments;
	}

	public String getProgressnotes() {
		return this.progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getReassestime() {
		return this.reassestime;
	}

	public void setReassestime(String reassestime) {
		this.reassestime = reassestime;
	}

	public String getReassestimeType() {
		return this.reassestimeType;
	}

	public void setReassestimeType(String reassestimeType) {
		this.reassestimeType = reassestimeType;
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

	public String getTreatmentOther() {
		return this.treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getTreatmentaction() {
		return this.treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getTreatmentplan() {
		return this.treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public List<String> getCauseofhypokalemiaList() {
		return causeofhypokalemiaList;
	}

	public void setCauseofhypokalemiaList(List<String> causeofhypokalemiaList) {
		this.causeofhypokalemiaList = causeofhypokalemiaList;
	}

}