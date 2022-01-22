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
@Table(name = "sa_hypernatremia")
@NamedQuery(name = "SaHypernatremia.findAll", query = "SELECT s FROM SaHypernatremia s")
public class SaHypernatremia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hypernatremiaid;
	
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
	
	@Column(name = "last_serumsodium")
	private String lastSerumsodium;
	
	@Column(name = "max_serumsodium")
	private String maxSerumsodium;
	
	@Column(columnDefinition = "bool", name = "symptomatic_status")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;
	
	private Integer currentweight;
	
	private Integer	weightdifference;
	
	@Column(columnDefinition = "bool")
	private Boolean isgainorloss;

	private String gainpercent;
	
	private String absoluteweightgain;
	
	@Column(columnDefinition = "bool")
	private Boolean isleanweightloss;

	private String losspercent;
	
	private String total;
	
	private Integer weightforcalculation;
	
	private Integer urineoutput;
	
	@Column(name = "urineoutput_duration")
	private Integer urineoutputDuration;
	
	@Column(name = "fulid_rate")
	private String fulidRate;

	private String deflcit;
	
	@Column(name = "insensible_loss")
	private String insensibleLoss;

	private String treatmentaction;
	
	@Transient private List<String> treatmentactionList;
	
	@Column(name = "treatment_other")
	private String treatmentOther;

	private String fluidintakeamount;
	
	private String fluidintakeduration;
	
	private String fluidstrength;
	
	private String treatmentplan;
	  
	private String reassestime;
	
	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;
	
	private String causeofhypernatremia;
	  
	@Transient private List<String> causeofhypernatremiaList;

	private String progressnotes;
	
	private String associatedevent;
	
	private String associatedeventid;
	
	@Column(columnDefinition = "bool")
	private Boolean isbolus;
	
	private String bolusdose;
	
	@Column(name = "bolus_rate")
	private String bolusRate;

	@Column(name = "bolus_strength")
	private String bolusStrength;
	
	@Column(name = "cause_other")
	private String causeOther;
	
	@Column(name = "deficit_day")
	private String deficitDay;
	
	@Column(name = "insensible_loss_day")
	private String insensibleLossDay;
	
	private String totalfluid;
	
	@Column(columnDefinition = "bool")
	private Boolean orderfornurse;

	public SaHypernatremia() {
		super();
		// TODO Auto-generated constructor stub
		this.ageinhoursdays = true;
		this.isleanweightloss = false;
	}

	public Long getHypernatremiaid() {
		return hypernatremiaid;
	}

	public void setHypernatremiaid(Long hypernatremiaid) {
		this.hypernatremiaid = hypernatremiaid;
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

	public String getLastSerumsodium() {
		return lastSerumsodium;
	}

	public void setLastSerumsodium(String lastSerumsodium) {
		this.lastSerumsodium = lastSerumsodium;
	}

	public String getMaxSerumsodium() {
		return maxSerumsodium;
	}

	public void setMaxSerumsodium(String maxSerumsodium) {
		this.maxSerumsodium = maxSerumsodium;
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

	public Integer getCurrentweight() {
		return currentweight;
	}

	public void setCurrentweight(Integer currentweight) {
		this.currentweight = currentweight;
	}

	public Integer getWeightdifference() {
		return weightdifference;
	}

	public void setWeightdifference(Integer weightdifference) {
		this.weightdifference = weightdifference;
	}

	public Boolean getIsgainorloss() {
		return isgainorloss;
	}

	public void setIsgainorloss(Boolean isgainorloss) {
		this.isgainorloss = isgainorloss;
	}

	public String getGainpercent() {
		return gainpercent;
	}

	public void setGainpercent(String gainpercent) {
		this.gainpercent = gainpercent;
	}

	public String getAbsoluteweightgain() {
		return absoluteweightgain;
	}

	public void setAbsoluteweightgain(String absoluteweightgain) {
		this.absoluteweightgain = absoluteweightgain;
	}

	public Boolean getIsleanweightloss() {
		return isleanweightloss;
	}

	public void setIsleanweightloss(Boolean isleanweightloss) {
		this.isleanweightloss = isleanweightloss;
	}

	public String getLosspercent() {
		return losspercent;
	}

	public void setLosspercent(String losspercent) {
		this.losspercent = losspercent;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Integer getWeightforcalculation() {
		return weightforcalculation;
	}

	public void setWeightforcalculation(Integer weightforcalculation) {
		this.weightforcalculation = weightforcalculation;
	}

	public Integer getUrineoutput() {
		return urineoutput;
	}

	public void setUrineoutput(Integer urineoutput) {
		this.urineoutput = urineoutput;
	}

	public Integer getUrineoutputDuration() {
		return urineoutputDuration;
	}

	public void setUrineoutputDuration(Integer urineoutputDuration) {
		this.urineoutputDuration = urineoutputDuration;
	}

	public String getFulidRate() {
		return fulidRate;
	}

	public void setFulidRate(String fulidRate) {
		this.fulidRate = fulidRate;
	}

	public String getDeflcit() {
		return deflcit;
	}

	public void setDeflcit(String deflcit) {
		this.deflcit = deflcit;
	}

	public String getInsensibleLoss() {
		return insensibleLoss;
	}

	public void setInsensibleLoss(String insensibleLoss) {
		this.insensibleLoss = insensibleLoss;
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

	public String getFluidintakeamount() {
		return fluidintakeamount;
	}

	public void setFluidintakeamount(String fluidintakeamount) {
		this.fluidintakeamount = fluidintakeamount;
	}

	public String getFluidintakeduration() {
		return fluidintakeduration;
	}

	public void setFluidintakeduration(String fluidintakeduration) {
		this.fluidintakeduration = fluidintakeduration;
	}

	public String getFluidstrength() {
		return fluidstrength;
	}

	public void setFluidstrength(String fluidstrength) {
		this.fluidstrength = fluidstrength;
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

	public String getCauseofhypernatremia() {
		return causeofhypernatremia;
	}

	public void setCauseofhypernatremia(String causeofhypernatremia) {
		this.causeofhypernatremia = causeofhypernatremia;
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

	public List<String> getCauseofhypernatremiaList() {
		return causeofhypernatremiaList;
	}

	public void setCauseofhypernatremiaList(List<String> causeofhypernatremiaList) {
		this.causeofhypernatremiaList = causeofhypernatremiaList;
	}

	public Boolean getIsbolus() {
		return isbolus;
	}

	public void setIsbolus(Boolean isbolus) {
		this.isbolus = isbolus;
	}

	public String getBolusdose() {
		return bolusdose;
	}

	public void setBolusdose(String bolusdose) {
		this.bolusdose = bolusdose;
	}

	public String getBolusRate() {
		return bolusRate;
	}

	public void setBolusRate(String bolusRate) {
		this.bolusRate = bolusRate;
	}

	public String getBolusStrength() {
		return bolusStrength;
	}

	public void setBolusStrength(String bolusStrength) {
		this.bolusStrength = bolusStrength;
	}

	public String getCauseOther() {
		return causeOther;
	}

	public void setCauseOther(String causeOther) {
		this.causeOther = causeOther;
	}

	public String getDeficitDay() {
		return deficitDay;
	}

	public void setDeficitDay(String deficitDay) {
		this.deficitDay = deficitDay;
	}

	public String getInsensibleLossDay() {
		return insensibleLossDay;
	}

	public void setInsensibleLossDay(String insensibleLossDay) {
		this.insensibleLossDay = insensibleLossDay;
	}

	public String getTotalfluid() {
		return totalfluid;
	}

	public void setTotalfluid(String totalfluid) {
		this.totalfluid = totalfluid;
	}

	public Boolean getOrderfornurse() {
		return orderfornurse;
	}

	public void setOrderfornurse(Boolean orderfornurse) {
		this.orderfornurse = orderfornurse;
	}
}
