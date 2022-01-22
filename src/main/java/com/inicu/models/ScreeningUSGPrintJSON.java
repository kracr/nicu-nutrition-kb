package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenUSG;

public class ScreeningUSGPrintJSON {

	List<ScreenUSG> usgList;

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

	public List<ScreenUSG> getUsgList() {
		return usgList;
	}

	public void setUsgList(List<ScreenUSG> usgList) {
		this.usgList = usgList;
	}

	@Override
	public String toString() {
		return "ScreeningUSGPrintJSON [usgList=" + usgList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
