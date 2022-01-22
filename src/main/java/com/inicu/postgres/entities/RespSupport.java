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
import javax.persistence.Transient;

@Entity
@Table(name = "respsupport")
@NamedQuery(name = "RespSupport.findAll", query = "SELECT r FROM RespSupport r")
public class RespSupport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long respsupportid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String eventid;

	private String eventname;

	private String uhid;

	@Column(name = "rs_vent_type")
	private String rsVentType;

	@Column(name = "rs_flow_rate")
	private String rsFlowRate;

	@Column(name = "rs_fio2")
	private String rsFio2;

	@Column(name = "rs_map")
	private String rsMap;

	@Column(name = "rs_mech_vent_type")
	private String rsMechVentType;
	
	@Column(name = "rs_controlled_vent_type")
	private String rsControlledVentType;

	@Column(name = "rs_peep")
	private String rsPeep;

	@Column(name = "rs_pip")
	private String rsPip;

	@Column(name = "rs_it")
	private String rsIt;

	@Column(name = "rs_et")
	private String rsEt;

	@Column(name = "rs_tv")
	private String rsTv;
	
	@Column(name = "rs_tv_in_ml" , columnDefinition="Float4")
	private Float rsTvInMl;

	@Column(name = "rs_mv")
	private String rsMv;

	@Column(name = "rs_amplitude")
	private String rsAmplitude;

	@Column(name = "rs_frequency")
	private String rsFrequency;

	// below ones new added, rate is not same as flow rate
	@Column(name = "rs_rate")
	private String rsRate;

	@Column(name = "rs_isendotracheal", columnDefinition = "bool")
	private Boolean rsIsEndotracheal;

	@Column(name = "rs_tubesize")
	private String rsTubeSize;

	@Column(name = "rs_fixation")
	private String rsFixation;

	@Column(name = "rs_spo2")
	private String rsSpo2;

	@Column(name = "rs_pco2")
	private String rspCO2;

	@Column(name = "rs_backuprate")
	private String rsBackuprate;

	@Column(columnDefinition = "bool")
	private Boolean isactive;
	
	@Column(name = "ph")
	private String ph;

	private String co2;

	private String hco3;

	private String be;
	
	private String dco2;
	
	private String iTime;
	
	@Column(name = "rs_control_type")
	private String rsControlType;
	
	@Column(name = "rs_cpap_type")
	private String rsCpapType;

	@Column(name = "bpd_resp_note")
	private String bpdRespNote;
	
	@Transient
	private String bloodGasDateTime;
	
	@Column(name = "rs_ptv_mode")
	private String rsPtvMode;
	
	@Column(name = "volume_gaurantee")
	private String volumeGaurantee;
	
	@Column(name = "volume_gauranteeml")
	private String volumeGauranteeml;
	
	@Column(name = "rs_support_type")
	private String rsSupportType;
	
	@Column(name = "po2")
	private String po2;
	
	@Column(name = "rs_volume_guarantee")
	private String rsVolumeGuarantee;
	
	
	@Column(name = "rs_pressure_support")
	private String rsPressureSupport;
	
	private Integer counterBpdRds;

	@Column(name = "is_eventid_updated", columnDefinition = "bool")
	private Boolean isEventidUpdated;
	
	@Transient
	private Timestamp endtime;
	
	@Transient
	private Long differenceinmins;

	//All the Parameter Coming From Draeger
	private String c;
	private String r;
	private String tc;
	private String c20;
	private String trigger;
	private String rvr;
	private String vtim;
	private String vthf;
	private String leak;
	private String spont;
	private String mvim;
	private String reason;
	
	public String getRsRate() {
		return rsRate;
	}

	public void setRsRate(String rsRate) {
		this.rsRate = rsRate;
	}

	public Boolean getRsIsEndotracheal() {
		return rsIsEndotracheal;
	}

	public void setRsIsEndotracheal(Boolean rsIsEndotracheal) {
		this.rsIsEndotracheal = rsIsEndotracheal;
	}

	public String getRsTubeSize() {
		return rsTubeSize;
	}

	public void setRsTubeSize(String rsTubeSize) {
		this.rsTubeSize = rsTubeSize;
	}

	public String getRsFixation() {
		return rsFixation;
	}

	public void setRsFixation(String rsFixation) {
		this.rsFixation = rsFixation;
	}

	public String getRsSpo2() {
		return rsSpo2;
	}

	public void setRsSpo2(String rsSpo2) {
		this.rsSpo2 = rsSpo2;
	}

	public String getRspCO2() {
		return rspCO2;
	}

	public void setRspCO2(String rspCO2) {
		this.rspCO2 = rspCO2;
	}

	public Long getRespsupportid() {
		return respsupportid;
	}

	public void setRespsupportid(Long respsupportid) {
		this.respsupportid = respsupportid;
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

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getRsVentType() {
		return rsVentType;
	}

	public void setRsVentType(String rsVentType) {
		this.rsVentType = rsVentType;
	}

	public String getRsFlowRate() {
		return rsFlowRate;
	}

	public void setRsFlowRate(String rsFlowRate) {
		this.rsFlowRate = rsFlowRate;
	}

	public String getRsFio2() {
		return rsFio2;
	}

	public void setRsFio2(String rsFio2) {
		this.rsFio2 = rsFio2;
	}

	public String getRsMap() {
		return rsMap;
	}

	public void setRsMap(String rsMap) {
		this.rsMap = rsMap;
	}

	public String getRsMechVentType() {
		return rsMechVentType;
	}

	public void setRsMechVentType(String rsMechVentType) {
		this.rsMechVentType = rsMechVentType;
	}

	public String getRsControlledVentType() {
		return rsControlledVentType;
	}

	public void setRsControlledVentType(String rsControlledVentType) {
		this.rsControlledVentType = rsControlledVentType;
	}

	public String getRsPeep() {
		return rsPeep;
	}

	public void setRsPeep(String rsPeep) {
		this.rsPeep = rsPeep;
	}

	public String getRsPip() {
		return rsPip;
	}

	public void setRsPip(String rsPip) {
		this.rsPip = rsPip;
	}

	public String getRsIt() {
		return rsIt;
	}

	public void setRsIt(String rsIt) {
		this.rsIt = rsIt;
	}

	public String getRsEt() {
		return rsEt;
	}

	public void setRsEt(String rsEt) {
		this.rsEt = rsEt;
	}

	public String getRsTv() {
		return rsTv;
	}

	public void setRsTv(String rsTv) {
		this.rsTv = rsTv;
	}

	public String getRsMv() {
		return rsMv;
	}

	public void setRsMv(String rsMv) {
		this.rsMv = rsMv;
	}

	public String getRsAmplitude() {
		return rsAmplitude;
	}

	public void setRsAmplitude(String rsAmplitude) {
		this.rsAmplitude = rsAmplitude;
	}

	public String getRsFrequency() {
		return rsFrequency;
	}

	public void setRsFrequency(String rsFrequency) {
		this.rsFrequency = rsFrequency;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getRsBackuprate() {
		return rsBackuprate;
	}

	public void setRsBackuprate(String rsBackuprate) {
		this.rsBackuprate = rsBackuprate;
	}

	public String getph() {
		return ph;
	}

	public void setph(String ph) {
		this.ph = ph;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public String getHco3() {
		return hco3;
	}

	public void setHco3(String hco3) {
		this.hco3 = hco3;
	}

	public String getBe() {
		return be;
	}

	public void setBe(String be) {
		this.be = be;
	}

	public String getRsControlType() {
		return rsControlType;
	}

	public void setRsControlType(String rsControlType) {
		this.rsControlType = rsControlType;
	}

	public String getRsCpapType() {
		return rsCpapType;
	}

	public void setRsCpapType(String rsCpapType) {
		this.rsCpapType = rsCpapType;
	}

	public String getDco2() {
		return dco2;
	}

	public void setDco2(String dco2) {
		this.dco2 = dco2;
	}

	public String getiTime() {
		return iTime;
	}

	public void setiTime(String iTime) {
		this.iTime = iTime;
	}
	public String getBpdRespNote() {
		return bpdRespNote;
	}

	public void setBpdRespNote(String bpdRespNote) {
		this.bpdRespNote = bpdRespNote;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getC20() {
		return c20;
	}

	public void setC20(String c20) {
		this.c20 = c20;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getRvr() {
		return rvr;
	}

	public void setRvr(String rvr) {
		this.rvr = rvr;
	}

	public String getVtim() {
		return vtim;
	}

	public void setVtim(String vtim) {
		this.vtim = vtim;
	}

	public String getVthf() {
		return vthf;
	}

	public void setVthf(String vthf) {
		this.vthf = vthf;
	}

	public String getLeak() {
		return leak;
	}

	public void setLeak(String leak) {
		this.leak = leak;
	}

	public String getSpont() {
		return spont;
	}

	public void setSpont(String spont) {
		this.spont = spont;
	}

	public String getMvim() {
		return mvim;
	}

	public void setMvim(String mvim) {
		this.mvim = mvim;
	}

	public Float getRsTvInMl() {
		return rsTvInMl;
	}

	public void setRsTvInMl(Float rsTvInMl) {
		this.rsTvInMl = rsTvInMl;
	}

	public String getBloodGasDateTime() {
		return bloodGasDateTime;
	}

	public void setBloodGasDateTime(String bloodGasDateTime) {
		this.bloodGasDateTime = bloodGasDateTime;
	}

	public String getRsPtvMode() {
		return rsPtvMode;
	}

	public void setRsPtvMode(String rsPtvMode) {
		this.rsPtvMode = rsPtvMode;
	}

	public String getVolumeGaurantee() {
		return volumeGaurantee;
	}

	public void setVolumeGaurantee(String volumeGaurantee) {
		this.volumeGaurantee = volumeGaurantee;
	}

	public String getRsSupportType() {
		return rsSupportType;
	}

	public void setRsSupportType(String rsSupportType) {
		this.rsSupportType = rsSupportType;
	}

	public String getPo2() {
		return po2;
	}

	public void setPo2(String po2) {
		this.po2 = po2;
	}

	public String getRsVolumeGuarantee() {
		return rsVolumeGuarantee;
	}

	public void setRsVolumeGuarantee(String rsVolumeGuarantee) {
		this.rsVolumeGuarantee = rsVolumeGuarantee;
	}

	public String getRsPressureSupport() {
		return rsPressureSupport;
	}

	public void setRsPressureSupport(String rsPressureSupport) {
		this.rsPressureSupport = rsPressureSupport;
	}

	public Integer getCounterBpdRds() {
		return counterBpdRds;
	}

	public void setCounterBpdRds(Integer counterBpdRds) {
		this.counterBpdRds = counterBpdRds;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getEventidUpdated() {
		return isEventidUpdated;
	}

	public void setEventidUpdated(Boolean eventidUpdated) {
		isEventidUpdated = eventidUpdated;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Long getDifferenceinmins() {
		return differenceinmins;
	}

	public void setDifferenceinmins(Long differenceinmins) {
		this.differenceinmins = differenceinmins;
	}

	public String getVolumeGauranteeml() {
		return volumeGauranteeml;
	}

	public void setVolumeGauranteeml(String volumeGauranteeml) {
		this.volumeGauranteeml = volumeGauranteeml;
	}

	@Override
	public String toString() {
		return "RespSupport [respsupportid=" + respsupportid + ", creationtime=" + creationtime + ", modificationtime="
				+ modificationtime + ", eventid=" + eventid + ", eventname=" + eventname + ", uhid=" + uhid
				+ ", rsVentType=" + rsVentType + ", rsFlowRate=" + rsFlowRate + ", rsFio2=" + rsFio2 + ", rsMap="
				+ rsMap + ", rsMechVentType=" + rsMechVentType + ", rsControlledVentType=" + rsControlledVentType
				+ ", rsPeep=" + rsPeep + ", rsPip=" + rsPip + ", rsIt=" + rsIt + ", rsEt=" + rsEt + ", rsTv=" + rsTv
				+ ", rsTvInMl=" + rsTvInMl + ", rsMv=" + rsMv + ", rsAmplitude=" + rsAmplitude + ", rsFrequency="
				+ rsFrequency + ", rsRate=" + rsRate + ", rsIsEndotracheal=" + rsIsEndotracheal + ", rsTubeSize="
				+ rsTubeSize + ", rsFixation=" + rsFixation + ", rsSpo2=" + rsSpo2 + ", rspCO2=" + rspCO2
				+ ", rsBackuprate=" + rsBackuprate + ", isactive=" + isactive + ", ph=" + ph + ", co2=" + co2
				+ ", hco3=" + hco3 + ", be=" + be + ", dco2=" + dco2 + ", iTime=" + iTime + ", rsControlType="
				+ rsControlType + ", rsCpapType=" + rsCpapType + ", bpdRespNote=" + bpdRespNote + ", bloodGasDateTime="
				+ bloodGasDateTime + ", rsPtvMode=" + rsPtvMode + ", volumeGaurantee=" + volumeGaurantee
				+ ", volumeGauranteeml=" + volumeGauranteeml + ", rsSupportType=" + rsSupportType + ", po2=" + po2
				+ ", rsVolumeGuarantee=" + rsVolumeGuarantee + ", rsPressureSupport=" + rsPressureSupport
				+ ", counterBpdRds=" + counterBpdRds + ", isEventidUpdated=" + isEventidUpdated + ", endtime=" + endtime
				+ ", differenceinmins=" + differenceinmins + ", c=" + c + ", r=" + r + ", tc=" + tc + ", c20=" + c20
				+ ", trigger=" + trigger + ", rvr=" + rvr + ", vtim=" + vtim + ", vthf=" + vthf + ", leak=" + leak
				+ ", spont=" + spont + ", mvim=" + mvim + ", reason=" + reason + "]";
	}


	public String toComparableString() {
		return "Vent Type=" + rsVentType + ", Flow Rate=" + rsFlowRate + ", Fio2=" + rsFio2 + ", MAP="
				+ rsMap + ", Mech Vent Type=" + rsMechVentType + ", Controlled Vent Type=" + rsControlledVentType
				+ ", PEEP=" + rsPeep + ", PIP=" + rsPip + ", It=" + rsIt + ", Et=" + rsEt + ", Tv=" + rsTv
				+ ", Tv In Ml=" + rsTvInMl + ", Mv=" + rsMv + ", Amplitude=" + rsAmplitude + ", Frequency="
				+ rsFrequency + ", Rate=" + rsRate + ", IsEndotracheal=" + rsIsEndotracheal + ", TubeSize="
				+ rsTubeSize + ", Fixation=" + rsFixation + ", Spo2=" + rsSpo2 + ", pCO2=" + rspCO2
				+ ", Backup rate=" + rsBackuprate  + ", ph=" + ph + ", co2=" + co2
				+ ", hco3=" + hco3 + ", be=" + be + ", dco2=" + dco2 +  ", Control Type="
				+ rsControlType + ", Cpap Type=" + rsCpapType + ", Resp Note=" + bpdRespNote + ", c=" + c + ", r="
				+ r + ", tc=" + tc + ", c20=" + c20 + ", trigger=" + trigger + ", rvr=" + rvr + ", vtim=" + vtim
				+ ", vthf=" + vthf + ", leak=" + leak + ", spont=" + spont + ", mvim=" + mvim + "]";
	}


}
