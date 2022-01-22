package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NurseBabyMapping;

public class NurseBabyPOJO {

	String uhid;
	
	String babyName;
	
	String babyType;
	
	String babyNumber;
	
//	List<NurseDetail> nurseDetailList;
	
	List<NurseDetail> shift1;
	
	List<NurseDetail> shift2;
	
	List<NurseDetail> shift3;
	
	List<NurseDetail> shift4;
	
	
	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public String getBabyType() {
		return babyType;
	}

	public void setBabyType(String babyType) {
		this.babyType = babyType;
	}

	public String getBabyNumber() {
		return babyNumber;
	}

	public void setBabyNumber(String babyNumber) {
		this.babyNumber = babyNumber;
	}

	public List<NurseDetail> getShift1() {
		return shift1;
	}

	public void setShift1(List<NurseDetail> shift1) {
		this.shift1 = shift1;
	}

	public List<NurseDetail> getShift2() {
		return shift2;
	}

	public void setShift2(List<NurseDetail> shift2) {
		this.shift2 = shift2;
	}

	public List<NurseDetail> getShift3() {
		return shift3;
	}

	public void setShift3(List<NurseDetail> shift3) {
		this.shift3 = shift3;
	}

	public List<NurseDetail> getShift4() {
		return shift4;
	}

	public void setShift4(List<NurseDetail> shift4) {
		this.shift4 = shift4;
	}

			
	
	
}
