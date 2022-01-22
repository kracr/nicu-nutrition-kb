package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the patient_device_detail database table.
 * 
 */
//@Entity
@Table(name="patient_device_detail")
@NamedQuery(name="PatientDeviceDetail.findAll", query="SELECT p FROM PatientDeviceDetail p")
public class PatientDeviceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long patientdeviceid;

	private String beddeviceid;

	@Column(columnDefinition="bool")
	private Boolean blood;

	private String bp;

	private Timestamp creationtime;

	@Column(columnDefinition="bool")
	private Boolean ecg;

	@Column(columnDefinition="float4")
	private Float fio2;

	@Column(columnDefinition="float4")
	private Float heartrate;

	private Timestamp modificationtime;

	@Column(columnDefinition="bool")
	private Boolean oxygen;

	@Column(columnDefinition="float4")
	private Float pulserate;

	@Column(columnDefinition="float4")
	private Float resprate;

	@Column(columnDefinition="float4")
	private Float sao2;

	@Column(name="tempaxilla_f")
	private String tempaxillaF;

	@Column(name="tempbed_c")
	private String tempbedC;

	private String uhid;

	public PatientDeviceDetail() {
	}

	public Long getPatientdeviceid() {
		return this.patientdeviceid;
	}

	public void setPatientdeviceid(Long patientdeviceid) {
		this.patientdeviceid = patientdeviceid;
	}

	public String getBeddeviceid() {
		return this.beddeviceid;
	}

	public void setBeddeviceid(String beddeviceid) {
		this.beddeviceid = beddeviceid;
	}

	public Boolean getBlood() {
		return this.blood;
	}

	public void setBlood(Boolean blood) {
		this.blood = blood;
	}

	public String getBp() {
		return this.bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Boolean getEcg() {
		return this.ecg;
	}

	public void setEcg(Boolean ecg) {
		this.ecg = ecg;
	}

	public Float getFio2() {
		return this.fio2;
	}

	public void setFio2(Float fio2) {
		this.fio2 = fio2;
	}

	public Float getHeartrate() {
		return this.heartrate;
	}

	public void setHeartrate(Float heartrate) {
		this.heartrate = heartrate;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getOxygen() {
		return this.oxygen;
	}

	public void setOxygen(Boolean oxygen) {
		this.oxygen = oxygen;
	}

	public Float getPulserate() {
		return this.pulserate;
	}

	public void setPulserate(Float pulserate) {
		this.pulserate = pulserate;
	}

	public Float getResprate() {
		return this.resprate;
	}

	public void setResprate(Float resprate) {
		this.resprate = resprate;
	}

	public Float getSao2() {
		return this.sao2;
	}

	public void setSao2(Float sao2) {
		this.sao2 = sao2;
	}

	public String getTempaxillaF() {
		return this.tempaxillaF;
	}

	public void setTempaxillaF(String tempaxillaF) {
		this.tempaxillaF = tempaxillaF;
	}

	public String getTempbedC() {
		return this.tempbedC;
	}

	public void setTempbedC(String tempbedC) {
		this.tempbedC = tempbedC;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

}