package com.inicu.models;

import java.sql.Timestamp;
import java.util.List;

import com.inicu.postgres.entities.NutritionalCompliance;

public class NutritionalCompliancePOJO {
	
	private String uhid;
	private String babyname;
	private Integer gestationdaysbylmp;
	private Integer gestationweekbylmp;
	private Timestamp dateofbirth;
	private Timestamp dateofadmission;
	private String gender;
	
	private String ph;
	private String be;
	private String chest_compression;
	private String pressors;
	private String iugr;
	private String mono_twins;
	private String feed_intolerance;
	
	private List<NutritionalCompliance> complianceData;

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

	public Timestamp getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Timestamp dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Timestamp getDateofadmission() {
		return dateofadmission;
	}

	public void setDateofadmission(Timestamp dateofadmission) {
		this.dateofadmission = dateofadmission;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<NutritionalCompliance> getComplianceData() {
		return complianceData;
	}

	public void setComplianceData(List<NutritionalCompliance> complianceData) {
		this.complianceData = complianceData;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getBe() {
		return be;
	}

	public void setBe(String be) {
		this.be = be;
	}

	public String getChest_compression() {
		return chest_compression;
	}

	public void setChest_compression(String chest_compression) {
		this.chest_compression = chest_compression;
	}

	public String getPressors() {
		return pressors;
	}

	public void setPressors(String pressors) {
		this.pressors = pressors;
	}

	public String getIugr() {
		return iugr;
	}

	public void setIugr(String iugr) {
		this.iugr = iugr;
	}

	public String getMono_twins() {
		return mono_twins;
	}

	public void setMono_twins(String mono_twins) {
		this.mono_twins = mono_twins;
	}

	public String getFeed_intolerance() {
		return feed_intolerance;
	}

	public void setFeed_intolerance(String feed_intolerance) {
		this.feed_intolerance = feed_intolerance;
	}

}
