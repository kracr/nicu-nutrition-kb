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

/**
 * The persistent class for the nursing_ventilaor database table.
 * 
 */
@Entity
@Table(name = "nursing_ventilaor")
@NamedQuery(name = "NursingVentilaor.findAll", query = "SELECT n FROM NursingVentilaor n")
public class NursingVentilaor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nn_ventilaorid")
	private Long nnVentilaorid;

	private Timestamp creationtime;

	@Column(name = "ett_color")
	private String ettColor;

	@Column(name = "ett_quantity")
	private String ettQuantity;

	private String fio2;

	@Column(name = "flow_per_min")
	private String flowPerMin;

	@Column(name = "freq_rate")
	private String freqRate;

	private String loggeduser;

	private String map;

	@Column(name = "minute_volume")
	private String minuteVolume;

	private Timestamp modificationtime;

	@Column(name = "nn_ventilaor_time")
	private String nnVentilaorTime;

	@Column(name = "no_ppm")
	private String noPpm;

	@Column(name = "peep_cpap")
	private String peepCpap;

	private String pip;

	private String userDate;

	@Column(name = "pressure_supply")
	private String pressureSupply;

	private String ti;

	@Column(name = "tidal_volume")
	private String tidalVolume;
	
	@Column(name = "tidal_vol")
	private String tidalVol;
	
	@Column(name = "volume_guarantee")
	private String volumeguarantee;
	
	@Column(name = "volume_guaranteeml")
	private String volumeguaranteeml;
	
	@Column(name = "pressure_support_type")
	private String pressuresupporttype;
	
	@Column(name = "control_type")
	private String controltype;
	
	@Column(name = "delivery_type")
	private String deliverytype;
	
	@Column(name = "cpap_type")
	private String cpaptype;
	
	@Column(name = "nursing_volume_guarantee")
	private String nursingVolumeGuarantee;
	
	private String vent_desc;

	private String uhid;

	private String ventmode;
	
	private String amplitude;

	private String frequency;
	
	private String dco2;
	
	private String humidity;
	
	private Timestamp entryDate;

	@Column(name ="intubated_humidification_temp")
	private String intubatedHumidificationTemp;

	//All the Parameter Coming From Draeger
	private String c;
	private String r;
	private String tc;
	private String c20;
	private String trigger;
	private String rvr;
	private String te;
	private String vtim;
	private String vthf;
	private String leak;
	private String spont;
	private String mvim;
	private String spo2;
	
	@Transient
	private String ventName;
	
	@Transient
	private String doctorventName;
	
	@Transient
	private String doctorventmode;
	
	@Transient
	private String doctorc;
	
	@Transient
	private String doctorr;

	@Transient
	private String doctortc;

	@Transient
	private String doctorc20;

	@Transient
	private String doctortrigger;

	@Transient
	private String doctorrvr;

	@Transient
	private String doctorte;

	@Transient
	private String doctorvtim;

	@Transient
	private String doctorvthf;

	@Transient
	private String doctorleak;

	@Transient
	private String doctorspont;

	@Transient
	private String doctormvim;

	@Transient
	private String doctorspo2;
	
	@Transient
	private String doctorpip;
	
	@Transient
	private String doctorpeepCpap;
	
	@Transient
	private String doctormap;
	
	@Transient
	private String doctorfreqRate;
	
	@Transient
	private String doctortidalVolume;
	
	@Transient
	private String doctorminuteVolume;
	
	@Transient
	private String doctorti;
	
	@Transient
	private String doctorfio2;
	
	@Transient
	private String doctorflowPerMin;
	
	@Transient
	private String doctordco2;
	
	@Transient
	private String doctoramplitude;
	
	@Transient
	private String doctorfrequency;
	
	@Transient
	private String doctorvolguarantee;
	
	@Transient
	private String doctorcpaptype;
	
	@Transient
	private String doctordeliverytype;
	
	@Transient
	private String doctorcontroltype;
	
	@Transient
	private String doctorpressuresupporttype;
	
	@Transient
	private String doctorVolumeGuarantee;
	
	@Transient
	private String doctortidalVolumeml;
	
	@Transient
	private String doctorvolguaranteeml;
	
	public NursingVentilaor() {
		ettColor = "";
		ettQuantity = "";
	}

	public Long getNnVentilaorid() {
		return this.nnVentilaorid;
	}

	public void setNnVentilaorid(Long nnVentilaorid) {
		this.nnVentilaorid = nnVentilaorid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getEttColor() {
		return this.ettColor;
	}

	public void setEttColor(String ettColor) {
		this.ettColor = ettColor;
	}

	public String getEttQuantity() {
		return this.ettQuantity;
	}

	public void setEttQuantity(String ettQuantity) {
		this.ettQuantity = ettQuantity;
	}

	public String getFio2() {
		return this.fio2;
	}

	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}

	public String getFlowPerMin() {
		return this.flowPerMin;
	}

	public void setFlowPerMin(String flowPerMin) {
		this.flowPerMin = flowPerMin;
	}

	public String getFreqRate() {
		return this.freqRate;
	}

	public void setFreqRate(String freqRate) {
		this.freqRate = freqRate;
	}

	public String getLoggeduser() {
		return this.loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMap() {
		return this.map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getMinuteVolume() {
		return this.minuteVolume;
	}

	public void setMinuteVolume(String minuteVolume) {
		this.minuteVolume = minuteVolume;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getNnVentilaorTime() {
		return this.nnVentilaorTime;
	}

	public void setNnVentilaorTime(String nnVentilaorTime) {
		this.nnVentilaorTime = nnVentilaorTime;
	}

	public String getNoPpm() {
		return this.noPpm;
	}

	public void setNoPpm(String noPpm) {
		this.noPpm = noPpm;
	}

	public String getPeepCpap() {
		return this.peepCpap;
	}

	public void setPeepCpap(String peepCpap) {
		this.peepCpap = peepCpap;
	}

	public String getPip() {
		return this.pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public String getPressureSupply() {
		return this.pressureSupply;
	}

	public void setPressureSupply(String pressureSupply) {
		this.pressureSupply = pressureSupply;
	}

	public String getTi() {
		return this.ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getTidalVolume() {
		return this.tidalVolume;
	}

	public void setTidalVolume(String tidalVolume) {
		this.tidalVolume = tidalVolume;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getVentmode() {
		return this.ventmode;
	}

	public void setVentmode(String ventmode) {
		this.ventmode = ventmode;
	}

	public String getUserDate() {
		return userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
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

	public String getTe() {
		return te;
	}

	public void setTe(String te) {
		this.te = te;
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

	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getVentName() {
		return ventName;
	}

	public void setVentName(String ventName) {
		this.ventName = ventName;
	}
	
	public String getDoctorventName() {
		return doctorventName;
	}

	public void setDoctorventName(String doctorventName) {
		this.doctorventName = doctorventName;
	}

	public String getDoctorventmode() {
		return doctorventmode;
	}

	public void setDoctorventmode(String doctorventmode) {
		this.doctorventmode = doctorventmode;
	}

	public String getDoctorc() {
		return doctorc;
	}

	public void setDoctorc(String doctorc) {
		this.doctorc = doctorc;
	}

	public String getDoctorr() {
		return doctorr;
	}

	public String getDoctortc() {
		return doctortc;
	}

	public void setDoctortc(String doctortc) {
		this.doctortc = doctortc;
	}

	public void setDoctorr(String doctorr) {
		this.doctorr = doctorr;
	}

	public String getDoctorc20() {
		return doctorc20;
	}

	public void setDoctorc20(String doctorc20) {
		this.doctorc20 = doctorc20;
	}

	public String getDoctortrigger() {
		return doctortrigger;
	}

	public void setDoctortrigger(String doctortrigger) {
		this.doctortrigger = doctortrigger;
	}

	public String getDoctorrvr() {
		return doctorrvr;
	}

	public void setDoctorrvr(String doctorrvr) {
		this.doctorrvr = doctorrvr;
	}
	
	public String getDoctorte() {
		return doctorte;
	}

	public void setDoctorte(String doctorte) {
		this.doctorte = doctorte;
	}

	public String getDoctorvtim() {
		return doctorvtim;
	}

	public void setDoctorvtim(String doctorvtim) {
		this.doctorvtim = doctorvtim;
	}

	public String getDoctorvthf() {
		return doctorvthf;
	}

	public void setDoctorvthf(String doctorvthf) {
		this.doctorvthf = doctorvthf;
	}

	public String getDoctorleak() {
		return doctorleak;
	}

	public void setDoctorleak(String doctorleak) {
		this.doctorleak = doctorleak;
	}

	public String getDoctorspont() {
		return doctorspont;
	}

	public void setDoctorspont(String doctorspont) {
		this.doctorspont = doctorspont;
	}

	public String getDoctormvim() {
		return doctormvim;
	}

	public void setDoctormvim(String doctormvim) {
		this.doctormvim = doctormvim;
	}

	public String getDoctorspo2() {
		return doctorspo2;
	}

	public void setDoctorspo2(String doctorspo2) {
		this.doctorspo2 = doctorspo2;
	}

	public String getDoctorpip() {
		return doctorpip;
	}

	public void setDoctorpip(String doctorpip) {
		this.doctorpip = doctorpip;
	}

	public String getDoctorpeepCpap() {
		return doctorpeepCpap;
	}

	public void setDoctorpeepCpap(String doctorpeepCpap) {
		this.doctorpeepCpap = doctorpeepCpap;
	}

	public String getDoctormap() {
		return doctormap;
	}

	public void setDoctormap(String doctormap) {
		this.doctormap = doctormap;
	}

	public String getDoctorfreqRate() {
		return doctorfreqRate;
	}

	public void setDoctorfreqRate(String doctorfreqRate) {
		this.doctorfreqRate = doctorfreqRate;
	}

	public String getDoctortidalVolume() {
		return doctortidalVolume;
	}

	public void setDoctortidalVolume(String doctortidalVolume) {
		this.doctortidalVolume = doctortidalVolume;
	}

	public String getDoctorminuteVolume() {
		return doctorminuteVolume;
	}

	public void setDoctorminuteVolume(String doctorminuteVolume) {
		this.doctorminuteVolume = doctorminuteVolume;
	}

	public String getDoctorti() {
		return doctorti;
	}

	public void setDoctorti(String doctorti) {
		this.doctorti = doctorti;
	}

	public String getDoctorfio2() {
		return doctorfio2;
	}

	public void setDoctorfio2(String doctorfio2) {
		this.doctorfio2 = doctorfio2;
	}

	public String getDoctorflowPerMin() {
		return doctorflowPerMin;
	}

	public void setDoctorflowPerMin(String doctorflowPerMin) {
		this.doctorflowPerMin = doctorflowPerMin;
	}

	public String getAmplitude() {
		return amplitude;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setAmplitude(String amplitude) {
		this.amplitude = amplitude;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDoctordco2() {
		return doctordco2;
	}

	public void setDoctordco2(String doctordco2) {
		this.doctordco2 = doctordco2;
	}

	public String getDoctoramplitude() {
		return doctoramplitude;
	}

	public void setDoctoramplitude(String doctoramplitude) {
		this.doctoramplitude = doctoramplitude;
	}

	public String getDoctorfrequency() {
		return doctorfrequency;
	}

	public void setDoctorfrequency(String doctorfrequency) {
		this.doctorfrequency = doctorfrequency;
	}

	public String getDco2() {
		return dco2;
	}

	public void setDco2(String dco2) {
		this.dco2 = dco2;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getDoctorvolguarantee() {
		return doctorvolguarantee;
	}

	public void setDoctorvolguarantee(String doctorvolguarantee) {
		this.doctorvolguarantee = doctorvolguarantee;
	}

	public String getDoctorcpaptype() {
		return doctorcpaptype;
	}

	public void setDoctorcpaptype(String doctorcpaptype) {
		this.doctorcpaptype = doctorcpaptype;
	}

	public String getDoctordeliverytype() {
		return doctordeliverytype;
	}

	public void setDoctordeliverytype(String doctordeliverytype) {
		this.doctordeliverytype = doctordeliverytype;
	}

	public String getDoctorcontroltype() {
		return doctorcontroltype;
	}

	public void setDoctorcontroltype(String doctorcontroltype) {
		this.doctorcontroltype = doctorcontroltype;
	}

	public String getDoctorpressuresupporttype() {
		return doctorpressuresupporttype;
	}

	public void setDoctorpressuresupporttype(String doctorpressuresupporttype) {
		this.doctorpressuresupporttype = doctorpressuresupporttype;
	}

	public String getVolumeguarantee() {
		return volumeguarantee;
	}

	public void setVolumeguarantee(String volumeguarantee) {
		this.volumeguarantee = volumeguarantee;
	}

	public String getPressuresupporttype() {
		return pressuresupporttype;
	}

	public void setPressuresupporttype(String pressuresupporttype) {
		this.pressuresupporttype = pressuresupporttype;
	}

	public String getControltype() {
		return controltype;
	}

	public void setControltype(String controltype) {
		this.controltype = controltype;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public String getCpaptype() {
		return cpaptype;
	}

	public void setCpaptype(String cpaptype) {
		this.cpaptype = cpaptype;
	}

	public String getVent_desc() {
		return vent_desc;
	}

	public void setVent_desc(String vent_desc) {
		this.vent_desc = vent_desc;
	}

	public String getNursingVolumeGuarantee() {
		return nursingVolumeGuarantee;
	}

	public void setNursingVolumeGuarantee(String nursingVolumeGuarantee) {
		this.nursingVolumeGuarantee = nursingVolumeGuarantee;
	}

	public String getDoctorVolumeGuarantee() {
		return doctorVolumeGuarantee;
	}

	public void setDoctorVolumeGuarantee(String doctorVolumeGuarantee) {
		this.doctorVolumeGuarantee = doctorVolumeGuarantee;
	}

	public String getIntubatedHumidificationTemp() {
		return intubatedHumidificationTemp;
	}

	public void setIntubatedHumidificationTemp(String intubatedHumidificationTemp) {
		this.intubatedHumidificationTemp = intubatedHumidificationTemp;
	}

	public String getTidalVol() {
		return tidalVol;
	}

	public void setTidalVol(String tidalVol) {
		this.tidalVol = tidalVol;
	}

	public String getVolumeguaranteeml() {
		return volumeguaranteeml;
	}

	public void setVolumeguaranteeml(String volumeguaranteeml) {
		this.volumeguaranteeml = volumeguaranteeml;
	}

	public String getDoctortidalVolumeml() {
		return doctortidalVolumeml;
	}

	public void setDoctortidalVolumeml(String doctortidalVolumeml) {
		this.doctortidalVolumeml = doctortidalVolumeml;
	}

	public String getDoctorvolguaranteeml() {
		return doctorvolguaranteeml;
	}

	public void setDoctorvolguaranteeml(String doctorvolguaranteeml) {
		this.doctorvolguaranteeml = doctorvolguaranteeml;
	}
	
	
}