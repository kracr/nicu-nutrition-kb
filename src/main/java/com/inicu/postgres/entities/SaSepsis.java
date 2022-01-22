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

@Entity
@Table(name = "sa_infection_sepsis")
@NamedQuery(name = "SaSepsis.findAll", query = "SELECT s FROM SaSepsis s")
public class SaSepsis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sasepsisid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	@Column(name = "infection_system_status")
	private String infectionSystemStatus;

	private String eventstatus;

	private String ageatonset;

	@Column(columnDefinition = "bool")
	private Boolean ageinhoursdays;

	@Column(columnDefinition = "bool", name = "symptomatic_status")
	private Boolean symptomaticStatus;

	@Column(columnDefinition = "bool", name = "asymptomatic_status")
	private Boolean aSymptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	private String downescoreid;

	@Column(columnDefinition = "bool")
	private Boolean shock;
	
	@Column(name = "sepsis_type")
	private String sepsisType;

	@Column(name = "shock_cft")
	private String shockCft;

	@Column(name = "shock_bp")
	private String shockBp;

	@Column(columnDefinition = "bool", name = "tachycardia_status")
	private Boolean tachycardiaStatus;

	@Column(name = "tachycardia_value")
	private String tachycardiaValue;

	@Column(columnDefinition = "bool", name = "temprature_status")
	private Boolean tempratureStatus;

	@Column(name = "temprature_value")
	private String tempratureValue;

	@Column(columnDefinition = "bool", name = "desaturation_status")
	private Boolean desaturationStatus;

	@Column(name = "desaturation_value")
	private String desaturationValue;

	@Column(columnDefinition = "bool", name = "oxygenreq_status")
	private Boolean oxygenreqStatus;

	@Column(name = "oxygenreq_value")
	private String oxygenreqValue;

	@Column(columnDefinition = "bool", name = "abdominal_status")
	private Boolean abdominalStatus;

	@Column(name = "abdominal_value")
	private String abdominalValue;

	private String treatmentaction;

	@Transient
	private List<String> treatmentactionList;

	@Column(name = "order_investigation")
	private String orderSelectedText;

	@Column(name = "treatment_other")
	private String treatmentOther;

	private String treatmentplan;

	@Transient
	private List<String> treatmentplanList;

	private String reassestime;

	@Column(name = "reassestime_type")
	private String reassestimeType;

	@Column(name = "otherplan_comments")
	private String otherplanComments;

	private String causeofsepsis;

	@Transient
	private List<String> causeofsepsisList;

	@Column(name = "causeofsepsis_other")
	private String causeofsepsisOther;

	private String progressnotes;
	
	//not in use anymore
	//private String associatedevent;

	//private String associatedeventid;

	@Column(name = "temperature_celsius")
	private String temperatureCelsius;

	@Column(columnDefinition = "bool", name = "bonejoint_status")
	private Boolean bonejointStatus;

	@Column(columnDefinition = "bool", name = "meningitis_status")
	private Boolean meningitisStatus;

	@Column(columnDefinition = "bool", name = "ventriculitis_status")
	private Boolean ventriculitisStatus;

	private Integer ageatassesment;

	@Column(columnDefinition = "bool")
	private Boolean isageofassesmentinhours;

	private Timestamp timeofassessment;

	private String riskfactor;

	@Transient
	private List<String> temperatureList;

	@Column(name = "shock_diastbp")
	private String shockDiastbp;

	@Column(name = "shock_systbp")
	private String shockSystbp;

	@Column(columnDefinition = "bool")
	private Boolean poorpulses;

	@Column(columnDefinition = "bool", name = "cold_extremities")
	private Boolean coldExtremities;

	@Column(columnDefinition = "bool", name = "nec_status")
	private Boolean necStatus;

	@Column(name = "nec_stage")
	private String necStage;

	@Column(name = "hypothermia_value")
	private String hypothermiaValue;

	@Column(name = "fever_value")
	private String feverValue;

	@Column(name = "central_peripheral_value")
	private String centralPeripheralValue;

	@Column(name = "riskfactor_other")
	private String riskfactorOther;

	@Transient
	private List<String> riskfactorList;

	@Column(columnDefinition = "bool")
	private Boolean isfeverhypo;

	@Transient
	List<NursingVitalparameter> nursingVitalsList;

	private String heartrate;

	private String bellscoreid;

	// addition of columns to enable manual date/time of assessment
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

	@Column(name = "medication_str")
	private String medicationStr;
	
	@Column(name = "blood_culture_status")
	private String bloodCultureStatus;

	@Column(name = "urine_culture_status")
	private String urineCultureStatus;

	@Column(name = "csf_culture_status")
	private String csfCultureStatus;
	
	@Column(name = "umblical_discharge_status")
	private String umblicalDischargeStatus;

	// Column Added later
	private String sepsis_screen;

	//  Review Response
	@Column(name="rs_lethargy")
	private String lethargy;

	@Column(name="rs_refusal_to_feeds")
	private String refusalToFeeds;

	@Column(name="rs_loose_motions")
	private String looseMotions;

	@Column(name="rs_apnea")
	private String apnea;

	@Column(name="rs_drowsiness")
	private String drowsiness;

	// New added symptoms
	@Column(name="rs_breathing_difficulty")
	private String breathingDifficulty;

	@Column(name="rs_tachypnea")
	private String tachypnea;

	@Column(name="rs_shock")
	private String rsShock;

	@Column(name="rs_fever")
	private String rsFever;

	@Column(name = "rs_others")
	private String rsOthers;

	@Column(name ="rs_respiratory_distress")
	private String rsRespiratoryDistress;

	@Transient
	private Boolean isNewEntry;
	
	@Transient
	private List testResultsSet;
	
	@Transient
	private String sepsis_type;

	private String icdCauseofSepsis;

	@Transient
	private List icdCauseofSepsisList;

	private String episodeid;

    @Transient
    private String earlyLateOnset;

	@Column(columnDefinition = "bool")
	private Boolean iseditednotes;

	public SaSepsis() {
		super();
		//this.isNewEntry=true;
		this.aSymptomaticStatus=false;
		this.episodeNumber = 1;
		this.ageinhoursdays = true;
		this.isageofassesmentinhours = true;
	}

	public String getIcdCauseofSepsis() {
		return icdCauseofSepsis;
	}

	public void setIcdCauseofSepsis(String icdCauseofSepsis) {
		this.icdCauseofSepsis = icdCauseofSepsis;
	}

	public List getIcdCauseofSepsisList() {
		return icdCauseofSepsisList;
	}

	public void setIcdCauseofSepsisList(List icdCauseofSepsisList) {
		this.icdCauseofSepsisList = icdCauseofSepsisList;
	}

	public Boolean getaSymptomaticStatus() {
		return aSymptomaticStatus;
	}

	public void setaSymptomaticStatus(Boolean aSymptomaticStatus) {
		this.aSymptomaticStatus = aSymptomaticStatus;
	}

	public String getLethargy() {
		return lethargy;
	}

	public void setLethargy(String lethargy) {
		this.lethargy = lethargy;
	}

	public String getRefusalToFeeds() {
		return refusalToFeeds;
	}

	public void setRefusalToFeeds(String refusalToFeeds) {
		this.refusalToFeeds = refusalToFeeds;
	}

	public String getLooseMotions() {
		return looseMotions;
	}

	public void setLooseMotions(String looseMotions) {
		this.looseMotions = looseMotions;
	}

	public String getApnea() {
		return apnea;
	}

	public void setApnea(String apnea) {
		this.apnea = apnea;
	}

	public String getDrowsiness() {
		return drowsiness;
	}

	public void setDrowsiness(String drowsiness) {
		this.drowsiness = drowsiness;
	}

	public String getSepsis_screen() {
		return sepsis_screen;
	}

	public void setSepsis_screen(String sepsis_screen) {
		this.sepsis_screen = sepsis_screen;
	}

	public Long getSasepsisid() {
		return sasepsisid;
	}

	public void setSasepsisid(Long sasepsisid) {
		this.sasepsisid = sasepsisid;
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

	public String getInfectionSystemStatus() {
		return infectionSystemStatus;
	}

	public void setInfectionSystemStatus(String infectionSystemStatus) {
		this.infectionSystemStatus = infectionSystemStatus;
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

	public String getDownescoreid() {
		return downescoreid;
	}

	public void setDownescoreid(String downescoreid) {
		this.downescoreid = downescoreid;
	}

	public Boolean getShock() {
		return shock;
	}

	public void setShock(Boolean shock) {
		this.shock = shock;
	}

	public String getShockCft() {
		return shockCft;
	}

	public void setShockCft(String shockCft) {
		this.shockCft = shockCft;
	}

	public String getShockBp() {
		return shockBp;
	}

	public void setShockBp(String shockBp) {
		this.shockBp = shockBp;
	}

	public Boolean getTachycardiaStatus() {
		return tachycardiaStatus;
	}

	public void setTachycardiaStatus(Boolean tachycardiaStatus) {
		this.tachycardiaStatus = tachycardiaStatus;
	}

	public String getTachycardiaValue() {
		return tachycardiaValue;
	}

	public void setTachycardiaValue(String tachycardiaValue) {
		this.tachycardiaValue = tachycardiaValue;
	}

	public Boolean getTempratureStatus() {
		return tempratureStatus;
	}

	public void setTempratureStatus(Boolean tempratureStatus) {
		this.tempratureStatus = tempratureStatus;
	}

	public String getTempratureValue() {
		return tempratureValue;
	}

	public void setTempratureValue(String tempratureValue) {
		this.tempratureValue = tempratureValue;
	}

	public Boolean getDesaturationStatus() {
		return desaturationStatus;
	}

	public void setDesaturationStatus(Boolean desaturationStatus) {
		this.desaturationStatus = desaturationStatus;
	}

	public String getDesaturationValue() {
		return desaturationValue;
	}

	public void setDesaturationValue(String desaturationValue) {
		this.desaturationValue = desaturationValue;
	}

	public Boolean getOxygenreqStatus() {
		return oxygenreqStatus;
	}

	public void setOxygenreqStatus(Boolean oxygenreqStatus) {
		this.oxygenreqStatus = oxygenreqStatus;
	}

	public String getOxygenreqValue() {
		return oxygenreqValue;
	}

	public void setOxygenreqValue(String oxygenreqValue) {
		this.oxygenreqValue = oxygenreqValue;
	}

	public Boolean getAbdominalStatus() {
		return abdominalStatus;
	}

	public void setAbdominalStatus(Boolean abdominalStatus) {
		this.abdominalStatus = abdominalStatus;
	}

	public String getAbdominalValue() {
		return abdominalValue;
	}

	public void setAbdominalValue(String abdominalValue) {
		this.abdominalValue = abdominalValue;
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

	public String getCauseofsepsis() {
		return causeofsepsis;
	}

	public void setCauseofsepsis(String causeofsepsis) {
		this.causeofsepsis = causeofsepsis;
	}

	public List<String> getCauseofsepsisList() {
		return causeofsepsisList;
	}

	public void setCauseofsepsisList(List<String> causeofsepsisList) {
		this.causeofsepsisList = causeofsepsisList;
	}

	public String getCauseofsepsisOther() {
		return causeofsepsisOther;
	}

	public void setCauseofsepsisOther(String causeofsepsisOther) {
		this.causeofsepsisOther = causeofsepsisOther;
	}

	public String getProgressnotes() {
		return progressnotes;
	}

	public void setProgressnotes(String progressnotes) {
		this.progressnotes = progressnotes;
	}

//	public String getAssociatedevent() {
//		return associatedevent;
//	}
//
//	public void setAssociatedevent(String associatedevent) {
//		this.associatedevent = associatedevent;
//	}
//
//	public String getAssociatedeventid() {
//		return associatedeventid;
//	}
//
//	public void setAssociatedeventid(String associatedeventid) {
//		this.associatedeventid = associatedeventid;
//	}

	public String getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(String temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}

	public Boolean getBonejointStatus() {
		return bonejointStatus;
	}

	public void setBonejointStatus(Boolean bonejointStatus) {
		this.bonejointStatus = bonejointStatus;
	}

	public Boolean getMeningitisStatus() {
		return meningitisStatus;
	}

	public void setMeningitisStatus(Boolean meningitisStatus) {
		this.meningitisStatus = meningitisStatus;
	}

	public Boolean getVentriculitisStatus() {
		return ventriculitisStatus;
	}

	public void setVentriculitisStatus(Boolean ventriculitisStatus) {
		this.ventriculitisStatus = ventriculitisStatus;
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

	public String getRiskfactor() {
		return riskfactor;
	}

	public void setRiskfactor(String riskfactor) {
		this.riskfactor = riskfactor;
	}

	public List<String> getRiskfactorList() {
		return riskfactorList;
	}

	public void setRiskfactorList(List<String> riskfactorList) {
		this.riskfactorList = riskfactorList;
	}

	public List<String> getTemperatureList() {
		return temperatureList;
	}

	public void setTemperatureList(List<String> temperatureList) {
		this.temperatureList = temperatureList;
	}

	public String getShockDiastbp() {
		return shockDiastbp;
	}

	public void setShockDiastbp(String shockDiastbp) {
		this.shockDiastbp = shockDiastbp;
	}

	public String getShockSystbp() {
		return shockSystbp;
	}

	public void setShockSystbp(String shockSystbp) {
		this.shockSystbp = shockSystbp;
	}

	public Boolean getPoorpulses() {
		return poorpulses;
	}

	public void setPoorpulses(Boolean poorpulses) {
		this.poorpulses = poorpulses;
	}

	public Boolean getColdExtremities() {
		return coldExtremities;
	}

	public void setColdExtremities(Boolean coldExtremities) {
		this.coldExtremities = coldExtremities;
	}

	public Boolean getNecStatus() {
		return necStatus;
	}

	public void setNecStatus(Boolean necStatus) {
		this.necStatus = necStatus;
	}

	public String getNecStage() {
		return necStage;
	}

	public void setNecStage(String necStage) {
		this.necStage = necStage;
	}

	public String getHypothermiaValue() {
		return hypothermiaValue;
	}

	public void setHypothermiaValue(String hypothermiaValue) {
		this.hypothermiaValue = hypothermiaValue;
	}

	public String getFeverValue() {
		return feverValue;
	}

	public void setFeverValue(String feverValue) {
		this.feverValue = feverValue;
	}

	public String getCentralPeripheralValue() {
		return centralPeripheralValue;
	}

	public void setCentralPeripheralValue(String centralPeripheralValue) {
		this.centralPeripheralValue = centralPeripheralValue;
	}

	public String getRiskfactorOther() {
		return riskfactorOther;
	}

	public void setRiskfactorOther(String riskfactorOther) {
		this.riskfactorOther = riskfactorOther;
	}

	public Boolean getIsfeverhypo() {
		return isfeverhypo;
	}

	public void setIsfeverhypo(Boolean isfeverhypo) {
		this.isfeverhypo = isfeverhypo;
	}

	public List<NursingVitalparameter> getNursingVitalsList() {
		return nursingVitalsList;
	}

	public void setNursingVitalsList(List<NursingVitalparameter> nursingVitalsList) {
		this.nursingVitalsList = nursingVitalsList;
	}

	public String getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}

	public String getBellscoreid() {
		return bellscoreid;
	}

	public void setBellscoreid(String bellscoreid) {
		this.bellscoreid = bellscoreid;
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

	public Boolean getAssessmentMeridiem() {
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

	public String getMedicationStr() {
		return medicationStr;
	}

	public void setMedicationStr(String medicationStr) {
		this.medicationStr = medicationStr;
	}

	public String getBloodCultureStatus() {
		return bloodCultureStatus;
	}

	public void setBloodCultureStatus(String bloodCultureStatus) {
		this.bloodCultureStatus = bloodCultureStatus;
	}

	public String getOrderSelectedText() {
		return orderSelectedText;
	}

	public void setOrderSelectedText(String orderSelectedText) {
		this.orderSelectedText = orderSelectedText;
	}

	public String getSepsisType() {
		return sepsisType;
	}

	public void setSepsisType(String sepsisType) {
		this.sepsisType = sepsisType;
	}

	public String getUrineCultureStatus() {
		return urineCultureStatus;
	}

	public void setUrineCultureStatus(String urineCultureStatus) {
		this.urineCultureStatus = urineCultureStatus;
	}

	public String getCsfCultureStatus() {
		return csfCultureStatus;
	}

	public void setCsfCultureStatus(String csfCultureStatus) {
		this.csfCultureStatus = csfCultureStatus;
	}

	public String getUmblicalDischargeStatus() {
		return umblicalDischargeStatus;
	}

	public void setUmblicalDischargeStatus(String umblicalDischargeStatus) {
		this.umblicalDischargeStatus = umblicalDischargeStatus;
	}

	public List getTestResultsSet() {
		return testResultsSet;
	}

	public void setTestResultsSet(List testResultsSet) {
		this.testResultsSet = testResultsSet;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getSepsis_type() {
		return sepsis_type;
	}

	public void setSepsis_type(String sepsis_type) {
		this.sepsis_type = sepsis_type;
	}

    public String getEarlyLateOnset() {
        return earlyLateOnset;
    }

    public void setEarlyLateOnset(String earlyLateOnset) {
        this.earlyLateOnset = earlyLateOnset;
    }

	public Boolean getIsNewEntry() {
		return isNewEntry;
	}

	public void setIsNewEntry(Boolean isNewEntry) {
		this.isNewEntry = isNewEntry;
	}

	public String getBreathingDifficulty() {
		return breathingDifficulty;
	}

	public void setBreathingDifficulty(String breathingDifficulty) {
		this.breathingDifficulty = breathingDifficulty;
	}

	public String getTachypnea() {
		return tachypnea;
	}

	public void setTachypnea(String tachypnea) {
		this.tachypnea = tachypnea;
	}

	public String getRsShock() {
		return rsShock;
	}

	public void setRsShock(String rsShock) {
		this.rsShock = rsShock;
	}

	public String getRsFever() {
		return rsFever;
	}

	public void setRsFever(String rsFever) {
		this.rsFever = rsFever;
	}

	public String getRsOthers() {
		return rsOthers;
	}

	public void setRsOthers(String rsOthers) {
		this.rsOthers = rsOthers;
	}

	public String getRsRespiratoryDistress() {
		return rsRespiratoryDistress;
	}

	public void setRsRespiratoryDistress(String rsRespiratoryDistress) {
		this.rsRespiratoryDistress = rsRespiratoryDistress;
	}

	public Boolean getIseditednotes() {
		return iseditednotes;
	}

	public void setIseditednotes(Boolean iseditednotes) {
		this.iseditednotes = iseditednotes;
	}
}
