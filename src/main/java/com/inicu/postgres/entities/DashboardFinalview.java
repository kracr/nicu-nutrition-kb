package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the dashboard_finalview database table.
 * 
 */
@Entity
@Table(name="dashboard_finalview")
@NamedQuery(name="DashboardFinalview.findAll", query="SELECT d FROM DashboardFinalview d")
public class DashboardFinalview implements Serializable {
	private static final long serialVersionUID = 1L;

	private String admissionstatus;

	@Temporal(TemporalType.DATE)
	private Date admitdate;

	private String admittime;

	private String babyname;

	@Id
	private String bedid;

	private String bedno;

	@Column(columnDefinition="float4")
	private Float birthweight;

	@Column(name="co2_resprate")
	private String co2Resprate;

	private String comments;
	
	private String episodeid;

	private String condition;

	private Timestamp creationtime;

	@Column(columnDefinition="float4")
	private Float currentdayweight;

	@Temporal(TemporalType.DATE)
	private Date dateofbirth;

	private String dayoflife;

	@Column(name="dia_bp")
	private String diaBp;

	private String diagnosis;

	@Column(columnDefinition="float4")
	private Float difference;

	@Column(name="ecg_resprate")
	private String ecgResprate;

	private String etco2;

	@Column(name="feed_detail")
	private String feedDetail;

	private String gender;

	private String gestation;

	@Column(columnDefinition="float4")
	private Float heartrate;
	
	@Column(columnDefinition="float4")
	private Float temp;

	@Column(columnDefinition="bool")
	private Boolean isavailable;

	@Column(columnDefinition="float4")
	private Float lastdayweight;

	@Column(name="mean_bp")
	private String meanBp;

	private Long messagecount;

	@Column(name="nbp_d")
	private String nbpD;

	@Column(name="nbp_m")
	private String nbpM;

	@Column(name="nbp_s")
	private String nbpS;

	private String patientlevel;

	private String pulserate;

	private String roomid;

	private String roomname;

	private String spo2;

	private Timestamp starttime;

	@Column(name="sys_bp")
	private String sysBp;

	private String timeofbirth;

	private String uhid;

	@Column(columnDefinition="float4")
	private Float weightatadmission;
	
	@Column(columnDefinition = "bool")
	private Boolean isassessmentsubmit;
	
	@Column(name = "baby_type")
	private String babyType;

	@Column(name = "baby_number")
	private String babyNumber;


	public DashboardFinalview() {
	}

	public String getAdmissionstatus() {
		return this.admissionstatus;
	}

	public void setAdmissionstatus(String admissionstatus) {
		this.admissionstatus = admissionstatus;
	}

	public Date getAdmitdate() {
		return this.admitdate;
	}

	public void setAdmitdate(Date admitdate) {
		this.admitdate = admitdate;
	}

	public String getAdmittime() {
		return this.admittime;
	}

	public void setAdmittime(String admittime) {
		this.admittime = admittime;
	}

	public String getBabyname() {
		return this.babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getBedid() {
		return this.bedid;
	}

	public void setBedid(String bedid) {
		this.bedid = bedid;
	}

	public String getBedno() {
		return this.bedno;
	}

	public void setBedno(String bedno) {
		this.bedno = bedno;
	}

	public Float getBirthweight() {
		return this.birthweight;
	}

	public void setBirthweight(Float birthweight) {
		this.birthweight = birthweight;
	}

	public String getCo2Resprate() {
		return this.co2Resprate;
	}

	public void setCo2Resprate(String co2Resprate) {
		this.co2Resprate = co2Resprate;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Float getCurrentdayweight() {
		return this.currentdayweight;
	}

	public void setCurrentdayweight(Float currentdayweight) {
		this.currentdayweight = currentdayweight;
	}

	public Date getDateofbirth() {
		return this.dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getDayoflife() {
		return this.dayoflife;
	}

	public void setDayoflife(String dayoflife) {
		this.dayoflife = dayoflife;
	}

	public String getDiaBp() {
		return this.diaBp;
	}

	public void setDiaBp(String diaBp) {
		this.diaBp = diaBp;
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Float getDifference() {
		return this.difference;
	}

	public void setDifference(Float difference) {
		this.difference = difference;
	}

	public String getEcgResprate() {
		return this.ecgResprate;
	}

	public void setEcgResprate(String ecgResprate) {
		this.ecgResprate = ecgResprate;
	}

	public String getEtco2() {
		return this.etco2;
	}

	public void setEtco2(String etco2) {
		this.etco2 = etco2;
	}

	public String getFeedDetail() {
		return this.feedDetail;
	}

	public void setFeedDetail(String feedDetail) {
		this.feedDetail = feedDetail;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGestation() {
		return this.gestation;
	}

	public void setGestation(String gestation) {
		this.gestation = gestation;
	}

	

	public Float getHeartrate() {
		return heartrate;
	}

	public void setHeartrate(Float heartrate) {
		this.heartrate = heartrate;
	}

	public Boolean getIsavailable() {
		return this.isavailable;
	}

	public void setIsavailable(Boolean isavailable) {
		this.isavailable = isavailable;
	}

	public Float getLastdayweight() {
		return this.lastdayweight;
	}

	public void setLastdayweight(Float lastdayweight) {
		this.lastdayweight = lastdayweight;
	}

	public String getMeanBp() {
		return this.meanBp;
	}

	public void setMeanBp(String meanBp) {
		this.meanBp = meanBp;
	}

	public Long getMessagecount() {
		return this.messagecount;
	}

	public void setMessagecount(Long messagecount) {
		this.messagecount = messagecount;
	}

	public String getNbpD() {
		return this.nbpD;
	}

	public void setNbpD(String nbpD) {
		this.nbpD = nbpD;
	}

	public String getNbpM() {
		return this.nbpM;
	}

	public void setNbpM(String nbpM) {
		this.nbpM = nbpM;
	}

	public String getNbpS() {
		return this.nbpS;
	}

	public void setNbpS(String nbpS) {
		this.nbpS = nbpS;
	}

	public String getPatientlevel() {
		return this.patientlevel;
	}

	public void setPatientlevel(String patientlevel) {
		this.patientlevel = patientlevel;
	}

	public String getPulserate() {
		return this.pulserate;
	}

	public void setPulserate(String pulserate) {
		this.pulserate = pulserate;
	}

	public String getRoomid() {
		return this.roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getRoomname() {
		return this.roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getSpo2() {
		return this.spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getSysBp() {
		return this.sysBp;
	}

	public void setSysBp(String sysBp) {
		this.sysBp = sysBp;
	}

	public String getTimeofbirth() {
		return this.timeofbirth;
	}

	public void setTimeofbirth(String timeofbirth) {
		this.timeofbirth = timeofbirth;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Float getWeightatadmission() {
		return this.weightatadmission;
	}

	public void setWeightatadmission(Float weightatadmission) {
		this.weightatadmission = weightatadmission;
	}

	public Boolean getIsassessmentsubmit() {
		return isassessmentsubmit;
	}

	public void setIsassessmentsubmit(Boolean isassessmentsubmit) {
		this.isassessmentsubmit = isassessmentsubmit;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getBabyType() {
		return babyType;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}
	
	

}