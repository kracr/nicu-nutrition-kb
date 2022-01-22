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
 * The persistent class for the sa_hyperglycemia database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "sa_hyperglycemia")
@NamedQuery(name = "SaHyperglycemia.findAll", query = "SELECT s FROM SaHyperglycemia s")
public class SaHyperglycemia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hyperglycemiaid;

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

	private String bloodsugar;

	@Column(name = "totalhyperglycemia_event")
	private String totalHyperglycemiaEvent;

	@Column(name = "total_symptomatic_event")
	private String totalSymptomaticEvent;

	@Column(name = "total_asymptomatic_event")
	private String totalAsymptomaticEvent;

	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	@Column(name = "urine_output")
	private String urineOutput;

	private String treatmentaction;

	@Column(name = "treatment_other")
	private String treatmentOther;

	@Column(name = "insulin_count")
	private String insulinCount;

	@Column(name = "insulin_route")
	private String insulinRoute;

	private String insulinbolus;

	@Column(name = "insulin_total")
	private String insulinTotal;

	private String insulinfusionrate;

	private String insulinfusionstatus;

	private String girvalue;

	@Column(name = "max_gir")
	private String maxGir;

	@Column(name = "min_gir")
	private String minGir;

	@Column(name = "decreased_gir")
	private String decreasedGir;

	@Column(name = "max_gir_total")
	private String maxGirTotal;

	@Column(name = "min_gir_total")
	private String minGirTotal;

	private String treatmentplan;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	@Column(name = "other_cause")
	private String otherCause;

	private String causeofhyperglycemia;

	private String progressnotes;

	private String associatedevent;

	private String associatedeventid;

	@Transient
	private List<String> treatmentactionList;

	@Transient
	private List<String> causeofhyperglycemiaList;

	public SaHyperglycemia() {
		super();
		this.ageinhoursdays = true;
	}

	public Long getHyperglycemiaid() {
		return hyperglycemiaid;
	}

	public void setHyperglycemiaid(Long hyperglycemiaid) {
		this.hyperglycemiaid = hyperglycemiaid;
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

	public String getBloodsugar() {
		return bloodsugar;
	}

	public void setBloodsugar(String bloodsugar) {
		this.bloodsugar = bloodsugar;
	}

	public String getTotalHyperglycemiaEvent() {
		return totalHyperglycemiaEvent;
	}

	public void setTotalHyperglycemiaEvent(String totalHyperglycemiaEvent) {
		this.totalHyperglycemiaEvent = totalHyperglycemiaEvent;
	}

	public String getTotalSymptomaticEvent() {
		return totalSymptomaticEvent;
	}

	public void setTotalSymptomaticEvent(String totalSymptomaticEvent) {
		this.totalSymptomaticEvent = totalSymptomaticEvent;
	}

	public String getTotalAsymptomaticEvent() {
		return totalAsymptomaticEvent;
	}

	public void setTotalAsymptomaticEvent(String totalAsymptomaticEvent) {
		this.totalAsymptomaticEvent = totalAsymptomaticEvent;
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

	public String getUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(String urineOutput) {
		this.urineOutput = urineOutput;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getInsulinCount() {
		return insulinCount;
	}

	public void setInsulinCount(String insulinCount) {
		this.insulinCount = insulinCount;
	}

	public String getInsulinRoute() {
		return insulinRoute;
	}

	public void setInsulinRoute(String insulinRoute) {
		this.insulinRoute = insulinRoute;
	}

	public String getInsulinbolus() {
		return insulinbolus;
	}

	public void setInsulinbolus(String insulinbolus) {
		this.insulinbolus = insulinbolus;
	}

	public String getInsulinTotal() {
		return insulinTotal;
	}

	public void setInsulinTotal(String insulinTotal) {
		this.insulinTotal = insulinTotal;
	}

	public String getInsulinfusionrate() {
		return insulinfusionrate;
	}

	public void setInsulinfusionrate(String insulinfusionrate) {
		this.insulinfusionrate = insulinfusionrate;
	}

	public String getInsulinfusionstatus() {
		return insulinfusionstatus;
	}

	public void setInsulinfusionstatus(String insulinfusionstatus) {
		this.insulinfusionstatus = insulinfusionstatus;
	}

	public String getGirvalue() {
		return girvalue;
	}

	public void setGirvalue(String girvalue) {
		this.girvalue = girvalue;
	}

	public String getMaxGir() {
		return maxGir;
	}

	public void setMaxGir(String maxGir) {
		this.maxGir = maxGir;
	}

	public String getMinGir() {
		return minGir;
	}

	public void setMinGir(String minGir) {
		this.minGir = minGir;
	}

	public String getDecreasedGir() {
		return decreasedGir;
	}

	public void setDecreasedGir(String decreasedGir) {
		this.decreasedGir = decreasedGir;
	}

	public String getMaxGirTotal() {
		return maxGirTotal;
	}

	public void setMaxGirTotal(String maxGirTotal) {
		this.maxGirTotal = maxGirTotal;
	}

	public String getMinGirTotal() {
		return minGirTotal;
	}

	public void setMinGirTotal(String minGirTotal) {
		this.minGirTotal = minGirTotal;
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

	public String getOtherCause() {
		return otherCause;
	}

	public void setOtherCause(String otherCause) {
		this.otherCause = otherCause;
	}

	public String getCauseofhyperglycemia() {
		return causeofhyperglycemia;
	}

	public void setCauseofhyperglycemia(String causeofhyperglycemia) {
		this.causeofhyperglycemia = causeofhyperglycemia;
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

	public List<String> getCauseofhyperglycemiaList() {
		return causeofhyperglycemiaList;
	}

	public void setCauseofhyperglycemiaList(List<String> causeofhyperglycemiaList) {
		this.causeofhyperglycemiaList = causeofhyperglycemiaList;
	}

	@Override
	public String toString() {
		return "SaHyperglycemia [hyperglycemiaid=" + hyperglycemiaid + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", loggeduser=" + loggeduser
				+ ", metabolicSystemStatus=" + metabolicSystemStatus + ", eventstatus=" + eventstatus + ", ageatonset="
				+ ageatonset + ", ageinhoursdays=" + ageinhoursdays + ", bloodsugar=" + bloodsugar
				+ ", totalHyperglycemiaEvent=" + totalHyperglycemiaEvent + ", totalSymptomaticEvent="
				+ totalSymptomaticEvent + ", totalAsymptomaticEvent=" + totalAsymptomaticEvent + ", symptomaticStatus="
				+ symptomaticStatus + ", symptomaticValue=" + symptomaticValue + ", urineOutput=" + urineOutput
				+ ", treatmentaction=" + treatmentaction + ", treatmentOther=" + treatmentOther + ", insulinRoute="
				+ insulinRoute + ", insulinbolus=" + insulinbolus + ", insulinfusionrate=" + insulinfusionrate
				+ ", insulinfusionstatus=" + insulinfusionstatus + ", girvalue=" + girvalue + ", maxGir=" + maxGir
				+ ", minGir=" + minGir + ", decreasedGir=" + decreasedGir + ", maxGirTotal=" + maxGirTotal
				+ ", minGirTotal=" + minGirTotal + ", treatmentplan=" + treatmentplan + ", reassestime=" + reassestime
				+ ", reassestimeType=" + reassestimeType + ", otherplanComments=" + otherplanComments + ", otherCause="
				+ otherCause + ", causeofhyperglycemia=" + causeofhyperglycemia + ", progressnotes=" + progressnotes
				+ ", associatedevent=" + associatedevent + ", associatedeventid=" + associatedeventid
				+ ", treatmentactionList=" + treatmentactionList + ", causeofhyperglycemiaList="
				+ causeofhyperglycemiaList + "]";
	}

}
