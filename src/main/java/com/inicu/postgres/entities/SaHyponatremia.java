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
 * The persistent class for the sa_hyponatremia database table.
 * 
 * @author Vedaant
 */
@Entity
@Table(name = "sa_hyponatremia")
@NamedQuery(name = "SaHyponatremia.findAll", query = "SELECT s FROM SaHyponatremia s")
public class SaHyponatremia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hyponatremiaid;

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
	
	private String serumsodium;
	
	@Column(name = "minimumserum_12hrs")
	private String minimumSerum12Hrs;
	
	@Column(name = "last_serumsodium")
	private String lastSerumSodium;
	
	@Column(name = "symptomatic_status", columnDefinition = "bool")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;
	
	@Column(columnDefinition="float4")
	private float currentweight;
	
	@Column(columnDefinition="float4")
	private float weightdifference;
	
	@Column(name = "isgainorloss", columnDefinition = "bool")
	private Boolean isgainorloss;
	
	@Column(name = "isdiuresisdone", columnDefinition ="bool")
	private Boolean isdiuresisdone;
	
	@Column(name = "urine_output")
	private String urineOutput;
	
	private String duration;
	
	private String diuresistype;
	
	@Column(name = "ishistoryofgi", columnDefinition = "bool")
	private Boolean isHistoryofGi;
	
	@Column(name = "treatment_other")
	private String treatmentOther;

//	@Column(name = "historyofgiloss")
//	private String historyOfGiLoss;
	
	private String treatmentaction;

	@Column(name = "totalfluidintake")
	private String totalFluidIntake;
	
	@Column(name = "additional_sodium")
	private String additionalSodium;
	
	// additional Sodium duration
	@Column(name = "additional_na_duration")
	private String additionalNaDuration; 
	
	@Column(name = "na_duration_timetype")
	private String naDurationTimetype;
	
	private String treatmentplan;
	
	@Transient private List<String> treatmentactionList;
	
	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	@Transient private List<String> causeofhyponatremiaList;
	
	private String causeofhyponatremia;

	private String progressnotes;

	private String associatedevent;

	private String associatedeventid;
	
	// added after latest meeting, to multiple gi loss types for single visit
	@Column(name = "giloss_24Hrs")
	private String historyOfGiLoss24Hrs;
	
	@Column(name = "giloss_lastvisit")
	private String historyOfGiLossLastVisit;
	
	@Column(name = "giloss_gastricflag", columnDefinition ="bool")
	private Boolean gilossGastricFlag;
	
	@Column(name = "giloss_rectumflag", columnDefinition ="bool")
	private Boolean gilossRectumFlag;
	
	@Column(name = "giloss_stomaflag", columnDefinition ="bool")
	private Boolean gilossStomaFlag;
	
	// below 3 to hold values in current visit
	@Column(name = "giloss_gastric")
	private String gilossGastric;
	
	@Column(name = "giloss_stoma")
	private String gilossStoma;
	
	@Column(name = "giloss_rectum")
	private String gilossRectum;
	
	@Column(name = "giloss_total")
	private String gilossTotal;

	public SaHyponatremia() {
		super();
		this.ageinhoursdays = true; // hours 
		this.gilossGastricFlag = false;
		this.gilossRectumFlag = false;
		this.gilossStomaFlag = false;
		this.gilossTotal = "0";
	}

	public Long getHyponatremiaid() {
		return hyponatremiaid;
	}

	public void setHyponatremiaid(Long hyponatremiaid) {
		this.hyponatremiaid = hyponatremiaid;
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

	public String getSerumsodium() {
		return serumsodium;
	}

	public void setSerumsodium(String serumsodium) {
		this.serumsodium = serumsodium;
	}

	public String getMinimumSerum12Hrs() {
		return minimumSerum12Hrs;
	}

	public void setMinimumSerum12Hrs(String minimumSerum12Hrs) {
		this.minimumSerum12Hrs = minimumSerum12Hrs;
	}

	public String getLastSerumSodium() {
		return lastSerumSodium;
	}

	public void setLastSerumSodium(String lastSerumSodium) {
		this.lastSerumSodium = lastSerumSodium;
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

	public float getCurrentweight() {
		return currentweight;
	}

	public void setCurrentweight(float currentweight) {
		this.currentweight = currentweight;
	}

	public float getWeightdifference() {
		return weightdifference;
	}

	public void setWeightdifference(float weightdifference) {
		this.weightdifference = weightdifference;
	}

	public Boolean getIsgainorloss() {
		return isgainorloss;
	}

	public void setIsgainorloss(Boolean isgainorloss) {
		this.isgainorloss = isgainorloss;
	}

	public Boolean getIsdiuresisdone() {
		return isdiuresisdone;
	}

	public void setIsdiuresisdone(Boolean isdiuresisdone) {
		this.isdiuresisdone = isdiuresisdone;
	}

	public String getUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(String urineOutput) {
		this.urineOutput = urineOutput;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDiuresistype() {
		return diuresistype;
	}

	public void setDiuresistype(String diuresistype) {
		this.diuresistype = diuresistype;
	}

	public Boolean getIsHistoryofGi() {
		return isHistoryofGi;
	}

	public void setIsHistoryofGi(Boolean isHistoryofGi) {
		this.isHistoryofGi = isHistoryofGi;
	}

	public String getTreatmentOther() {
		return treatmentOther;
	}

	public void setTreatmentOther(String treatmentOther) {
		this.treatmentOther = treatmentOther;
	}

	public String getTreatmentaction() {
		return treatmentaction;
	}

	public void setTreatmentaction(String treatmentaction) {
		this.treatmentaction = treatmentaction;
	}

	public String getTotalFluidIntake() {
		return totalFluidIntake;
	}

	public void setTotalFluidIntake(String totalFluidIntake) {
		this.totalFluidIntake = totalFluidIntake;
	}

	public String getAdditionalSodium() {
		return additionalSodium;
	}

	public void setAdditionalSodium(String additionalSodium) {
		this.additionalSodium = additionalSodium;
	}

	public String getAdditionalNaDuration() {
		return additionalNaDuration;
	}

	public void setAdditionalNaDuration(String additionalNaDuration) {
		this.additionalNaDuration = additionalNaDuration;
	}

	public String getNaDurationTimetype() {
		return naDurationTimetype;
	}

	public void setNaDurationTimetype(String naDurationTimetype) {
		this.naDurationTimetype = naDurationTimetype;
	}

	public String getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(String treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public List<String> getTreatmentactionList() {
		return treatmentactionList;
	}

	public void setTreatmentactionList(List<String> treatmentactionList) {
		this.treatmentactionList = treatmentactionList;
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

	public List<String> getCauseofhyponatremiaList() {
		return causeofhyponatremiaList;
	}

	public void setCauseofhyponatremiaList(List<String> causeofhyponatremiaList) {
		this.causeofhyponatremiaList = causeofhyponatremiaList;
	}

	public String getCauseofhyponatremia() {
		return causeofhyponatremia;
	}

	public void setCauseofhyponatremia(String causeofhyponatremia) {
		this.causeofhyponatremia = causeofhyponatremia;
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

	public String getHistoryOfGiLoss24Hrs() {
		return historyOfGiLoss24Hrs;
	}

	public void setHistoryOfGiLoss24Hrs(String historyOfGiLoss24Hrs) {
		this.historyOfGiLoss24Hrs = historyOfGiLoss24Hrs;
	}

	public String getHistoryOfGiLossLastVisit() {
		return historyOfGiLossLastVisit;
	}

	public void setHistoryOfGiLossLastVisit(String historyOfGiLossLastVisit) {
		this.historyOfGiLossLastVisit = historyOfGiLossLastVisit;
	}

	public Boolean getGilossGastricFlag() {
		return gilossGastricFlag;
	}

	public void setGilossGastricFlag(Boolean gilossGastricFlag) {
		this.gilossGastricFlag = gilossGastricFlag;
	}

	public Boolean getGilossRectumFlag() {
		return gilossRectumFlag;
	}

	public void setGilossRectumFlag(Boolean gilossRectumFlag) {
		this.gilossRectumFlag = gilossRectumFlag;
	}

	public Boolean getGilossStomaFlag() {
		return gilossStomaFlag;
	}

	public void setGilossStomaFlag(Boolean gilossStomaFlag) {
		this.gilossStomaFlag = gilossStomaFlag;
	}

	public String getGilossGastric() {
		return gilossGastric;
	}

	public void setGilossGastric(String gilossGastric) {
		this.gilossGastric = gilossGastric;
	}

	public String getGilossStoma() {
		return gilossStoma;
	}

	public void setGilossStoma(String gilossStoma) {
		this.gilossStoma = gilossStoma;
	}

	public String getGilossRectum() {
		return gilossRectum;
	}

	public void setGilossRectum(String gilossRectum) {
		this.gilossRectum = gilossRectum;
	}

	public String getGilossTotal() {
		return gilossTotal;
	}

	public void setGilossTotal(String gilossTotal) {
		this.gilossTotal = gilossTotal;
	}

	
	
	

	
}
