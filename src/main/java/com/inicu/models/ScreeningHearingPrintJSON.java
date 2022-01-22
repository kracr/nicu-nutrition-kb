package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.ScreenHearing;

public class ScreeningHearingPrintJSON {

	List<ScreenHearing> hearingList;

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

	public List<ScreenHearing> getHearingList() {
		return hearingList;
	}

	public void setHearingList(List<ScreenHearing> hearingList) {
		this.hearingList = hearingList;
	}

	@Override
	public String toString() {
		return "ScreeningHearingPrintJSON [hearingList=" + hearingList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
