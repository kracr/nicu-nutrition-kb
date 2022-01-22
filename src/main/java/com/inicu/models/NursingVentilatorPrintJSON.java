package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NursingVentilaor;

public class NursingVentilatorPrintJSON {

	List<NursingVentilaor> ventilatorList;

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

	public List<NursingVentilaor> getVentilatorList() {
		return ventilatorList;
	}

	public void setVentilatorList(List<NursingVentilaor> ventilatorList) {
		this.ventilatorList = ventilatorList;
	}

	@Override
	public String toString() {
		return "NursingVentilatorPrintJSON [ventilatorList=" + ventilatorList + ", fromTime=" + fromTime + ", toTime="
				+ toTime + ", dateofAdmission=" + dateofAdmission + "]";
	}

}
