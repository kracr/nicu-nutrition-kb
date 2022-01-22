package com.inicu.models;

import java.sql.Date;
import java.util.List;

import com.inicu.postgres.entities.NursingVitalparameter;

public class NursingVitalPrintJSON {

	List<NursingVitalparameter> vitalList;

	Long fromTime;

	Long toTime;

	Long dateofAdmission;

	public List<NursingVitalparameter> getVitalList() {
		return vitalList;
	}

	public void setVitalList(List<NursingVitalparameter> vitalList) {
		this.vitalList = vitalList;
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
		return "NursingVitalPrintJSON [vitalList=" + vitalList + ", fromTime=" + fromTime + ", toTime=" + toTime
				+ ", dateofAdmission=" + dateofAdmission + "]";
	}

}
