package com.inicu.models;

import java.util.Date;

import javax.persistence.Column;

public class WeightTrackingJSON {

	private String uhid;
	private String babyname;
	private String babyType;
	private String babyNumber;
	private String nicuRoom;
	private String bedno;
	private String age;
	private Float birthWeight;
	private Float lastWeight;
	private Float todayWeight;
	private Float weightChange;
	private String nicudays;
	private boolean isWeightIncrement;
	private String gaAtBirth;
	private String correctedGa;
	private Float currentdateheadcircum;
	private Float currentdateheight;
	private String dateofbirth;
	private Integer gestationdaysbylmp;
	private Integer gestationweekbylmp;
	private Integer gestationWeek;
	private Integer gestationDays;
	private Float weightForCal;
	private String dol; 
	
	public String getDol() {
		return dol;
	}
	public void setDol(String dol) {
		this.dol = dol;
	}
	public String getUhid() {
		return uhid;
	}
	public void setUhid(String uhid) {
		this.uhid = uhid;
	}
	public String getBabyname() {
		return babyname;
	}
	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}
	public String getNicuRoom() {
		return nicuRoom;
	}
	public void setNicuRoom(String nicuRoom) {
		this.nicuRoom = nicuRoom;
	}
	public String getBedno() {
		return bedno;
	}
	public void setBedno(String bedno) {
		this.bedno = bedno;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Float getBirthWeight() {
		return birthWeight;
	}
	public void setBirthWeight(Float birthWeight) {
		this.birthWeight = birthWeight;
	}
	public Float getLastWeight() {
		return lastWeight;
	}
	public void setLastWeight(Float lastWeight) {
		this.lastWeight = lastWeight;
	}
	public Float getTodayWeight() {
		return todayWeight;
	}
	public void setTodayWeight(Float todayWeight) {
		this.todayWeight = todayWeight;
	}
	public Float getWeightChange() {
		return weightChange;
	}
	public void setWeightChange(Float weightChange) {
		this.weightChange = weightChange;
	}
	public String getNicudays() {
		return nicudays;
	}
	public void setNicudays(String nicudays) {
		this.nicudays = nicudays;
	}
	public boolean isWeightIncrement() {
		return isWeightIncrement;
	}
	public void setWeightIncrement(boolean isWeightIncrement) {
		this.isWeightIncrement = isWeightIncrement;
	}
	public String getGaAtBirth() {
		return gaAtBirth;
	}
	public void setGaAtBirth(String gaAtBirth) {
		this.gaAtBirth = gaAtBirth;
	}
	public String getCorrectedGa() {
		return correctedGa;
	}
	public void setCorrectedGa(String correctedGa) {
		this.correctedGa = correctedGa;
	}
	public Float getCurrentdateheadcircum() {
		return currentdateheadcircum;
	}
	public void setCurrentdateheadcircum(Float currentdateheadcircum) {
		this.currentdateheadcircum = currentdateheadcircum;
	}
	public Float getCurrentdateheight() {
		return currentdateheight;
	}
	public void setCurrentdateheight(Float currentdateheight) {
		this.currentdateheight = currentdateheight;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public Integer getGestationdaysbylmp() {
		return gestationdaysbylmp;
	}
	public void setGestationdaysbylmp(Integer gestationdaysbylmp) {
		this.gestationdaysbylmp = gestationdaysbylmp;
	}
	public Integer getGestationweekbylmp() {
		return gestationweekbylmp;
	}
	public void setGestationweekbylmp(Integer gestationweekbylmp) {
		this.gestationweekbylmp = gestationweekbylmp;
	}
	public Integer getGestationWeek() {
		return gestationWeek;
	}
	public void setGestationWeek(Integer gestationWeek) {
		this.gestationWeek = gestationWeek;
	}
	public Integer getGestationDays() {
		return gestationDays;
	}
	public void setGestationDays(Integer gestationDays) {
		this.gestationDays = gestationDays;
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
	public Float getWeightForCal() {
		return weightForCal;
	}
	public void setWeightForCal(Float weightForCal) {
		this.weightForCal = weightForCal;
	}
}
