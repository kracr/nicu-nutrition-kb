package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "outcomes_notes")
@NamedQuery(name = "OutcomeNotes.findAll", query = "SELECT s FROM OutcomeNotes s")
public class OutcomeNotes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long outcomesId;

	private String condition;
	
	
	private String spo2;
	
	private String uhid;

	@Column(columnDefinition ="Float4")
	private Float heartrate;
	
	@Column(columnDefinition ="Float4")
	private Float meanBp;
	
	@Column(columnDefinition ="Float4")
	private Float rr;
	
	@Column(columnDefinition ="Float4")
	private Float temp;
	
	@Column(name = "respiratory_status")
	private String respiratoryStatus;
	
	@Column(name = "growth_velocity", columnDefinition ="Float4")
	private Float growthVelocity;
	
	private String reason;
	
	@Column(name="transp_mode")
	private String modeOfTransportation;
	
	private String progressNotes;
	
	private String outcome;
	
	private String mode;
	
	private String type;
	
	@Column(name = "frequency")
	private String frequency;
	
	@Column(name = "volume_per_feed" , columnDefinition ="Float4")
	private Float volumePerFeed;
	
	@Column(name = "active_assessment")
	private String activeAssessments;
	
	private String fluidType;
	
	private String actualDextroseConc;
	
	@Column(columnDefinition = "bool")
	private Boolean enteralGiven;
	
	@Column(columnDefinition = "bool")
	private Boolean parenteralGiven;
	
	@Column(columnDefinition = "bool")
	private Boolean adlibGiven;
	
	@Column(name = "human_milk")
	private String humanMilk;
	
	@Column(name = "formula_milk")
	private String formulaMilk;
	
	@Column(name="progress_note")
	private String progressNote;
	
	private String event;
	
	private String ppv;
	
	@Column(name = "death_type")
	private String deathType;
	
	@Column (name = "chest_compression" , columnDefinition="bool")
	private Boolean chestCompression;
	
	@Column(columnDefinition = "Float4")
	private Float duration;
	
	@Column(name = "death_progress_notes")
	private String deathprogressNotes;
	
	
	
	

	public String getProgressNote() {
		return progressNote;
	}

	public void setProgressNote(String progressNote) {
		this.progressNote = progressNote;
	}

	public Long getOutcomesId() {
		return outcomesId;
	}

	public void setOutcomesId(Long outcomesId) {
		this.outcomesId = outcomesId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(Float heartrate) {
		this.heartrate = heartrate;
	}

	public Float getMeanBp() {
		return meanBp;
	}

	public void setMeanBp(Float meanBp) {
		this.meanBp = meanBp;
	}

	public Float getRr() {
		return rr;
	}

	public void setRr(Float rr) {
		this.rr = rr;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public String getRespiratoryStatus() {
		return respiratoryStatus;
	}

	public void setRespiratoryStatus(String respiratoryStatus) {
		this.respiratoryStatus = respiratoryStatus;
	}

	public Float getGrowthVelocity() {
		return growthVelocity;
	}

	public void setGrowthVelocity(Float growthVelocity) {
		this.growthVelocity = growthVelocity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getModeOfTransportation() {
		return modeOfTransportation;
	}

	public void setModeOfTransportation(String modeOfTransportation) {
		this.modeOfTransportation = modeOfTransportation;
	}

	public String getProgressNotes() {
		return progressNotes;
	}

	public void setProgressNotes(String progressNotes) {
		this.progressNotes = progressNotes;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Float getVolumePerFeed() {
		return volumePerFeed;
	}

	public void setVolumePerFeed(Float volumePerFeed) {
		this.volumePerFeed = volumePerFeed;
	}

	public String getActiveAssessments() {
		return activeAssessments;
	}

	public void setActiveAssessments(String activeAssessments) {
		this.activeAssessments = activeAssessments;
	}

	public String getFluidType() {
		return fluidType;
	}

	public void setFluidType(String fluidType) {
		this.fluidType = fluidType;
	}

	public String getActualDextroseConc() {
		return actualDextroseConc;
	}

	public void setActualDextroseConc(String actualDextroseConc) {
		this.actualDextroseConc = actualDextroseConc;
	}

	public Boolean getEnteralGiven() {
		return enteralGiven;
	}

	public void setEnteralGiven(Boolean enteralGiven) {
		this.enteralGiven = enteralGiven;
	}

	public Boolean getParenteralGiven() {
		return parenteralGiven;
	}

	public void setParenteralGiven(Boolean parenteralGiven) {
		this.parenteralGiven = parenteralGiven;
	}

	public Boolean getAdlibGiven() {
		return adlibGiven;
	}

	public void setAdlibGiven(Boolean adlibGiven) {
		this.adlibGiven = adlibGiven;
	}

	public String getHumanMilk() {
		return humanMilk;
	}

	public void setHumanMilk(String humanMilk) {
		this.humanMilk = humanMilk;
	}

	public String getFormulaMilk() {
		return formulaMilk;
	}

	public void setFormulaMilk(String formulaMilk) {
		this.formulaMilk = formulaMilk;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getPpv() {
		return ppv;
	}

	public void setPpv(String ppv) {
		this.ppv = ppv;
	}

	public String getDeathType() {
		return deathType;
	}

	public void setDeathType(String deathType) {
		this.deathType = deathType;
	}

	public Boolean getChestCompression() {
		return chestCompression;
	}

	public void setChestCompression(Boolean chestCompression) {
		this.chestCompression = chestCompression;
	}

	
	public String getDeathprogressNotes() {
		return deathprogressNotes;
	}

	public void setDeathprogressNotes(String deathprogressNotes) {
		this.deathprogressNotes = deathprogressNotes;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}
	
	
	
	
	
	


}
