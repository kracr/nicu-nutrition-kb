package com.inicu.postgres.entities;

import javax.persistence.*;

import com.inicu.models.ScoreLevene;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "therapeutic_hypothermia")
@NamedQuery(name = "TherapeuticHypothermia.findAll", query = "SELECT s FROM TherapeuticHypothermia s")
public class TherapeuticHypothermia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "therapeutic_hypothermia_id")
	private Long therapeuticHypothermiaId;

	@Column(name = "gestation_age")
	private String gestationAge;

	@Column(name = "apgar_at")
	private String apgarAt;

	private String asphyxiaid;

	private String uhid;

	private String loggeduser;

	private String indication;

	@Column(name = "is_evidence_of_fetal_distress_present", columnDefinition = "bool")
	private Boolean isEvidenceOfFetalDistressPresent;

	@Column(name = "is_evidence_of_multiorgansystem_present", columnDefinition = "bool")
	private Boolean isEvidenceOfMultiOrganSystemPresent;

	@Column(name = "is_cordblood_or_arterial", columnDefinition = "bool")
	private Boolean isCordBloodOrArterial;

	private String age;

	private String ppv;

	private String ph;

	private String baseExcess;

	private String levenescoreid;

	@Column(name = "device_start_time")
	private Date deviceStarttime;

	@Column(name = "entry_time")
	private Timestamp entryTime;

	@Column(name = "is_therapeutic_hypothermia_active", columnDefinition = "bool")
	private Boolean therapeuticHypothermiaActive;

	@Column(name = "is_device_active", columnDefinition = "bool")
	private Boolean deviceActive;

	private String frequency;
	private String temperature;
	private String hr;
	private String rr;
	private String spo2;
	private String bp;
	private String lactate;

	@Column(name = "lactate_value", columnDefinition = "float4")
	private Float lactateValue;

	@Column(name = "birth_asphyxia", columnDefinition = "bool")
	private boolean birthAsphyxia;

	@Column(name = "birth_weight", columnDefinition = "float4")
	private Float birthWeight;

	@Column(name = "last_entry_date")
	private Timestamp lastEntryDate;

	@Column(name = "device_start_stop_time")
	private Date deviceStartStopTime;

	@Column(columnDefinition = "float4")
	private Float inr;

	@Column(columnDefinition = "float4")
	private Float nirs;

	@Column(name = "left_pupil_size")
	private String leftPupilSize;

	@Column(name = "right_pupil_size")
	private String rightPupilSize;

	@Column(name = "left_pupil_reaction")
	private String leftPupilReaction;

	@Column(name = "right_pupil_reaction")
	private String rightPupilReaction;

	// Transient Variables
	@Transient
	private List<String> indicationList;

	@Transient
	private Boolean isLeveneEnabled;
	
	@Transient
	private ScoreLevene leveneObj;
	

	public TherapeuticHypothermia(){
		super();
		this.leveneObj = new ScoreLevene();
		this.isLeveneEnabled = false;
	}

	public Timestamp getLastEntryDate() {
		return lastEntryDate;
	}

	public void setLastEntryDate(Timestamp lastEntryDate) {
		this.lastEntryDate = lastEntryDate;
	}

	public Float getLactateValue() {
		return lactateValue;
	}

	public void setLactateValue(Float lactateValue) {
		this.lactateValue = lactateValue;
	}

	public boolean isBirthAsphyxia() {
		return birthAsphyxia;
	}

	public void setBirthAsphyxia(boolean birthAsphyxia) {
		this.birthAsphyxia = birthAsphyxia;
	}

	public Float getBirthWeight() {
		return birthWeight;
	}

	public void setBirthWeight(Float birthWeight) {
		this.birthWeight = birthWeight;
	}

	public Boolean getLeveneEnabled() {
		return isLeveneEnabled;
	}

	public void setLeveneEnabled(Boolean leveneEnabled) {
		isLeveneEnabled = leveneEnabled;
	}

	public String getAsphyxiaid() {
		return asphyxiaid;
	}

	public void setAsphyxiaid(String asphyxiaid) {
		this.asphyxiaid = asphyxiaid;
	}

	public Long getTherapeuticHypothermiaId() {
		return therapeuticHypothermiaId;
	}

	public void setTherapeuticHypothermiaId(Long therapeuticHypothermiaId) {
		this.therapeuticHypothermiaId = therapeuticHypothermiaId;
	}

	public String getGestationAge() {
		return gestationAge;
	}

	public void setGestationAge(String gestationAge) {
		this.gestationAge = gestationAge;
	}

	public String getApgarAt() {
		return apgarAt;
	}

	public void setApgarAt(String apgarAt) {
		this.apgarAt = apgarAt;
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

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public Boolean getEvidenceOfFetalDistressPresent() {
		return isEvidenceOfFetalDistressPresent;
	}

	public void setEvidenceOfFetalDistressPresent(Boolean evidenceOfFetalDistressPresent) {
		isEvidenceOfFetalDistressPresent = evidenceOfFetalDistressPresent;
	}

	public Boolean getEvidenceOfMultiOrganSystemPresent() {
		return isEvidenceOfMultiOrganSystemPresent;
	}

	public void setEvidenceOfMultiOrganSystemPresent(Boolean evidenceOfMultiOrganSystemPresent) {
		isEvidenceOfMultiOrganSystemPresent = evidenceOfMultiOrganSystemPresent;
	}

	public Boolean getCordBloodOrArterial() {
		return isCordBloodOrArterial;
	}

	public void setCordBloodOrArterial(Boolean cordBloodOrArterial) {
		isCordBloodOrArterial = cordBloodOrArterial;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPpv() {
		return ppv;
	}

	public void setPpv(String ppv) {
		this.ppv = ppv;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getBaseExcess() {
		return baseExcess;
	}

	public void setBaseExcess(String baseExcess) {
		this.baseExcess = baseExcess;
	}

	public String getLevenescoreid() {
		return levenescoreid;
	}

	public void setLevenescoreid(String levenescoreid) {
		this.levenescoreid = levenescoreid;
	}

	public Date getDeviceStarttime() {
		return deviceStarttime;
	}

	public void setDeviceStarttime(Date deviceStarttime) {
		this.deviceStarttime = deviceStarttime;
	}

	
	

	public Timestamp getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	public Boolean getTherapeuticHypothermiaActive() {
		return therapeuticHypothermiaActive;
	}

	public void setTherapeuticHypothermiaActive(Boolean therapeuticHypothermiaActive) {
		this.therapeuticHypothermiaActive = therapeuticHypothermiaActive;
	}

	public Boolean getDeviceActive() {
		return deviceActive;
	}

	public void setDeviceActive(Boolean deviceActive) {
		this.deviceActive = deviceActive;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
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

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public String getLactate() {
		return lactate;
	}

	public void setLactate(String lactate) {
		this.lactate = lactate;
	}

	public List<String> getIndicationList() {
		return indicationList;
	}

	public void setIndicationList(List<String> indicationList) {
		this.indicationList = indicationList;
	}

	public Float getInr() {
		return inr;
	}

	public void setInr(Float inr) {
		this.inr = inr;
	}

	public Float getNirs() {
		return nirs;
	}

	public void setNirs(Float nirs) {
		this.nirs = nirs;
	}

	public String getLeftPupilSize() {
		return leftPupilSize;
	}

	public void setLeftPupilSize(String leftPupilSize) {
		this.leftPupilSize = leftPupilSize;
	}

	public String getRightPupilSize() {
		return rightPupilSize;
	}

	public void setRightPupilSize(String rightPupilSize) {
		this.rightPupilSize = rightPupilSize;
	}

	public String getLeftPupilReaction() {
		return leftPupilReaction;
	}

	public void setLeftPupilReaction(String leftPupilReaction) {
		this.leftPupilReaction = leftPupilReaction;
	}

	public String getRightPupilReaction() {
		return rightPupilReaction;
	}

	public void setRightPupilReaction(String rightPupilReaction) {
		this.rightPupilReaction = rightPupilReaction;
	}

	public Date getDeviceStartStopTime() {
		return deviceStartStopTime;
	}

	public void setDeviceStartStopTime(Date deviceStartStopTime) {
		this.deviceStartStopTime = deviceStartStopTime;
	}

	public ScoreLevene getLeveneObj() {
		return leveneObj;
	}

	public void setLeveneObj(ScoreLevene leveneObj) {
		this.leveneObj = leveneObj;
	}
	

}