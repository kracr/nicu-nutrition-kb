package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenRop;

public class ScreeningRopPrintJSON {

	List<ScreenRop> ropList;

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

	public List<ScreenRop> getRopList() {
		return ropList;
	}

	public void setRopList(List<ScreenRop> ropList) {
		this.ropList = ropList;
	}

	@Override
	public String toString() {
		return "ScreeningRopPrintJSON [ropList=" + ropList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
