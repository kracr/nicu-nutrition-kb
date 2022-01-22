package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.BabyVisit;

public class AnthropometryPrintJSON {
	
	List<BabyVisit> visitList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

	public List<BabyVisit> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<BabyVisit> visitList) {
		this.visitList = visitList;
	}

	public Long getFromTime() {
		return fromTime;
	}

	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}

	public Long getToTime() {
		return toTime;
	}

	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}

	public Long getDateofAdmission() {
		return dateofAdmission;
	}

	public void setDateofAdmission(Long dateofAdmission) {
		this.dateofAdmission = dateofAdmission;
	}

	@Override
	public String toString() {
		return "NursingVitalPrintJSON [vitalList=" + visitList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
