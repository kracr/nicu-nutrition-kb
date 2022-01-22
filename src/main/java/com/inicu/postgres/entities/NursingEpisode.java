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
 * The persistent class for the nursing_episode database table.
 * 
 * @author Sourabh Verma
 */
@Entity
@Table(name = "nursing_episode")
@NamedQuery(name = "NursingEpisode.findAll", query = "SELECT s FROM NursingEpisode s")
public class NursingEpisode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long episodeid;

	@Column(columnDefinition = "timestamp with time zone")
	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String loggeduser;

	@Column(name = "nn_vitalparameter_time")
	private String nnVitalparameterTime;

	@Column(columnDefinition = "bool")
	private Boolean apnea;

	@Column(columnDefinition = "bool")
	private Boolean bradycardia;

	@Column(columnDefinition = "bool")
	private Boolean tachycardia;

	private String hr;

	@Column(columnDefinition = "bool")
	private Boolean disaturation;
	
	@Column(columnDefinition = "bool")
	private Boolean desaturation;

	private String spo2;
	
	private Integer desaturationSpo2;

	private String recovery;

	@Column(columnDefinition = "bool")
	private Boolean seizures;

	@Column(columnDefinition = "bool", name = "symptomatic_status")
	private Boolean symptomaticStatus;

	@Column(name = "symptomatic_value")
	private String symptomaticValue;

	@Column(name = "seizure_duration")
	private String seizureDuration;
	
	@Column(name = "apnea_duration")
	private String apneaDuration;

	@Column(name = "seizure_type")
	private String seizureType;
	
	private String others;
	
	private String duration_unit_seizure;
	
	private String duration_unit_apnea;

	@Column(name = "recovery_desaturation")
	private String recoveryDesaturation;
	
	@Column(name="events_done")
	private String eventsDone;

	public String getRecoveryDesaturation() {
		return recoveryDesaturation;
	}

	public void setRecoveryDesaturation(String recoveryDesaturation) {
		this.recoveryDesaturation = recoveryDesaturation;
	}

	public NursingEpisode() {
		super();
	}

	public Long getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(Long episodeid) {
		this.episodeid = episodeid;
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

	public String getNnVitalparameterTime() {
		return nnVitalparameterTime;
	}

	public void setNnVitalparameterTime(String nnVitalparameterTime) {
		this.nnVitalparameterTime = nnVitalparameterTime;
	}

	public Boolean getApnea() {
		return apnea;
	}

	public void setApnea(Boolean apnea) {
		this.apnea = apnea;
	}

	public Boolean getBradycardia() {
		return bradycardia;
	}

	public void setBradycardia(Boolean bradycardia) {
		this.bradycardia = bradycardia;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public Boolean getDisaturation() {
		return disaturation;
	}

	public void setDisaturation(Boolean disaturation) {
		this.disaturation = disaturation;
	}

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

	public Boolean getSeizures() {
		return seizures;
	}

	public void setSeizures(Boolean seizures) {
		this.seizures = seizures;
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

	public Boolean getTachycardia() {
		return tachycardia;
	}

	public void setTachycardia(Boolean tachycardia) {
		this.tachycardia = tachycardia;
	}

	public String getSeizureDuration() {
		return seizureDuration;
	}

	public void setSeizureDuration(String seizureDuration) {
		this.seizureDuration = seizureDuration;
	}

	public String getSeizureType() {
		return seizureType;
	}

	public void setSeizureType(String seizureType) {
		this.seizureType = seizureType;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getApneaDuration() {
		return apneaDuration;
	}

	public String getDuration_unit_seizure() {
		return duration_unit_seizure;
	}

	public String getDuration_unit_apnea() {
		return duration_unit_apnea;
	}

	public void setApneaDuration(String apneaDuration) {
		this.apneaDuration = apneaDuration;
	}

	public void setDuration_unit_seizure(String duration_unit_seizure) {
		this.duration_unit_seizure = duration_unit_seizure;
	}

	public void setDuration_unit_apnea(String duration_unit_apnea) {
		this.duration_unit_apnea = duration_unit_apnea;
	}

	public Boolean getDesaturation() {
		return desaturation;
	}

	public void setDesaturation(Boolean desaturation) {
		this.desaturation = desaturation;
	}

	public Integer getDesaturationSpo2() {
		return desaturationSpo2;
	}

	public void setDesaturationSpo2(Integer desaturationSpo2) {
		this.desaturationSpo2 = desaturationSpo2;
	}

	public String getEventsDone() {
		return eventsDone;
	}

	public void setEventsDone(String eventsDone) {
		this.eventsDone = eventsDone;
	}
	
}
