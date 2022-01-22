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
 * The persistent class for the sa_hyperkalemia database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "sa_hyperkalemia")
@NamedQuery(name = "SaHyperkalemia.findAll", query = "SELECT s FROM SaHyperkalemia s")
public class SaHyperkalemia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hyperkalemiaid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	@Column(name = "metabolic_system_status")
	private String metabolicSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	private String serumpotassium;

	private String serumsodium;

	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	@Column(name = "abg_ph")
	private String abgPh;

	@Column(name = "abg_pco2")
	private String abgPco2;

	@Column(name = "abg_hco3")
	private String abgHco3;

	@Column(name = "ecg_twave", columnDefinition = "bool")
	private Boolean ecgTwave;

	@Column(name = "ventricular_tachycardia", columnDefinition = "bool")
	private Boolean ventricularTachycardia;

	private String treatmentaction;

	@Column(name = "calcium_total", columnDefinition = "float4")
	private Float calciumTotal;

	@Column(name = "calcium_volume", columnDefinition = "float4")
	private Float calciumVolume;
	
	@Column(name = "ecg_reversion", columnDefinition = "bool")
	private Boolean ecgReversion;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentplan;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	@Column(name = "causeofhyperkalemia_other")
	private String causeofhyperkalemiaOther;

	private String causeofhyperkalemia;

	private String progressnotes;

	private String associatedevent;

	private String associatedeventid;

	@Transient
	private List<String> treatmentactionList;

	@Transient
	private List<String> causeofhyperkalemiaList;

	public SaHyperkalemia() {
		super();
		this.ageinhoursdays = true;
	}

	public Long getHyperkalemiaid() {
		return hyperkalemiaid;
	}

	public void setHyperkalemiaid(Long hyperkalemiaid) {
		this.hyperkalemiaid = hyperkalemiaid;
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

	public String getMetabolicSystemStatus() {
		return metabolicSystemStatus;
	}

	public void setMetabolicSystemStatus(String metabolicSystemStatus) {
		this.metabolicSystemStatus = metabolicSystemStatus;
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

	public String getSerumpotassium() {
		return serumpotassium;
	}

	public void setSerumpotassium(String serumpotassium) {
		this.serumpotassium = serumpotassium;
	}

	public String getSerumsodium() {
		return serumsodium;
	}

	public void setSerumsodium(String serumsodium) {
		this.serumsodium = serumsodium;
	}

	public Boolean getSymptomaticStatus() {
		return symptomaticStatus;
	}

	public void setSymptomaticStatus(Boolean symptomaticStatus) {
		this.symptomaticStatus = symptomaticStatus;
	}

	public String getSymptomaticValue() {
		return symptomaticValue;
	}

	public void setSymptomaticValue(String symptomaticValue) {
		this.symptomaticValue = symptomaticValue;
	}

	public String getAbgPh() {
		return abgPh;
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

	public String getAbgHco3() {
		return abgHco3;
	}

	public void setAbgHco3(String abgHco3) {
		this.abgHco3 = abgHco3;
	}

	public Boolean getEcgTwave() {
		return ecgTwave;
	}

	public void setEcgTwave(Boolean ecgTwave) {
		this.ecgTwave = ecgTwave;
	}

	public Boolean getVentricularTachycardia() {
		return ventricularTachycardia;
	}

	public void setVentricularTachycardia(Boolean ventricularTachycardia) {
		this.ventricularTachycardia = ventricularTachycardia;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public Float getCalciumTotal() {
		return calciumTotal;
	}

	public void setCalciumTotal(Float calciumTotal) {
		this.calciumTotal = calciumTotal;
	}

	public Float getCalciumVolume() {
		return calciumVolume;
	}

	public void setCalciumVolume(Float calciumVolume) {
		this.calciumVolume = calciumVolume;
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

	public String getCauseofhyperkalemiaOther() {
		return causeofhyperkalemiaOther;
	}

	public void setCauseofhyperkalemiaOther(String causeofhyperkalemiaOther) {
		this.causeofhyperkalemiaOther = causeofhyperkalemiaOther;
	}

	public String getCauseofhyperkalemia() {
		return causeofhyperkalemia;
	}

	public void setCauseofhyperkalemia(String causeofhyperkalemia) {
		this.causeofhyperkalemia = causeofhyperkalemia;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

	public String getAssociatedevent() {
		return associatedevent;
	}

	public void setAssociatedevent(String associatedevent) {
		this.associatedevent = associatedevent;
	}

	public String getAssociatedeventid() {
		return associatedeventid;
	}

	public void setAssociatedeventid(String associatedeventid) {
		this.associatedeventid = associatedeventid;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
	}

	public List<String> getCauseofhyperkalemiaList() {
		return causeofhyperkalemiaList;
	}

	public void setCauseofhyperkalemiaList(List<String> causeofhyperkalemiaList) {
		this.causeofhyperkalemiaList = causeofhyperkalemiaList;
	}

	@Override
	public String toString() {
		return "SaHyperkalemia [hyperkalemiaid=" + hyperkalemiaid + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", loggeduser=" + loggeduser
				+ ", metabolicSystemStatus=" + metabolicSystemStatus + ", eventstatus=" + eventstatus + ", ageatonset="
				+ ageatonset + ", ageinhoursdays=" + ageinhoursdays + ", serumpotassium=" + serumpotassium
				+ ", serumsodium=" + serumsodium + ", symptomaticStatus=" + symptomaticStatus + ", symptomaticValue="
				+ symptomaticValue + ", abgPh=" + abgPh + ", abgPco2=" + abgPco2 + ", abgHco3=" + abgHco3
				+ ", ecgTwave=" + ecgTwave + ", ventricularTachycardia=" + ventricularTachycardia + ", treatmentaction="
				+ treatmentaction + ", treatmentOther=" + treatmentOther + ", treatmentplan=" + treatmentplan
				+ ", reassestime=" + reassestime + ", reassestimeType=" + reassestimeType + ", otherplanComments="
				+ otherplanComments + ", causeofhyperkalemiaOther=" + causeofhyperkalemiaOther
				+ ", causeofhyperkalemia=" + causeofhyperkalemia + ", progressnotes=" + progressnotes
				+ ", associatedevent=" + associatedevent + ", associatedeventid=" + associatedeventid
				+ ", treatmentactionList=" + treatmentactionList + ", causeofhyperkalemiaList="
				+ causeofhyperkalemiaList + "]";
	}

}
