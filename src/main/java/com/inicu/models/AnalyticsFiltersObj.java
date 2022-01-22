package com.inicu.models;

public class AnalyticsFiltersObj {
	private Integer weightFrom;
	private Integer weightTo;
	private Integer gestationFrom;
	private Integer gestationTo;
	private String dischargeTypeList;
	private String assessmentTypeList;
	public Integer getWeightFrom() {
		return weightFrom;
	}
	public void setWeightFrom(Integer weightFrom) {
		this.weightFrom = weightFrom;
	}
	public Integer getWeightTo() {
		return weightTo;
	}
	public void setWeightTo(Integer weightTo) {
		this.weightTo = weightTo;
	}
	public Integer getGestationFrom() {
		return gestationFrom;
	}
	public void setGestationFrom(Integer gestationFrom) {
		this.gestationFrom = gestationFrom;
	}
	public Integer getGestationTo() {
		return gestationTo;
	}
	public void setGestationTo(Integer gestationTo) {
		this.gestationTo = gestationTo;
	}
	public String getDischargeTypeList() {
		return dischargeTypeList;
	}
	public void setDischargeTypeList(String dischargeTypeList) {
		this.dischargeTypeList = dischargeTypeList;
	}
	public String getAssessmentTypeList() {
		return assessmentTypeList;
	}
	public void setAssessmentTypeList(String assessmentTypeList) {
		this.assessmentTypeList = assessmentTypeList;
	}
	
}
