package com.inicu.postgres.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dashboard_finalview")
public class DashboardDeviceDetailView {

	@Column(name = "uhid")
	private String uhid;

	@Column(columnDefinition = "float4")
	private Float temp;

	@Column(name = "isavailable", columnDefinition = "bool")
	private Boolean isAvailable;

	@Column(name = "condition")
	private String condition;

	private String address;

	@Column(name = "admitdate")
	private Date admitDate;

	@Column(name = "admittime")
	private String admittime;

	@Column(name = "bedno")
	private String bedNo;

	@Column(name = "patientlevel")
	private String patientLevel;

	@Id
	@Column(name = "bedid")
	private String bedId;

	private String episodeid;

	private Timestamp creationtime;

	/*
	 * @Column(name="ecg",columnDefinition="bool") private Boolean ecg;
	 */
	/*
	 * @Column(name="oxygen",columnDefinition="bool") private Boolean oxygen;
	 */
	/*
	 * @Column(name="blood",columnDefinition="bool") private Boolean blood;
	 */

	@Column(name = "heartrate")
	private String heartRate;

	@Column(name = "spo2")
	private String sp02;

	@Column(name = "ecg_resprate")
	private String respRate;

	@Column(name = "pulserate")
	private String pulseRate;

	@Column(name = "messagecount", columnDefinition = "int8")
	private Integer totalMessage;

	@Column(name = "babyname")
	private String babyName;

	private String gestation;

	@Column(name = "co2_resprate")
	private String co2RespRate;

	private Timestamp starttime;

	// @Column(columnDefinition="int4")
	private String dayoflife;

	@Column(columnDefinition = "float4")
	private Float weightatadmission;

	@Column(columnDefinition = "float4")
	private Float lastdayweight;

	@Column(columnDefinition = "float4")
	private Float currentdayweight;

	@Column(columnDefinition = "float4")
	private Float difference;

	@Column(name = "feed_detail")
	private String feedDetail;

	private String diagnosis;

	@Column(columnDefinition = "bool")
	private Boolean isassessmentsubmit;

	private String sys_bp;

	private String dia_bp;

	private String mean_bp;

	private String fio2;

	private String pip;

	private String peep;

	@Column(name = "baby_type")
	private String babyType;

	@Column(name = "baby_number")
	private String babyNumber;
	
	private String branchName;

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getSp02() {
		return sp02;
	}

	public void setSp02(String sp02) {
		this.sp02 = sp02;
	}

	public String getRespRate() {
		return respRate;
	}

	public void setRespRate(String respRate) {
		this.respRate = respRate;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}

	@Column(columnDefinition = "float4")
	private String birthweight;

	private String gender;

	@Column(name = "comments")
	private String comment;

	private Date dateofbirth;

	private String timeofbirth;

	private String roomname;

	@Column(name  = "audio_enabled", columnDefinition = "bool")
	private Boolean audioEnabled;


	public Boolean getAudioEnabled() {
		return audioEnabled;
	}

	public void setAudioEnabled(Boolean audioEnabled) {
		this.audioEnabled = audioEnabled;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getGestation() {
		return gestation;
	}

	public void setGestation(String gestation) {
		this.gestation = gestation;
	}

	public String getBirthweight() {
		return birthweight;
	}

	public void setBirthweight(String birthweight) {
		this.birthweight = birthweight;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	/*
	 * public Boolean getEcg() { return ecg; }
	 */

	/*
	 * public Boolean getOxygen() { return oxygen; }
	 */

	/*
	 * public Boolean getBlood() { return blood; }
	 */

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public Boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAdmittime() {
		return admittime;
	}

	public void setAdmittime(String admittime) {
		this.admittime = admittime;
	}

	public Date getAdmitDate() {
		return admitDate;
	}

	public void setAdmitDate(Date admitDate) {
		this.admitDate = admitDate;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getPatientLevel() {
		return patientLevel;
	}

	public void setPatientLevel(String patientLevel) {
		this.patientLevel = patientLevel;
	}



	/*
	 * public Boolean isEcg() { return ecg; }
	 * 
	 * public void setEcg(Boolean ecg) { this.ecg = ecg; }
	 */
	/*
	 * public Boolean isOxygen() { return oxygen; }
	 * 
	 * public void setOxygen(Boolean oxygen) { this.oxygen = oxygen; }
	 */

	/*
	 * public Boolean isBlood() { return blood; }
	 * 
	 * public void setBlood(Boolean blood) { this.blood = blood; }
	 */

	/*
	 * public Float getHeartRate() { return heartRate; }
	 * 
	 * public void setHeartRate(Float heartRate) { this.heartRate = heartRate; }
	 */
	/*
	 * public Float getSp02() { return sp02; }
	 * 
	 * public void setSp02(Float sp02) { this.sp02 = sp02; }
	 */
	/*
	 * public Float getRespRate() { return respRate; }
	 * 
	 * public void setRespRate(Float respRate) { this.respRate = respRate; }
	 */

	/*
	 * public Float getPulseRate() { return pulseRate; }
	 * 
	 * public void setPulseRate(Float pulseRate) { this.pulseRate = pulseRate; }
	 */

	public Integer getTotalMessage() {
		return totalMessage;
	}

	public void setTotalMessage(Integer totalMessage) {
		this.totalMessage = totalMessage;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getBedId() {
		return bedId;
	}

	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

	public String getCo2RespRate() {
		return co2RespRate;
	}

	public void setCo2RespRate(String co2RespRate) {
		this.co2RespRate = co2RespRate;
	}

	public String getDayoflife() {
		return dayoflife;
	}

	public void setDayoflife(String dayoflife) {
		this.dayoflife = dayoflife;
	}

	public Float getWeightatadmission() {
		return weightatadmission;
	}

	public void setWeightatadmission(Float weightatadmission) {
		this.weightatadmission = weightatadmission;
	}

	public Float getLastdayweight() {
		return lastdayweight;
	}

	public void setLastdayweight(Float lastdayweight) {
		this.lastdayweight = lastdayweight;
	}

	public Float getCurrentdayweight() {
		return currentdayweight;
	}

	public void setCurrentdayweight(Float currentdayweight) {
		this.currentdayweight = currentdayweight;
	}

	public Float getDifference() {
		return difference;
	}

	public void setDifference(Float difference) {
		this.difference = difference;
	}

	public String getFeedDetail() {
		return feedDetail;
	}

	public void setFeedDetail(String feedDetail) {
		this.feedDetail = feedDetail;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTimeofbirth() {
		return timeofbirth;
	}

	public void setTimeofbirth(String timeofbirth) {
		this.timeofbirth = timeofbirth;
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

	public String getSys_bp() {
		return sys_bp;
	}

	public void setSys_bp(String sys_bp) {
		this.sys_bp = sys_bp;
	}

	public String getDia_bp() {
		return dia_bp;
	}

	public void setDia_bp(String dia_bp) {
		this.dia_bp = dia_bp;
	}

	public String getMean_bp() {
		return mean_bp;
	}

	public void setMean_bp(String mean_bp) {
		this.mean_bp = mean_bp;
	}

	public String getFio2() {
		return fio2;
	}

	public void setFio2(String fio2) {
		this.fio2 = fio2;
	}

	public String getPip() {
		return pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public String getPeep() {
		return peep;
	}

	public void setPeep(String peep) {
		this.peep = peep;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
