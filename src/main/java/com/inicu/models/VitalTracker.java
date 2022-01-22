package com.inicu.models;

import java.beans.Transient;
import java.sql.Timestamp;

public class VitalTracker {
	
	String uhid;
	String babyname;
	String boxName;
	Boolean babyAdmissionStatus;
	Integer vitalTotalCount;
	Long numberOfMinutes;
	Integer hrCount;
	Integer spo2Count;
	Integer prCount;
	Integer rrCount;
	Integer ventilatorTotalCount;
	Integer fio2Count;
	Integer pipCount;
	Integer peepCount;

	// New column added
	Timestamp entryDate;
	String deviceBrandName;
	Boolean deviceHealth;

	String vitalDeviceBoxName;
	String VitalDeviceType;

	String ventilatorDeviceBoxName;
	String ventilatorDeviceType;


	public String getVitalDeviceBoxName() {
		return vitalDeviceBoxName;
	}

	public void setVitalDeviceBoxName(String vitalDeviceBoxName) {
		this.vitalDeviceBoxName = vitalDeviceBoxName;
	}

	public String getVitalDeviceType() {
		return VitalDeviceType;
	}

	public void setVitalDeviceType(String vitalDeviceType) {
		VitalDeviceType = vitalDeviceType;
	}

	public String getVentilatorDeviceBoxName() {
		return ventilatorDeviceBoxName;
	}

	public void setVentilatorDeviceBoxName(String ventilatorDeviceBoxName) {
		this.ventilatorDeviceBoxName = ventilatorDeviceBoxName;
	}

	public String getVentilatorDeviceType() {
		return ventilatorDeviceType;
	}

	public void setVentilatorDeviceType(String ventilatorDeviceType) {
		this.ventilatorDeviceType = ventilatorDeviceType;
	}

	public Boolean getBabyAdmissionStatus() {
		return babyAdmissionStatus;
	}

	public void setBabyAdmissionStatus(Boolean babyAdmissionStatus) {
		this.babyAdmissionStatus = babyAdmissionStatus;
	}

	public Boolean getDeviceHealth() {
		return deviceHealth;
	}

	public void setDeviceHealth(Boolean deviceHealth) {
		this.deviceHealth = deviceHealth;
	}

	public String getDeviceBrandName() {
		return deviceBrandName;
	}

	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	public VitalTracker(){
		this.vitalTotalCount=0;
		this.hrCount=0;
		this.spo2Count=0;
		this.prCount=0;
		this.rrCount=0;
		this.ventilatorTotalCount=0;
		this.fio2Count=0;
		this.pipCount=0;
		this.peepCount=0;
		this.deviceHealth=true;
		this.numberOfMinutes=Long.valueOf(0);
		this.deviceBrandName="";
	}

	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public Integer getHrCount() {
		return hrCount;
	}
	public void setHrCount(Integer hrCount) {
		this.hrCount = hrCount;
	}
	public Integer getSpo2Count() {
		return spo2Count;
	}
	public void setSpo2Count(Integer spo2Count) {
		this.spo2Count = spo2Count;
	}
	public Integer getPrCount() {
		return prCount;
	}
	public void setPrCount(Integer prCount) {
		this.prCount = prCount;
	}
	public Integer getRrCount() {
		return rrCount;
	}
	public void setRrCount(Integer rrCount) {
		this.rrCount = rrCount;
	}
	public String getBabyname() {
		return babyname;
	}
	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public Integer getVitalTotalCount() {
		return vitalTotalCount;
	}
	public void setVitalTotalCount(Integer vitalTotalCount) {
		this.vitalTotalCount = vitalTotalCount;
	}
	public Integer getVentilatorTotalCount() {
		return ventilatorTotalCount;
	}
	public void setVentilatorTotalCount(Integer ventilatorTotalCount) {
		this.ventilatorTotalCount = ventilatorTotalCount;
	}
	public Integer getFio2Count() {
		return fio2Count;
	}
	public void setFio2Count(Integer fio2Count) {
		this.fio2Count = fio2Count;
	}
	public Integer getPipCount() {
		return pipCount;
	}
	public void setPipCount(Integer pipCount) {
		this.pipCount = pipCount;
	}
	public Integer getPeepCount() {
		return peepCount;
	}
	public void setPeepCount(Integer peepCount) {
		this.peepCount = peepCount;
	}
	public Long getNumberOfMinutes() {
		return numberOfMinutes;
	}
	public void setNumberOfMinutes(Long numberOfMinutes) {
		this.numberOfMinutes = numberOfMinutes;
	}

}
