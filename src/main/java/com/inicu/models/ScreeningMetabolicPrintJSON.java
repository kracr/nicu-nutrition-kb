package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenMetabolic;

public class ScreeningMetabolicPrintJSON {

	List<ScreenMetabolic> metabolicList;

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

	public List<ScreenMetabolic> getMetabolicList() {
		return metabolicList;
	}

	public void setMetabolicList(List<ScreenMetabolic> metabolicList) {
		this.metabolicList = metabolicList;
	}

	@Override
	public String toString() {
		return "ScreeningUSGPrintJSON [metabolicList=" + metabolicList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
