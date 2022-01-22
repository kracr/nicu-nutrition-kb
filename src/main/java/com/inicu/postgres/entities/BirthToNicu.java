package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the birth_to_nicu database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "birth_to_nicu")
@NamedQuery(name = "BirthToNicu.findAll", query = "SELECT b FROM BirthToNicu b")
public class BirthToNicu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "birth_to_nicu_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long birthToNicuId;

	@Column(name = "apgar_fivemin")
	private Integer apgarFivemin;

	@Column(name = "apgar_onemin")
	private Integer apgarOnemin;

	@Column(name = "apgar_tenmin")
	private Integer apgarTenmin;

	@Column(columnDefinition = "bool")
	private Boolean apnea;

	@Column(name = "apnea_ageatonset")
	private Integer apneaAgeatonset;

	@Column(columnDefinition = "bool")
	private Boolean apnea_ageinhoursdays;

	@Column(name = "apnea_no_episode")
	private Integer apneaNoEpisode;

	@Column(columnDefinition = "bool", name = "cong_malform")
	private Boolean congMalform;

	@Column(name = "cong_malform_details")
	private String congMalformDetails;

	private Timestamp creationtime;

	@Column(columnDefinition = "bool", name = "cried_immediately")
	private Boolean criedImmediately;

	@Column(columnDefinition = "bool")
	private Boolean encephalopathy;

	private String episodeid;

	@Column(columnDefinition = "bool", name = "exchange_transfusion")
	private Boolean exchangeTransfusion;

	@Column(columnDefinition = "bool", name = "initial_step")
	private Boolean initialStep;

	@Column(name = "inout_patient_status")
	private String inoutPatientStatus;

	@Column(name = "investigation_report")
	private String investigationReport;

	@Column(columnDefinition = "bool")
	private Boolean ivig;

	@Column(name = "ivig_dose")
	private String ivigDose;

	@Column(name = "ivig_no_dose")
	private String ivigNoDose;

	@Column(columnDefinition = "bool")
	private Boolean jaundice;

	@Column(name = "jaundice_ageatonset")
	private Integer jaundiceAgeatonset;

	@Column(columnDefinition = "bool")
	private Boolean jaundice_ageinhoursdays;

	@Column(columnDefinition = "float4")
	private Float tbcvalue;

	@Column(name = "jaundice_cause")
	private String jaundiceCause;

	@Column(name = "levene_grade")
	private String leveneGrade;

	private String leveneid;

	private String loggeduser;

	@Column(columnDefinition = "bool")
	private Boolean meconium;

	@Column(name = "meconium_hour")
	private Integer meconiumHour;

	private Timestamp modificationtime;

	@Column(name = "other_details")
	private String otherDetails;

	@Column(columnDefinition = "bool", name = "passed_urine")
	private Boolean passedUrine;

	@Column(columnDefinition = "bool")
	private Boolean phototherapy;

	@Column(name = "phototherapy_duration")
	private Integer phototherapyDuration;
	
	@Column(name = "phototherapy_duration_unit")
	private String phototherapyDurationUnit;

	@Column(columnDefinition = "bool")
	private Boolean rds;

	@Column(name = "rds_ageatonset")
	private Integer rdsAgeatonset;

	@Column(columnDefinition = "bool")
	private Boolean rds_ageinhoursdays;

	@Column(columnDefinition = "bool")
	private Boolean respsupport;

	@Column(columnDefinition = "bool")
	private Boolean resuscitation;

	@Column(columnDefinition = "bool", name = "resuscitation_chesttube_compression")
	private Boolean resuscitationChesttubeCompression;

	@Column(name = "resuscitation_chesttube_compression_duration")
	private String resuscitationChesttubeCompressionDuration;

	@Column(columnDefinition = "bool", name = "resuscitation_medication")
	private Boolean resuscitationMedication;

	@Column(name = "resuscitation_medication_details")
	private String resuscitationMedicationDetails;

	@Column(columnDefinition = "bool", name = "resuscitation_o2")
	private Boolean resuscitationO2;

	@Column(name = "resuscitation_o2_duration")
	private String resuscitationO2Duration;

	@Column(columnDefinition = "bool", name = "resuscitation_ppv")
	private Boolean resuscitationPpv;

	@Column(name = "resuscitation_ppv_duration")
	private String resuscitationPpvDuration;

	@Column(columnDefinition = "bool")
	private Boolean seizures;

	@Column(name = "seizures_ageatonset")
	private Integer seizuresAgeatonset;

	@Column(columnDefinition = "bool")
	private Boolean seizures_ageinhoursdays;

	@Column(name = "seizures_no_episode")
	private Integer seizuresNoEpisode;

	@Column(name = "seizures_type")
	private String seizuresType;

	@Column(name = "transport_backuprate")
	private String transportBackuprate;

	@Column(name = "transport_breathrate")
	private String transportBreathrate;

	@Column(name = "transport_et")
	private String transportEt;

	@Column(name = "transport_fio2")
	private String transportFio2;

	@Column(name = "transport_flowrate")
	private String transportFlowrate;

	@Column(name = "transport_it")
	private String transportIt;

	@Column(name = "transport_map")
	private String transportMap;

	@Column(name = "transport_mech_vent_type")
	private String transportMechVentType;

	@Column(name = "transport_mv")
	private String transportMv;

	@Column(name = "transport_peep")
	private String transportPeep;

	@Column(name = "transport_pip")
	private String transportPip;

	@Column(name = "transport_tv")
	private String transportTv;
	
	@Column(name = "transport_tv_in_ml" ,columnDefinition = "Float4" )
	private Float transportTvInMl;

	@Column(name = "transported_in")
	private String transportedIn;

	@Column(name = "transported_type")
	private String transportedType;

	private String uhid;

	@Column(name = "urine_output")
	private String urineOutput;

	@Column(name = "duration_o2_time")
	private String durationO2Time;

	@Column(name = "ppv_time")
	private String ppvTime;

	@Column(name = "chest_comp_time")
	private String chestCompTime;
	
	@Column(name = "type_of_cry")
	private String typeOfCry;
	
	@Column(name = "controlledventtype")
	private String controlledventtype;
	
	private String hr;
	
	private String rr;
	
	private String systolicBp;
	
	private String diastolicBp;
	
	private String meanBp;
	
	private String peripheralTemp;
	
	private String centralTemp;
	
	private String spo2;
	
	private String transportVolumeGuarantee;
	
	private String tsvolumeGaurantee;
	
	private String tsPressureSupport;
	
	@Column(name="surfactant_name")
	private String sufactantname;
	
	@Column(name="surfactant_dose")
	private String surfactantDose;
	
	@Column(name="surfactant_dose_in_ml")
	private String surfactantDoseMl;
	
	private String site;
	
	private String size;

	private String fixation;
	
	@Column(name="delayed_clamping")
	private String delayedClamping;
	
	@Column(name="chord_duration")
	private Integer chordDuration;
	
	private String duration_unit_chord;
	
	@Column(columnDefinition = "bool")
	private Boolean apgarAvailable;

	public BirthToNicu() {
		super();
	}

	public String getDelayedClamping() {
		return delayedClamping;
	}



	public void setDelayedClamping(String delayedClamping) {
		this.delayedClamping = delayedClamping;
	}



	public Integer getChordDuration() {
		return chordDuration;
	}



	public void setChordDuration(Integer chordDuration) {
		this.chordDuration = chordDuration;
	}



	public String getDuration_unit_chord() {
		return duration_unit_chord;
	}



	public void setDuration_unit_chord(String duration_unit_chord) {
		this.duration_unit_chord = duration_unit_chord;
	}



	public Long getBirthToNicuId() {
		return birthToNicuId;
	}

	public void setBirthToNicuId(Long birthToNicuId) {
		this.birthToNicuId = birthToNicuId;
	}

	public Integer getApgarFivemin() {
		return apgarFivemin;
	}

	public void setApgarFivemin(Integer apgarFivemin) {
		this.apgarFivemin = apgarFivemin;
	}

	public Integer getApgarOnemin() {
		return apgarOnemin;
	}

	public void setApgarOnemin(Integer apgarOnemin) {
		this.apgarOnemin = apgarOnemin;
	}

	public Integer getApgarTenmin() {
		return apgarTenmin;
	}

	public void setApgarTenmin(Integer apgarTenmin) {
		this.apgarTenmin = apgarTenmin;
	}

	public Boolean getApnea() {
		return apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Integer getApneaAgeatonset() {
		return apneaAgeatonset;
	}

	public void setApneaAgeatonset(Integer apneaAgeatonset) {
		this.apneaAgeatonset = apneaAgeatonset;
	}

	public Boolean getApnea_ageinhoursdays() {
		return apnea_ageinhoursdays;
	}

	public void setApnea_ageinhoursdays(Boolean apnea_ageinhoursdays) {
		this.apnea_ageinhoursdays = apnea_ageinhoursdays;
	}

	public Integer getApneaNoEpisode() {
		return apneaNoEpisode;
	}

	public void setApneaNoEpisode(Integer apneaNoEpisode) {
		this.apneaNoEpisode = apneaNoEpisode;
	}

	public Boolean getCongMalform() {
		return congMalform;
	}

	public void setCongMalform(Boolean congMalform) {
		this.congMalform = congMalform;
	}

	public String getCongMalformDetails() {
		return congMalformDetails;
	}

	public void setCongMalformDetails(String congMalformDetails) {
		this.congMalformDetails = congMalformDetails;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getCriedImmediately() {
		return criedImmediately;
	}

	public void setCriedImmediately(Boolean criedImmediately) {
		this.criedImmediately = criedImmediately;
	}

	public Boolean getEncephalopathy() {
		return encephalopathy;
	}

	public void setEncephalopathy(Boolean encephalopathy) {
		this.encephalopathy = encephalopathy;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public Boolean getExchangeTransfusion() {
		return exchangeTransfusion;
	}

	public void setExchangeTransfusion(Boolean exchangeTransfusion) {
		this.exchangeTransfusion = exchangeTransfusion;
	}

	public Boolean getInitialStep() {
		return initialStep;
	}

	public void setInitialStep(Boolean initialStep) {
		this.initialStep = initialStep;
	}

	public String getInoutPatientStatus() {
		return inoutPatientStatus;
	}

	public void setInoutPatientStatus(String inoutPatientStatus) {
		this.inoutPatientStatus = inoutPatientStatus;
	}

	public String getInvestigationReport() {
		return investigationReport;
	}

	public void setInvestigationReport(String investigationReport) {
		this.investigationReport = investigationReport;
	}

	public Boolean getIvig() {
		return ivig;
	}

	public void setIvig(Boolean ivig) {
		this.ivig = ivig;
	}

	public String getIvigDose() {
		return ivigDose;
	}

	public void setIvigDose(String ivigDose) {
		this.ivigDose = ivigDose;
	}

	public String getIvigNoDose() {
		return ivigNoDose;
	}

	public void setIvigNoDose(String ivigNoDose) {
		this.ivigNoDose = ivigNoDose;
	}

	public Boolean getJaundice() {
		return jaundice;
	}

	public void setJaundice(Boolean jaundice) {
		this.jaundice = jaundice;
	}

	public Integer getJaundiceAgeatonset() {
		return jaundiceAgeatonset;
	}

	public void setJaundiceAgeatonset(Integer jaundiceAgeatonset) {
		this.jaundiceAgeatonset = jaundiceAgeatonset;
	}

	public Boolean getJaundice_ageinhoursdays() {
		return jaundice_ageinhoursdays;
	}

	public void setJaundice_ageinhoursdays(Boolean jaundice_ageinhoursdays) {
		this.jaundice_ageinhoursdays = jaundice_ageinhoursdays;
	}

	public Float getTbcvalue() {
		return tbcvalue;
	}

	public void setTbcvalue(Float tbcvalue) {
		this.tbcvalue = tbcvalue;
	}

	public String getJaundiceCause() {
		return jaundiceCause;
	}

	public void setJaundiceCause(String jaundiceCause) {
		this.jaundiceCause = jaundiceCause;
	}

	public String getLeveneGrade() {
		return leveneGrade;
	}

	public void setLeveneGrade(String leveneGrade) {
		this.leveneGrade = leveneGrade;
	}

	public String getLeveneid() {
		return leveneid;
	}

	public void setLeveneid(String leveneid) {
		this.leveneid = leveneid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Boolean getMeconium() {
		return meconium;
	}

	public void setMeconium(Boolean meconium) {
		this.meconium = meconium;
	}

	public Integer getMeconiumHour() {
		return meconiumHour;
	}

	public void setMeconiumHour(Integer meconiumHour) {
		this.meconiumHour = meconiumHour;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public Boolean getPassedUrine() {
		return passedUrine;
	}

	public void setPassedUrine(Boolean passedUrine) {
		this.passedUrine = passedUrine;
	}

	public Boolean getPhototherapy() {
		return phototherapy;
	}

	public void setPhototherapy(Boolean phototherapy) {
		this.phototherapy = phototherapy;
	}

	public Integer getPhototherapyDuration() {
		return phototherapyDuration;
	}
	
	public String getPhototherapyDurationUnit() {
		return phototherapyDurationUnit;
	}

	public void setPhototherapyDuration(Integer phototherapyDuration) {
		this.phototherapyDuration = phototherapyDuration;
	}
	public void setPhototherapyDurationUnit(String phototherapyDurationUnit) {
		this.phototherapyDurationUnit = phototherapyDurationUnit;
	}

	public Boolean getRds() {
		return rds;
	}

	public void setRds(Boolean rds) {
		this.rds = rds;
	}

	public Integer getRdsAgeatonset() {
		return rdsAgeatonset;
	}

	public void setRdsAgeatonset(Integer rdsAgeatonset) {
		this.rdsAgeatonset = rdsAgeatonset;
	}

	public Boolean getRds_ageinhoursdays() {
		return rds_ageinhoursdays;
	}

	public void setRds_ageinhoursdays(Boolean rds_ageinhoursdays) {
		this.rds_ageinhoursdays = rds_ageinhoursdays;
	}

	public Boolean getRespsupport() {
		return respsupport;
	}

	public void setRespsupport(Boolean respsupport) {
		this.respsupport = respsupport;
	}

	public Boolean getResuscitation() {
		return resuscitation;
	}

	public void setResuscitation(Boolean resuscitation) {
		this.resuscitation = resuscitation;
	}

	public Boolean getResuscitationChesttubeCompression() {
		return resuscitationChesttubeCompression;
	}

	public void setResuscitationChesttubeCompression(Boolean resuscitationChesttubeCompression) {
		this.resuscitationChesttubeCompression = resuscitationChesttubeCompression;
	}

	public String getResuscitationChesttubeCompressionDuration() {
		return resuscitationChesttubeCompressionDuration;
	}

	public void setResuscitationChesttubeCompressionDuration(String resuscitationChesttubeCompressionDuration) {
		this.resuscitationChesttubeCompressionDuration = resuscitationChesttubeCompressionDuration;
	}

	public Boolean getResuscitationMedication() {
		return resuscitationMedication;
	}

	public void setResuscitationMedication(Boolean resuscitationMedication) {
		this.resuscitationMedication = resuscitationMedication;
	}

	public String getResuscitationMedicationDetails() {
		return resuscitationMedicationDetails;
	}

	public void setResuscitationMedicationDetails(String resuscitationMedicationDetails) {
		this.resuscitationMedicationDetails = resuscitationMedicationDetails;
	}

	public Boolean getResuscitationO2() {
		return resuscitationO2;
	}

	public void setResuscitationO2(Boolean resuscitationO2) {
		this.resuscitationO2 = resuscitationO2;
	}

	public String getResuscitationO2Duration() {
		return resuscitationO2Duration;
	}

	public void setResuscitationO2Duration(String resuscitationO2Duration) {
		this.resuscitationO2Duration = resuscitationO2Duration;
	}

	public Boolean getResuscitationPpv() {
		return resuscitationPpv;
	}

	public void setResuscitationPpv(Boolean resuscitationPpv) {
		this.resuscitationPpv = resuscitationPpv;
	}

	public String getResuscitationPpvDuration() {
		return resuscitationPpvDuration;
	}

	public void setResuscitationPpvDuration(String resuscitationPpvDuration) {
		this.resuscitationPpvDuration = resuscitationPpvDuration;
	}

	public Boolean getSeizures() {
		return seizures;
	}

	public void setSeizures(Boolean seizures) {
		this.seizures = seizures;
	}

	public Integer getSeizuresAgeatonset() {
		return seizuresAgeatonset;
	}

	public void setSeizuresAgeatonset(Integer seizuresAgeatonset) {
		this.seizuresAgeatonset = seizuresAgeatonset;
	}

	public Boolean getSeizures_ageinhoursdays() {
		return seizures_ageinhoursdays;
	}

	public void setSeizures_ageinhoursdays(Boolean seizures_ageinhoursdays) {
		this.seizures_ageinhoursdays = seizures_ageinhoursdays;
	}

	public Integer getSeizuresNoEpisode() {
		return seizuresNoEpisode;
	}

	public void setSeizuresNoEpisode(Integer seizuresNoEpisode) {
		this.seizuresNoEpisode = seizuresNoEpisode;
	}

	public String getSeizuresType() {
		return seizuresType;
	}

	public void setSeizuresType(String seizuresType) {
		this.seizuresType = seizuresType;
	}

	public String getTransportBackuprate() {
		return transportBackuprate;
	}

	public void setTransportBackuprate(String transportBackuprate) {
		this.transportBackuprate = transportBackuprate;
	}

	public String getTransportBreathrate() {
		return transportBreathrate;
	}

	public void setTransportBreathrate(String transportBreathrate) {
		this.transportBreathrate = transportBreathrate;
	}

	public String getTransportEt() {
		return transportEt;
	}

	public void setTransportEt(String transportEt) {
		this.transportEt = transportEt;
	}

	public String getTransportFio2() {
		return transportFio2;
	}

	public void setTransportFio2(String transportFio2) {
		this.transportFio2 = transportFio2;
	}

	public String getTransportFlowrate() {
		return transportFlowrate;
	}

	public void setTransportFlowrate(String transportFlowrate) {
		this.transportFlowrate = transportFlowrate;
	}

	public String getTransportIt() {
		return transportIt;
	}

	public void setTransportIt(String transportIt) {
		this.transportIt = transportIt;
	}

	public String getTransportMap() {
		return transportMap;
	}

	public void setTransportMap(String transportMap) {
		this.transportMap = transportMap;
	}

	public String getTransportMechVentType() {
		return transportMechVentType;
	}

	public void setTransportMechVentType(String transportMechVentType) {
		this.transportMechVentType = transportMechVentType;
	}

	public String getTransportMv() {
		return transportMv;
	}

	public void setTransportMv(String transportMv) {
		this.transportMv = transportMv;
	}

	public String getTransportPeep() {
		return transportPeep;
	}

	public void setTransportPeep(String transportPeep) {
		this.transportPeep = transportPeep;
	}

	public String getTransportPip() {
		return transportPip;
	}

	public void setTransportPip(String transportPip) {
		this.transportPip = transportPip;
	}

	public String getTransportTv() {
		return transportTv;
	}

	public void setTransportTv(String transportTv) {
		this.transportTv = transportTv;
	}

	public String getTransportedIn() {
		return transportedIn;
	}

	public void setTransportedIn(String transportedIn) {
		this.transportedIn = transportedIn;
	}

	public String getTransportedType() {
		return transportedType;
	}

	public void setTransportedType(String transportedType) {
		this.transportedType = transportedType;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getUrineOutput() {
		return urineOutput;
	}

	public void setUrineOutput(String urineOutput) {
		this.urineOutput = urineOutput;
	}

	public String getDurationO2Time() {
		return durationO2Time;
	}

	public void setDurationO2Time(String durationO2Time) {
		this.durationO2Time = durationO2Time;
	}

	public String getPpvTime() {
		return ppvTime;
	}

	public void setPpvTime(String ppvTime) {
		this.ppvTime = ppvTime;
	}

	public String getChestCompTime() {
		return chestCompTime;
	}

	public void setChestCompTime(String chestCompTime) {
		this.chestCompTime = chestCompTime;
	}

	public String getTypeOfCry() {
		return typeOfCry;
	}

	public void setTypeOfCry(String typeOfCry) {
		this.typeOfCry = typeOfCry;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getRr() {
		return rr;
	}

	public void setRr(String rr) {
		this.rr = rr;
	}

	public String getSystolicBp() {
		return systolicBp;
	}

	public void setSystolicBp(String systolicBp) {
		this.systolicBp = systolicBp;
	}

	public String getDiastolicBp() {
		return diastolicBp;
	}

	public void setDiastolicBp(String diastolicBp) {
		this.diastolicBp = diastolicBp;
	}

	public String getMeanBp() {
		return meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public String getPeripheralTemp() {
		return peripheralTemp;
	}

	public void setPeripheralTemp(String peripheralTemp) {
		this.peripheralTemp = peripheralTemp;
	}

	public String getCentralTemp() {
		return centralTemp;
	}

	public void setCentralTemp(String centralTemp) {
		this.centralTemp = centralTemp;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getControlledventtype() {
		return controlledventtype;
	}

	public void setControlledventtype(String controlledventtype) {
		this.controlledventtype = controlledventtype;
	}

	public Float getTransportTvInMl() {
		return transportTvInMl;
	}

	public void setTransportTvInMl(Float transportTvInMl) {
		this.transportTvInMl = transportTvInMl;
	}

	public String getTransportVolumeGuarantee() {
		return transportVolumeGuarantee;
	}

	public void setTransportVolumeGuarantee(String transportVolumeGuarantee) {
		this.transportVolumeGuarantee = transportVolumeGuarantee;
	}

	public String getTsvolumeGaurantee() {
		return tsvolumeGaurantee;
	}

	public void setTsvolumeGaurantee(String tsvolumeGaurantee) {
		this.tsvolumeGaurantee = tsvolumeGaurantee;
	}

	public String getTsPressureSupport() {
		return tsPressureSupport;
	}

	public void setTsPressureSupport(String tsPressureSupport) {
		this.tsPressureSupport = tsPressureSupport;
	}

	public Boolean getApgarAvailable() {
		return apgarAvailable;
	}

	public void setApgarAvailable(Boolean apgarAvailable) {
		this.apgarAvailable = apgarAvailable;
	}

	public String getSufactantname() {
		return sufactantname;
	}

	public void setSufactantname(String sufactantname) {
		this.sufactantname = sufactantname;
	}

	public String getSurfactantDose() {
		return surfactantDose;
	}

	public void setSurfactantDose(String surfactantDose) {
		this.surfactantDose = surfactantDose;
	}

	public String getSurfactantDoseMl() {
		return surfactantDoseMl;
	}

	public void setSurfactantDoseMl(String surfactantDoseMl) {
		this.surfactantDoseMl = surfactantDoseMl;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFixation() {
		return fixation;
	}

	public void setFixation(String fixation) {
		this.fixation = fixation;
	}
}