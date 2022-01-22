package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="readmitpatient_final")
public class ReAdmitChildDetailView implements Serializable{

	@Column(name="uhid")
	private String uhid;
	
	@Column(name="isavailable", columnDefinition="bool")
	private Boolean isAvailable;
	
	@Column(name="condition")
	private String condition;
	
	@Column(name="admitdate")
	private Date admitDate;
	
	@Column(name="bedno")
	private String bedNo;
	
	@Column(name="patientlevel")
	private String patientLevel;
	
	@Id
	@Column(name="bedid")
	private String bedId;
	
	private Timestamp creationtime;
	
	/*	@Column(name="ecg",columnDefinition="bool")
	private Boolean ecg;
*/	
	/*@Column(name="oxygen",columnDefinition="bool")
	private Boolean oxygen;
	*/
	/*@Column(name="blood",columnDefinition="bool")
	private Boolean blood;
	*/
	
	@Column(name="heartrate")
	private String heartRate;
	
	@Column(name="spo2")
	private String sp02;
	
	@Column(name="ecg_resprate")
	private String respRate;
	
	
	@Column(name="pulserate")
	private String pulseRate;
	
	
	@Column(name="messagecount", columnDefinition="int8")
	private Integer totalMessage;
	
	@Column(name="babyname")
	private String babyName;
	
	private String gestation;
	
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

	@Column(columnDefinition="float4")
	private String birthweight;
	
	private String gender;
	
	@Column(name="comments")
	private String comment;
	
	private Date dateofbirth;
	
	private String roomname;
	
	private String branchname;
	
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

	/*public Boolean getEcg() {
		return ecg;
	}*/

	/*public Boolean getOxygen() {
		return oxygen;
	}*/

	/*public Boolean getBlood() {
		return blood;
	}*/

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

	/*public Boolean isEcg() {
		return ecg;
	}

	public void setEcg(Boolean ecg) {
		this.ecg = ecg;
	}
*/
	/*public Boolean isOxygen() {
		return oxygen;
	}

	public void setOxygen(Boolean oxygen) {
		this.oxygen = oxygen;
	}*/

	/*public Boolean isBlood() {
		return blood;
	}

	public void setBlood(Boolean blood) {
		this.blood = blood;
	}*/

	/*public Float getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Float heartRate) {
		this.heartRate = heartRate;
	}*/
/*
	public Float getSp02() {
		return sp02;
	}

	public void setSp02(Float sp02) {
		this.sp02 = sp02;
	}
*/
	/*public Float getRespRate() {
		return respRate;
	}

	public void setRespRate(Float respRate) {
		this.respRate = respRate;
	}*/

	/*public Float getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(Float pulseRate) {
		this.pulseRate = pulseRate;
	}*/

	public Integer getTotalMessage() {
		return totalMessage;
	}

	public void setTotalMessage(Integer totalMessage) {
		this.totalMessage = totalMessage;
	}
	public void setComment(String comment)
	{
		this.comment=comment;
	}
	public String getComment()
	{
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

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

}
