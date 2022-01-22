package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenNeurological;

public class ScreeningNeurologicalPrintJSON {

	List<ScreenNeurological> neurologicalList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

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

	public List<ScreenNeurological> getNeurologicalList() {
		return neurologicalList;
	}

	public void setNeurologicalList(List<ScreenNeurological> neurologicalList) {
		this.neurologicalList = neurologicalList;
	}

	@Override
	public String toString() {
		return "ScreeningNeurologicalPrintJSON [neurologicalList=" + neurologicalList + ", fromTime=" + fromTime
				+ ", toTime=" + toTime + ", dateofAdmission=" + dateofAdmission + "]";
	}

}
